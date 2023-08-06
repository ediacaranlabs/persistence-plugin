package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.loadData;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerLangPt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoNorteEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoNortePt;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoSulEn;
import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.registerRegionAmericaDoSulPt;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entity.Region;
import br.com.uoutec.community.ediacaran.persistence.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.persistence.registry.RegionRegistry;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParameterTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParametersTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigTest;
import br.com.uoutec.community.ediacaran.test.EdiacaranTestRunner;
import br.com.uoutec.community.ediacaran.test.PluginContext;

@RunWith(EdiacaranTestRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_config_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="security_plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="app_policy_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/run.policy"),
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/regionregistrytest/plugins"),
})
public class RegionRegistryTest {

	@Test
	@PluginContext("persistence")
	public void testRegister(RegionRegistry regionRegistry) throws Throwable {
		
		clearData();
		registerLangPt();
		registerRegionAmericaDoSulPt();
		
		Region region = regionRegistry.getRegion(1);

		Assert.assertNotNull(region);
		Assert.assertEquals(1, region.getId());
		Assert.assertEquals("América do Sul", region.getName());
		Assert.assertEquals(0, region.getParent());
		Assert.assertEquals(1, region.getLanguage().getId());

	}

	@Test
	@PluginContext("persistence")
	public void testFindByID(RegionRegistry regionRegistry) throws Throwable {
		
		clearData();
		loadData();
		
		Region region = regionRegistry.getRegion(1);

		Assert.assertNotNull(region);
		Assert.assertEquals(1, region.getId());
		Assert.assertEquals("América do Sul", region.getName());
		Assert.assertEquals(0, region.getParent());
		Assert.assertEquals(1, region.getLanguage().getId());

	}

	@Test
	@PluginContext("persistence")
	public void testRemove(RegionRegistry regionRegistry) throws Throwable {
		
		clearData();
		loadData();
		
		Region region = regionRegistry.getRegion(1);
		Assert.assertNotNull(region);
		Assert.assertEquals(1, region.getId());
		
		regionRegistry.removeRegion(region);

		region = regionRegistry.getRegion(1);
		Assert.assertNull(region);
		
		
	}

	@Test
	@PluginContext("persistence")
	public void testGetAll(RegionRegistry regionRegistry) throws Throwable {
		
		clearData();
		
		registerLangPt();
		registerRegionAmericaDoSulPt();
		registerRegionAmericaDoNortePt();
		
		List<Region> list = regionRegistry.getAll();
		
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());

		Assert.assertEquals("América do Sul", list.get(0).getName());
		Assert.assertEquals("América do Norte", list.get(1).getName());
	}

	@Test
	@PluginContext("persistence")
	public void testGetAllLanguage(LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		clearData();
		registerLangPt();
		registerLangEn();
		registerRegionAmericaDoSulPt();
		registerRegionAmericaDoSulEn();
		registerRegionAmericaDoNortePt();
		registerRegionAmericaDoNorteEn();
		
		Language lang = languageRegistry.getLanguageByIso6391("pt");
		List<Region> list = regionRegistry.getAll(lang);
		
		Assert.assertNotNull(list);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("América do Sul", list.get(0).getName());
		Assert.assertEquals("América do Norte", list.get(1).getName());
	}

	@Test
	@PluginContext("persistence")
	public void testGetAllLocale(LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		clearData();
		registerLangPt();
		registerLangEn();
		registerRegionAmericaDoNortePt();
		registerRegionAmericaDoNorteEn();

		List<Region> list = regionRegistry.getAll(Locale.US);
		
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("North America", list.get(0).getName());
	}
	
}
