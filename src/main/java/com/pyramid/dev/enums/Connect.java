package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum Connect {
	CONNECTED("Connected"),
	NOTCONNECTED("NotConnected");
	
	private String value;
	private static List<Connect> statut_caisse = new ArrayList<Connect>();
	static {
		statut_caisse.add(CONNECTED);
		statut_caisse.add(NOTCONNECTED);
	}
	
	private Connect(String value) {
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
	
	public static List<Connect> statutCaisse(){
		return statut_caisse;
	}
}
