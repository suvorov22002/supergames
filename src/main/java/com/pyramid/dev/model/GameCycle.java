package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.pyramid.dev.enums.Jeu;

@Entity
@Table(name = "game_cycle",
       uniqueConstraints = {
		@UniqueConstraint(columnNames = {"idPartner", "jeu","mise","archive","idcycle"})})
public class GameCycle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idcycle")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idcycle;
	
	@Column
	private double percent = 90d;
	
	@Column
	private double curr_percent = 0d;
	
	@Column(nullable=false, columnDefinition="int default 100")
	private int tour;
	
	@Column(nullable=false, columnDefinition="int default 20")
	private int hitfrequence;
	
	@Column
	private int refundp = 0;
	
	@Column
	private double stake = 0d;
	
	@Column
	private double payout = 0d;
	
	@Column
	private double jkpt = 0d;
	
	@Column(nullable=false, columnDefinition="int default 1")
	private int position;
	
	@Column(length=65535, columnDefinition="TEXT")
	@Type(type="text")
	private String arrangement = "1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20";
	
	@Column(nullable=false)
	private Long mise;
	
	@Column(nullable=false)
	private Long misef;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int archive;
	
	@Enumerated(EnumType.STRING)
	private Jeu jeu;
	
	@Column
	private String date_fin = "01/01/2015-12:00:00";;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double real_percent = 0d;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idPartner")
    private Partner partner;

	public GameCycle() {
		super();
	}

	public Long getIdcycle() {
		return idcycle;
	}

	public void setIdcycle(Long idcycle) {
		this.idcycle = idcycle;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getCurr_percent() {
		return curr_percent;
	}

	public void setCurr_percent(double curr_percent) {
		this.curr_percent = curr_percent;
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

	public Long getMise() {
		return mise;
	}

	public void setMise(Long mise) {
		this.mise = mise;
	}

	public Long getMisef() {
		return misef;
	}

	public void setMisef(Long misef) {
		this.misef = misef;
	}

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public double getReal_percent() {
		return real_percent;
	}

	public void setReal_percent(double real_percent) {
		this.real_percent = real_percent;
	}

	@Override
	public String toString() {
		return "GameCycle [percent=" + percent + ", tour=" + tour + ", hitfrequence=" + hitfrequence + ", refundp="
				+ refundp + ", mise=" + mise + ", misef=" + misef + ", archive=" + archive + ", date_fin=" + date_fin
				+ ", partner=" + partner + "]";
	}
	
	
	
}
