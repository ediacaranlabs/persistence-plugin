package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.loadData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangPt;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.uoutec.community.ediacaran.core.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParameterTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParametersTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigTest;
import br.com.uoutec.community.ediacaran.test.EdiacaranTestRunner;
import br.com.uoutec.community.ediacaran.test.PluginContext;

@RunWith(EdiacaranTestRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/langregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_config_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/langregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="security_plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/langregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="app_policy_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/langregistrytest/run.policy"),
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/langregistrytest/plugins"),
	@ApplicationConfigParameterTest(paramName="default", paramValue="classpath:META-INF/ediacaran-test.properties"),
	@ApplicationConfigParameterTest(paramName="logger", paramValue="classpath:META-INF/log4j.configuration"),
	@ApplicationConfigParameterTest(paramName="path", paramValue="../../../ediacaran-base-test")
})
public class LangRegistryTest {

	@Test
	@PluginContext("persistence")
	public void testRegister(LanguageRegistry languageRegistry) throws Throwable {
		
		clearData();
		registerLangPt();
		
		Language lang = languageRegistry.getLanguage(1);

		Assert.assertNotNull(lang);
		Assert.assertEquals(1, lang.getId());
		Assert.assertEquals("Português", lang.getName());
		Assert.assertEquals("Portuguese", lang.getIsoName());
		Assert.assertEquals("pt", lang.getIso6391());
		Assert.assertEquals("por", lang.getIso6392t());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByID(LanguageRegistry languageRegistry) throws Throwable {
		
		clearData();
		registerLangPt();
		registerLangEn();
		
		Language lang = languageRegistry.getLanguage(1);

		Assert.assertNotNull(lang);
		Assert.assertEquals(1, lang.getId());
		Assert.assertEquals("Português", lang.getName());
		Assert.assertEquals("Portuguese", lang.getIsoName());
		Assert.assertEquals("pt", lang.getIso6391());
		Assert.assertEquals("por", lang.getIso6392t());

	}

	@Test
	@PluginContext("persistence")
	public void testRemove(LanguageRegistry languageRegistry) throws Throwable {
		
		clearData();
		registerLangPt();
		registerLangEn();
		
		Language lang = languageRegistry.getLanguage(1);

		Assert.assertNotNull(lang);
		Assert.assertEquals(1, lang.getId());
		
		languageRegistry.removeLanguage(lang);

		lang = languageRegistry.getLanguage(1);
		Assert.assertNull(lang);
		
	}

	@Test
	@PluginContext("persistence")
	public void testFindByIso6391(LanguageRegistry languageRegistry) throws Throwable {
		
		clearData();
		loadData();
		
		Language lang = languageRegistry.getLanguageByIso6391("pt");
		
		Assert.assertNotNull(lang);
		Assert.assertEquals("pt", lang.getIso6391());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByIso6392t(LanguageRegistry languageRegistry) throws Throwable {
		
		clearData();
		loadData();
		
		Language lang = languageRegistry.getLanguageByIso6392t("eng");
		
		Assert.assertNotNull(lang);
		Assert.assertEquals("eng", lang.getIso6392t());

	}

	@Test
	@PluginContext("persistence")
	public void testGetAll(LanguageRegistry languageRegistry) throws Throwable {
		
		clearData();
		registerLangPt();
		registerLangEn();
		
		List<Language> list = languageRegistry.getAll();
		
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());

		Assert.assertEquals("pt", list.get(0).getIso6391());
		Assert.assertEquals("en", list.get(1).getIso6391());
	}
	
}