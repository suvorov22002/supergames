package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.GameCycleDAO;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.GameCycleService;

@Service
@Transactional
public class GameServiceImpl implements GameCycleService {
	
	@Autowired
	GameCycleDAO gamecycledao;
	
	@Override
	public boolean create(GameCycle gmc) throws DAOException {
		return gamecycledao.create(gmc);
	}

	@Override
	public List<GameCycle> find(Partner partner) throws DAOException {
		return gamecycledao.find(partner);
	}

	@Override
	public int update(GameCycle gmc) throws DAOException {
		return gamecycledao.update(gmc);
	}

	@Override
	public boolean delete(GameCycle gmc) throws DAOException {
		return gamecycledao.delete(gmc);
	}

	@Override
	@Modifying
	public int updateRfp(double rfp, Partner partner, Jeu jeu) throws DAOException {
		return gamecycledao.updateRfp(rfp, partner, jeu);
	}

	@Override
	@Modifying
	public int updatePos(int pos, Partner partner, Jeu jeu) throws DAOException {
		return gamecycledao.updatePos(pos, partner, jeu);
	}

	@Override
	public GameCycle findByGame(Partner partner, Jeu jeu) throws DAOException {
		return gamecycledao.findByGame(partner, jeu);
	}

	@Override
	public int updateArchive(double percent, String date, int archive, Partner partner, Jeu jeu, long misef,
			double stake, double payout, double jkpt) throws DAOException {
		return gamecycledao.updateArchive(percent, date, archive, partner, jeu, misef, stake, payout, jkpt);
	}

	@Override
	public List<GameCycle> findAll(Partner partner) throws DAOException {
		return gamecycledao.findAll(partner);
	}

}
