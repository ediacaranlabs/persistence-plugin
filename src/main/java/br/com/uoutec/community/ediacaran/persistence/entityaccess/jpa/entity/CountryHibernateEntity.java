package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.uoutec.community.ediacaran.persistence.entity.Country;

@Entity
@Table(name="rw_country")
public class CountryHibernateEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "cod_country", length=2)
	private Integer id;

	@Column(name = "dsc_name")
	private String name;

	@Column(name = "num_uni")
	private Integer uni;

	@Column(name = "num_ufi")
	private Integer ufi;
	
	@Column(name = "dsc_iso_alpha2")
	private String isoAlpha2;

	@Column(name = "dsc_iso_alpha3")
	private String isoAlpha3;

	@Column(name = "dsc_tld")
	private String tld;

	@Column(name = "dsc_iso_4217")
	private String iso4217;

	@OneToOne
	@JoinColumn(name = "cod_language", updatable = true, insertable = true)
	private LanguageHibernateEntity language;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cod_region")
	private RegionHibernateEntity region;

	public CountryHibernateEntity(){
	}
	
	public CountryHibernateEntity(Country e){
		this.id = e.getId() <= 0? null : e.getId();
		this.iso4217 = e.getIso4217();
		this.isoAlpha2 = e.getIsoAlpha2();
		this.isoAlpha3 = e.getIsoAlpha3();
		this.name = e.getName();
		this.region = e.getRegion() == null? null : new RegionHibernateEntity(e.getRegion());
		this.language = e.getLanguage() == null? null : new LanguageHibernateEntity(e.getLanguage());
		this.tld = e.getTld();
		this.ufi = e.getUfi() <= 0? null : e.getUfi();
		this.uni = e.getUni() <= 0? null : e.getUni();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUni() {
		return uni;
	}

	public void setUni(Integer uni) {
		this.uni = uni;
	}

	public Integer getUfi() {
		return ufi;
	}

	public void setUfi(Integer ufi) {
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

	public RegionHibernateEntity getRegion() {
		return region;
	}

	public void setRegion(RegionHibernateEntity region) {
		this.region = region;
	}

	public Country toEntity(){
		return this.toEntity(null);
	}
	
	public Country toEntity(Country e){
		
		if(e == null){
			e = new Country();
		}
		
		e.setId(this.id);
		e.setIso4217(this.iso4217);
		e.setIsoAlpha2(this.isoAlpha2);
		e.setIsoAlpha3(this.isoAlpha3);
		e.setName(this.name);
		e.setRegion(this.region == null? null : this.region.toEntity());
		e.setLanguage(this.language == null? null : this.language.toEntity());
		e.setTld(this.tld);
		e.setUfi(this.ufi == null? 0 : this.ufi);
		e.setUni(this.uni == null? 0 : this.uni);
		return e;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CountryHibernateEntity other = (CountryHibernateEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
