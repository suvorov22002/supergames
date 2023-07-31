package com.pyramid.dev.model;

import java.io.Serializable;

public class GameCycleDto implements Serializable{

	private double percent;
	private int tour;
	private int hitfrequence;
	private int refundp;
	private int position;
	private String arrangement;
	private Long partner;
	private String jeu;
	private long mise;
	private long misef;
	private int archive;
	private String date_fin;
	private double curr_percent;
	private double stake;
	private double payout;
	private double jkpt;
	private double real_percent;

	public GameCycleDto() {
		// TODO document why this constructor is empty
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public int getHitfrequence() {
		return hitfrequence;
	}

	public void setHitfrequence(int hitfrequence) {
		this.hitfrequence = hitfrequence;
	}

	public int getRefundp() {
		return refundp;
	}

	public void setRefundp(int refundp) {
		this.refundp = refundp;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getArrangement() {
		return arrangement;
	}

	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

	public Long getPartner() {
		return partner;
	}

	public void setPartner(Long partner) {
		this.partner = partner;
	}

	public String getJeu() {
		return jeu;
	}

	public void setJeu(String jeu) {
		this.jeu = jeu;
	}

	public long getMise() {
		return mise;
	}

	public void setMise(long mise) {
		this.mise = mise;
	}

	public long getMisef() {
		return misef;
	}

	public void setMisef(long misef) {
		this.misef = misef;
	}

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public double getCurr_percent() {
		return curr_percent;
	}

	public void setCurr_percent(double curr_percent) {
		this.curr_percent = curr_percent;
	}

	public double getStake() {
		return stake;
	}

	public void setStake(double stake) {
		this.stake = stake;
	}

	public double getPayout() {
		return payout;
	}

	public void setPayout(double payout) {
		this.payout = payout;
	}

	public double getJkpt() {
		return jkpt;
	}

	public void setJkpt(double jkpt) {
		this.jkpt = jkpt;
	}

	public double getReal_percent() {
		return real_percent;
	}

	public void setReal_percent(double real_percent) {
		this.real_percent = real_percent;
	}

	public GameCycleDto transToGameCycle(GameCycle c) {
		this.archive = c.getArchive();
		this.arrangement = c.getArrangement();
		this.curr_percent = c.getCurr_percent();
		//this.real_percent = c.getPayout()/c.getStake();
		this.date_fin = c.getDate_fin().substring(0, 10);
		this.hitfrequence = c.getHitfrequence();
		this.jeu = c.getJeu().getValue();
		this.jkpt = c.getJkpt();
		this.mise = c.getMise();
		this.misef = c.getMisef();
		//System.out.println("c.getPartner().getIdPartner() "+c.getPartner().getIdPartner());
		this.partner = c.getPartner().getIdpartner();
		this.payout = c.getPayout();
		this.percent = c.getPercent();
		this.position = c.getPosition();
		this.refundp = c.getRefundp();
		this.stake = c.getStake();
		this.tour = c.getTour();

		if (c.getStake() != 0) {
			this.real_percent = (this.refundp + c.getPayout() + this.jkpt)/c.getStake();
			//this.real_percent = Double.parseDouble(String.format("%.2f", this.real_percent));
			this.real_percent = (double)((int)(this.real_percent*100))/100;
		} else {
			this.real_percent = 0;
		}
		return this;
	}

}
