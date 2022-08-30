package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table(name = "cagnotte")
public class Cagnotte implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idCagnotte")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idCagnotte;
	
	@Column
	private String day;
	
	@Column
	private String heur;
	
	@Column
	private String lot;
	
	@Column
	private long barcode;
	
	@Column
	private String jeu;
	
	@Column
	private int give;
	
	@JsonIgnore
	@ManyToOne
	//(fetch=FetchType.EAGER)
	@JoinColumn(name="idPartner")
	private Partner partner;
	
	@Column(name = "idmise")
	private long mise;
	
	@Transient
	private long idpart;
	
	public Cagnotte() {
		
	}

	public Cagnotte(String day, String heur, String lot, String jeu, Partner partner, int mise, long barcode, int give) {
		super();
		this.day = day;
		this.heur = heur;
		this.lot = lot;
		this.jeu = jeu;
		this.partner = partner;
		this.mise = mise;
		this.barcode = barcode;
		this.give = give;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHeur() {
		return heur;
	}

	public void setHeur(String heur) {
		this.heur = heur;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
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

	public long getBarcode() {
		return barcode;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Partner getPartner() {
		return partner;
	}

	public long getIdCagnotte() {
		return idCagnotte;
	}

	public int getGive() {
		return give;
	}

	public void setGive(int give) {
		this.give = give;
	}

	public void setIdCagnotte(long idCagnotte) {
		this.idCagnotte = idCagnotte;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	public long getIdpart() {
		return idpart;
	}

	public void setIdpart(long idpart) {
		this.idpart = idpart;
	}

}
