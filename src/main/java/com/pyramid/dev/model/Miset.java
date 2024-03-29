package com.pyramid.dev.model;

import com.pyramid.dev.enums.Jeu;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "miset")
public class Miset implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idMiseT;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Jeu typeJeu;
	
	@Column(nullable=false, unique=true)
	private String barcode;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double summise;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idPartner")
	private Partner coderace;

	public Miset() {
		super();
	}

	public Long getIdMiseT() {
		return idMiseT;
	}

	public void setIdMiseT(Long idMiseT) {
		this.idMiseT = idMiseT;
	}

	public Jeu getTypeJeu() {
		return typeJeu;
	}

	public void setTypeJeu(Jeu typeJeu) {
		this.typeJeu = typeJeu;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public double getSummise() {
		return summise;
	}

	public void setSummise(double summise) {
		this.summise = summise;
	}

	public Partner getCoderace() {
		return coderace;
	}

	public void setCoderace(Partner coderace) {
		this.coderace = coderace;
	}

}
