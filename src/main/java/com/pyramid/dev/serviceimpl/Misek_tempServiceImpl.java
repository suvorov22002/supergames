package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.Misek_tempDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.Misek_tempService;

@Transactional
@Service
public class Misek_tempServiceImpl implements Misek_tempService {
	
	@Autowired
	Misek_tempDAO mtpdao;
	
	@Override
	public boolean create(Misek_temp misek) throws DAOException {
		return mtpdao.create(misek);
	}

	@Override
	public Misek_temp find(Long misek) throws DAOException {
		return mtpdao.find(misek);
	}

	@Override
	@Modifying
	public int update(Long misek) throws DAOException {
		return mtpdao.update(misek);
	}

	@Override
	public boolean delete(Misek_temp misek) throws DAOException {
		return mtpdao.delete(misek);
	}

	@Override
	public List<Misek_temp> searchWaitingBet() throws DAOException {
		return mtpdao.searchWaitingBet();
	}

	@Override
	public List<Misek_temp> waitingDrawBet(int drawnum, Partner p) throws DAOException {
		return mtpdao.waitingDrawBet(drawnum, p);
	}

}
