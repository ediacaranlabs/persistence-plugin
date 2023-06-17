package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.LanguageEntityAccess;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity.LanguageHibernateEntity;
import br.com.uoutec.persistence.EntityAccessException;


public class LanguageEntityAccessImp
	extends AbstractEntityAccess<Language, LanguageHibernateEntity>
	implements LanguageEntityAccess{

	public LanguageEntityAccessImp() {
		super(null);
	}
	
	@Inject
	public LanguageEntityAccessImp(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Language getLanguageByIso6391(String value)
			throws EntityAccessException {
		
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<LanguageHibernateEntity> criteria = builder.createQuery(LanguageHibernateEntity.class);
		    Root<LanguageHibernateEntity> from = criteria.from(LanguageHibernateEntity.class);
		    
		    criteria.select(from);
		    
		    criteria.where(builder.equal(from.get("iso6391"), value));
			
		    TypedQuery<LanguageHibernateEntity> typed = entityManager.createQuery(criteria);			
		    LanguageHibernateEntity e = (LanguageHibernateEntity)typed.getSingleResult();
		    
			return e == null? null : e.toEntity();
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}
		
	}

	@Override
	public Language getLanguageByIso6392t(String value)
			throws EntityAccessException {
		
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<LanguageHibernateEntity> criteria = builder.createQuery(LanguageHibernateEntity.class);
		    Root<LanguageHibernateEntity> from = criteria.from(LanguageHibernateEntity.class);
		    
		    criteria.select(from);
		    
		    criteria.where(builder.equal(from.get("iso6392t"), value));
			
		    TypedQuery<LanguageHibernateEntity> typed = entityManager.createQuery(criteria);			
		    LanguageHibernateEntity e = (LanguageHibernateEntity)typed.getSingleResult();
		    
			return e == null? null : e.toEntity();
		}
		catch (Throwable e) {
			throw new EntityAccessException(e);
		}
		
	}
	
	@Override
	protected LanguageHibernateEntity toPersistenceEntity(Language entity)
			throws Throwable {
		return new LanguageHibernateEntity(entity);
	}

	@Override
	protected Language toEntity(LanguageHibernateEntity entity)
			throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(Language entity, Serializable id) throws Throwable {
		entity.setId((Integer)id);
	}

	@Override
	protected Serializable getPersistenceID(LanguageHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(Language value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}

}
