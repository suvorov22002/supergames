package com.pyramid.dev.service;

import java.util.Date;
import java.util.List;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Airtime;
import com.pyramid.dev.model.Caissier;

public interface AirtimeService {
	
	boolean create(Airtime airtime) throws DAOException;
	boolean update(Airtime airtime) throws DAOException;
	boolean delete(Airtime airtime) throws DAOException;
	Airtime find(Caissier user) throws DAOException;
	Airtime findByDate(Caissier user, Date date) throws DAOException;
	List<Airtime> find(Caissier caissier, String dat1, String dat2) throws DAOException;
	double findCumulCredit(Caissier caissier, String dat1, String dat2) throws DAOException;
}
