package com.pyramid.dev.serviceimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.MouvementDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Mouvement;
import com.pyramid.dev.service.MouvementService;

@Transactional
@Service
public class MouvementServiceImpl implements MouvementService {
	
	@Autowired
	MouvementDAO mouvementdao;
	
	@Override
	public boolean create(Mouvement mvnt) throws DAOException {
		// TODO Auto-generated method stub
		return mouvementdao.create(mvnt);
	}

	@Override
	public boolean update(Mouvement mvnt) throws DAOException {
		return mouvementdao.update(mvnt);
	}

	@Override
	public Mouvement findByCaissier(Caissier caissier) throws DAOException {
		return mouvementdao.findByCaissier(caissier);
	}

	@Override
	public double findMvt(Caissier caissier) throws DAOException {
		return mouvementdao.findMvt(caissier);
	}

	@Override
	public int countMvt(Caissier caissier) throws DAOException {
		return mouvementdao.countMvt(caissier);
	}

	@Override
	public boolean updateMvt(Caissier caissier, double credit) {
		return mouvementdao.updateMvt(caissier, credit);
	}

}
