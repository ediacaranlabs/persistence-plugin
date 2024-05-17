package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.uoutec.community.ediacaran.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.CountryEntityAccess;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity.CountryHibernateEntity;
import br.com.uoutec.persistence.EntityAccessException;

public class CountryEntityAccessImp
	extends AbstractEntityAccess<Country, CountryHibernateEntity>
	implements CountryEntityAccess {

	public CountryEntityAccessImp(){
		super(null);
	}
	
	@Inject
	public CountryEntityAccessImp(EntityManager entityManager) {
		super(entityManager);
	}

	public Country findByUFI(Integer value, Language lang) throws EntityAccessException{
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<CountryHibernateEntity> criteria = builder.createQuery(CountryHibernateEntity.class);
		    Root<CountryHibernateEntity> from = criteria.from(CountryHibernateEntity.class);
			Join<Object, Object> languageJoin = from.join("language", JoinType.INNER);
		    
		    criteria.select(from);
		    
		    Predicate ufi = value == null? builder.isNull(from.get("ufi")) : builder.equal(from.get("ufi"), value);
		    Predicate langID = builder.equal(languageJoin.get("id"), lang.getId());
			
			Predicate ufiAndLangID = builder.and(ufi, langID);		

		    criteria.where(ufiAndLangID);
			
		    TypedQuery<CountryHibernateEntity> typed = entityManager.createQuery(criteria);			
		    CountryHibernateEntity e = (CountryHibernateEntity)typed.getSingleResult();
		    
			return e == null? null : e.toEntity();
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}
	}
	
	public Country findByUFI(Integer value, Locale locale) throws EntityAccessException{
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<CountryHibernateEntity> criteria = builder.createQuery(CountryHibernateEntity.class);
		    Root<CountryHibernateEntity> from = criteria.from(CountryHibernateEntity.class);
			Join<Object, Object> languageJoin = from.join("language", JoinType.INNER);
		    
		    criteria.select(from);
		    
		    Predicate ufi = 
		    		value == null? 
	    				builder.isNull(from.get("ufi")) : 
    					builder.equal(from.get("ufi"), value);
		    
		    Predicate langID = builder.equal(languageJoin.get("iso6392t"), locale.getISO3Language());
			
			Predicate ufiAndLangID = builder.and(ufi, langID);		

		    criteria.where(ufiAndLangID);
			
		    TypedQuery<CountryHibernateEntity> typed = entityManager.createQuery(criteria);			
		    CountryHibernateEntity e = (CountryHibernateEntity)typed.getSingleResult();
		    
			return e == null? null : e.toEntity();
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}
	}	
	public Country findByIsoAlpha3(String value) throws EntityAccessException{
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<CountryHibernateEntity> criteria = builder.createQuery(CountryHibernateEntity.class);
		    Root<CountryHibernateEntity> from = criteria.from(CountryHibernateEntity.class);
		    
		    criteria.select(from);
		    
		    criteria.where(
		    		builder.and(
		    				builder.isNull(from.get("ufi")), 
		    				builder.equal(from.get("isoAlpha3"), value)
    				)
    		);
			
		    TypedQuery<CountryHibernateEntity> typed = entityManager.createQuery(criteria);			
		    CountryHibernateEntity e = (CountryHibernateEntity)typed.getSingleResult();
		    
			return e == null? null : e.toEntity();
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}
	}

	public Country findByIsoAlpha2(String value) throws EntityAccessException{
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<CountryHibernateEntity> criteria = builder.createQuery(CountryHibernateEntity.class);
		    Root<CountryHibernateEntity> from = criteria.from(CountryHibernateEntity.class);
		    
		    criteria.select(from);
		    
		    criteria.where(
		    		builder.and(
		    				builder.isNull(from.get("ufi")), 
		    				builder.equal(from.get("isoAlpha2"), value)
    				)
    		);
			
		    TypedQuery<CountryHibernateEntity> typed = entityManager.createQuery(criteria);			
		    CountryHibernateEntity e = (CountryHibernateEntity)typed.getSingleResult();
		    
			return e == null? null : e.toEntity();
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}
	}
	
	public List<Country> findAll(Language lang) throws EntityAccessException{
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<CountryHibernateEntity> criteria = builder.createQuery(CountryHibernateEntity.class);
		    Root<CountryHibernateEntity> from = criteria.from(CountryHibernateEntity.class);
			Join<Object, Object> languageJoin = from.join("language", JoinType.INNER);
		    
		    criteria.select(from);

		    criteria.where(
		    		builder.equal(
		    				languageJoin.get("id"), lang.getId()
    				)
    		);
			
		    TypedQuery<CountryHibernateEntity> typed = entityManager.createQuery(criteria);			
		    List<CountryHibernateEntity> list = typed.getResultList();
			
			if (list != null) {
				List<Country> r = new ArrayList<Country>();
				for (CountryHibernateEntity e : list) {
					r.add(e.toEntity());
				}
				return r;
			}
			return null;
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}		
	}
	
	public List<Country> findAll(Locale locale) throws EntityAccessException {
		try {
			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<CountryHibernateEntity> criteria = builder.createQuery(CountryHibernateEntity.class);
		    Root<CountryHibernateEntity> from = criteria.from(CountryHibernateEntity.class);
			Join<Object, Object> languageJoin = from.join("language", JoinType.INNER);
		    
		    criteria.select(from);

		    criteria.where(
		    		builder.equal(
		    				languageJoin.get("iso6392t"), locale.getISO3Language()
    				)
    		);
			
		    TypedQuery<CountryHibernateEntity> typed = entityManager.createQuery(criteria);			
		    List<CountryHibernateEntity> list = typed.getResultList();
			
			if (list != null) {
				List<Country> r = new ArrayList<Country>();
				for (CountryHibernateEntity e : list) {
					r.add(e.toEntity());
				}
				return r;
			}
			
			return null;
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}		
	}
	
	@Override
	protected CountryHibernateEntity toPersistenceEntity(Country entity)
			throws Throwable {
		return new CountryHibernateEntity(entity);
	}

	@Override
	protected Country toEntity(CountryHibernateEntity entity) throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(Country entity, Serializable id) throws Throwable {
		entity.setId((Integer)id);
	}

	@Override
	protected Serializable getPersistenceID(CountryHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(Country value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}

}
