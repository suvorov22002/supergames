package com.pyramid.dev.service;

import java.util.List;

import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.AdminTicketDto;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.EffChoicek;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;

public interface MisekService {
	boolean create(Misek misek) throws DAOException;
	Misek find(String login, String pass) throws DAOException;
	boolean update(Misek misek) throws DAOException;
	boolean delete(Misek misek) throws DAOException;
	Long ifindId(Partner partner) throws DAOException; 
	int findId() throws DAOException; 
	List<Misek> searchMisesK(Miset miset) throws DAOException;
	//Misek searchMisesK(Miset miset, int drawnum) throws DAOException;
	Misek searchMiseK(Long misek) throws DAOException;
	int getNumDraw(Misek misek) throws DAOException;
	public double getMiseRK(Caissier caissier, String date, String date1) throws DAOException;
	int getIntvTicketK(Caissier caissier, String date, String date1) throws DAOException;
	List<Misek> getMisek(String date, String date1, Partner partner) throws DAOException;
	List<AdminTicketDto> getMisekt(String date, String date1, Partner partner) throws DAOException;
	int searchDrawNumK(Keno keno) throws DAOException;
	List<Misek> searchMiseKdraw(Keno keno, int num) throws DAOException;
	List<Misek> searchWaitingBet(Partner partner, int drawnum) throws DAOException;
	List<Misek> searchWaitingKenoBet(Partner partner, int drawnum, EtatMise etat) throws DAOException;
	List<Misek> getMiseKCycle(Long misek, Long mise, Partner partner) throws DAOException;
	double getMiseKCycleWin(long misek, long mise,Partner partner) throws DAOException;
	int findId(Miset miset) throws DAOException;
	int updateMiseRK(Caissier caissier, String date, String date1) throws DAOException;
	List<Misek> searchAllMisek(Partner partner) throws DAOException;
	List<EffChoicek> waitingKenoBet(Partner partner, int drawnum) throws DAOException;
	int updateAll(List<Misek> list) throws DAOException;
}
