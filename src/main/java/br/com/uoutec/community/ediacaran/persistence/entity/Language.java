package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.entity.registry.DataValidation;
import br.com.uoutec.entity.registry.IdValidation;
import br.com.uoutec.entity.registry.ParentEntity;

public class Language implements Serializable, ParentEntity{

	private static final long serialVersionUID = -7898530339550200721L;

	@Min(value=1, groups = IdValidation.class)
	protected int id;

	@NotNull(groups={DataValidation.class})
	@Size(min=2,max=12, groups={DataValidation.class})
	protected String iso6391;

	@NotNull(groups={DataValidation.class})
	@Size(min=2,max=12, groups={DataValidation.class})
	protected String iso6392t;
	
	@NotNull(groups={DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidation.class})
	protected String name;

	@NotNull(groups={DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidation.class})
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

	@Override
	public boolean isValidParent() {
		return this.id > 0;
	}
	
}
