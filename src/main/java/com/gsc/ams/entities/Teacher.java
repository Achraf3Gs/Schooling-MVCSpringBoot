package com.gsc.ams.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(message = "Name is mandatory")
	@Column(name = "name")
	private String name;

	@NotBlank(message = "Address is mandatory")
	@Column(name = "address")
	private String address;

	@NotBlank(message = "Email is mandatory")
	@Column(name = "email")
	private String email;
	
	@Column(name = "picture")
	 private String picture;

	public Teacher() {
	}

	public Teacher(String name, String address, String email) {
		this.name = name;
		this.address = address;
		this.email = email;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "teacher")
	private List<Pupil> pupils;

	public List<Pupil> getPupils() {
		return pupils;
	}

	public void setPupils(List<Pupil> Pupils) {
		this.pupils = pupils;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPicture() {
	return picture;
	

	}

}
