package com.covid19.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity

@Table(name = "Hospital")
public class Hospital implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id // Specifies That hospitalId is primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // Incrementing the hospitalId automatically
	@Column(name = "hospital_id")
	private int hospitalId;

	
	@Column(name = "hospital_name", length = 50)
	private String hospitalName;

	
	/*
	 * Giving Relationship Between Hospital and hospitalZone as many to one show
	 * that many hospital can have one zone
	*/
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "zone_id") // Specifying zoneId as Foreign key of Hospital
	private HospitalZone hospitalZone;


	/*
	 * Giving Relationship Between Hospital and hospitalType as many to one show
	 * that many hospital can have one Type
	 */
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id") // Specifying typeId as Foreign key of Hospital
	private HospitalType hospitalType;


	@Column(name = "general_bed")
	private int hospitalGeneralBed;

	@Column(name = "icu_bed")
	private int hospitalIcuBed;

//	Giving ManyToMany Relation Between Admin and Hospital so that Many hospital have many admins
	@ManyToMany(mappedBy = "hospitals", fetch = FetchType.LAZY)
	private List<Admin> admins;

	
	// Giving OneToMany Relation Between Hospital and Patients so that One hospital
	// have many Patients
	
	@OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private List<Patient> patients;
	
	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public HospitalZone getHospitalZone() {
		return hospitalZone;
	}

	
	public void setHospitalZone(HospitalZone hospitalZone) {
		this.hospitalZone = hospitalZone;
	}

	
	public HospitalType getHospitalType() {
		return hospitalType;
	}

	
	public void setHospitalType(HospitalType hospitalType) {
		this.hospitalType = hospitalType;
	}

	public int getHospitalGeneralBed() {
		return hospitalGeneralBed;
	}

	public void setHospitalGeneralBed(int hospitalGeneralBed) {
		this.hospitalGeneralBed = hospitalGeneralBed;
	}

	public int getHospitalICUBed() {
		return hospitalIcuBed;
	}

	public void setHospitalICUBed(int hospitalICUBed) {
		this.hospitalIcuBed = hospitalICUBed;
	}

	
	public List<Admin> getAdmins() {
		return admins;
	}

	
	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
	}

	
	public List<Patient> getPatients() {
		return patients;
	}

	
	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}
}

