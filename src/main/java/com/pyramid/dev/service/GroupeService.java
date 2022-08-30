package com.pyramid.dev.service;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Groupe;

public interface GroupeService {
	
	boolean create(Groupe grpe) throws DAOException;
	Groupe find(Groupe grpe) throws DAOException;
	boolean update(Groupe grpe) throws DAOException;
	boolean delete(Groupe grpe) throws DAOException;
}
