package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.persistence.entity.SystemEvent;
import br.com.uoutec.community.ediacaran.persistence.entity.SystemEventType;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.SystemEventEntityAccess;


@Singleton
public class SystemEventRegistryImp 
	implements SystemEventRegistry {

	@Inject
	private SystemEventEntityAccess entityAccess;

	@Override
	public void registerSystemEvent(SystemEvent entity)
			throws SystemEventRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "register"));
		}

		try{
			if(entity.getId() == null){
				this.entityAccess.save(entity);
			}
			else{
				this.entityAccess.update(entity);
			}
		}
		catch(Throwable e){
			throw new SystemEventRegistryException(e);
		}
	}

	@Override
	public void removeSystemEvent(SystemEvent entity)
			throws SystemEventRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "unregister"));
		}
		
		try{
			this.entityAccess.delete(entity);
		}
		catch(Throwable e){
			throw new SystemEventRegistryException(e);
		}
	}

	@Override
	public List<SystemEvent> getSystemEventByType(SystemEventType type,
			Integer first, Integer max) throws SystemEventRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.type"));
		}
		
		try{
			return this.entityAccess.getByType(type, first, max);
		}
		catch(Throwable e){
			throw new SystemEventRegistryException(e);
		}
	}

	@Override
	public void flush() {
		entityAccess.flush();
	}
	
}
