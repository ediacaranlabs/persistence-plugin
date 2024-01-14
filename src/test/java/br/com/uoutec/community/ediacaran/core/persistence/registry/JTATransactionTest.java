package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin.getEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.Callable;

import javax.persistence.TransactionRequiredException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.application.junit.JunitRunner;
import br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.persistence.test.TransactionCaller;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParameterTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParametersTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigTest;
import br.com.uoutec.ediacaran.junit.PluginContext;

@ExtendWith(JunitRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/jtatransactiontest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/jtatransactiontest/plugins"),
})
@PluginContext("persistence")
public class JTATransactionTest {

	@Test
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
		
		assertNotNull(lang);
		assertEquals("pt", lang.getIso6391());
		
	}

	
	@Test
	public void requireTransactionTest() throws Throwable {
		try {
			//clearData();
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
