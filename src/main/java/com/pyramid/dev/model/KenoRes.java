package com.pyramid.dev.model;

import java.io.Serializable;

public class KenoRes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String drawnumbK;
	private double bonusKamount = 0d;
	private int drawnumK;
	private String heureTirage;
	private String multiplicateur;
	private int gameState;
	private String str_draw_combi = "";
	private int bonuscod;
	
	public KenoRes() { 
		super();
	}

	public String getDrawnumbK() {
		return drawnumbK;
	}

	public void setDrawnumbK(String drawnumbK) {
		this.drawnumbK = drawnumbK;
	}

	public double getBonusKamount() {
		return bonusKamount;
	}

	public void setBonusKamount(double bonusKamount) {
		this.bonusKamount = bonusKamount;
	}

	public int getDrawnumK() {
		return drawnumK;
	}

	public void setDrawnumK(int drawnumK) {
		this.drawnumK = drawnumK;
	}

	public String getHeureTirage() {
		return heureTirage;
	}

	public void setHeureTirage(String heureTirage) {
		this.heureTirage = heureTirage;
	}

	public String getMultiplicateur() {
		return multiplicateur;
	}

	public void setMultiplicateur(String multiplicateur) {
		this.multiplicateur = multiplicateur;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public String getStr_draw_combi() {
		return str_draw_combi;
	}

	public void setStr_draw_combi(String str_draw_combi) {
		this.str_draw_combi = str_draw_combi;
	}
	
	public int getBonuscod() {
		return bonuscod;
	}

	public void setBonuscod(int bonuscod) {
		this.bonuscod = bonuscod;
	}
	
}
