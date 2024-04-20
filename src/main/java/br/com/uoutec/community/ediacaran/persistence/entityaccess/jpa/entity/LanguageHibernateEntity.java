package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.uoutec.community.ediacaran.persistence.entity.Language;
import br.com.uoutec.ediacaran.core.plugins.PublicType;

@Entity
@Table(name="rw_language")
@EntityListeners(LanguageHibernateEntityListener.class)
public class LanguageHibernateEntity implements PublicType{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "cod_language", length=5)
	private Integer id;

	@Column(name = "cod_iso_639_1")
	private String iso6391;

	@Column(name = "cod_iso_639_2t")
	private String iso6392t;

	@Column(name = "dsc_iso_name")
	private String isoName;
	
	@Column(name = "dsc_name")
	private String name;

	public LanguageHibernateEntity(){
	}
	
	public LanguageHibernateEntity(Language e){
		this.id = e.getId() <= 0? null : e.getId();
		this.isoName = e.getIsoName();
		this.iso6392t = e.getIso6392t();
		this.iso6391 = e.getIso6391();
		this.name = e.getName();
	}
	
	public String getIso6392t() {
		return iso6392t;
	}

	public void setIso6392t(String iso6392t) {
		this.iso6392t = iso6392t;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Language toEntity(){
		return this.toEntity(null);
	}
	
	public Language toEntity(Language e){
		if(e == null){
			e = new Language();
		}
		
		e.setId(this.id);
		e.setIso6391(this.iso6391);
		e.setIso6392t(this.iso6392t);
		e.setName(this.name);
		e.setIsoName(this.isoName);
		
		return e;
	}
}
