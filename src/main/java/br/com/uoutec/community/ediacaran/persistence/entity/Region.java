package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.community.ediacaran.system.util.SecretUtil;
import br.com.uoutec.entity.registry.DataValidation;
import br.com.uoutec.entity.registry.IdValidation;
import br.com.uoutec.entity.registry.Parent;
import br.com.uoutec.entity.registry.ParentEntity;

public class Region implements Serializable, ParentEntity{

	private static final long serialVersionUID = -5628222195056787786L;

	@Min(value=1, groups = IdValidation.class)
	protected int id;

	protected int parent;

	@NotNull(groups={DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidation.class})
	protected String name;

	@NotNull(groups={DataValidation.class})
	@Parent(groups={DataValidation.class})
	protected Language language;

	@NotNull(groups={DataValidation.class})
	@Size(min=2,max=12, groups={DataValidation.class})
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

	public String getProtectedID() {
		return SecretUtil.toProtectedID(String.valueOf(id));		
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

	@Override
	public boolean isValidParent() {
		return this.id > 0;
	}
	
}
