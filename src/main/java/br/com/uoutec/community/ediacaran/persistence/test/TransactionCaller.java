package br.com.uoutec.community.ediacaran.persistence.test;

import java.util.concurrent.Callable;

import javax.transaction.Transactional;

public class TransactionCaller implements Caller {

	@Transactional(rollbackOn = Throwable.class)
    public <V> V call(Callable<V> callable) throws Exception {
        return callable.call();
    }
	
}