package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.uoutec.community.ediacaran.persistence.Constants;
import br.com.uoutec.community.ediacaran.plugins.PluginConfiguration;
import br.com.uoutec.community.ediacaran.plugins.PluginType;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;

@Singleton
public class JPAInitializerGlobal implements PublicBean{

	private static final Logger logger = LoggerFactory.getLogger(JPAInitializerGlobal.class);
	
	private EntityManager entityManager;
	
	@Inject
	private PluginType pluginConfig;
	
	public void close(EntityManager entityManager) {
		entityManager.close();
		if(logger.isTraceEnabled()) {
			logger.trace("Entity manager closed: {}" , entityManager);
		}
	}

	@SuppressWarnings("unchecked")
	public EntityManager createSessionFactory(PluginType pluginData) throws Throwable {

		if(entityManager != null) {
			return entityManager;
		}
		
		if(logger.isTraceEnabled()) {
			logger.trace("Creating new session factory...");
		}

		PluginConfiguration config = pluginConfig.getConfiguration();
		
		Object jtaDataSource = null;
		Object dataSource = null;
		
		String dataSourceName = config.getString(Constants.JTA_DATA_SOURCE);
		
		if(dataSourceName != null && dataSourceName.trim().length() != 0) {
			ResourceRegistry resourceRegistry = new ResourceRegistry();
			jtaDataSource = resourceRegistry.lookup(dataSourceName);
		}

		dataSourceName = config.getString(Constants.DATA_SOURCE);
		
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
				new PersistenceUnitInfoImp(config, 
						"default", 
						managedClass, 
						(DataSource)jtaDataSource, 
						(DataSource)dataSource);
		
		EntityManagerFactory emf = 
				new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(pui, new HashMap<Object,Object>());
		
		return emf.createEntityManager();
	}

}
