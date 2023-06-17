package br.com.uoutec.community.ediacaran.persistence.entity;

import java.io.Serializable;
import java.util.Map;

@Deprecated
public class ComponentConfigurer implements Serializable{

	private static final long serialVersionUID = 5026229112700424007L;

	private int id;
	
	private String code;

	private String name;
	
	private String bundleName;
	
	private String type;
	
	private Map<String,String> properties;
	
	private boolean enabled;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name ;
	}
	
	/*
	public String getName(Locale locale) {
		return 
			this.bundleName == null? 
				name : 
				MessageBundleUtils.getMessageResourceString(this.bundleName, this.name, locale);
	}
	*/
	
	public void setName(String name) {
		this.name = name;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
