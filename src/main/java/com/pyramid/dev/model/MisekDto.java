package com.pyramid.dev.model;

import java.io.Serializable;

public class MisekDto implements Serializable {
	
	private Long idmisek;
	private String idCaissier;
	private String heurMise;
	private String sumMise;
	private String datMise;
	private String etatMise;
	private String drawNumK;
	private String bonusCodeK; //code bonus du ticket 
	private String idMiset;
	private String idKeno;
	private double sumWin;
	private int xmulti;
	
	public MisekDto() {
		super();
	}

	public Long getIdmisek() {
		return idmisek;
	}

	public void setIdmisek(Long idmisek) {
		this.idmisek = idmisek;
	}

	public String getIdCaissier() {
		return idCaissier;
	}

	public void setIdCaissier(String idCaissier) {
		this.idCaissier = idCaissier;
	}

	public String getHeurMise() {
		return heurMise;
	}

	public void setHeurMise(String heurMise) {
		this.heurMise = heurMise;
	}

	public String getSumMise() {
		return sumMise;
	}

	public void setSumMise(String sumMise) {
		this.sumMise = sumMise;
	}

	public String getDatMise() {
		return datMise;
	}

	public void setDatMise(String datMise) {
		this.datMise = datMise;
	}

	public String getEtatMise() {
		return etatMise;
	}

	public void setEtatMise(String etatMise) {
		this.etatMise = etatMise;
	}

	public String getDrawNumK() {
		return drawNumK;
	}

	public void setDrawNumK(String drawNumK) {
		this.drawNumK = drawNumK;
	}

	public String getBonusCodeK() {
		return bonusCodeK;
	}

	public void setBonusCodeK(String bonusCodeK) {
		this.bonusCodeK = bonusCodeK;
	}

	public String getIdMiset() {
		return idMiset;
	}

	public void setIdMiset(String idMiset) {
		this.idMiset = idMiset;
	}

	public String getIdKeno() {
		return idKeno;
	}

	public void setIdKeno(String idKeno) {
		this.idKeno = idKeno;
	}

	public double getSumWin() {
		return sumWin;
	}

	public void setSumWin(double sumWin) {
		this.sumWin = sumWin;
	}

	public int getXmulti() {
		return xmulti;
	}

	public void setXmulti(int xmulti) {
		this.xmulti = xmulti;
	}
	
	public MisekDto transToMisek(Misek m) {
		this.idCaissier = String.valueOf(m.getCaissier().getIdCaissier());
		this.idKeno = String.valueOf(m.getKeno().getIdKeno());
		this.idmisek = m.getIdMiseK();
		this.idMiset = String.valueOf(m.getMiset().getIdMiseT());
		this.heurMise = m.getHeureMise();
		this.sumMise = String.valueOf(m.getSumMise());
		this.sumWin = m.getSumWin();
		this.bonusCodeK = String.valueOf(m.getBonusCod());
		this.datMise = m.getDateMise().replace('/', '-').substring(0, 10);
		this.drawNumK = String.valueOf(m.getDrawnumk());
		this.xmulti = m.getXmulti();
		this.etatMise = String.valueOf(m.getEtatMise());
		return this;
	}
	
}
