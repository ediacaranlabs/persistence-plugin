package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.Generated;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.community.ediacaran.persistence.entity.Region;

public class Country implements Serializable{

	private static final long serialVersionUID = 1111966468404063L;

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

	@Generated("SparkTools")
	private Country(Builder builder) {
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

	public Country() {
	}
	
	public Country(int id, String name, int uni, int ufi, String isoAlpha2, String isoAlpha3, String tld,
			String iso4217, Language language, Region region) {
		this.id = id;
		this.name = name;
		this.uni = uni;
		this.ufi = ufi;
		this.isoAlpha2 = isoAlpha2;
		this.isoAlpha3 = isoAlpha3;
		this.tld = tld;
		this.iso4217 = iso4217;
		this.language = language;
		this.region = region;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUni() {
		return uni;
	}

	public void setUni(int uni) {
		this.uni = uni;
	}

	public int getUfi() {
		return ufi;
	}

	public void setUfi(int ufi) {
		this.ufi = ufi;
	}

	public String getIsoAlpha2() {
		return isoAlpha2;
	}

	public void setIsoAlpha2(String isoAlpha2) {
		this.isoAlpha2 = isoAlpha2;
	}

	public String getIsoAlpha3() {
		return isoAlpha3;
	}

	public void setIsoAlpha3(String isoAlpha3) {
		this.isoAlpha3 = isoAlpha3;
	}

	public String getTld() {
		return tld;
	}

	public void setTld(String tld) {
		this.tld = tld;
	}

	public String getIso4217() {
		return iso4217;
	}

	public void setIso4217(String iso4217) {
		this.iso4217 = iso4217;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Locale getLocale(){
		return new Locale(this.language.getIso6391(), this.isoAlpha2);
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

		public Country build() {
			return new Country(this);
		}
	}
}
