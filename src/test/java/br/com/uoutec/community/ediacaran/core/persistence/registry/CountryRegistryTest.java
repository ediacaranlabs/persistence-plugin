package br.com.uoutec.community.ediacaran.core.persistence.registry;

import static br.com.uoutec.community.ediacaran.persistence.DataLoaderHelper.clearData;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.uoutec.community.ediacaran.core.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.core.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.core.persistence.entity.Region;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParameterTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParametersTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigTest;
import br.com.uoutec.community.ediacaran.test.EdiacaranTestRunner;
import br.com.uoutec.community.ediacaran.test.PluginContext;

@RunWith(EdiacaranTestRunner.class)
@ApplicationConfigTest("ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_config_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="security_plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="app_policy_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/run.policy"),
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/countryregistrytest/plugins"),
	@ApplicationConfigParameterTest(paramName="default", paramValue="classpath:META-INF/ediacaran-test.properties"),
	@ApplicationConfigParameterTest(paramName="logger", paramValue="classpath:META-INF/log4j.configuration"),
	@ApplicationConfigParameterTest(paramName="path", paramValue="../../../ediacaran-base-test")
})
public class CountryRegistryTest {

	@Test
	@PluginContext("persistence")
	public void testRegister(
			CountryRegistry countryRegistry, LanguageRegistry languageRegistry, 
			RegionRegistry regionRegistry) throws Throwable {
		
		clearData();
		
		languageRegistry.registerLanguage(
				new Language(
						99, 
						"pt", 
						"por", 
						"Português", 
						"Portuguese"
				)
		);
		languageRegistry.flush();
		
		regionRegistry.registerRegion(
				new Region(
						99, 
						0, 
						"América do Sul", 
						new Language(
								1, 
								null, 
								null, 
								null, 
								null
						), 
						null
				)
		);
		
		regionRegistry.flush();
		
		countryRegistry.registerCountry(
				new Country(
						1, 
						"Brasil", 
						0, 
						1, 
						"BR", 
						"BRA", 
						".com.br", 
						"BRL", 
						new Language(
								1, 
								null, 
								null, 
								null, 
								null
						), 
						new Region(
								1, 
								0, 
								null, 
								null, 
								null
						)
				)
		);
		countryRegistry.flush();
		
		Country country = countryRegistry.getCountry(1);
		
		Assert.assertNotNull(country);
		Assert.assertEquals(1, country.getId());
		Assert.assertEquals("Brasil", country.getName());
		Assert.assertEquals(0, country.getUni());
		Assert.assertEquals(1, country.getUfi());
		Assert.assertEquals("BR", country.getIsoAlpha2());
		Assert.assertEquals("BRA", country.getIsoAlpha3());
		Assert.assertEquals(".com.br", country.getTld());
		Assert.assertEquals("BRL", country.getIso4217());
		Assert.assertEquals(1, country.getLanguage().getId());
		Assert.assertEquals(1, country.getRegion().getId());
	}
	
}
