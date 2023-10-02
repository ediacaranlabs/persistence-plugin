package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;
import java.util.Locale;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.entity.registry.DataValidation;
import br.com.uoutec.entity.registry.IdValidation;
import br.com.uoutec.entity.registry.Parent;
import br.com.uoutec.entity.registry.ParentEntity;

public class Country implements Serializable, ParentEntity{

	private static final long serialVersionUID = 1111966468404063L;

	@Min(value=1, groups = IdValidation.class)
	protected int id;

	@NotNull(groups={DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidation.class})
	protected String name;

	protected int uni;

	protected int ufi;
	
	@NotNull(groups={DataValidation.class})
	@Size(min=2,max=2, groups={DataValidation.class})
	protected String isoAlpha2;

	@NotNull(groups={DataValidation.class})
	@Size(min=2,max=2, groups={DataValidation.class})
	protected String isoAlpha3;

	@NotNull(groups={DataValidation.class})
	@Size(min=2,max=6, groups={DataValidation.class})
	protected String tld;

	@NotNull(groups={DataValidation.class})
	@Size(min=2,max=6, groups={DataValidation.class})
	protected String iso4217;

	@NotNull(groups={DataValidation.class})
	@Parent(groups={DataValidation.class})
	protected Language language;
	
	@NotNull(groups={DataValidation.class})
	@Parent(groups={DataValidation.class})
	protected Region region;

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

	@Override
	public boolean isValidParent() {
		return this.id > 0;
	}

}
