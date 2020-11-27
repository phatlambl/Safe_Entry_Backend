package com.example.demo.model.databaseupdate;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/*
 * 
 * Embeddable
 * check modify database user * 
 * 
 * */

@Entity
@Table(name ="database_update")
public class DatabaseUpdate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "last_modify")
	private Long last_modify;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getLast_modify() {
		return last_modify;
	}

	public void setLast_modify(Long last_modify) {
		this.last_modify = last_modify;
	}

	public DatabaseUpdate(int id, Long last_modify) {		
		this.id = id;
		this.last_modify = last_modify;
	}

	public DatabaseUpdate() {
		
	}

	
	
	
	
	
	
	
}
