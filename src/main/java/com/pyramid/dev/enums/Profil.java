package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum Profil {
	
	ADMINISTRATEUR("Admin"),
	CAISSIER("Caissier");
	
	private String value;
	private static List<Profil> profil = new ArrayList<>();
	
	static {
		
		profil.add(ADMINISTRATEUR);
		profil.add(CAISSIER);
		
	}
	
	private Profil(String value) {
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
	
	public static List<Profil> listeProfil(){
		return profil;
	}
}
