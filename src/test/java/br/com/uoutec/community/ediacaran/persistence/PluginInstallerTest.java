package br.com.uoutec.community.ediacaran.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.uoutec.community.ediacaran.core.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.core.persistence.registry.CountryRegistry;
import br.com.uoutec.community.ediacaran.core.persistence.registry.CountryRegistryException;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParameterTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigParametersTest;
import br.com.uoutec.community.ediacaran.test.ApplicationConfigTest;
import br.com.uoutec.community.ediacaran.test.EdiacaranTestRunner;
import br.com.uoutec.community.ediacaran.test.PluginContext;

@RunWith(EdiacaranTestRunner.class)
@ApplicationConfigTest("ediacaran/config/ediacaran-config.xml")
@ApplicationConfigParametersTest({
	@ApplicationConfigParameterTest(paramName="default", paramValue="classpath:META-INF/ediacaran-test.properties"),
	@ApplicationConfigParameterTest(paramName="logger", paramValue="classpath:META-INF/log4j.configuration"),
	@ApplicationConfigParameterTest(paramName="path", paramValue="ediacaran")
})
public class PluginInstallerTest {

	@Test
	@PluginContext("persistence")
	public void test(CountryRegistry countryRegistry) throws CountryRegistryException, ClassNotFoundException {
		Country c = countryRegistry.getCountry(1);
	}
	
}
