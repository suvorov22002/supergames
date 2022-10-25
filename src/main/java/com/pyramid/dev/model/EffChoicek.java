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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="effchoicek")
public class EffChoicek implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ideffchoicek")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ideffchoicek;
	
	@Column
	private String idparil;
	
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idMiseK")
	private Misek misek;
	
	@Transient
	@Column
	private Long imisek;


	@Column
	private String kchoice;
	
	@Column
	private String cote;
	
	@Column
	private Long idkeno;
	
	@Column
	private String mtchoix;
	
	@Column
	private double mtwin = 0;
	@Column
	private Integer drawnum;
	@Column
	private String drawresult;
	@Transient
	private boolean state;

	public EffChoicek() {
		super();
	}

	public Long getIdeffchoicek() {
		return ideffchoicek;
	}

	public void setIdeffchoicek(Long ideffchoicek) {
		this.ideffchoicek = ideffchoicek;
	}

	public String getIdparil() {
		return idparil;
	}

	public void setIdparil(String idparil) {
		this.idparil = idparil;
	}

	public Misek getMisek() {
		return misek;
	}

	public void setMisek(Misek misek) {
		this.misek = misek;
	}

	public String getKchoice() {
		return kchoice;
	}

	public void setKchoice(String kchoice) {
		this.kchoice = kchoice;
	}

	public String getCote() {
		return cote;
	}

	public void setCote(String cote) {
		this.cote = cote;
	}

	public Long getIdkeno() {
		return idkeno;
	}

	public void setIdkeno(Long idkeno) {
		this.idkeno = idkeno;
	}

	public String getMtchoix() {
		return mtchoix;
	}

	public void setMtchoix(String mtchoix) {
		this.mtchoix = mtchoix;
	}

	public Long getImisek() {
		return imisek;
	}

	public void setImisek(Long idmisek) {
		this.imisek = idmisek;
	}

	public double getMtwin() {
		return mtwin;
	}

	public void setMtwin(double mtwin) {
		this.mtwin = mtwin;
	}

	public Integer getDrawnum() {
		return drawnum;
	}

	public void setDrawnum(Integer drawnum) {
		this.drawnum = drawnum;
	}

	public String getDrawresult() {
		return drawresult;
	}

	public void setDrawresult(String drawresult) {
		this.drawresult = drawresult;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	
}
