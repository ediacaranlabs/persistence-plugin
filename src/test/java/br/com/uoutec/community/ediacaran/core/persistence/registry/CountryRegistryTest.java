package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.loadData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerCountryBrasilPtLang;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerCountryEstadosUnidosPtLang;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangPt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoNortePt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoSulPt;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.uoutec.community.ediacaran.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.registry.CountryRegistry;
import br.com.uoutec.community.ediacaran.persistence.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.persistence.registry.RegionRegistry;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParameterTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParametersTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigTest;
import br.com.uoutec.community.ediacaran.test.PluginContext;
import br.com.uoutec.community.ediacaran.test.junit4.EdiacaranTestRunner;

@RunWith(EdiacaranTestRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_config_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="security_plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="app_policy_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/run.policy"),
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/plugins"),
})
public class CountryRegistryTest {

	@Before
	public void beforeTest() {
		clearData();
	}
	
	@Test
	@PluginContext("persistence")
	public void testRegister(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		registerLangPt();
		registerRegionAmericaDoSulPt();
		registerCountryBrasilPtLang();
		
		Country country = countryRegistry.getCountry(1);

		Assert.assertNotNull(country);
		Assert.assertEquals(1, country.getId());
		Assert.assertEquals("Brasil", country.getName());
		Assert.assertEquals(0, country.getUfi());
		Assert.assertEquals(1, country.getUni());
		Assert.assertEquals("BR", country.getIsoAlpha2());
		Assert.assertEquals("BRA", country.getIsoAlpha3());
		Assert.assertEquals(".com.br", country.getTld());
		Assert.assertEquals("BRL", country.getIso4217());
		Assert.assertEquals(1, country.getLanguage().getId());
		Assert.assertEquals(1, country.getRegion().getId());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByID(
			CountryRegistry countryRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountry(1);

		Assert.assertNotNull(country);
		Assert.assertEquals(1, country.getId());
		Assert.assertEquals("Brasil", country.getName());
		Assert.assertEquals(0, country.getUfi());
		Assert.assertEquals(1, country.getUni());
		Assert.assertEquals("BR", country.getIsoAlpha2());
		Assert.assertEquals("BRA", country.getIsoAlpha3());
		Assert.assertEquals(".com.br", country.getTld());
		Assert.assertEquals("BRL", country.getIso4217());
		Assert.assertEquals(1, country.getLanguage().getId());
		Assert.assertEquals(1, country.getRegion().getId());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByUFI(
			CountryRegistry countryRegistry,
			LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Language lang = languageRegistry.getLanguageByIso6391("en");
		
		Country country = countryRegistry.getCountry(1);
		Country engCountry = countryRegistry.getCountryByUFI(country.getUni(), lang);
		
		Assert.assertNotNull(country);
		Assert.assertEquals("Brazil", engCountry.getName());
		Assert.assertEquals(1, engCountry.getUfi());
		Assert.assertEquals("BR", engCountry.getIsoAlpha2());
		Assert.assertEquals("BRA", engCountry.getIsoAlpha3());
		Assert.assertEquals(".com.br", engCountry.getTld());
		Assert.assertEquals("BRL", engCountry.getIso4217());

	}

	@Test
	@PluginContext("persistence")
	public void testRemove(
			CountryRegistry countryRegistry,
			LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountry(1);
		Assert.assertNotNull(country);
		Assert.assertEquals(1, country.getId());
		
		countryRegistry.removeCountry(country);

		country = countryRegistry.getCountry(1);
		Assert.assertNull(country);
		
		
	}

	@Test
	@PluginContext("persistence")
	public void testFindByIsoAlpha3(
			CountryRegistry countryRegistry,
			LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountryByIsoAlpha3("BRA");
		
		Assert.assertNotNull(country);
		Assert.assertEquals(1, country.getId());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByIsoAlpha2(
			CountryRegistry countryRegistry,
			LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountryByIsoAlpha2("BR");
		
		Assert.assertNotNull(country);
		Assert.assertEquals(1, country.getId());

	}

	@Test
	@PluginContext("persistence")
	public void testGetAll(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		registerLangPt();
		registerRegionAmericaDoSulPt();
		registerRegionAmericaDoNortePt();
		registerCountryBrasilPtLang();
		registerCountryEstadosUnidosPtLang();
		
		List<Country> list = countryRegistry.getAll();
		
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());

		Assert.assertEquals(1, list.get(0).getId());
		Assert.assertEquals(2, list.get(1).getId());
	}

	@Test
	@PluginContext("persistence")
	public void testGetAllLanguage(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		loadData();

		Language lang = languageRegistry.getLanguageByIso6391("pt");
		List<Country> list = countryRegistry.getAll(lang);
		
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("Brasil", list.get(0).getName());
	}

	@Test
	@PluginContext("persistence")
	public void testGetAllLocale(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		loadData();

		List<Country> list = countryRegistry.getAll(Locale.US);
		
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("Brazil", list.get(0).getName());
	}
	
}
