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
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/br/com/uoutec/community/ediacaran/core/persistence/registry/countryregistrytest/plugins"),
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
				Language.builder()
					.withIso6391("pt")
					.withIso6392t("por")
					.withName("Português")
					.withIsoName("Portuguese")
					.build()
		);
		languageRegistry.flush();
		
		regionRegistry.registerRegion(
				Region.builder()
					.withName("América do Sul")
					.withLanguage(Language.builder().withId(1).build())
					.build()
		);
		
		regionRegistry.flush();
		
		countryRegistry.registerCountry(
				Country.builder()
					.withName("Brasil")
					.withUni(1)
					.withIsoAlpha2("BR")
					.withIsoAlpha3("BRA")
					.withTld(".com.br")
					.withIso4217("BRL")
					.withLanguage(Language.builder().withId(1).build())
					.withRegion(Region.builder().withId(1).build())
					.build()
		);
		
		countryRegistry.flush();
		
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
	
}
