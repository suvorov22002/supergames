package com.pyramid.dev.dao;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Groupe;

public interface GroupeDAO {
	
	boolean create(Groupe grpe) throws DAOException;
	Groupe find(Groupe grpe) throws DAOException;
	boolean update(Groupe grpe) throws DAOException;
	boolean delete(Groupe grpe) throws DAOException;
}
