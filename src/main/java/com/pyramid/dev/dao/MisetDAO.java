package com.pyramid.dev.dao;

import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;

public interface MisetDAO {
	boolean create(Miset miset) throws DAOException;
	Miset find(Miset miset) throws DAOException;
	boolean update(Miset miset) throws DAOException;
	boolean delete(Miset miset) throws DAOException;
	int findId(Partner partner) throws DAOException; 
	Miset searchTicketT(String barcode) throws DAOException;
	Miset searchTicketTAlrPay(String barcode, Long miset) throws DAOException;
	Miset findById(Long idmiset) throws DAOException; 
	double findFreeSlipByPartner(String game, Partner coderace)throws DAOException;
	int updateFree(String game, double step, Partner coderace) throws DAOException;
	int createFree(Partner coderace) throws DAOException;;
	Miset findBarcode(Long code, Jeu jeu) throws DAOException;
}
