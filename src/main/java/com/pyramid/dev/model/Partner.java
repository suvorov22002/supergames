package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.enums.Room;

@Entity
@Table(name = "partner")
public class Partner implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idpartner;
	
	@Column(nullable=false, unique=true)
	private String coderace;
	@Column
	private String zone;
	@Column(nullable=false, columnDefinition="int default 600")
	private double bonuskamount;
	@Column(nullable=false, columnDefinition="int default 0")
	private int bonuskcode;
	@Column(nullable=false, columnDefinition="int default 0")
	private int bonuspcode;
	@Column(nullable=false, columnDefinition="int default 0")
	private int bonusrcode;
	@Column(nullable=false, columnDefinition="int default 0")
	private int bonusbcode;
	@Column(nullable=false, columnDefinition="int default 0")
	private int bonusdcode;
	@Column(nullable=false, columnDefinition="int default 600")
	private double bonusBamount;
	@Column(nullable=false, columnDefinition="int default 600")
	private double bonusRamount;
	@Column(nullable=false, columnDefinition="int default 600")
	private double bonusDamount;
	@Column(nullable=false, columnDefinition="int default 600")
	private double bonusPamount;
	
	@Enumerated(EnumType.STRING)
	private Room cob = Room.CLOSED;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idGroupe")
	private Groupe groupe;
	
	@JsonIgnore
//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "config_id", referencedColumnName = "idconfig")
	@OneToOne(fetch = FetchType.EAGER,
    cascade =  CascadeType.ALL,
    mappedBy = "coderace")
	private Config config;
	
	@Column
	private int actif;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Partner() {
		super();
	}

	

	public Long getIdpartner() {
		return idpartner;
	}

	public void setIdpartner(Long idpartner) {
		this.idpartner = idpartner;
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public double getBonuskamount() {
		return bonuskamount;
	}

	public void setBonuskamount(double bonuskamount) {
		this.bonuskamount = bonuskamount;
	}

	public int getBonuskcode() {
		return bonuskcode;
	}

	public void setBonuskcode(int bonuskcode) {
		this.bonuskcode = bonuskcode;
	}

	public int getBonuspcode() {
		return bonuspcode;
	}

	public void setBonuspcode(int bonuspcode) {
		this.bonuspcode = bonuspcode;
	}

	public int getBonusrcode() {
		return bonusrcode;
	}

	public void setBonusrcode(int bonusrcode) {
		this.bonusrcode = bonusrcode;
	}

	public int getBonusbcode() {
		return bonusbcode;
	}

	public void setBonusbcode(int bonusbcode) {
		this.bonusbcode = bonusbcode;
	}

	public int getBonusdcode() {
		return bonusdcode;
	}

	public void setBonusdcode(int bonusdcode) {
		this.bonusdcode = bonusdcode;
	}

	public double getBonusBamount() {
		return bonusBamount;
	}

	public void setBonusBamount(double bonusBamount) {
		this.bonusBamount = bonusBamount;
	}

	public double getBonusRamount() {
		return bonusRamount;
	}

	public void setBonusRamount(double bonusRamount) {
		this.bonusRamount = bonusRamount;
	}

	public double getBonusDamount() {
		return bonusDamount;
	}

	public void setBonusDamount(double bonusDamount) {
		this.bonusDamount = bonusDamount;
	}

	public double getBonusPamount() {
		return bonusPamount;
	}

	public void setBonusPamount(double bonusPamount) {
		this.bonusPamount = bonusPamount;
	}

	public Room getCob() {
		return cob;
	}

	public void setCob(Room cob) {
		this.cob = cob;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}
	
	
	
	public int getActif() {
		return actif;
	}

	public void setActif(int actif) {
		this.actif = actif;
	}

	@Override
	public String toString() {
		return "Partner [coderace=" + coderace + ", zone=" + zone + ", id= "+ idpartner + " ,Bonusk= "+bonuskamount+"]";
	}
	
}
