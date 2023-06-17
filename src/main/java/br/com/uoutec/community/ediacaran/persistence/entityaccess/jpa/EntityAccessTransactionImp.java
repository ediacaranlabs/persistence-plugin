package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import javax.persistence.EntityTransaction;

import br.com.uoutec.persistence.EntityAccessException;
import br.com.uoutec.persistence.EntityAccessTransaction;

public class EntityAccessTransactionImp  
implements EntityAccessTransaction{

	private EntityTransaction entityTransaction;

	public EntityAccessTransactionImp(EntityTransaction entityTransaction){
		this.entityTransaction = entityTransaction;
	}
	
	public void commit() throws EntityAccessException {
		try{
			entityTransaction.commit();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}
	
	public void rollback() throws EntityAccessException {
		try{
			entityTransaction.rollback();
		}
		catch(Throwable e){
			throw new EntityAccessException(e);
		}
	}

}
