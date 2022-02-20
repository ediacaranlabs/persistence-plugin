package br.com.uoutec.community.ediacaran.persistence;

import javax.persistence.EntityManager;

import br.com.uoutec.community.ediacaran.core.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.core.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.core.persistence.entity.Region;
import br.com.uoutec.community.ediacaran.core.persistence.registry.CountryRegistry;
import br.com.uoutec.community.ediacaran.core.persistence.registry.CountryRegistryException;
import br.com.uoutec.community.ediacaran.core.persistence.registry.LanguageRegistry;
import br.com.uoutec.community.ediacaran.core.persistence.registry.LanguageRegistryException;
import br.com.uoutec.community.ediacaran.core.persistence.registry.RegionRegistry;
import br.com.uoutec.community.ediacaran.core.persistence.registry.RegionRegistryException;
import br.com.uoutec.community.ediacaran.plugins.EntityContextPlugin;

public class DataLoaderHelper {

	public static void loadData() throws LanguageRegistryException, RegionRegistryException, CountryRegistryException {
		LanguageRegistry languageRegistry = EntityContextPlugin.getEntity(LanguageRegistry.class);
		languageRegistry.registerLanguage(new Language(0, "pt", "por", "Português", "Portuguese"));
		languageRegistry.registerLanguage(new Language(0, "en", "eng", "English", "English"));
		languageRegistry.flush();
		
		RegionRegistry regionRegistry = EntityContextPlugin.getEntity(RegionRegistry.class);
		regionRegistry.registerRegion(new Region(0, 0, "América do Sul", new Language(1, null, null, null, null), null));
		regionRegistry.registerRegion(new Region(0, 0, "South America", new Language(2, null, null, null, null), null));
		regionRegistry.flush();
		
		CountryRegistry countryRegistry = EntityContextPlugin.getEntity(CountryRegistry.class);
		countryRegistry.registerCountry(
				new Country(0, "Brasil", 1, 0, "BR", "BRA", ".com.br", "BRL", 
						new Language(1, null, null, null, null), 
						new Region(1, 0, null, null, null)
				)
		);
		countryRegistry.registerCountry(
				new Country(0, "Brazil", 2, 1, "BR", "BRA", ".com.br", "BRL", 
						new Language(2, null, null, null, null), 
						new Region(2, 0, null, null, null)
				)
		);
		countryRegistry.flush();
	}

	public static void clearData() {
		
		EntityManager em = EntityContextPlugin.getEntity(EntityManager.class);
		em.createNativeQuery("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK").executeUpdate();
		
		if (em.getTransaction().isActive()) {
			em.getTransaction().rollback();
	    }
		
		em.clear();
	}
}
