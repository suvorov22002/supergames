package com.pyramid.dev.service;

import java.util.List;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.EffChoicek;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;

public interface EffChoicekService {
	boolean create(EffChoicek effchoicek) throws DAOException;
	EffChoicek find(Misek misek) throws DAOException;
	EffChoicek update(EffChoicek effchoicek) throws DAOException;
	void delete(EffChoicek effchoicek) throws DAOException;
	List<EffChoicek> searchTicketK(Misek misek) throws DAOException;
	List<EffChoicek> searchTicketK(Misek misek,  int drawnum) throws DAOException;
}
