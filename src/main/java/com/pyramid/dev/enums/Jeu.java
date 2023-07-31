package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum Jeu {
	
	M("Match"),
	D("Dog"),
	L("Loto"),
	B("Bingo"),
	S("Spin"),
	K("Keno");
	
	private String value;
	private static List<Jeu> styleJeu = new ArrayList<>();
	static {
		styleJeu.add(M);
		styleJeu.add(D);
		styleJeu.add(L);
		styleJeu.add(B);
		styleJeu.add(S);
		styleJeu.add(K);
	}
	
	private Jeu(String value) {
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
	
	public static List<Jeu> styleJeu(){
		return styleJeu;
	}
}
