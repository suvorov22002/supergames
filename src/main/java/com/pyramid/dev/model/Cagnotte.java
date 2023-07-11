package com.pyramid.dev.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.enums.Room;



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
	
	@JsonIgnore
	@Column
	private Date day;
	
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
	
	@JsonIgnore
	@Column
	@Enumerated(EnumType.STRING)
	private Room status = Room.OPENED;
	
	@JsonIgnore
	@Column
	private Date createdAt;
	
	@Transient
	private long idpart;
	
	@Transient
	private String heur;
	
	public Cagnotte() {
		
	}
	
	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Room getStatus() {
		return status;
	}

	public void setStatus(Room status) {
		this.status = status;
	}

	public String getHeur() {
		return heur;
	}

	public void setHeur(String heur) {
		this.heur = heur;
	}
	
	
	
}
