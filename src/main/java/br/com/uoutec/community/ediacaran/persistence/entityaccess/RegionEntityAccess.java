package br.com.uoutec.community.ediacaran.persistence.entityaccess;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entity.Region;
import br.com.uoutec.persistence.EntityAccessException;

public interface RegionEntityAccess {

	Region findById(Serializable id) throws EntityAccessException;
	
	void save(Region e) throws EntityAccessException;

	void update(Region e) throws EntityAccessException;

	void delete(Region e) throws EntityAccessException;
	
	List<Region> findAll() throws EntityAccessException;

	List<Region> findAll(Language lang) throws EntityAccessException;

	List<Region> findAll(Locale locale) throws EntityAccessException;
	
	void flush();
	
}
