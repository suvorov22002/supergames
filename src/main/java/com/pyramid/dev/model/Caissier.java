package com.pyramid.dev.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyramid.dev.enums.Connect;



@Entity
@Table( name="caissier",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"coderace", "loginc"})})
public class Caissier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idCaissier")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idCaissier;
	
    @Column
	private String nomC;
    
    @Column(nullable = false)
    private String loginc;
    
    @Column(nullable = false)
    private String mdpc;
    
    @Enumerated(EnumType.STRING)
    private Connect statut;
    
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="coderace")
    private Partner partner;
    
//    @ManyToOne(fetch=FetchType.EAGER)
//	@JoinColumn(name="idGroupe")
//    private Groupe groupe;
    
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
   	@JoinColumn(name="idProfil")
    private Profil profil;
    
//  //  @Transient
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "mvnt", referencedColumnName = "idmvnt")
//	private Mouvement mvnt;
    
    @OneToOne(fetch = FetchType.LAZY,
    cascade =  CascadeType.ALL,
    mappedBy = "caissier")
	private Mouvement mvnt;

	public Caissier() {
		super();
	}

	public Long getIdCaissier() {
		return idCaissier;
	}

	public void setIdCaissier(Long idCaissier) {
		this.idCaissier = idCaissier;
	}

	public String getNomC() {
		return nomC;
	}

	public void setNomC(String nomC) {
		this.nomC = nomC;
	}

	public String getLoginc() {
		return loginc;
	}

	public void setLoginc(String loginc) {
		this.loginc = loginc;
	}

	public String getMdpc() {
		return mdpc;
	}

	public void setMdpc(String mdpc) {
		this.mdpc = mdpc;
	}

	public Connect getStatut() {
		return statut;
	}

	public void setStatut(Connect statut) {
		this.statut = statut;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

//	public Groupe getGroupe() {
//		return groupe;
//	}
//
//	public void setGroupe(Groupe groupe) {
//		this.groupe = groupe;
//	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	@Override
	public String toString() {
		return "Caissier [loginc=" + loginc + ", statut=" + statut + ", partner=" + partner
				+ "]";
	}
	
	public static Caissier map(CaissierDto caisDto) {
		Caissier c = new Caissier();
		c.setLoginc(caisDto.getLoginc());
		c.setMdpc(caisDto.getMdpc());
		c.setNomC(caisDto.getNomc());
		return c;
	}
	
}
