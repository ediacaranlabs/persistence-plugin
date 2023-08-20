package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;

public class Region implements Serializable{

	private static final long serialVersionUID = -5628222195056787786L;

	protected int id;

	protected int parent;

	protected String name;

	protected Language language;

	protected String alphabet;

	public Region() {
	}
	
	public Region(int id, int parent, String name, Language language, String alphabet) {
		this.id = id;
		this.parent = parent;
		this.name = name;
		this.language = language;
		this.alphabet = alphabet;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}
	
}
