package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.loadData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerCountryBrasilPtLang;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerCountryEstadosUnidosPtLang;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangPt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoNortePt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoSulPt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.community.ediacaran.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.registry.CountryRegistry;
import br.com.uoutec.community.ediacaran.persistence.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.persistence.registry.RegionRegistry;
import br.com.uoutec.community.ediacaran.system.cdi.ActiveRequestContext;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParameterTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParametersTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigTest;
import br.com.uoutec.ediacaran.junit.PluginContext;
import br.com.uoutec.ediacaran.junit.junit5.EdiacaranExt;

@ExtendWith(EdiacaranExt.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/plugins"),
})
@PluginContext("persistence")
public class CountryRegistryTest {

	@BeforeEach
	@ActiveRequestContext
	public void beforeTest() {
		clearData();
	}
	
	@Test
	@ActiveRequestContext
	public void testRegister(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		registerLangPt();
		registerRegionAmericaDoSulPt();
		registerCountryBrasilPtLang();
		
		Country country = countryRegistry.getCountry(1);

		assertNotNull(country);
		assertEquals(1, country.getId());
		assertEquals("Brasil", country.getName());
		assertEquals(0, country.getUfi());
		assertEquals(1, country.getUni());
		assertEquals("BR", country.getIsoAlpha2());
		assertEquals("BRA", country.getIsoAlpha3());
		assertEquals(".com.br", country.getTld());
		assertEquals("BRL", country.getIso4217());
		assertEquals(1, country.getLanguage().getId());
		assertEquals(1, country.getRegion().getId());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByID(
			CountryRegistry countryRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountry(1);

		assertNotNull(country);
		assertEquals(1, country.getId());
		assertEquals("Brasil", country.getName());
		assertEquals(0, country.getUfi());
		assertEquals(1, country.getUni());
		assertEquals("BR", country.getIsoAlpha2());
		assertEquals("BRA", country.getIsoAlpha3());
		assertEquals(".com.br", country.getTld());
		assertEquals("BRL", country.getIso4217());
		assertEquals(1, country.getLanguage().getId());
		assertEquals(1, country.getRegion().getId());

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
		
		assertNotNull(country);
		assertEquals("Brazil", engCountry.getName());
		assertEquals(1, engCountry.getUfi());
		assertEquals("BR", engCountry.getIsoAlpha2());
		assertEquals("BRA", engCountry.getIsoAlpha3());
		assertEquals(".com.br", engCountry.getTld());
		assertEquals("BRL", engCountry.getIso4217());

	}

	@Test
	@PluginContext("persistence")
	public void testRemove(
			CountryRegistry countryRegistry,
			LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountry(1);
		assertNotNull(country);
		assertEquals(1, country.getId());
		
		countryRegistry.removeCountry(country);

		country = countryRegistry.getCountry(1);
		assertNull(country);
		
		
	}

	@Test
	@PluginContext("persistence")
	public void testFindByIsoAlpha3(
			CountryRegistry countryRegistry,
			LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountryByIsoAlpha3("BRA");
		
		assertNotNull(country);
		assertEquals(1, country.getId());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByIsoAlpha2(
			CountryRegistry countryRegistry,
			LanguageRegistry languageRegistry) throws Throwable {
		
		loadData();
		
		Country country = countryRegistry.getCountryByIsoAlpha2("BR");
		
		assertNotNull(country);
		assertEquals(1, country.getId());

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
		
		assertNotNull(list);
		assertEquals(2, list.size());

		assertEquals(1, list.get(0).getId());
		assertEquals(2, list.get(1).getId());
	}

	@Test
	@PluginContext("persistence")
	public void testGetAllLanguage(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		loadData();

		Language lang = languageRegistry.getLanguageByIso6391("pt");
		List<Country> list = countryRegistry.getAll(lang);
		
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("Brasil", list.get(0).getName());
	}

	@Test
	@PluginContext("persistence")
	public void testGetAllLocale(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		loadData();

		List<Country> list = countryRegistry.getAll(Locale.US);
		
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("Brazil", list.get(0).getName());
	}
	
}
