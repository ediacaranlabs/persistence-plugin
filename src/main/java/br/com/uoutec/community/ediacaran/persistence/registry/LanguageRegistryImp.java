package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.LanguageEntityAccess;

@Singleton
@Default
public class LanguageRegistryImp 
	implements LanguageRegistry{

	@Inject
	private LanguageEntityAccess entityAccess;
	
	@Override
	public void registerLanguage(Language e) throws LanguageRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "register"));
		}
		
		try{
			if(e.getId() <=0 ){
				this.entityAccess.save(e);
			}
			else{
				this.entityAccess.update(e);
			}
			this.entityAccess.flush();
		}
		catch(Throwable ex){
			throw new LanguageRegistryException(ex);
		}
	}

	@Override
	public void removeLanguage(Language e) throws LanguageRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "unregister"));
		}
		
		try{
			this.entityAccess.delete(e);
		}
		catch(Throwable ex){
			throw new LanguageRegistryException(ex);
		}
	}

	@Override
	public Language getLanguage(int id) throws LanguageRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access"));
		}
		
		try{
			return this.entityAccess.findById(id);
		}
		catch(Throwable ex){
			throw new LanguageRegistryException(ex);
		}
	}

	@Override
	public Language getLanguageByIso6391(String value)
			throws LanguageRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.iso6391"));
		}
		
		try{
			return this.entityAccess.getLanguageByIso6391(value);
		}
		catch(Throwable ex){
			throw new LanguageRegistryException(ex);
		}
	}

	@Override
	public Language getLanguageByIso6392t(String value)
			throws LanguageRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.iso6392t"));
		}
		
		try{
			return this.entityAccess.getLanguageByIso6392t(value);
		}
		catch(Throwable ex){
			throw new LanguageRegistryException(ex);
		}
	}
	
	@Override
	public List<Language> getAll() throws LanguageRegistryException {
		
		SecurityManager sm = System.getSecurityManager();

		if(sm != null) {
			sm.checkPermission(new RuntimePermission(PERMISSION_PREFIX + "access.all"));
		}
		
		try{
			return this.entityAccess.findAll();
		}
		catch(Throwable ex){
			throw new LanguageRegistryException(ex);
		}
	}

	@Override
	public void flush() {
		entityAccess.flush();
	}

}
