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
import javax.persistence.criteria.Root;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entity.Region;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.RegionEntityAccess;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity.RegionHibernateEntity;
import br.com.uoutec.persistence.EntityAccessException;

public class RegionEntityAccessImp
	extends AbstractEntityAccess<Region, RegionHibernateEntity>
	implements RegionEntityAccess {

	public RegionEntityAccessImp(){
		super(null);
	}
	
	@Inject
	public RegionEntityAccessImp(EntityManager entityManager) {
		super(entityManager);
	}

	public List<Region> findAll(Language lang) throws EntityAccessException{
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<RegionHibernateEntity> criteria = builder.createQuery(RegionHibernateEntity.class);
		    Root<RegionHibernateEntity> from = criteria.from(RegionHibernateEntity.class);
			Join<Object, Object> languageJoin = from.join("language", JoinType.INNER);
		    
		    criteria.select(from);

		    criteria.where(
		    		builder.equal(
		    				languageJoin.get("id"), lang.getId()
    				)
    		);
			
		    TypedQuery<RegionHibernateEntity> typed = entityManager.createQuery(criteria);			
		    List<RegionHibernateEntity> list = typed.getResultList();
			
			if (list != null) {
				List<Region> r = new ArrayList<Region>();
				for (RegionHibernateEntity e : list) {
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
	
	public List<Region> findAll(Locale locale) throws EntityAccessException {
		try {
			
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<RegionHibernateEntity> criteria = builder.createQuery(RegionHibernateEntity.class);
		    Root<RegionHibernateEntity> from = criteria.from(RegionHibernateEntity.class);
			Join<Object, Object> languageJoin = from.join("language", JoinType.INNER);
		    
		    criteria.select(from);

		    criteria.where(
		    		builder.equal(
		    				languageJoin.get("iso6392t"), locale.getISO3Language()
    				)
    		);
			
		    TypedQuery<RegionHibernateEntity> typed = entityManager.createQuery(criteria);			
		    List<RegionHibernateEntity> list = typed.getResultList();
			
			if (list != null) {
				List<Region> r = new ArrayList<Region>();
				for (RegionHibernateEntity e : list) {
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
	protected RegionHibernateEntity toPersistenceEntity(Region entity)
			throws Throwable {
		return new RegionHibernateEntity(entity);
	}

	@Override
	protected Region toEntity(RegionHibernateEntity entity) throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(Region entity, Serializable id) throws Throwable {
		entity.setId((Integer)id);
	}

	@Override
	protected Serializable getPersistenceID(RegionHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(Region value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}

}
