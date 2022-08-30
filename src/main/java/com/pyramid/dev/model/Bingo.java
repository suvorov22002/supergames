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
import javax.persistence.UniqueConstraint;

@Entity
@Table( name="bingo",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"drawnum", "idPartner"})})
public class Bingo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long iddraw;
	
	@Column
	private String drawnumb = "'1-2-3-4-5-6-7-8-9-10-11-12-13-14-15-16-17-18-19-20-21-22-23-24-25-26-27-28-29-30-31-32-33-34-35'"; 
	
	@Column(nullable=false, columnDefinition="int default 0")
	private Long bonusbingocod;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double bonusamount;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int drawnum;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double bonuscoloramount;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private Long bonuscolorcod;
	
	@Column
	private String heureTirage = "01/01/2015-12:00:00";
	
	@Column(nullable=false, columnDefinition="int default 0")
	private int start;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idPartner")
	private Partner coderace;

	public Bingo() {
		super();
	}

	public Long getIddraw() {
		return iddraw;
	}

	public void setIddraw(Long iddraw) {
		this.iddraw = iddraw;
	}

	public String getDrawnumb() {
		return drawnumb;
	}

	public void setDrawnumb(String drawnumb) {
		this.drawnumb = drawnumb;
	}

	public Long getBonusbingocod() {
		return bonusbingocod;
	}

	public void setBonusbingocod(Long bonusbingocod) {
		this.bonusbingocod = bonusbingocod;
	}

	public double getBonusamount() {
		return bonusamount;
	}

	public void setBonusamount(double bonusamount) {
		this.bonusamount = bonusamount;
	}

	public int getDrawnum() {
		return drawnum;
	}

	public void setDrawnum(int drawnum) {
		this.drawnum = drawnum;
	}

	public double getBonuscoloramount() {
		return bonuscoloramount;
	}

	public void setBonuscoloramount(double bonuscoloramount) {
		this.bonuscoloramount = bonuscoloramount;
	}

	public Long getBonuscolorcod() {
		return bonuscolorcod;
	}

	public void setBonuscolorcod(Long bonuscolorcod) {
		this.bonuscolorcod = bonuscolorcod;
	}

	public String getHeureTirage() {
		return heureTirage;
	}

	public void setHeureTirage(String heureTirage) {
		this.heureTirage = heureTirage;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public Partner getCoderace() {
		return coderace;
	}

	public void setCoderace(Partner coderace) {
		this.coderace = coderace;
	}
	
}
