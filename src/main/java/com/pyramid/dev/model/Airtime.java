package com.pyramid.dev.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="airtime")
public class Airtime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idairtime")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idairtime;
	
	@Column(nullable=false)
	private Date date;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double credit;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double debit;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double balance;
	
	@Column
	private String eta = "NV";
	
	@Column
	private String libelle;
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idCaissier")
	private Caissier caissier;

	public Airtime() {
		super();
	}

	public Long getIdairtime() {
		return idairtime;
	}

	public void setIdairtime(Long idairtime) {
		this.idairtime = idairtime;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public double getDebit() {
		return debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public Caissier getCaissier() {
		return caissier;
	}

	public void setCaissier(Caissier caissier) {
		this.caissier = caissier;
	}

}
