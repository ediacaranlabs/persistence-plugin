package br.com.uoutec.community.ediacaran.persistence.entity;

import javax.annotation.Generated;

public class LanguageMock extends Language{

	private static final long serialVersionUID = 7692802401281549186L;

	@Generated("SparkTools")
	private LanguageMock(Builder builder) {
		this.id = builder.id;
		this.iso6391 = builder.iso6391;
		this.iso6392t = builder.iso6392t;
		this.name = builder.name;
		this.isoName = builder.isoName;
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

		public LanguageMock build() {
			return new LanguageMock(this);
		}
	}

}
