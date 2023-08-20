package br.com.uoutec.community.ediacaran.persistence.entity;

import javax.annotation.Generated;

public class RegionMock extends Region{

	private static final long serialVersionUID = 1L;

	@Generated("SparkTools")
	private RegionMock(Builder builder) {
		this.id = builder.id;
		this.parent = builder.parent;
		this.name = builder.name;
		this.language = builder.language;
		this.alphabet = builder.alphabet;
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

		public RegionMock build() {
			return new RegionMock(this);
		}
	}

	
}
