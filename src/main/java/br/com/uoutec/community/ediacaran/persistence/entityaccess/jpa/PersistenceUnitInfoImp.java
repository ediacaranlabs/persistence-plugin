package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import br.com.uoutec.community.ediacaran.persistence.Constants;
import br.com.uoutec.community.ediacaran.plugins.PluginConfiguration;
import br.com.uoutec.community.ediacaran.plugins.PluginException;

public class PersistenceUnitInfoImp implements PersistenceUnitInfo {

	private PluginConfiguration config;

	private String unitName;
	
	private DataSource jtaDataSource;
	
	private DataSource dataSource;
	
	private List<String> managedClassNames;
	
	public PersistenceUnitInfoImp(PluginConfiguration config, 
			String unitName, List<String> managedClassNames, 
			DataSource jtaDataSource, DataSource dataSource) {
		this.config = config;
		this.unitName = unitName;
		this.jtaDataSource = jtaDataSource;
		this.dataSource = dataSource;
		this.managedClassNames = managedClassNames;
	}
	
	@Override
	public String getPersistenceUnitName() {
		return unitName;
	}

	@Override
	public String getPersistenceProviderClassName() {
		return config.getString(Constants.PERSISTENCE_PROVIDER_CLASS_NAME);
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		return PersistenceUnitTransactionType.valueOf(config.getString(Constants.TRANSACTION_TYPE));
	}

	@Override
	public DataSource getJtaDataSource() {
		return jtaDataSource;
	}

	@Override
	public DataSource getNonJtaDataSource() {
		return dataSource;
	}

	@Override
	public List<String> getMappingFileNames() {
		return null;
	}

	@Override
	public List<URL> getJarFileUrls() {
		return Collections.emptyList();
	}

	@Override
	public URL getPersistenceUnitRootUrl() {
		return null;
	}

	@Override
	public List<String> getManagedClassNames() {
		return managedClassNames;
	}

	@Override
	public boolean excludeUnlistedClasses() {
		return false;
	}

	@Override
	public SharedCacheMode getSharedCacheMode() {
		return SharedCacheMode.valueOf(config.getString(Constants.SHARED_CACHE_MODE));
	}

	@Override
	public ValidationMode getValidationMode() {
		return ValidationMode.valueOf(config.getString(Constants.VALIDATION_MODE));
	}

	@Override
	public Properties getProperties() {
		
		String properties = config.getString(Constants.PROPERTIES);
		
		if(properties.trim().length() == 0) {
			return new Properties();
		}
		
		Properties p = new Properties();
		
		try {
			p.load(new ByteArrayInputStream(properties.getBytes("UTF-8")));
		}
		catch (IOException e) {
			throw new PluginException(e);
		}
		
		return p;
	}

	@Override
	public String getPersistenceXMLSchemaVersion() {
		return "2.1";
	}

	@Override
	public ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	@Override
	public void addTransformer(ClassTransformer transformer) {
	}

	@Override
	public ClassLoader getNewTempClassLoader() {
		return null;
	}

}
