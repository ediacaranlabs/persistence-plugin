package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;

public class Language implements Serializable{

	private static final long serialVersionUID = -7898530339550200721L;

	protected int id;

	protected String iso6391;

	protected String iso6392t;
	
	protected String name;

	protected String isoName;

	public Language() {
	}
	
	public Language(int id, String iso6391, String iso6392t, String name, String isoName) {
		this.id = id;
		this.iso6391 = iso6391;
		this.iso6392t = iso6392t;
		this.name = name;
		this.isoName = isoName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsoName() {
		return isoName;
	}

	public void setIsoName(String isoName) {
		this.isoName = isoName;
	}

	public String getIso6392t() {
		return iso6392t;
	}

	public void setIso6392t(String iso6392t) {
		this.iso6392t = iso6392t;
	}

	public String getIso6391() {
		return iso6391;
	}

	public void setIso6391(String iso6391) {
		this.iso6391 = iso6391;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
