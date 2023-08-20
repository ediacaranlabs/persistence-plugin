package br.com.uoutec.community.ediacaran.persistence.entity;

import javax.annotation.Generated;

public class CountryMock extends Country{

	private static final long serialVersionUID = -3646830964267068460L;

	@Generated("SparkTools")
	private CountryMock(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.uni = builder.uni;
		this.ufi = builder.ufi;
		this.isoAlpha2 = builder.isoAlpha2;
		this.isoAlpha3 = builder.isoAlpha3;
		this.tld = builder.tld;
		this.iso4217 = builder.iso4217;
		this.language = builder.language;
		this.region = builder.region;
	}

	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	@Generated("SparkTools")
	public static final class Builder {
		private int id;
		private String name;
		private int uni;
		private int ufi;
		private String isoAlpha2;
		private String isoAlpha3;
		private String tld;
		private String iso4217;
		private Language language;
		private Region region;

		private Builder() {
		}

		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withUni(int uni) {
			this.uni = uni;
			return this;
		}

		public Builder withUfi(int ufi) {
			this.ufi = ufi;
			return this;
		}

		public Builder withIsoAlpha2(String isoAlpha2) {
			this.isoAlpha2 = isoAlpha2;
			return this;
		}

		public Builder withIsoAlpha3(String isoAlpha3) {
			this.isoAlpha3 = isoAlpha3;
			return this;
		}

		public Builder withTld(String tld) {
			this.tld = tld;
			return this;
		}

		public Builder withIso4217(String iso4217) {
			this.iso4217 = iso4217;
			return this;
		}

		public Builder withLanguage(Language language) {
			this.language = language;
			return this;
		}

		public Builder withRegion(Region region) {
			this.region = region;
			return this;
		}

		public CountryMock build() {
			return new CountryMock(this);
		}
	}

}
