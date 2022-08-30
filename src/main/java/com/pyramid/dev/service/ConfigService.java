package com.pyramid.dev.service;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.Partner;

public interface ConfigService {
	
	boolean create(Config config) throws DAOException;
	Config find(Partner partner) throws DAOException;
	boolean update(Config config) throws DAOException;
	boolean updateBonus(String step, String coderace) throws DAOException;
	boolean delete(Config config) throws DAOException;
	int updateBonusK(double bonusrate, double bnkmin, double bnkmax,
			Partner partner) throws DAOException;
	int updateBonusP(double bonusrate, double bnpmin, double bnpmax,
			Partner partner) throws DAOException;
	
}
