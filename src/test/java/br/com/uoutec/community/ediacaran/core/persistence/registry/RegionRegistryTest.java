package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.loadData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangPt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoNorteEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoNortePt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoSulEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoSulPt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import br.com.uoutec.application.junit.JunitRunner;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entity.Region;
import br.com.uoutec.community.ediacaran.persistence.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.persistence.registry.RegionRegistry;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParameterTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigParametersTest;
import br.com.uoutec.ediacaran.junit.ApplicationConfigTest;
import br.com.uoutec.ediacaran.junit.PluginContext;

@ExtendWith(JunitRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_config_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="security_plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="app_policy_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/run.policy"),
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/plugins"),
})
@PluginContext("persistence")
public class RegionRegistryTest {

	@BeforeEach
	public void beforeTest() {
		clearData();
	}
	
	@Test
	public void testRegister(RegionRegistry regionRegistry) throws Throwable {
		
		registerLangPt();
		registerRegionAmericaDoSulPt();
		
		Region region = regionRegistry.getRegion(1);

		assertNotNull(region);
		assertEquals(1, region.getId());
		assertEquals("América do Sul", region.getName());
		assertEquals(0, region.getParent());
		assertEquals(1, region.getLanguage().getId());

	}

	@Test
	public void testFindByID(RegionRegistry regionRegistry) throws Throwable {
		
		loadData();
		
		Region region = regionRegistry.getRegion(1);

		assertNotNull(region);
		assertEquals(1, region.getId());
		assertEquals("América do Sul", region.getName());
		assertEquals(0, region.getParent());
		assertEquals(1, region.getLanguage().getId());

	}

	@Test
	public void testRemove(RegionRegistry regionRegistry) throws Throwable {
		
		loadData();
		
		Region region = regionRegistry.getRegion(1);
		assertNotNull(region);
		assertEquals(1, region.getId());
		
		regionRegistry.removeRegion(region);

		region = regionRegistry.getRegion(1);
		assertNull(region);
		
		
	}

	@Test
	public void testGetAll(RegionRegistry regionRegistry) throws Throwable {
		
		registerLangPt();
		registerRegionAmericaDoSulPt();
		registerRegionAmericaDoNortePt();
		
		List<Region> list = regionRegistry.getAll();
		
		assertNotNull(list);
		assertEquals(2, list.size());

		assertEquals("América do Sul", list.get(0).getName());
		assertEquals("América do Norte", list.get(1).getName());
	}

	@Test
	public void testGetAllLanguage(LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		registerLangPt();
		registerLangEn();
		registerRegionAmericaDoSulPt();
		registerRegionAmericaDoSulEn();
		registerRegionAmericaDoNortePt();
		registerRegionAmericaDoNorteEn();
		
		Language lang = languageRegistry.getLanguageByIso6391("pt");
		List<Region> list = regionRegistry.getAll(lang);
		
		assertNotNull(list);
		assertEquals(2, list.size());
		assertEquals("América do Sul", list.get(0).getName());
		assertEquals("América do Norte", list.get(1).getName());
	}

	@Test
	public void testGetAllLocale(LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		registerLangPt();
		registerLangEn();
		registerRegionAmericaDoNortePt();
		registerRegionAmericaDoNorteEn();

		List<Region> list = regionRegistry.getAll(Locale.US);
		
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals("North America", list.get(0).getName());
	}
	
}
