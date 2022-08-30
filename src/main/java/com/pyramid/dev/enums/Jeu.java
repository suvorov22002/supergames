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
	private static List<Jeu> style_jeu = new ArrayList<Jeu>();
	static {
		style_jeu.add(M);
		style_jeu.add(D);
		style_jeu.add(L);
		style_jeu.add(B);
		style_jeu.add(S);
		style_jeu.add(K);
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
		return style_jeu;
	}
}
