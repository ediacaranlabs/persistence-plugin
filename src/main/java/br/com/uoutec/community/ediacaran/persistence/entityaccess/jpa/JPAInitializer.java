package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.brandao.brutos.annotation.scanner.DefaultScanner;
import org.brandao.brutos.annotation.scanner.filter.AnnotationTypeFilter;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.community.ediacaran.ResourceRegistry;
import br.com.uoutec.community.ediacaran.VarParser;
import br.com.uoutec.community.ediacaran.persistence.Constants;
import br.com.uoutec.community.ediacaran.plugins.PluginType;

@Singleton
public class JPAInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(JPAInitializer.class);
	
	@Inject
	private PluginType pluginData;
	
	@Inject
	private VarParser varParser;
	
	private EntityManagerFactory emf;
	
	public void close(@Disposes EntityManager entityManager) {
		entityManager.close();
		if(logger.isTraceEnabled()) {
			logger.trace("Entity manager closed: {}" , entityManager);
		}
	}

	@SuppressWarnings("unchecked")
	@Produces
	@RequestScoped
	public EntityManager createSessionFactory() throws Throwable {

		if(emf != null) {
			return emf.createEntityManager();
		}
		
		if(logger.isTraceEnabled()) {
			logger.trace("Creating new session factory...");
		}

		Object jtaDataSource = null;
		Object dataSource = null;
		
		String dataSourceName = 
				varParser.getValue(
						String.format("${plugins.%s.%s.%s}", Constants.PROVIDER, Constants.PLUGIN, Constants.JTA_DATA_SOURCE)
				);
		
		if(dataSourceName != null && dataSourceName.trim().length() != 0) {
			ResourceRegistry resourceRegistry = new ResourceRegistry();
			jtaDataSource = resourceRegistry.lookup(dataSourceName);
		}

		dataSourceName = 
				varParser.getValue(
						String.format("${plugins.%s.%s.%s}", Constants.PROVIDER, Constants.PLUGIN, Constants.DATA_SOURCE)
				);
				
		if(dataSourceName != null && dataSourceName.trim().length() != 0) {
			ResourceRegistry resourceRegistry = new ResourceRegistry();
			dataSource = resourceRegistry.lookup(dataSourceName);
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
		
		DefaultScanner s = new DefaultScanner();
		s.setBasePackage(packageNames);
		s.addIncludeFilter(filter);
		s.scan();
		List<Class<?>> list = s.getClassList();
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
						(DataSource)dataSource);
		
		this.emf = 
				new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(pui, new HashMap<Object,Object>());

		return emf.createEntityManager();
	}

}
