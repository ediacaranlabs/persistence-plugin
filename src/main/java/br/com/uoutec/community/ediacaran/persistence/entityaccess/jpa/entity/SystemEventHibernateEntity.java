package br.com.uoutec.community.ediacaran.persistence.entityaccess.jpa.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.uoutec.community.ediacaran.system.event.SystemEvent;
import br.com.uoutec.community.ediacaran.system.event.SystemEventType;

@Entity
@Table(name="rw_system_event")
@EntityListeners(SystemEventHibernateEntityListener.class)
public class SystemEventHibernateEntity implements Serializable{

	private static transient final long serialVersionUID = -816509735915090284L;

	@Id
	@Column(name="cod_system_event", length=32)
	private String id;

	@Column(name="set_type", length=38)
	@Enumerated(EnumType.STRING)
	private SystemEventType type;

	@Column(name="set_group", length=38)
	private String group;

	@Column(name="set_subgroup", length=38)
	private String subgroup;
	
	@Column(name="dat_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Lob
	@Column(name="dsc_message")
	private String message;

	public SystemEventHibernateEntity(){
	}
	
	public SystemEventHibernateEntity(SystemEvent e){
		this.group = e.getGroup();
		this.subgroup = e.getSubgroup();
		this.date = e.getDate();
		this.id = e.getId();
		this.message = e.getMessage();
		this.type = e.getType();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SystemEventType getType() {
		return type;
	}

	public void setType(SystemEventType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public SystemEvent toEntity(){
		return this.toEntity(null);
	}
	
	public SystemEvent toEntity(SystemEvent e){
		if(e == null){
			e = new SystemEvent();
		}
		
		e.setGroup(this.group);
		e.setSubgroup(this.subgroup);
		e.setDate(this.date);
		e.setId(this.id);
		e.setMessage(this.message);
		e.setType(this.type);
		
		return e;
	}
	
	
}
