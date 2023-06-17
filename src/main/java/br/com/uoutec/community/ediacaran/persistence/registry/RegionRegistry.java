package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;
import java.util.Locale;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entity.Region;
import br.com.uoutec.community.ediacaran.plugins.PublicBean;
import br.com.uoutec.entity.registry.Registry;


public interface RegionRegistry extends Registry, PublicBean{

	public static final String PERMISSION_PREFIX = "app.registry.region.";
	
	void registerRegion(Region e) throws RegionRegistryException;

	void removeRegion(Region e) throws RegionRegistryException;

	Region getRegion(int id) throws RegionRegistryException;
	
	List<Region> getAll() throws RegionRegistryException;
	
	List<Region> getAll(Language lang) throws RegionRegistryException;
	
	List<Region> getAll(Locale locale) throws RegionRegistryException;
	
}
