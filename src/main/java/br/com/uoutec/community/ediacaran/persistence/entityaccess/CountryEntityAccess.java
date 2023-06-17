package br.com.uoutec.community.ediacaran.persistence.entityaccess;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import br.com.uoutec.community.ediacaran.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.persistence.EntityAccessException;

public interface CountryEntityAccess {

	Country findByUFI(Integer value, Language lang) throws EntityAccessException;
	
	Country findByIsoAlpha3(String value) throws EntityAccessException;

	Country findByIsoAlpha2(String value) throws EntityAccessException;
	
	Country findById(Serializable id) throws EntityAccessException;
	
	void save(Country e) throws EntityAccessException;

	void update(Country e) throws EntityAccessException;

	void delete(Country e) throws EntityAccessException;
	
	List<Country> findAll() throws EntityAccessException;

	List<Country> findAll(Language lang) throws EntityAccessException;

	List<Country> findAll(Locale locale) throws EntityAccessException;
	
	void flush();
	
}
