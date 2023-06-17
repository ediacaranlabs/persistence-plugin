package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;
import java.util.Locale;

import br.com.uoutec.community.ediacaran.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.entity.registry.Registry;


public interface CountryRegistry extends Registry, PublicBean{

	public static final String PERMISSION_PREFIX = "app.registry.country.";
	
	void registerCountry(Country e) throws CountryRegistryException;

	void removeCountry(Country e) throws CountryRegistryException;

	Country getCountry(int id) throws CountryRegistryException;

	Country getCountryByUFI(int value, Language lang) throws CountryRegistryException;
	
	Country getCountryByIsoAlpha3(String value) throws CountryRegistryException;

	Country getCountryByIsoAlpha2(String value) throws CountryRegistryException;
	
	List<Country> getAll() throws CountryRegistryException;
	
	List<Country> getAll(Language lang) throws CountryRegistryException;
	
	List<Country> getAll(Locale locale) throws CountryRegistryException;
	
	void flush();
	
}
