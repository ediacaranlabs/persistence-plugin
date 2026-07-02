package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.DoPrivilegedException;
import br.com.uoutec.application.security.RuntimeSecurityPermission;
import br.com.uoutec.community.ediacaran.system.concurrent.ExecutorServicesProducer;
import br.com.uoutec.community.ediacaran.system.concurrent.ThreadGroupManager;
import br.com.uoutec.ediacaran.core.ResourceRegistry;
import br.com.uoutec.ediacaran.core.VarParser;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;
import br.com.uoutec.ediacaran.core.plugins.PluginType;

@Singleton
public class JPAInitializer {
	
	private Logger logger = LoggerFactory.getLogger(JPAInitializer.class);
	
	private PluginType pluginData;
	
	private VarParser varParser;
	
	private volatile EntityManagerFactory emf;

	private ResourceRegistry resourceRegistry;
	
	private ThreadGroupManager threadGroupManager;
	
	private ExecutorService defaultExecutorService;
	
	@Inject
	public JPAInitializer(PluginType pluginData,VarParser varParser, ResourceRegistry resourceRegistry, ThreadGroupManager threadGroupManager) throws Throwable {
		this.pluginData = pluginData;
		this.varParser = varParser;
		this.resourceRegistry = resourceRegistry;
		this.threadGroupManager = threadGroupManager;
		this.defaultExecutorService = this.threadGroupManager.getThreadGroup(ExecutorServicesProducer.DEFAULT_THREAD_GROUP);
		createSessionFactory0();
	}
	
	public void close(@Disposes EntityManager entityManager) {
		
		entityManager.close();
		
		if(logger.isTraceEnabled()) {
			logger.trace("Entity manager closed: {}" , entityManager);
		}
	}

	@Produces
	@RequestScoped
	public EntityManager createSessionFactory() throws Throwable {
		
		EntityManagerFactory localEMF = emf;
		
		if(localEMF == null) {
			localEMF = createSessionFactory0();
		}
		
		EntityManager em = localEMF.createEntityManager();
		
		if(logger.isTraceEnabled()) {
			logger.trace("Entity manager created: {}" , em);
		}
		
		return (EntityManager) Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class<?>[] {EntityManager.class}, 
				new InvocationHandlerJPA(em, logger)
		);
		
	}
	
	public synchronized EntityManagerFactory createSessionFactory0() throws Throwable {

		if(emf != null) {
			return emf;
		}
		
		emf = createEntityManagerFactory();
		
		return emf;
	}

	public EntityManagerFactory createEntityManagerFactory() {
		
		EntityManagerFactoryCreatorTask task = EntityContextPlugin.getEntity(EntityManagerFactoryCreatorTask.class);
		task.setPluginData(pluginData);
		task.setResourceRegistry(resourceRegistry);
		task.setVarParser(varParser);
		
		Future<EntityManagerFactoryCreatorResult> future = defaultExecutorService.submit(task);

		EntityManagerFactoryCreatorResult result;
		
		try {
			result = future.get(5, TimeUnit.MINUTES);
		}
		catch (InterruptedException | ExecutionException | TimeoutException e) {
			throw new PersistenceException(e);
		}
		
		return result.entityManagerFactory;
	}
	
	public static class EntityManagerFactoryCreatorTask 
		implements Callable<EntityManagerFactoryCreatorResult> {

		private PluginType pluginData;
		
		private VarParser varParser;
		
		private ResourceRegistry resourceRegistry;
		
		public EntityManagerFactoryCreatorTask() {
		}

		public void setPluginData(PluginType pluginData) {
			this.pluginData = pluginData;
		}


		public void setVarParser(VarParser varParser) {
			this.varParser = varParser;
		}


		public void setResourceRegistry(ResourceRegistry resourceRegistry) {
			this.resourceRegistry = resourceRegistry;
		}


		@Override
		public EntityManagerFactoryCreatorResult call() throws Exception {
			EntityManagerFactory entityManagerFactory = null;
			try {
				EntityManagerFactoryCreator emfc = new EntityManagerFactoryCreator(pluginData, varParser, resourceRegistry);
				entityManagerFactory = emfc.createSessionFactory();
				return new EntityManagerFactoryCreatorResult(entityManagerFactory, null);
			}
			catch(Throwable ex) {
				return new EntityManagerFactoryCreatorResult(entityManagerFactory, ex);
			}
		}
		
	}
	
	public static class EntityManagerFactoryCreatorResult {
		
		public EntityManagerFactory entityManagerFactory;
		
		public Throwable error;

		public EntityManagerFactoryCreatorResult(EntityManagerFactory entityManagerFactory, Throwable error) {
			this.entityManagerFactory = entityManagerFactory;
			this.error = error;
		}
		
	}
	
	public static class InvocationHandlerJPA implements InvocationHandler{

		private Logger logger = LoggerFactory.getLogger(JPAInitializer.class);
		
		private EntityManager em;
		
		public InvocationHandlerJPA(EntityManager em, Logger logger) {
			this.em = em;
			this.logger = logger;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			return ContextSystemSecurityCheck.doPrivileged(()->{
				
				ContextSystemSecurityCheck.checkPermission(
						new RuntimeSecurityPermission(
								"persistence.context." +  method.getName().toLowerCase()
						)
				);
				
				try {
					if(logger.isTraceEnabled()) {
						logger.trace(em + ": " + method.toString());
					}
					return method.invoke(em, args);
				}
				catch(DoPrivilegedException e) {
					Throwable ex = e.getCause();
					
					if(ex instanceof InvocationTargetException) {
						throw ((InvocationTargetException) ex).getTargetException();
					}
					else {
						throw ex;
					}
				}
				
			});
			
		}
		
	}
}
