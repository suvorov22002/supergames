package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.CaissierDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.CaissierService;

@Service
@Transactional
public class CaissierServiceImpl implements CaissierService {
	
	@Autowired
	CaissierDAO caissierdao;
	
	@Override
	public Response create(Caissier caissier) throws DAOException {
		return caissierdao.create(caissier);
	}

	@Override
	public Response find(Caissier caissier) throws DAOException {
		return caissierdao.find(caissier);
	}

	@Override
	public boolean update(Caissier caissier) throws DAOException {
		return caissierdao.update(caissier);
	}

	@Override
	public Caissier findById(Caissier caissier) throws DAOException {
		return caissierdao.findById(caissier);
	}

	@Override
	public boolean delete(Caissier caissier) throws DAOException {
		return caissierdao.delete(caissier);
	}

	@Override
	public int updateState(Caissier caissier) throws DAOException {
		return caissierdao.updateState(caissier);
	}

	@Override
	public Caissier findByLogin(String login) throws DAOException {
		return caissierdao.findByLogin(login);
	}

	@Override
	public List<Caissier> findByPartner(Partner partner) throws DAOException {
		return caissierdao.findByPartner(partner);
	}

	@Override
	public Caissier findByLoginIdPartner(String login, Partner partner) throws DAOException {
		return caissierdao.findByLoginIdPartner(login, partner);
	}

}
