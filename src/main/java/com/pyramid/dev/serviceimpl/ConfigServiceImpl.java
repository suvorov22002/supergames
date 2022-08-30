package com.pyramid.dev.serviceimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.ConfigDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.ConfigService;

@Service
@Transactional
public class ConfigServiceImpl implements ConfigService {
	
	@Autowired
	ConfigDAO configdao;
	
	@Override
	public boolean create(Config config) throws DAOException {
		return configdao.delete(config);
	}

	@Override
	public Config find(Partner partner) throws DAOException {
		return configdao.find(partner);
	}

	@Override
	public boolean update(Config config) throws DAOException {
		return configdao.update(config);
	}

	@Override
	public boolean updateBonus(String step, String coderace) throws DAOException {
		return configdao.updateBonus(step, coderace);
	}

	@Override
	public boolean delete(Config config) throws DAOException {
		return configdao.delete(config);
	}

	@Override
	public int updateBonusK(double bonusrate, double bnkmin, double bnkmax, Partner partner) throws DAOException {
		return configdao.updateBonusK(bonusrate, bnkmin, bnkmax, partner);
	}

	@Override
	public int updateBonusP(double bonusrate, double bnpmin, double bnpmax, Partner partner) throws DAOException {
		return configdao.updateBonusP(bonusrate, bnpmin, bnpmax, partner);
	}

}
