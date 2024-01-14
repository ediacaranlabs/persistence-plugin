package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.loadData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangPt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.application.junit.JunitRunner;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.registry.LanguageRegistry;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParameterTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParametersTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigTest;
import br.com.uoutec.ediacaran.junit.PluginContext;

@ExtendWith(JunitRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/langregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/langregistrytest/plugins"),
})
@PluginContext("persistence")
public class LangRegistryTest {

	@BeforeEach
	public void beforeTest() {
		clearData();
	}
	
	@Test
	public void testRegister(LanguageRegistry languageRegistry) throws Throwable {
		
		registerLangPt();
		
		Language lang = languageRegistry.getLanguage(1);

		assertNotNull(lang);
		assertEquals(1, lang.getId());
		assertEquals("Português", lang.getName());
		assertEquals("Portuguese", lang.getIsoName());
		assertEquals("pt", lang.getIso6391());
		assertEquals("por", lang.getIso6392t());

	}

	@Test
	public void testFindByID(LanguageRegistry languageRegistry) throws Throwable {
		
		registerLangPt();
		registerLangEn();
		
		Language lang = languageRegistry.getLanguage(1);

		assertNotNull(lang);
		assertEquals(1, lang.getId());
		assertEquals("Português", lang.getName());
		assertEquals("Portuguese", lang.getIsoName());
		assertEquals("pt", lang.getIso6391());
		assertEquals("por", lang.getIso6392t());

	}

	@Test
	public void testRemove(LanguageRegistry languageRegistry) throws Throwable {
		
		registerLangPt();
		registerLangEn();
		
		Language lang = languageRegistry.getLanguage(1);

		assertNotNull(lang);
		assertEquals(1, lang.getId());
		
		languageRegistry.removeLanguage(lang);

		lang = languageRegistry.getLanguage(1);
		assertNull(lang);
		
	}

	@Test
	public void testFindByIso6391(LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Language lang = languageRegistry.getLanguageByIso6391("pt");
		
		assertNotNull(lang);
		assertEquals("pt", lang.getIso6391());

	}

	@Test
	public void testFindByIso6392t(LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Language lang = languageRegistry.getLanguageByIso6392t("eng");
		
		assertNotNull(lang);
		assertEquals("eng", lang.getIso6392t());

	}

	@Test
	public void testGetAll(LanguageRegistry languageRegistry) throws Throwable {
		
		registerLangPt();
		registerLangEn();
		
		List<Language> list = languageRegistry.getAll();
		
		assertNotNull(list);
		assertEquals(2, list.size());

		assertEquals("pt", list.get(0).getIso6391());
		assertEquals("en", list.get(1).getIso6391());
	}
	
}
