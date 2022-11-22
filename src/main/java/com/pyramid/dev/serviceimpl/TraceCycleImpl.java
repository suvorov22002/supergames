package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.TraceCycleDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.TraceCycle;
import com.pyramid.dev.service.TraceCycleService;

@Transactional
@Service
public class TraceCycleImpl implements TraceCycleService {
	
	@Autowired
	private TraceCycleDAO trcDao;
	
	@Override
	public boolean create(TraceCycle trc) throws DAOException {
		return trcDao.create(trc);
	}

	@Override
	public List<TraceCycle> find(String coderace) throws DAOException {
		return trcDao.find(coderace);
	}

	@Override
	public int update(TraceCycle trc) throws DAOException {
		return trcDao.update(trc);
	}

	@Override
	public boolean delete(TraceCycle trc) throws DAOException {
		return trcDao.delete(trc);
	}

	@Override
	public TraceCycle find(String coderace, int keno) throws DAOException {
		return trcDao.find(coderace, keno);
	}

	@Override
	public List<TraceCycle> find(GameCycle gmc) throws DAOException {
		return trcDao.find(gmc);
	}

}
