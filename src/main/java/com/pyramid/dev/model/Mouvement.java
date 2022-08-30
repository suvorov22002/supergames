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

@Entity
@Table(name = "mouvement")
public class Mouvement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmvnt")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idmvnt;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caissier", nullable = false)
	private Caissier caissier;
	
	@Column
	private double mvt;

	public Mouvement() {
		super();
	}
	
	public Long getIdmvnt() {
		return idmvnt;
	}

	public void setIdmvnt(Long idmvnt) {
		this.idmvnt = idmvnt;
	}


	public Caissier getCaissier() {
		return caissier;
	}

	public void setCaissier(Caissier caissier) {
		this.caissier = caissier;
	}

	public double getMvt() {
		return mvt;
	}

	public void setMvt(double mvt) {
		this.mvt = mvt;
	}
	
}
