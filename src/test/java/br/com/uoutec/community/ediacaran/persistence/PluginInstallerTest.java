package br.com.uoutec.community.ediacaran.persistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.uoutec.community.ediacaran.core.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.core.persistence.registry.CountryRegistry;
import br.com.uoutec.community.ediacaran.core.persistence.registry.CountryRegistryException;
import br.com.uoutec.community.ediacaran.core.persistence.registry.LanguageRegistryException;
import br.com.uoutec.community.ediacaran.core.persistence.registry.RegionRegistryException;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParameterTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParametersTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigTest;
import br.com.uoutec.community.ediacaran.test.EdiacaranTestRunner;
import br.com.uoutec.community.ediacaran.test.PluginContext;

@RunWith(EdiacaranTestRunner.class)
@ApplicationConfigTest("ediacaran/test/countryregistrytest/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="plugins_config_path", paramValue="ediacaran/test/countryregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="security_plugins_path", paramValue="ediacaran/test/countryregistrytest/config"),
	@ApplicationConfigParameterTest(paramName="app_policy_path", paramValue="ediacaran/test/countryregistrytest/run.policy"),
	@ApplicationConfigParameterTest(paramName="plugins_path", paramValue="ediacaran/test/countryregistrytest/plugins"),
	@ApplicationConfigParameterTest(paramName="default", paramValue="classpath:META-INF/ediacaran-test.properties"),
	@ApplicationConfigParameterTest(paramName="logger", paramValue="classpath:META-INF/log4j.configuration"),
	@ApplicationConfigParameterTest(paramName="path", paramValue="../../../ediacaran-base-test")
})
public class PluginInstallerTest {

	/*
	@Before
	public void setup() {
		TestDataLoader.clearData();
	}
	*/
	
	@Test
	@PluginContext("persistence")
	public void test(CountryRegistry countryRegistry) throws CountryRegistryException, ClassNotFoundException, LanguageRegistryException, RegionRegistryException {
		DataLoaderHelper.loadData();
		Country c = countryRegistry.getCountry(1);
	}
	
}
