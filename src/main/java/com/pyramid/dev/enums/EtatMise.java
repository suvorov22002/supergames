package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum EtatMise {

	ATTENTE("ATTENTE"),
	PERDANT("PERDANT"),
	GAGNANT("GAGNANT");

	private String value;
	private static List<EtatMise> statutCoupon = new ArrayList<>();

	static {
		statutCoupon.add(ATTENTE);
		statutCoupon.add(PERDANT);
		statutCoupon.add(GAGNANT);
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
		return statutCoupon;
	}
}
