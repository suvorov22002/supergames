package com.pyramid.dev.model;

public class AdminTicketDto {
	private String datMise;
	private String etatMise;
	private String typeJeu;
	private String barcode;
	private double summise;
	private double sumwin;
	
	public AdminTicketDto() {
		super();
	}

	public String getDatMise() {
		return datMise;
	}

	public void setDatMise(String datMise) {
		this.datMise = datMise;
	}

	public String getEtatMise() {
		return etatMise;
	}

	public void setEtatMise(String etatMise) {
		this.etatMise = etatMise;
	}

	public String getTypeJeu() {
		return typeJeu;
	}

	public void setTypeJeu(String typeJeu) {
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

	public double getSumwin() {
		return sumwin;
	}

	public void setSumwin(double sumwin) {
		this.sumwin = sumwin;
	}

	@Override
	public String toString() {
		return "AdminTicketDto [datMise=" + datMise + ", etatMise=" + etatMise + ", typeJeu=" + typeJeu + ", barcode="
				+ barcode + ", summise=" + summise + ", sumwin=" + sumwin + "]";
	}
	
	
	
}
