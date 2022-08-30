package com.pyramid.dev.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.enums.Jeu;

public class BetTicketK implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	
	private Jeu typeJeu;
	private String barcode;
	private double summise;
	private Long idPartner;  
//	private EffChoicek efchk;
	private String heureMise;
	private String dateMise;
	private int drawnumk;
	private EtatMise etatMise = EtatMise.ATTENTE;
	private int bonusCod;
	private double sumWin = 0d;
	private int archive;
	private int xmulti; //multiplicateur de gain
	private Long caissier;
	private Long keno;
	private Long idMiseT;
	private double mvt;
	private int multiplicite; //tour multiple
	private double cotejeu;
	private int event; //nombre d'evenemnts pour le ticket
	private String paril;
	private String kchoice;
	private String draw_result;
	private boolean bonus = false;
	private String message;
	private Versement vers;
	private List<EffChoicek> list_efchk = new ArrayList<EffChoicek>();
	
	public BetTicketK() {
		super();
	}

	public Jeu getTypeJeu() {
		return typeJeu;
	}

	public void setTypeJeu(Jeu typeJeu) {
		this.typeJeu = typeJeu;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public double getSummise() {
		return summise;
	}

	public void setSummise(double summise) {
		this.summise = summise;
	}
	
	public Long getIdPartner() {
		return idPartner;
	}

	public void setIdPartner(Long idPartner) {
		this.idPartner = idPartner;
	}

//	public EffChoicek getEfchk() {
//		return efchk;
//	}
//
//	public void setEfchk(EffChoicek efchk) {
//		this.efchk = efchk;
//	}

	public String getHeureMise() {
		return heureMise;
	}

	public void setHeureMise(String heureMise) {
		this.heureMise = heureMise;
	}

	public String getDateMise() {
		return dateMise;
	}

	public void setDateMise(String dateMise) {
		this.dateMise = dateMise;
	}

	public int getDrawnumk() {
		return drawnumk;
	}

	public void setDrawnumk(int drawnumk) {
		this.drawnumk = drawnumk;
	}

	public EtatMise getEtatMise() {
		return etatMise;
	}

	public void setEtatMise(EtatMise etatMise) {
		this.etatMise = etatMise;
	}

	public int getBonusCod() {
		return bonusCod;
	}

	public void setBonusCod(int bonusCod) {
		this.bonusCod = bonusCod;
	}

	public double getSumWin() {
		return sumWin;
	}

	public void setSumWin(double sumWin) {
		this.sumWin = sumWin;
	}

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public int getXmulti() {
		return xmulti;
	}

	public void setXmulti(int xmulti) {
		this.xmulti = xmulti;
	}

	public Long getCaissier() {
		return caissier;
	}

	public void setCaissier(Long caissier) {
		this.caissier = caissier;
	}

	public Long getKeno() {
		return keno;
	}

	public void setKeno(Long keno) {
		this.keno = keno;
	}

	public Long getIdMiseT() {
		return idMiseT;
	}

	public void setIdMiseT(Long idMiseT) {
		this.idMiseT = idMiseT;
	}

	public double getMvt() {
		return mvt;
	}

	public void setMvt(double mvt) {
		this.mvt = mvt;
	}

	public int getMultiplicite() {
		return multiplicite;
	}

	public void setMultiplicite(int multiplicite) {
		this.multiplicite = multiplicite;
	}

	public double getCotejeu() {
		return cotejeu;
	}

	public void setCotejeu(double cotejeu) {
		this.cotejeu = cotejeu;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

	public String getParil() {
		return paril;
	}

	public void setParil(String paril) {
		this.paril = paril;
	}

	public String getKchoice() {
		return kchoice;
	}

	public void setKchoice(String kchoice) {
		this.kchoice = kchoice;
	}

	public String getDraw_result() {
		return draw_result;
	}

	public void setDraw_result(String draw_result) {
		this.draw_result = draw_result;
	}

	public List<EffChoicek> getList_efchk() {
		return list_efchk;
	}

	public void setList_efchk(List<EffChoicek> list_efchk) {
		this.list_efchk = list_efchk;
	}

	public boolean isBonus() {
		return bonus;
	}

	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Versement getVers() {
		return vers;
	}

	public void setVers(Versement vers) {
		this.vers = vers;
	}
	
}
