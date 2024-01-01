package br.com.uoutec.community.ediacaran.persistence.registry;

import java.util.List;
import java.util.Locale;

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
	implements CountryRegistry{

	@Inject
	private CountryEntityAccess entityAcess;
	
	public Country getCountryByUFI(int value, Language lang) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.ufi"));
		
		try{
			return this.entityAcess.findByUFI(value <= 0?null : value, lang);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	public Country getCountryByIsoAlpha2(String value) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.isoalpha2"));
		
		try{
			return this.entityAcess.findByIsoAlpha2(value);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	public Country getCountryByIsoAlpha3(String value) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.isoalpha3"));
		
		try{
			return entityAcess.findByIsoAlpha3(value);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}
	
	
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
	public List<Country> getAll() throws CountryRegistryException {
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.all"));
		
		try{
			return this.entityAcess.findAll();
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	public List<Country> getAll(Language lang) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.language"));
		
		try{
			return this.entityAcess.findAll(lang);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}
	
	public List<Country> getAll(Locale locale) throws CountryRegistryException{
		
		ContextSystemSecurityCheck.checkPermission(new RuntimeSecurityPermission(PERMISSION_PREFIX + "access.locale"));
		
		try{
			return this.entityAcess.findAll(locale);
		}
		catch(Throwable ex){
			throw new CountryRegistryException(ex);
		}
	}

	@Override
	public void flush() {
		entityAcess.flush();
	}
	
}
