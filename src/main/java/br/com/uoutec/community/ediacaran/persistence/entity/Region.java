package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;
import javax.annotation.Generated;

public class Region implements Serializable{

	private static final long serialVersionUID = -5628222195056787786L;

	private int id;

	private int parent;

	private String name;

	private Language language;

	private String alphabet;

	@Generated("SparkTools")
	private Region(Builder builder) {
		this.id = builder.id;
		this.parent = builder.parent;
		this.name = builder.name;
		this.language = builder.language;
		this.alphabet = builder.alphabet;
	}

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

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private int id;
		private int parent;
		private String name;
		private Language language;
		private String alphabet;

		private Builder() {
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withParent(int parent) {
			this.parent = parent;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withLanguage(Language language) {
			this.language = language;
			return this;
		}

		public Builder withAlphabet(String alphabet) {
			this.alphabet = alphabet;
			return this;
		}

		public Region build() {
			return new Region(this);
		}
	}
	
}
