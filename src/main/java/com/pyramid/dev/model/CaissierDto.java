package com.pyramid.dev.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CaissierDto implements Serializable{
	
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	private Long idCaissier;
	private Long profil;
	private String nomc;
	private String loginc;
	private String mdpc;
	private Long partner;
	private String statut;
	private int grpe;
	private double airtime;
	
	public CaissierDto() {
		super();
	}

	public Long getIdCaissier() {
		return idCaissier;
	}

	public void setIdCaissier(Long idCaissier) {
		this.idCaissier = idCaissier;
	}

	public Long getProfil() {
		return profil;
	}

	public void setProfil(Long profil) {
		this.profil = profil;
	}

	public String getNomc() {
		return nomc;
	}

	public void setNomc(String nomc) {
		this.nomc = nomc;
	}

	public String getLoginc() {
		return loginc;
	}

	public void setLoginc(String loginc) {
		this.loginc = loginc;
	}

	public String getMdpc() {
		return mdpc;
	}

	public void setMdpc(String mdpc) {
		this.mdpc = mdpc;
	}

	public Long getPartner() {
		return partner;
	}

	public void setPartner(Long partner) {
		this.partner = partner;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public int getGrpe() {
		return grpe;
	}

	public void setGrpe(int grpe) {
		this.grpe = grpe;
	}

	public double getAirtime() {
		return airtime;
	}

	public void setAirtime(double airtime) {
		this.airtime = airtime;
	}
	
	public CaissierDto transToCaissier(Caissier c) {
		this.idCaissier = c.getIdCaissier();
		this.nomc = c.getNomC();
		this.loginc = c.getLoginc();
		this.partner = c.getPartner().getIdpartner();
		this.profil = c.getProfil().getId();
		this.mdpc = c.getMdpc();
		this.statut = "C";
		return this;
	}
}
