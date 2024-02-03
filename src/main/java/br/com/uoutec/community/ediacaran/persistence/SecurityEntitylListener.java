package br.com.uoutec.community.ediacaran.persistence;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import br.com.uoutec.application.security.ContextSystemSecurityCheck;
import br.com.uoutec.application.security.RuntimeSecurityPermission;

public class SecurityEntitylListener<T> {

	public static final String PREFIX = "persistence.entity.";
	
	private Class<?> type;
	
	public SecurityEntitylListener() {
		this.type = 
				getClass().getSuperclass()
				.getTypeParameters()[0].getGenericDeclaration();
	}
	
	@PrePersist
    private void beforePersist(T o) {
		
		if(o == null || o.getClass() != type) {
			throw new IllegalArgumentException(String.valueOf(o));
		}
		
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PREFIX + o.getClass().getSimpleName().toLowerCase() + ".persist")
		);
		
    }

    @PreUpdate
    private void beforeUpdate(T o) {
    	
		if(o == null || o.getClass() != type) {
			throw new IllegalArgumentException(String.valueOf(o));
		}
    	
		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PREFIX + o.getClass().getSimpleName().toLowerCase() + ".update")
		);
		
    }

    @PreRemove
    private void beforeRemove(T o) {
    	
		if(o == null || o.getClass() != type) {
			throw new IllegalArgumentException(String.valueOf(o));
		}

		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PREFIX + o.getClass().getSimpleName().toLowerCase() + ".remove")
		);
		
    }

    @PostLoad
    private void afterLoad(T o) {

		if(o == null || o.getClass() != type) {
			throw new IllegalArgumentException(String.valueOf(o));
		}

		ContextSystemSecurityCheck.checkPermission(
				new RuntimeSecurityPermission(PREFIX + o.getClass().getSimpleName().toLowerCase() + ".load")
		);
		
    }
    
}
