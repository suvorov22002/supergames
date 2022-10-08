package com.pyramid.dev.serviceimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.MisetDAO;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.MisetService;

@Transactional
@Service
public class MisetServiceImpl implements MisetService {
	
	@Autowired
	MisetDAO misetdao;
	
	@Override
	public boolean create(Miset miset) throws DAOException {
		return misetdao.create(miset);
	}

	@Override
	public Miset find(Miset miset) throws DAOException {
		return misetdao.find(miset);
	}

	@Override
	public boolean update(Miset miset) throws DAOException {
		return misetdao.update(miset);
	}

	@Override
	public boolean delete(Miset miset) throws DAOException {
		return misetdao.delete(miset);
	}

	@Override
	public int findId(Partner partner) throws DAOException {
		return misetdao.findId(partner);
	}

	@Override
	public Miset searchTicketT(String barcode) throws DAOException {
		return misetdao.searchTicketT(barcode);
	}

	@Override
	public Miset searchTicketTAlrPay(String barcode, Long miset) throws DAOException {
		return misetdao.searchTicketTAlrPay(barcode, miset);
	}

	@Override
	public Miset findById(Long idmiset) throws DAOException {
		return misetdao.findById(idmiset);
	}

	@Override
	public double findFreeSlipByPartner(String game, Partner coderace) throws DAOException {
		return misetdao.findFreeSlipByPartner(game, coderace);
	}

	@Override
	public int updateFree(String game, double step, Partner coderace) throws DAOException {
		return misetdao.updateFree(game, step, coderace);
	}

	@Override
	public int createFree(Partner coderace) {
		return misetdao.createFree(coderace);
	}

	@Override
	public Miset findBarcode(Long code, Jeu jeu) throws DAOException {
		// TODO Auto-generated method stub
		return misetdao.findBarcode(code, jeu);
	}

	@Override
	public long searchBarcode(Jeu jeu) throws DAOException {
		return misetdao.searchBarcode(jeu);
	}

}
