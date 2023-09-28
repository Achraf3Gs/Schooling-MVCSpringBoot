package com.gsc.ams.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Pupil {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(message = "Label is mandatory")
	@Column(name = "label")
	private String label;
     
	//@NotBlank(message = "Price is mandatory")
	@Column(name = "tall")
	private  float tall;

	
	@Column(name = "picture")
	 private String picture;
	
	
	
	
	
	public Pupil() {
	}

	public Pupil(String label, float tall) {
		this.tall = tall;
		this.label = label;
	}
	public Pupil(String label, float tall, String picture) {
		 this.tall = tall;
		 this.label = label;
		 this.picture = picture;
		 }
	
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPicture() {
	return picture;
	

	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public float getTall() {
		return tall;
	}

	public void setTall(float tall) {
		this.tall = tall;
	}

	/**** Many To One ****/
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "teacher_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Teacher teacher;

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}