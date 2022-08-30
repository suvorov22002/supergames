package com.pyramid.dev.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CagnotteDto implements Serializable {
	
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Long idCagnotte;
	private String day;
	private String heur;
	private String lot;
	private Long barcode;
	private String jeu;
	private Long partner;
	private int mise;
	
	public CagnotteDto() {
		super();
	}

	public Long getIdCagnotte() {
		return idCagnotte;
	}

	public void setIdCagnotte(Long idCagnotte) {
		this.idCagnotte = idCagnotte;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHeur() {
		return heur;
	}

	public void setHeur(String heur) {
		this.heur = heur;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public Long getBarcode() {
		return barcode;
	}

	public void setBarcode(Long barcode) {
		this.barcode = barcode;
	}

	public String getJeu() {
		return jeu;
	}

	public void setJeu(String jeu) {
		this.jeu = jeu;
	}

	public Long getPartner() {
		return partner;
	}

	public void setPartner(Long partner) {
		this.partner = partner;
	}

	public int getMise() {
		return mise;
	}

	public void setMise(int mise) {
		this.mise = mise;
	}
	
	
	
	
}
