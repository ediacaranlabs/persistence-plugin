package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.uoutec.community.ediacaran.persistence.entity.Country;
import br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity.CountryHibernateEntity;
import br.com.uoutec.persistence.BasicEntityAccess;
import br.com.uoutec.persistence.EntityAccessException;
import br.com.uoutec.persistence.EntityAccessTransaction;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class AbstractEntityAccess <T, K> 
	implements BasicEntityAccess<T>{

	protected EntityManager entityManager;
	
	public AbstractEntityAccess(){
		this(null);
	}
	
	public AbstractEntityAccess(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	public void save(T value) throws EntityAccessException {
		try{
			K pEntity = this.toPersistenceEntity(value);
			entityManager.persist(pEntity);
			entityManager.flush();
			T newValue = this.toEntity(pEntity);
			this.setId(value, this.getID(newValue));
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public void update(T value) throws EntityAccessException {
		try{
			K pEntity = this.toPersistenceEntity(value);
			pEntity = (K)entityManager.merge(pEntity);
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public void delete(T value) throws EntityAccessException {
		try{
			K pEntity = this.toPersistenceEntity(value);
			pEntity = (K)entityManager.merge(pEntity);
			entityManager.remove(pEntity);
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public T findById(Serializable value) throws EntityAccessException {
		try{
			Serializable pk = this.toPersistenceID(value);
			K pEntity = (K)entityManager.find(this.getEntityClass(), pk);
			return pEntity == null? null : this.toEntity(pEntity);
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public T findById(Serializable value, boolean lock) throws EntityAccessException {
		try{
			Serializable pk = this.toPersistenceID(value);
			K pEntity =
				lock?
					(K)entityManager.find(this.getEntityClass(), pk, LockModeType.OPTIMISTIC) :
					(K)entityManager.find(this.getEntityClass(), pk);
			return pEntity == null? null : this.toEntity(pEntity);
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public List<T> findAll() throws EntityAccessException {
		try{
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery(this.getEntityClass());
			Root rootEntry = cq.from(this.getEntityClass());
			
			CriteriaQuery<?> all = cq.select(rootEntry);
		    TypedQuery allQuery = entityManager.createQuery(all);
		    List<K> list = allQuery.getResultList();
		    
			if (list != null) {
				List<T> r = new ArrayList<T>();
				for (K e : list) {
					r.add(toEntity(e));
				}
				return r;
			}
			return null;
		    
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public List<T> findAll(int first, int max) throws EntityAccessException{
		try{
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery cq = cb.createQuery(this.getEntityClass());
			Root rootEntry = cq.from(this.getEntityClass());
			CriteriaQuery<?> all = cq.select(rootEntry);
		    TypedQuery allQuery = entityManager.createQuery(all);
		    
		    if(first > 0) {
		    	allQuery.setFirstResult(first);
		    }
		    
		    if(max > 0) {
		    	allQuery.setMaxResults(max);
		    }
		    
		    return allQuery.getResultList();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	protected List<T> toCollection(List<K> list) throws Throwable{
		List<T> result = new ArrayList<T>();
		
		for(K e: list){
			result.add(this.toEntity(e));
		}
		
		return result;
	}
	
	protected Class<T> getEntityClass() {
		 return (Class<T>) ((ParameterizedType) getClass()
				 .getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	public EntityAccessTransaction beginTransaction() throws EntityAccessException{
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		return new EntityAccessTransactionImp(entityTransaction);
	}
	
	public void flush(){
		this.entityManager.flush();
	}
	
	
	protected abstract K toPersistenceEntity(T entity) throws Throwable;
	
	
	protected abstract T toEntity(K entity) throws Throwable;
	
	
	protected abstract void setId(T entity, Serializable id) throws Throwable;
	
	
	protected abstract Serializable getPersistenceID(K value) throws Throwable;
	
	
	protected abstract Serializable getID(T value) throws Throwable;
	
	
	protected abstract Serializable toPersistenceID(Serializable value) throws Throwable;

}
