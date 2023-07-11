package com.pyramid.dev.dao;

import java.util.List;

import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Partner;

public interface GameCycleDAO {
	
	boolean create(GameCycle gmc) throws DAOException;
	List<GameCycle> find(Partner partner) throws DAOException;
	int update(GameCycle gmc) throws DAOException;
	boolean delete(GameCycle gmc) throws DAOException;
	int updateRfp(int rfp, Partner partner, Jeu jeu) throws DAOException;
	int updatePos(int pos, Partner partner, Jeu jeu) throws DAOException;
	GameCycle findByGame(Partner partner, Jeu jeu) throws DAOException;
	int updateArchive(double percent, String date, int archive, Partner partner, Jeu jeu,long misef, double stake, double payout, double jkpt) throws DAOException;
	List<GameCycle> findAll(Partner partner) throws DAOException;
}
