package com.pyramid.dev.service;

import java.util.ArrayList;
import java.util.List;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.Versement;

public interface VersementService {
	boolean create(Versement versement) throws DAOException;
	Versement find(Long idmiset) throws DAOException;
	Versement findById(Versement versement) throws DAOException;
	boolean update(Versement versement) throws DAOException;
	boolean delete(Versement versement) throws DAOException;
	Versement find_vers_miset(Long idmiset) throws DAOException;
	double getVersementD(String date, Caissier caissier, String date1, String jeu) throws DAOException;
	double getVersements(Caissier caissier, Long date1, Long date2) throws DAOException;
	int getPayTicket(Caissier caissier, String date, String date1, String jeu)  throws DAOException;
	ArrayList<Versement> getVersementk(String min, String max, String jeu) throws DAOException;
	int updateVersementD(String date, Caissier caissier, String date1) throws DAOException;
	List<Versement> getVersement(String date, String date1, Partner partner) throws DAOException;
}
