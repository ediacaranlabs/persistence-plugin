package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;
import java.util.Locale;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entity.Region;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.RegionEntityAccess;
import br.com.uoutec.entity.registry.AbstractRegistry;

@Singleton
@Default
public class RegionRegistryImp
	extends AbstractRegistry
	implements RegionRegistry{

	@Inject
	private RegionEntityAccess entityAcess;
	
	public Region getRegion(int id) throws RegionRegistryException{
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access"));
		}
		
		try{
			return this.entityAcess.findById(id);
		}
		catch(Throwable ex){
			throw new RegionRegistryException(ex);
		}
	}
	
	@Override
	public void registerRegion(Region e) throws RegionRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "register"));
		}
		
		try{
			if(e.getId() <= 0){
				this.entityAcess.save(e);
			}
			else{
				this.entityAcess.update(e);
			}
		}
		catch(Throwable ex){
			throw new RegionRegistryException(ex);
		}
	}

	@Override
	public void removeRegion(Region e) throws RegionRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "unregister"));
		}
		
		try{
			this.entityAcess.delete(e);
		}
		catch(Throwable ex){
			throw new RegionRegistryException(ex);
		}
	}

	@Override
	public List<Region> getAll() throws RegionRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.all"));
		}
		
		try{
			return this.entityAcess.findAll();
		}
		catch(Throwable ex){
			throw new RegionRegistryException(ex);
		}
	}

	public List<Region> getAll(Language lang) throws RegionRegistryException{
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.language"));
		}
		
		try{
			return this.entityAcess.findAll(lang);
		}
		catch(Throwable ex){
			throw new RegionRegistryException(ex);
		}
	}
	
	public List<Region> getAll(Locale locale) throws RegionRegistryException{
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.locale"));
		}
		
		try{
			return this.entityAcess.findAll(locale);
		}
		catch(Throwable ex){
			throw new RegionRegistryException(ex);
		}
	}

	@Override
	public void flush() {
		entityAcess.flush();
	}
	
}
