package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin.getEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.Callable;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.persistence.TransactionRequiredException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.persistence.test.TransactionCaller;
import br.com.uoutec.community.ediacaran.test.mock.PluginsLoaderMock;
import br.com.uoutec.community.ediacaran.test.mock.SecurityPolicyManagerMock;
import br.com.uoutec.ediacaran.core.EdiacaranBootstrap;
import br.com.uoutec.ediacaran.core.ResourceBuilder;
import br.com.uoutec.ediacaran.junit.PluginContext;
import br.com.uoutec.ediacaran.junit.junit5.EdiacaranExt;
import br.com.uoutec.ediacaran.web.tomcat.TomcatClassLoaderFactoryBuilder;
import br.com.uoutec.ediacaran.weld.tomcat.TomcatServerBootstrapBuilder;

@ExtendWith(EdiacaranExt.class)
@PluginContext("persistence")
public class JTATransactionTest {

	public EdiacaranBootstrap getEdiacaranBootstrap() {
		return TomcatServerBootstrapBuilder.builder()
				.withPluginClassLoaderFactory(TomcatClassLoaderFactoryBuilder.builder()
						.withIncludePluginClassPathMatch("entity_filter", ".*/invoker-filter.*")
						.withIncludePluginClassPathMatch("entity_filter", ".*/filter.*")
				.build())
				.withSecurityPolicyManager(SecurityPolicyManagerMock.builder()
					.withLoadAllPermissions(true)
				.build())
				.withPluginLoader(PluginsLoaderMock.builder()
						.withLoadAllPlugins(true)
						.withProperty("persistence", "jta_data_source", "java:comp/env/ds/database")
						.withProperty("persistence", "transaction_type", "JTA")
						.withProperty("persistence", "properties", 
							new StringBuilder()
								.append("hibernate.dialect=org.hibernate.dialect.HSQLDialect\n")
								.append("hibernate.hbm2ddl.auto=update\n")
							.toString()
						)
				.build())
				.withSystemProperty("java.naming.factory.url.pkgs", "org.apache.naming")
				.withSystemProperty("java.naming.factory.initial", "org.apache.naming.java.javaURLContextFactory")
				.withResource(ResourceBuilder.builder()
						.withName("java:comp/env/ds/database")
						.withType(org.apache.tomcat.jdbc.pool.DataSource.class.getName())
						.withProperty("driverClassName", "com.arjuna.ats.jdbc.TransactionalDriver")
						.withProperty("url", "jdbc:arjuna:java:comp/env/ds/direct_database")
				.build())
				.withResource(ResourceBuilder.builder()
						.withName("java:comp/env/TransactionSynchronizationRegistry")
						.withFactory("com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionSynchronizationRegistryImple")
				.build())
				.withResource(ResourceBuilder.builder()
						.withName("java:comp/env/TransactionManager")
						.withFactory("com.arjuna.ats.internal.jta.transaction.arjunacore.TransactionManagerImple")
				.build())
				.withResource(ResourceBuilder.builder()
						.withName("java:comp/UserTransaction")
						.withFactory("com.arjuna.ats.internal.jta.transaction.arjunacore.UserTransactionImple")
				.build())
				.withResource(ResourceBuilder.builder()
						.withName("java:comp/env/ds/direct_database")
						.withType(JDBCXADataSourceWrapper.class.getName())
						.withProperty("url", "jdbc:hsqldb:mem:testdb")
						.withProperty("user", "sa")
						.withProperty("password", "")
				.build())				
		.build();
	}
	
	@Test
	@ActivateRequestContext
	public void simpleTransactionTest(LanguageRegistry languageRegistry) throws Exception {

		DataLoaderHelper.transactionalClearData();
		
		getEntity(TransactionCaller.class)
			.call(new Callable<Object>() {
		            public Object call() throws Exception {
			            	DataLoaderHelper.registerLangPt();
			            	DataLoaderHelper.registerLangEn();
			                return null;
		            }
	        });
		
		Language lang = languageRegistry.getLanguageByIso6391("pt");
		
		assertNotNull(lang);
		assertEquals("pt", lang.getIso6391());
		
	}

	@Test
	@ActivateRequestContext
	public void rollbackTransactionTest(LanguageRegistry languageRegistry) throws Exception {

		DataLoaderHelper.transactionalClearData();
		
		try {
			getEntity(TransactionCaller.class)
				.call(new Callable<Object>() {
			            public Object call() throws Exception {
				            	DataLoaderHelper.registerLangPt();
				            	throw new RuntimeException();
			            }
		        });
		}
		catch(Throwable ex) {
		}
		
		Language lang = languageRegistry.getLanguageByIso6391("pt");
		
		assertNull(lang);
		
	}
	
	
	@Test
	@ActivateRequestContext
	public void requireTransactionTest() throws Throwable {
		try {
			DataLoaderHelper.registerLangPt();
			fail("expected javax.persistence.TransactionRequiredException(\"no transaction is in progress\")");
		}
		catch(Throwable e) {
			while(e != null && !(e instanceof TransactionRequiredException)) {
				e = e.getCause();
			}
			if(!(e instanceof TransactionRequiredException && "no transaction is in progress".equals(e.getMessage()))) {
				throw e;
			}
		}
		
	}
	
}
