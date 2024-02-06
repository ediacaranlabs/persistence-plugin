package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.uoutec.community.ediacaran.persistence.entity.Region;

@Entity
@Table(name="rw_region")
@EntityListeners(RegionHibernateEntityListener.class)
public class RegionHibernateEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_region", length=11)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_parent", referencedColumnName = "cod_region", insertable = true, updatable = true, nullable = true)
	private RegionHibernateEntity parent;

	@Column(name = "dsc_name")
	private String name;

	@OneToOne
	@JoinColumn(name = "cod_language", updatable = true, insertable = true)
	private LanguageHibernateEntity language;

	@Column(name = "dsc_alphabet", length=28)
	private String alphabet;

	public RegionHibernateEntity(){
	}

	public RegionHibernateEntity(Integer id){
		this.id = id;
	}
	
	public RegionHibernateEntity(Region e){
		this.alphabet = e.getAlphabet();
		this.id = e.getId() <= 0? null : e.getId();
		this.language = e.getLanguage() == null? null : new LanguageHibernateEntity(e.getLanguage());
		this.name = e.getName();
		this.parent = e.getParent() <= 0? null : new RegionHibernateEntity(e.getParent());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RegionHibernateEntity getParent() {
		return parent;
	}

	public void setParent(RegionHibernateEntity parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LanguageHibernateEntity getLanguage() {
		return language;
	}

	public void setLanguage(LanguageHibernateEntity language) {
		this.language = language;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	public Region toEntity(){
		return this.toEntity(null);
	}
	
	public Region toEntity(Region e){
		if(e == null){
			e = new Region();
		}
		
		e.setAlphabet(this.alphabet);
		e.setId(this.id);
		e.setLanguage(this.language == null? null : this.language.toEntity());
		e.setName(this.name);
		e.setParent(this.parent == null? 0 : this.parent.getId());

		return e;
	}
}
