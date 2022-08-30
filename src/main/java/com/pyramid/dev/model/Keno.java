package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="keno",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"drawnumK", "idPartner"})})
public class Keno implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idKeno")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKeno;
	
	@Column
	private String drawnumbK = "'1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20'";
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int bonusKcod;
	
	@Column
	private double bonusKamount = 0d;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int drawnumK;
	
	@Column
	private String heureTirage = "01/01/2015-12:00:00";
	
	@Column
	private String multiplicateur = "0";
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int started;
	
	@JsonIgnore
	@ManyToOne
	//(fetch=FetchType.EAGER)
	@JoinColumn(name="idPartner")
	private Partner partner;
	
	@Column
	private String coderace;

	public Keno() {
		super();
	}

	public Long getIdKeno() {
		return idKeno;
	}

	public void setIdKeno(Long idKeno) {
		this.idKeno = idKeno;
	}

	public String getDrawnumbK() {
		return drawnumbK;
	}

	public void setDrawnumbK(String drawnumbK) {
		this.drawnumbK = drawnumbK;
	}

	public int getBonusKcod() {
		return bonusKcod;
	}

	public void setBonusKcod(int bonusKcod) {
		this.bonusKcod = bonusKcod;
	}

	public double getBonusKamount() {
		return bonusKamount;
	}

	public void setBonusKamount(double bonusKamount) {
		this.bonusKamount = bonusKamount;
	}

	public int getDrawnumK() {
		return drawnumK;
	}

	public void setDrawnumK(int drawnumK) {
		this.drawnumK = drawnumK;
	}

	public String getHeureTirage() {
		return heureTirage;
	}

	public void setHeureTirage(String heureTirage) {
		this.heureTirage = heureTirage;
	}

	public String getMultiplicateur() {
		return multiplicateur;
	}

	public void setMultiplicateur(String multiplicateur) {
		this.multiplicateur = multiplicateur;
	}

	public int getStarted() {
		return started;
	}

	public void setStarted(int started) {
		this.started = started;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public String getCoderace() {
		return this.coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

}
