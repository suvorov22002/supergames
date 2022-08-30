package com.pyramid.dev.model;

import java.io.Serializable;

public class ShiftDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double cashink;
	private double cashinp;
	private double cashind;
	private double cashinb;
	private double cashinf;
	private double cashoutk;
	private double cashoutp;
	private double cashoutd;
	private double cashoutb;
	private double cashoutf;
	// nombre de ticket
	private int slipk; 
	private int slipp;
	private int slipd;
	private int slipb;
	private int slipf;
	// nombre de ticket payés
	private int vslipk; 
	private int vslipp;
	private int vslipd;
	private int vslipb;
	private int vslipf;
	
	private double balancein;
	private double balanceout;
	
	public ShiftDay() {
		super();
		cashink = 0d;
		cashinp = 0d;
		cashind = 0d;
		cashinb = 0d;
		cashinf = 0d;
		cashoutk = 0d;
		cashoutp = 0d;
		cashoutd = 0d;
		cashoutb = 0d;
		cashoutf = 0d;
		slipk = 0;
		slipp = 0;
		slipd = 0;
		slipb = 0;
		slipf = 0;
		vslipk = 0;
		vslipp = 0;
		vslipd = 0;
		vslipb = 0;
		vslipf = 0;
		
	}

	public double getCashink() {
		return cashink;
	}

	public void setCashink(double cashink) {
		this.cashink = cashink;
	}

	public double getCashinp() {
		return cashinp;
	}

	public void setCashinp(double cashinp) {
		this.cashinp = cashinp;
	}

	public double getCashind() {
		return cashind;
	}

	public void setCashind(double cashind) {
		this.cashind = cashind;
	}

	public double getCashinb() {
		return cashinb;
	}

	public void setCashinb(double cashinb) {
		this.cashinb = cashinb;
	}

	public double getCashinf() {
		return cashinf;
	}

	public void setCashinf(double cashinf) {
		this.cashinf = cashinf;
	}

	public double getCashoutk() {
		return cashoutk;
	}

	public void setCashoutk(double cashoutk) {
		this.cashoutk = cashoutk;
	}

	public double getCashoutp() {
		return cashoutp;
	}

	public void setCashoutp(double cashoutp) {
		this.cashoutp = cashoutp;
	}

	public double getCashoutd() {
		return cashoutd;
	}

	public void setCashoutd(double cashoutd) {
		this.cashoutd = cashoutd;
	}

	public double getCashoutb() {
		return cashoutb;
	}

	public void setCashoutb(double cashoutb) {
		this.cashoutb = cashoutb;
	}

	public double getCashoutf() {
		return cashoutf;
	}

	public void setCashoutf(double cashoutf) {
		this.cashoutf = cashoutf;
	}

	public int getSlipk() {
		return slipk;
	}

	public void setSlipk(int slipk) {
		this.slipk = slipk;
	}

	public int getSlipp() {
		return slipp;
	}

	public void setSlipp(int slipp) {
		this.slipp = slipp;
	}

	public int getSlipd() {
		return slipd;
	}

	public void setSlipd(int slipd) {
		this.slipd = slipd;
	}

	public int getSlipb() {
		return slipb;
	}

	public void setSlipb(int slipb) {
		this.slipb = slipb;
	}

	public int getSlipf() {
		return slipf;
	}

	public void setSlipf(int slipf) {
		this.slipf = slipf;
	}

	public int getVslipk() {
		return vslipk;
	}

	public void setVslipk(int vslipk) {
		this.vslipk = vslipk;
	}

	public int getVslipp() {
		return vslipp;
	}

	public void setVslipp(int vslipp) {
		this.vslipp = vslipp;
	}

	public int getVslipd() {
		return vslipd;
	}

	public void setVslipd(int vslipd) {
		this.vslipd = vslipd;
	}

	public int getVslipb() {
		return vslipb;
	}

	public void setVslipb(int vslipb) {
		this.vslipb = vslipb;
	}

	public int getVslipf() {
		return vslipf;
	}

	public void setVslipf(int vslipf) {
		this.vslipf = vslipf;
	}

	public double getBalancein() {
		return balancein;
	}

	public void setBalancein(double balancein) {
		this.balancein = balancein;
	}

	public double getBalanceout() {
		return balanceout;
	}

	public void setBalanceout(double balanceout) {
		this.balanceout = balanceout;
	}
	
	
	

}
