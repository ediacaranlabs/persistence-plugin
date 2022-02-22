package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin.getEntity;

import java.util.concurrent.Callable;

import javax.persistence.TransactionRequiredException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.uoutec.community.ediacaran.core.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.core.persistence.test.TransactionCaller;
import br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParameterTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParametersTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigTest;
import br.com.uoutec.community.ediacaran.test.EdiacaranTestRunner;
import br.com.uoutec.community.ediacaran.test.PluginContext;

@RunWith(EdiacaranTestRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/jtatransactiontest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_config_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/jtatransactiontest/config"),
	@ApplicationConfigParameterTest(paramName="security_plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/jtatransactiontest/config"),
	@ApplicationConfigParameterTest(paramName="app_policy_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/jtatransactiontest/run.policy"),
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/jtatransactiontest/plugins"),
	@ApplicationConfigParameterTest(paramName="default", paramValue="classpath:META-INF/ediacaran-test.properties"),
	@ApplicationConfigParameterTest(paramName="logger", paramValue="classpath:META-INF/log4j.configuration"),
	@ApplicationConfigParameterTest(paramName="path", paramValue="../../../ediacaran-base-test")
})
public class JTATransactionTest {

	@Test
	@PluginContext("persistence")
	public void simpleTransactionTest(LanguageRegistry languageRegistry) throws Exception {

		getEntity(TransactionCaller.class)
		.call(new Callable<Object>() {
	            public Object call() throws Exception {
	            		clearData();
		                return null;
	            }
        });
		

		getEntity(TransactionCaller.class)
			.call(new Callable<Object>() {
		            public Object call() throws Exception {
			            	DataLoaderHelper.registerLangPt();
			            	DataLoaderHelper.registerLangEn();
			                return null;
		            }
	        });
		
		Language lang = languageRegistry.getLanguageByIso6391("pt");
		
		Assert.assertNotNull(lang);
		Assert.assertEquals("pt", lang.getIso6391());
		
	}

	
	@Test
	@PluginContext("persistence")
	public void requireTransactionTest() throws Throwable {
		try {
			//clearData();
			DataLoaderHelper.registerLangPt();
			Assert.fail("expected javax.persistence.TransactionRequiredException(\"no transaction is in progress\")");
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
