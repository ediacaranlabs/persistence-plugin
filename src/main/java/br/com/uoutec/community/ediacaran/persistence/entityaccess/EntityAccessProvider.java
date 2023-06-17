package br.com.uoutec.community.ediacaran.persistence.entityaccess;

public interface EntityAccessProvider {

	Class<?> getEntityAcessType(String provider, Class<?> type);
}
