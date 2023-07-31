package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum Profil {
	
	ADMINISTRATEUR("Admin"),
	CAISSIER("Caissier");
	
	private String value;
	private static List<Profil> profils = new ArrayList<>();
	
	static {
		
		profils.add(ADMINISTRATEUR);
		profils.add(CAISSIER);
		
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
		return profils;
	}
}
