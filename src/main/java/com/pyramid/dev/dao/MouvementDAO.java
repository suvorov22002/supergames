package com.pyramid.dev.dao;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Mouvement;

public interface MouvementDAO {
	
	boolean create(Mouvement mvnt) throws DAOException;
	boolean update(Mouvement mvnt) throws DAOException;
	Mouvement findByCaissier(Caissier caissier) throws DAOException;
	double findMvt(Caissier caissier) throws DAOException;
	int countMvt(Caissier caissier) throws DAOException;
	boolean updateMvt(Caissier caissier, double credit) throws DAOException;
}
