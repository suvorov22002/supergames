package com.pyramid.dev.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyramid.dev.dao.GroupeDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Groupe;
import com.pyramid.dev.service.GroupeService;

@Service
@Transactional
public class GroupeServiceImpl implements GroupeService {
	
	@Autowired
	GroupeDAO groupeDao;
	
	@Override
	public boolean create(Groupe grpe) throws DAOException {
		return groupeDao.create(grpe);
	}

	@Override
	public Groupe find(Groupe grpe) throws DAOException {
		return groupeDao.find(grpe);
	}

	@Override
	public boolean update(Groupe grpe) throws DAOException {
		return groupeDao.update(grpe);
	}

	@Override
	public boolean delete(Groupe grpe) throws DAOException {
		return groupeDao.delete(grpe);
	}

}
