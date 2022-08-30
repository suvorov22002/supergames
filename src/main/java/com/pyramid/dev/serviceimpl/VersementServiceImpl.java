package com.pyramid.dev.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyramid.dev.dao.VersementDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.Versement;
import com.pyramid.dev.service.VersementService;

@Service
@Transactional
public class VersementServiceImpl implements VersementService {
	
	@Autowired 
	VersementDAO versdao;
	
	@Override
	public boolean create(Versement versement) throws DAOException {
		return versdao.create(versement);
	}

	@Override
	public Versement find(Long idmiset) throws DAOException {
		return versdao.find(idmiset);
	}

	@Override
	public Versement findById(Versement versement) throws DAOException {
		return versdao.findById(versement);
	}

	@Override
	public boolean update(Versement versement) throws DAOException {
		return versdao.update(versement);
	}

	@Override
	public boolean delete(Versement versement) throws DAOException {
		return versdao.delete(versement);
	}

	@Override
	public Versement find_vers_miset(Long idmiset) throws DAOException {
		return versdao.find_vers_miset(idmiset);
	}

	@Override
	public double getVersementD(String date, Caissier caissier, String date1, String jeu) throws DAOException {
		return versdao.getVersementD(date, caissier, date1, jeu);
	}

	@Override
	public int getPayTicket(Caissier caissier, String date, String date1, String jeu) throws DAOException {
		return versdao.getPayTicket(caissier, date, date1, jeu);
	}

	@Override
	public ArrayList<Versement> getVersementk(String min, String max, String jeu) throws DAOException {
		return versdao.getVersementk(min, max, jeu);
	}

	@Override
	public int updateVersementD(String date, Caissier caissier, String date1) throws DAOException {
		return versdao.updateVersementD(date, caissier, date1);
	}

	@Override
	public List<Versement> getVersement(String date, String date1, Partner partner) throws DAOException {
		return versdao.getVersement(date, date1, partner);
	}

	@Override
	public double getVersements(Caissier caissier, Long date1, Long date2) throws DAOException {
		return versdao.getVersements(caissier, date1, date2);
	}
	
	

}
