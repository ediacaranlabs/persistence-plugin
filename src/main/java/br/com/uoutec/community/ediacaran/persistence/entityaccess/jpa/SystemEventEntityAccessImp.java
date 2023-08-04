package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity.SystemEventHibernateEntity;
import br.com.uoutec.community.ediacaran.system.event.SystemEvent;
import br.com.uoutec.community.ediacaran.system.event.SystemEventEntityAccess;
import br.com.uoutec.community.ediacaran.system.event.SystemEventType;
import br.com.uoutec.community.ediacaran.system.util.IDGenerator;
import br.com.uoutec.persistence.EntityAccessException;


public class SystemEventEntityAccessImp 
	extends AbstractEntityAccess<SystemEvent, SystemEventHibernateEntity>
	implements SystemEventEntityAccess{

	private Random groupRandom;
	
	public SystemEventEntityAccessImp() {
		super(null);
	}
	
	@Inject
	public SystemEventEntityAccessImp(EntityManager entityManager) {
		super(entityManager);
		this.groupRandom = new Random();
	}

	public void save(SystemEvent entity) throws EntityAccessException{
		if(entity.getId() != null){
			throw new EntityAccessException("entidade já foi persistida");
		}
		
		entity.setId(IDGenerator.getUniqueOrderID('S', this.groupRandom.nextInt()));
		super.save(entity);
	}
	
	@Override
	public List<SystemEvent> getByType(SystemEventType type, Integer first,
			Integer max) throws EntityAccessException {
		
		try {
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		    CriteriaQuery<SystemEventHibernateEntity> criteria = builder.createQuery(SystemEventHibernateEntity.class);
		    Root<SystemEventHibernateEntity> from = criteria.from(SystemEventHibernateEntity.class);
		    
		    criteria.select(from);
		    
		    criteria.where(builder.equal(from.get("type"), type));
			
		    TypedQuery<SystemEventHibernateEntity> typed = entityManager.createQuery(criteria);			
		    
		    if(first > 0) {
		    	typed.setFirstResult(first);
		    }
		    
		    if(max > 0) {
		    	typed.setMaxResults(max);
		    }
		    
		    List<SystemEventHibernateEntity> list = typed.getResultList();
		    
			if(list != null){
				List<SystemEvent> r = new ArrayList<SystemEvent>();
				for(SystemEventHibernateEntity e: list){
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
	protected SystemEventHibernateEntity toPersistenceEntity(SystemEvent entity)
			throws Throwable {
		return new SystemEventHibernateEntity(entity);
	}

	@Override
	protected SystemEvent toEntity(SystemEventHibernateEntity entity)
			throws Throwable {
		return entity.toEntity();
	}

	@Override
	protected void setId(SystemEvent entity, Serializable id) throws Throwable {
		entity.setId((String) id);
	}

	@Override
	protected Serializable getPersistenceID(SystemEventHibernateEntity value)
			throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable getID(SystemEvent value) throws Throwable {
		return value.getId();
	}

	@Override
	protected Serializable toPersistenceID(Serializable value) throws Throwable {
		return value;
	}

}
