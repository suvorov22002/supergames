package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Misek_temp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idMsktmp")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTmp;
	@Column
	private int multi;
	@Column
	private double sumMise;
	@Column
	private int etatMise;
	@Column(nullable=false, unique=true)
	private Long idmisek;

	public Misek_temp() {
		super();
	}

	public Long getIdTmp() {
		return idTmp;
	}

	public void setIdTmp(Long idTmp) {
		this.idTmp = idTmp;
	}

	public int getMulti() {
		return multi;
	}

	public void setMulti(int multi) {
		this.multi = multi;
	}

	public double getSumMise() {
		return sumMise;
	}

	public void setSumMise(double sumMise) {
		this.sumMise = sumMise;
	}

	public int getEtatMise() {
		return etatMise;
	}

	public void setEtatMise(int etatMise) {
		this.etatMise = etatMise;
	}

	public Long getIdmisek() {
		return idmisek;
	}

	public void setIdmisek(Long idmisek) {
		this.idmisek = idmisek;
	}
	
}
