package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;
import java.util.Locale;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.community.ediacaran.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.CountryEntityAccess;
import br.com.uoutec.entity.registry.AbstractRegistry;

@Singleton
@Default
public class CountryRegistryImp
	extends AbstractRegistry
	implements CountryRegistry {

	@Inject
	private CountryEntityAccess entityAcess;
	
	@ActivateRequestContext
	public Country getCountryByUFI(int value, Language lang) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.ufi"));
		
		try{
			return this.entityAcess.findByUFI(value <= 0?null : value, lang);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	@ActivateRequestContext
	public Country getCountryByUFI(int value, Locale locale) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.ufi"));
		
		try{
			return this.entityAcess.findByUFI(value <= 0?null : value, locale);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}
	
	@ActivateRequestContext
	public Country getCountryByIsoAlpha2(String value) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.isoalpha2"));
		
		try{
			return this.entityAcess.findByIsoAlpha2(value);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	@ActivateRequestContext
	public Country getCountryByIsoAlpha3(String value) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.isoalpha3"));
		
		try{
			return entityAcess.findByIsoAlpha3(value);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}
	
	
	@ActivateRequestContext
	public Country getCountry(int id) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access"));
		
		try{
			return this.entityAcess.findById(id);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}
	
	@Override
	@ActivateRequestContext
	public void registerCountry(Country e) throws CountryRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "register"));
		
		try{
			if(e.getId() <= 0){
				this.entityAcess.save(e);
			}
			else{
				this.entityAcess.update(e);
			}
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	@Override
	@ActivateRequestContext
	public void removeCountry(Country e) throws CountryRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "unregister"));
		
		try{
			this.entityAcess.delete(e);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	@Override
	@ActivateRequestContext
	public List<Country> getAll() throws CountryRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.all"));
		
		try{
			return this.entityAcess.findAll();
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	@ActivateRequestContext
	public List<Country> getAll(Language lang) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.language"));
		
		try{
			List<Country> result = this.entityAcess.findAll(lang);
			return result.isEmpty()? this.entityAcess.findAll() : result;
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}
	
	@ActivateRequestContext
	public List<Country> getAll(Locale locale) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.locale"));
		
		try{
			List<Country> result = this.entityAcess.findAll(locale);
			return result.isEmpty()? this.entityAcess.findAll() : result;
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	@Override
	@ActivateRequestContext
	public void flush() {
		entityAcess.flush();
	}
	
}
