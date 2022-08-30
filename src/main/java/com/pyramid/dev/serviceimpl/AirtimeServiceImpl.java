package com.pyramid.dev.serviceimpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.AirtimeDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Airtime;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.service.AirtimeService;

@Transactional
@Service
public class AirtimeServiceImpl implements AirtimeService {
	
	@Autowired
	AirtimeDAO airtimedao;
	
	@Override
	public boolean create(Airtime airtime) throws DAOException {
		return airtimedao.create(airtime);
	}

	@Override
	public boolean update(Airtime airtime) throws DAOException {
		return airtimedao.update(airtime);
	}

	@Override
	public boolean delete(Airtime airtime) throws DAOException {
		return airtimedao.delete(airtime);
	}

	@Override
	public Airtime find(Caissier user) throws DAOException {
		return airtimedao.find(user);
	}

	@Override
	public Airtime findByDate(Caissier user, Date date) throws DAOException {
		return airtimedao.findByDate(user, date);
	}

	@Override
	public List<Airtime> find(Caissier caissier, String dat1, String dat2) throws DAOException {
		return airtimedao.find(caissier, dat1, dat2);
	}

	@Override
	public double findCumulCredit(Caissier caissier, String dat1, String dat2) throws DAOException {
		return airtimedao.findCumulCredit(caissier, dat1, dat2);
	}

}
