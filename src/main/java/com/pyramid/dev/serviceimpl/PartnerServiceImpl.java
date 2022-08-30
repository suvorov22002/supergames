package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyramid.dev.dao.PartnerDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.PartnerDto;
import com.pyramid.dev.service.PartnerService;
import com.pyramid.dev.tools.ControlDisplayKeno;

@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {
	
	@Autowired
	private PartnerDAO partnerdao;
	
	@Override
	public Response create(Partner partner) throws DAOException {
		return partnerdao.create(partner);
	}

	@Override
	public Partner find(Partner partner) throws DAOException {
		return partnerdao.find(partner);
	}

	@Override
	public boolean update(Partner partner) throws DAOException {
		return partnerdao.update(partner);
	}

	@Override
	public void delete(Partner partner) throws DAOException {
		partnerdao.delete(partner);
	}

	@Override
	public int update_bonusk(double dbl, int bncd, Partner partner) throws DAOException {
		return partnerdao.update_bonusk(dbl, bncd, partner);
	}

	@Override
	public int update_bonusp(double dbl, int bncd, Partner partner) throws DAOException {
		return partnerdao.update_bonusp(dbl, bncd, partner);
	}

	@Override
	public int update_reset_bonusk(double dbl, Partner partner) throws DAOException {
		return partnerdao.update_reset_bonusk(dbl, partner);
	}

	@Override
	public int update_reset_bonusp(double dbl, Partner partner) throws DAOException {
		return partnerdao.update_reset_bonusp(dbl, partner);
	}

	@Override
	public Partner findById(Long id) throws DAOException {
		return partnerdao.findById(id);
	}

	@Override
	public List<Partner> getAllPartners() throws DAOException {
		return partnerdao.getAllPartners();
	}

	@Override
	public int update_cob(String cob, Partner partner) throws DAOException {
		return partnerdao.update_cob(cob, partner);
	}

	@Override
	public List<Partner> getAllPartnersByGroup(String idgrp) throws DAOException {
		return partnerdao.getAllPartnersByGroup(idgrp);
	}

	@Override
	public PartnerDto find2(Partner partner) throws DAOException {
		return partnerdao.find2(partner);
	}

	@Override
	public int retrieveTimeKeno(ControlDisplayKeno cds) {
		return partnerdao.retrieveTimeKeno(cds);
	}

	@Override
	public String retrieveDrawCombi(ControlDisplayKeno cds) {
		return partnerdao.retrieveDrawCombi(cds);
	}

}
