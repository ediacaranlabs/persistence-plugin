package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityListeners;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.application.scanner.filter.AnnotationTypeFilter;
import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.community.ediacaran.persistence.Constants;
import br.com.uoutec.community.ediacaran.persistence.SecurityEntitylListener;
import br.com.uoutec.ediacaran.core.EdiacaranScanner;
import br.com.uoutec.ediacaran.core.ResourceRegistry;
import br.com.uoutec.ediacaran.core.VarParser;
import br.com.uoutec.ediacaran.core.plugins.PluginType;

@Singleton
public class JPAInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(JPAInitializer.class);
	
	private PluginType pluginData;
	
	private VarParser varParser;
	
	private EntityManagerFactory emf;

	private ResourceRegistry resourceRegistry;
	
	@Inject
	public JPAInitializer(PluginType pluginData,VarParser varParser, ResourceRegistry resourceRegistry) {
		this.pluginData = pluginData;
		this.varParser = varParser;
		this.resourceRegistry = resourceRegistry;
	}
	
	public void close(@Disposes EntityManager entityManager) {
		entityManager.close();
		if(logger.isTraceEnabled()) {
			logger.trace("Entity manager closed: {}" , entityManager);
		}
	}

	@Produces
	@RequestScoped
	public EntityManager createSessionFactory() throws Throwable {
		EntityManager em = createSessionFactory0();
		
		return (EntityManager) Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class<?>[] {EntityManager.class},
				(InvocationHandler)(proxy, method, args)->{
					
					return ContextSystemSecurityCheck.doPrivileged(()->{
						return executeAction(em, method, args);
					});
					
				}
		);
		
	}
	
	private Object executeAction(EntityManager em, Method method, Object[] args
			) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(
						"persistence.context." +  method.getName().toLowerCase()
				)
		);
		
		return method.invoke(em, args);
	}
	
	public EntityManager createSessionFactory0() throws Throwable {

		if(emf != null) {
			return emf.createEntityManager();
		}
		
		if(logger.isTraceEnabled()) {
			logger.trace("Creating new session factory...");
		}

		Object jtaDataSource = null;
		Object dataSource = null;
		
		String jtaDataSourceName = 
				varParser.getValue(
						String.format("${plugins.%s.%s.%s}", Constants.PROVIDER, Constants.PLUGIN, Constants.JTA_DATA_SOURCE)
				);
		
		if(jtaDataSourceName != null && jtaDataSourceName.trim().length() != 0) {
			jtaDataSource = ContextSystemSecurityCheck.doPrivileged(()->resourceRegistry.lookup(jtaDataSourceName));
		}

		String dataSourceName = 
				varParser.getValue(
						String.format("${plugins.%s.%s.%s}", Constants.PROVIDER, Constants.PLUGIN, Constants.DATA_SOURCE)
				);
				
		if(dataSourceName != null && dataSourceName.trim().length() != 0) {
			dataSource = ContextSystemSecurityCheck.doPrivileged(()->resourceRegistry.lookup(dataSourceName));
		}
		
		AnnotationTypeFilter filter = new AnnotationTypeFilter();
		
		filter.setExpression(Arrays
				.asList("javax.persistence.Entity"));

		List<Package> packages = new ArrayList<Package>();
		packages.addAll(Arrays.asList(pluginData.getPackages()));
		packages.addAll(Arrays.asList(pluginData.getParentPackages()));
		
		String[] packageNames = new String[packages.size()];
		
		int i=0;
		for(Package p: packages) {
			packageNames[i++] = p.getName();
		}

		if(logger.isTraceEnabled()) {
			logger.trace("Packages: {}", packages);
			logger.trace("filter: {}", filter);
		}
		
		EdiacaranScanner s = new EdiacaranScanner();
		s.setBasePackage(packageNames);
		s.addIncludeFilter(filter);
		s.scan();
		List<Class<?>> list = s.getClassList();
		
		classList: for(Class<?> entityClass: list) {
		
			
			EntityListeners elAnnotation = 
					entityClass.getAnnotation(EntityListeners.class);
			
			if(elAnnotation == null) {
				throw new PersistenceException(
						"not found SecurityEntitylListener: " + entityClass.getName());
			}
			
			Class<?>[] listeners =  elAnnotation.value();
			
			for(Class<?> l: listeners) {
				if(SecurityEntitylListener.class.isAssignableFrom(l)) {
					continue classList;
				}
			}
			
			throw new PersistenceException("not found listener: " + SecurityEntitylListener.class.getName());
			
		}
		
		List<String> managedClass = list.stream().map(e->e.getName()).collect(Collectors.toList());
		
		if(logger.isTraceEnabled()) {
			for(String clazz: managedClass) {
				logger.trace("Found entity: {}", clazz);
			}
		}
		
		PersistenceUnitInfo pui = 
				new PersistenceUnitInfoImp(varParser, 
						"default-" + pluginData.getConfiguration().getMetadata().getCode(),
						managedClass, 
						(DataSource)jtaDataSource, 
						(DataSource)dataSource,
						s.getClassLoader());
		
		HibernatePersistenceProvider hibernatePersistenceProvider =
				new HibernatePersistenceProvider();
		
		this.emf = 
				ContextSystemSecurityCheck.doPrivileged(
						()->hibernatePersistenceProvider
							.createContainerEntityManagerFactory(pui, new HashMap<Object,Object>())
				);
				

		
		return emf.createEntityManager();
	}

}
