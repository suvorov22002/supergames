package com.pyramid.dev.service;

import java.util.List;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Partner;

public interface Misek_tempService {
	boolean create(Misek_temp misek) throws DAOException;
	Misek_temp find(Long misek) throws DAOException;
	int update(Long misek) throws DAOException;
	boolean delete(Misek_temp misek) throws DAOException;
	List<Misek_temp> searchWaitingBet() throws DAOException;
	List<Misek_temp> waitingDrawBet(int drawnum, Partner p) throws DAOException;
}
