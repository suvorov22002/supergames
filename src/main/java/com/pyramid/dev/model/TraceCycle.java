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

import lombok.Data;

@Data
@Entity
@Table(name = "trace") 
public class TraceCycle implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String coderace;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idcycle")
	private GameCycle cycle;
	
	@Column(name="drawnum")
	private int keno;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double sumDist;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double realDist;
	
	@Column(nullable=false, columnDefinition="int default 0")
	private double refund;
}
