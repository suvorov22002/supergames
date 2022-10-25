package com.pyramid.dev.dao;

import java.util.List;

import javax.ws.rs.core.Response;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.PartnerDto;
import com.pyramid.dev.tools.ControlDisplayKeno;

public interface PartnerDAO {
	
	boolean create(Partner partner) throws DAOException;
	Partner find(Partner partner) throws DAOException;
	boolean update(Partner partner) throws DAOException;
	void delete(Partner partner) throws DAOException;
	int update_bonusk(double dbl, int bncd, Partner partner) throws DAOException;
	int update_bonusp(double dbl, int bncd, Partner partner) throws DAOException;
	int update_reset_bonusk(double dbl, Partner partner) throws DAOException;
	int update_reset_bonusp(double dbl, Partner partner) throws DAOException;
	Partner findById(String id) throws DAOException;
	List<Partner> getAllPartners() throws DAOException;
	int update_cob(String cob,Partner partner) throws DAOException;
	List<Partner> getAllPartnersByGroup(String idgrp) throws DAOException;
	PartnerDto find2(Partner partner) throws DAOException;
	int retrieveTimeKeno(ControlDisplayKeno cds);
	String retrieveDrawCombi(ControlDisplayKeno cds);
}
