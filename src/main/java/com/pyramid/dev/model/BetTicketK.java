package com.pyramid.dev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.enums.Jeu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class BetTicketK implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1L;

	private Jeu typeJeu;
	private String barcode;
	private double summise;
	private String coderace;
	private String heureMise;
	private String dateMise;
	private int drawnumk;
	private EtatMise etatMise = EtatMise.ATTENTE;
	private int bonusCod;
	private double sumWin = 0d;
	private int archive;
	private String xmulti; //multiplicateur de gain
	private String caissier;
	private Long keno;
	private Long idMiseT;
	private double mvt;  // solde caissier
	private int multiplicite; //tour multiple
	private double cotejeu;
	private int event; //nombre d'evenemnts pour le ticket
	private String paril;
	private String kchoice;
	private String drawResult;
	private boolean bonus = false;
	private String message;
	private Versement vers;
	private List<EffChoicek> listEfchk;
	private boolean cagnotte = false;
	private long bonusAmount;

	@Override
	public String toString() {
		return "BetTicketK [typeJeu=" + typeJeu + ", barcode=" + barcode + ", summise=" + summise + ", Multiplicite="
				+ multiplicite + ", drawnumk=" + drawnumk + ", etatMise=" + etatMise + ", xmulti=" + xmulti + ", caissier="
				+ caissier + ", cotejeu=" + cotejeu + ", kchoice=" + kchoice + ", list_efchk=" + listEfchk.size() + "]";
	}

}
