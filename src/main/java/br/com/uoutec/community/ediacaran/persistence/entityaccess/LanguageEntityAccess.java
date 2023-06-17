package br.com.uoutec.community.ediacaran.persistence.entityaccess;

import java.io.Serializable;
import java.util.List;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.persistence.EntityAccessException;

public interface LanguageEntityAccess {
	
	void save(Language e) throws EntityAccessException;

	void update(Language e) throws EntityAccessException;

	void delete(Language e) throws EntityAccessException;
	
	Language getLanguageByIso6391(String value) throws EntityAccessException;
	
	Language getLanguageByIso6392t(String value) throws EntityAccessException;
	
	Language findById(Serializable id) throws EntityAccessException;
	
	List<Language> findAll() throws EntityAccessException;
	
	void flush();
	
}
