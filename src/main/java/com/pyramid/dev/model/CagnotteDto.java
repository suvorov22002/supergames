package com.pyramid.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class CagnotteDto implements Serializable {
	
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Date heur;
	private String lot;
	private String jeu;
	private Partner partner;
	private int mise;
	private long barcode;
	
	public CagnotteDto() {
		super();
	}

	public Date getHeur() {
		return heur;
	}

	public void setHeur(Date heur) {
		this.heur = heur;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getJeu() {
		return jeu;
	}

	public void setJeu(String jeu) {
		this.jeu = jeu;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public int getMise() {
		return mise;
	}

	public void setMise(int mise) {
		this.mise = mise;
	}

	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}
}
