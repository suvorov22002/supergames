package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.EffChoicekDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.EffChoicek;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.service.EffChoicekService;

@Transactional
@Service
public class EffChoicekServiceImpl implements EffChoicekService {
	
	@Autowired
	EffChoicekDAO efckdao;
	
	@Override
	public boolean create(EffChoicek effchoicek) throws DAOException {
		return efckdao.create(effchoicek);
	}

	@Override
	public EffChoicek find(Misek misek) throws DAOException {
		return efckdao.find(misek);
	}

	@Override
	public EffChoicek update(EffChoicek effchoicek) throws DAOException {
		return efckdao.update(effchoicek);
	}

	@Override
	public void delete(EffChoicek effchoicek) throws DAOException {
		efckdao.delete(effchoicek);
	}

	@Override
	public List<EffChoicek> searchTicketK(Misek misek) throws DAOException {
		return efckdao.searchTicketK(misek);
	}

	@Override
	public List<EffChoicek> searchTicketK(Misek misek, int drawnum) throws DAOException {
		return efckdao.searchTicketK(misek, drawnum);
	}

}
