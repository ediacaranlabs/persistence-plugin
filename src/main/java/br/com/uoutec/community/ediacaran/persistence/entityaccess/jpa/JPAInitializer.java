package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import br.com.uoutec.community.ediacaran.plugins.PluginType;

@Singleton
public class JPAInitializer {

	@Inject
	private JPAInitializerGlobal jpaig;
	
	public void close(@Disposes EntityManager entityManager) {
		jpaig.close(entityManager);
	}

	@Produces
	@Singleton
	public EntityManager createSessionFactory(PluginType pluginData) throws Throwable {
		return jpaig.createSessionFactory(pluginData);
	}

}
