package com.pyramid.dev.model;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CagnotteDto implements Serializable {
	
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private String heur;
	private String lot;
	private String jeu;
	private Partner partner;
	private int mise;
	
	public CagnotteDto() {
		super();
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
}
