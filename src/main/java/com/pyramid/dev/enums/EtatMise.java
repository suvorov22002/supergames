package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum EtatMise {

	ATTENTE("ATTENTE"),
	PERDANT("PERDANT"),
	GAGNANT("GAGNANT"),
	BALANCE("Solde insuffisant"),
	UNKNOWPARTNER("Partenaire inconnu"),
	UNKNOWNCASHIER("Caissier inconnu"),
	UNKNOWNDRAW("Tirage non reconnu"),
	TCKINCON("TICKET INCONNU"),
	TCKNEVAL("TICKET NON EVALUE"),
	TCKNRECON("TICKET NON RECONNU"),
	TCKNREG("TICKET NON ENREGISTRE"),
	TCKALRPAID("TICKET ALREADY PAID");

	private String value;
	private static final List<EtatMise> statutCoupon = new ArrayList<>();

	static {
		statutCoupon.add(ATTENTE);
		statutCoupon.add(PERDANT);
		statutCoupon.add(GAGNANT);
		statutCoupon.add(BALANCE);
		statutCoupon.add(UNKNOWPARTNER);
		statutCoupon.add(UNKNOWNCASHIER);
		statutCoupon.add(UNKNOWNDRAW);
		statutCoupon.add(TCKINCON);
		statutCoupon.add(TCKALRPAID);
		statutCoupon.add(TCKNEVAL);
		statutCoupon.add(TCKNRECON);
		statutCoupon.add(TCKNREG);
	}

	private EtatMise(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}

	public static List<EtatMise> statutCoupon(){
		return statutCoupon;
	}
}
