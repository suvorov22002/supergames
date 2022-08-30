package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "config")
public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idconfig")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idconfig;
	
	@Column
	private double percent = 0.92d;
	
	@Column
	private double bonusrate = 0.02d;
	@Column
	private double bnkmin = 600d;
	@Column
	private double bnkmax = 5000d;
	@Column
	private double bnpmin = 600d;
	@Column
	private double bnpmax = 5000d;
	@Column
	private double bndmin = 600d;
	@Column
	private double bndmax = 5000d;
	
	@JsonIgnore
//	@OneToOne(mappedBy = "config")
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partner_id", nullable = false)
	private Partner coderace;

	public Config() {
		super();
	}

	public Long getIdconfig() {
		return idconfig;
	}

	public void setIdconfig(Long idconfig) {
		this.idconfig = idconfig;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getBonusrate() {
		return bonusrate;
	}

	public void setBonusrate(double bonusrate) {
		this.bonusrate = bonusrate;
	}

	public double getBnkmin() {
		return bnkmin;
	}

	public void setBnkmin(double bnkmin) {
		this.bnkmin = bnkmin;
	}

	public double getBnkmax() {
		return bnkmax;
	}

	public void setBnkmax(double bnkmax) {
		this.bnkmax = bnkmax;
	}

	public double getBnpmin() {
		return bnpmin;
	}

	public void setBnpmin(double bnpmin) {
		this.bnpmin = bnpmin;
	}

	public double getBnpmax() {
		return bnpmax;
	}

	public void setBnpmax(double bnpmax) {
		this.bnpmax = bnpmax;
	}

	public double getBndmin() {
		return bndmin;
	}

	public void setBndmin(double bndmin) {
		this.bndmin = bndmin;
	}

	public double getBndmax() {
		return bndmax;
	}

	public void setBndmax(double bndmax) {
		this.bndmax = bndmax;
	}

	public Partner getCoderace() {
		return coderace;
	}

	public void setCoderace(Partner coderace) {
		this.coderace = coderace;
	}

}
