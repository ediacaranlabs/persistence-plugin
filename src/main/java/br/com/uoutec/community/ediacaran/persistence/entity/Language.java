package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;
import javax.annotation.Generated;

public class Language implements Serializable{

	private static final long serialVersionUID = -7898530339550200721L;

	private int id;

	private String iso6391;

	private String iso6392t;
	
	private String name;

	private String isoName;

	@Generated("SparkTools")
	private Language(Builder builder) {
		this.id = builder.id;
		this.iso6391 = builder.iso6391;
		this.iso6392t = builder.iso6392t;
		this.name = builder.name;
		this.isoName = builder.isoName;
	}
	
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

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private int id;
		private String iso6391;
		private String iso6392t;
		private String name;
		private String isoName;

		private Builder() {
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withIso6391(String iso6391) {
			this.iso6391 = iso6391;
			return this;
		}

		public Builder withIso6392t(String iso6392t) {
			this.iso6392t = iso6392t;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withIsoName(String isoName) {
			this.isoName = isoName;
			return this;
		}

		public Language build() {
			return new Language(this);
		}
	}
	
}
