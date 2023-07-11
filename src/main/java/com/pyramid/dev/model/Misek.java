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

import org.apache.commons.lang3.StringUtils;

import com.pyramid.dev.enums.EtatMise;

@Entity
@Table(name = "misek")
public class Misek implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idMiseK;
	
	@Column
	private Long numeroTicket;
	
	@Column
	private String heureMise;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double sumMise;
	
	@Column
	private String dateMise;
	
	@Column(nullable=false)
	private int drawnumk;
	
	@Enumerated(EnumType.STRING)
	private EtatMise etatMise = EtatMise.ATTENTE;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int bonusCod;
	
	@Column
	private double sumWin = 0d;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int archive;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int xmulti;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idCaissier")
	private Caissier caissier;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idKeno")
	private Keno keno;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idMiseT")
	private Miset miset;

	public Misek() {
		super();
	}

	public Long getIdMiseK() {
		return idMiseK;
	}

	public void setIdMiseK(Long idMiseK) {
		this.idMiseK = idMiseK;
	}
	
	public Long getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(Long numeroTicket) {
		this.numeroTicket = numeroTicket;
	}

	public String getHeureMise() {
		return heureMise;
	}

	public void setHeureMise(String heureMise) {
		this.heureMise = heureMise;
	}

	public double getSumMise() {
		return sumMise;
	}

	public void setSumMise(double sumMise) {
		this.sumMise = sumMise;
	}

	public String getDateMise() {
		return dateMise;
	}

	public void setDateMise(String dateMise) {
		this.dateMise = dateMise;
	}

	public int getDrawnumk() {
		return drawnumk;
	}

	public void setDrawnumk(int drawnumk) {
		this.drawnumk = drawnumk;
	}

	public EtatMise getEtatMise() {
		return etatMise;
	}

	public void setEtatMise(EtatMise etatMise) {
		this.etatMise = etatMise;
	}

	public int getBonusCod() {
		return bonusCod;
	}

	public void setBonusCod(int bonusCod) {
		this.bonusCod = bonusCod;
	}

	public double getSumWin() {
		return sumWin;
	}

	public void setSumWin(double sumWin) {
		this.sumWin = sumWin;
	}

	public int getArchive() {
		return archive;
	}

	public void setArchive(int archive) {
		this.archive = archive;
	}

	public int getXmulti() {
		return xmulti;
	}

	public void setXmulti(int xmulti) {
		this.xmulti = xmulti;
	}

	public Caissier getCaissier() {
		return caissier;
	}

	public void setCaissier(Caissier caissier) {
		this.caissier = caissier;
	}

	public Keno getKeno() {
		return keno;
	}

	public void setKeno(Keno keno) {
		this.keno = keno;
	}

	public Miset getMiset() {
		return miset;
	}

	public void setMiset(Miset miset) {
		this.miset = miset;
	}

	@Override
	public int hashCode() {
		int hash = 7;
//		hash = 31 * hash + bonusCod;
//		hash = 31 * hash + idMiseK.hashCode();
		hash = 31 + idMiseK.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		
//		if(this == obj) {
//			return true;
//		}
//		if(obj == null || (obj.getClass() != this.getClass())) {
//			return false;
//		}
//		System.out.println("Comparable this: "+this.toString());
		
		if (obj instanceof Misek) {
			Misek mise = (Misek) obj;
//			System.out.println("Comparable: "+mise.toString());
			if (this.idMiseK.equals(mise.idMiseK)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public String toString() {
		return "[ BonusCode = " + this.bonusCod + " , Caissier = " + this.caissier.getLoginc() + " , Partner = " + this.caissier.getPartner().getCoderace() + 
				" , ID = " + this.idMiseK + " , IDT = " + this.miset.getIdMiseT() + " ]";
	}
	
}
