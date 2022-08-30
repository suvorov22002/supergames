package com.pyramid.dev.dao;

import java.util.List;

import javax.ws.rs.core.Response;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Partner;

public interface CaissierDAO {
	
	Response create(Caissier caissier) throws DAOException;
	Response find(Caissier caissier) throws DAOException;
	boolean update(Caissier caissier) throws DAOException;
	Caissier findById(Caissier caissier) throws DAOException;
	boolean delete(Caissier caissier) throws DAOException;
	int updateState(Caissier caissier) throws DAOException;
	Caissier findByLogin(String login) throws DAOException;
	List<Caissier> findByPartner(Partner partner) throws DAOException;
	Caissier findByLoginIdPartner(String login, Partner partner)
			throws DAOException;
}
