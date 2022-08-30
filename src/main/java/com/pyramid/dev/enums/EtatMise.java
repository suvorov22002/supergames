package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum EtatMise {
	
	ATTENTE("ATTENTE"),
	PERDANT("PERDANT"),
	GAGNANT("GAGNANT");
	
	private String value;
	private static List<EtatMise> statut_coupon = new ArrayList<EtatMise>();
	static {
		statut_coupon.add(ATTENTE);
		statut_coupon.add(PERDANT);
		statut_coupon.add(GAGNANT);
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
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public static List<EtatMise> statutCoupon(){
		return statut_coupon;
	}
}
