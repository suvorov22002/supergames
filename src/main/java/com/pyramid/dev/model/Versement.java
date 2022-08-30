package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "versement")
public class Versement implements Serializable {
	
	private static final long serialVersionUID = -1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idvers;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idCaissier")
	private Caissier caissier;
	
	@Column
	private double montant;
	@Column
	private String heureV;
	@Column
	private String datV;
	@Column
	private String typeVers;
	@Column
	private Long mise;
	@Column(nullable=false, columnDefinition="int default 0")
	private int archive;
	
	public Versement() {
		super();
	}

	public Long getIdvers() {
		return idvers;
	}

	public void setIdvers(Long idvers) {
		this.idvers = idvers;
	}

	public Caissier getCaissier() {
		return caissier;
	}

	public void setCaissier(Caissier caissier) {
		this.caissier = caissier;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getHeureV() {
		return heureV;
	}

	public void setHeureV(String heureV) {
		this.heureV = heureV;
	}

	public String getDatV() {
		return datV;
	}

	public void setDatV(String datV) {
		this.datV = datV;
	}

	public String getTypeVers() {
		return typeVers;
	}

	public void setTypeVers(String typeVers) {
		this.typeVers = typeVers;
	}

	public Long getMise() {
		return mise;
	}

	public void setMise(Long miset) {
		this.mise = miset;
	}
	
	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	@Override
	public String toString() {
		return "Versement [caissier=" + caissier.getLoginc() + ", Montant=" + montant + ", TypeVers= "+ typeVers +"]";
	}
}
