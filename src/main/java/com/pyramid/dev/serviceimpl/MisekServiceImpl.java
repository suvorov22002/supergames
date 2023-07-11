package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.MisekDAO;
import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.AdminTicketDto;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.EffChoicek;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.MisekService;

@Transactional
@Service
public class MisekServiceImpl implements MisekService {
	
	@Autowired
	MisekDAO misekdao;
	
	@Override
	public boolean create(Misek misek) throws DAOException {
		return misekdao.create(misek);
	}

	@Override
	public Misek find(String login, String pass) throws DAOException {
		return misekdao.find(login, pass);
	}

	@Override
	public boolean update(Misek misek) throws DAOException {
		return misekdao.update(misek);
	}

	@Override
	public boolean delete(Misek misek) throws DAOException {
		return misekdao.delete(misek);
	}

	@Override
	public Long ifindId(Partner partner) throws DAOException {
		return misekdao.ifindId(partner);
	}

	@Override
	public int findId() throws DAOException {
		return misekdao.findId();
	}

	@Override
	public List<Misek> searchMisesK(Miset miset) throws DAOException {
		return misekdao.searchMisesK(miset);
	}

	@Override
	public Misek searchMiseK(Long misek) throws DAOException {
		return misekdao.searchMiseK(misek);
	}

	@Override
	public int getNumDraw(Misek misek) throws DAOException {
		return misekdao.getNumDraw(misek);
	}

	@Override
	public double getMiseRK(Caissier caissier, String date, String date1) throws DAOException {
		return misekdao.getMiseRK(caissier, date, date1);
	}

	@Override
	public int getIntvTicketK(Caissier caissier, String date, String date1) throws DAOException {
		return misekdao.getIntvTicketK(caissier, date, date1);
	}

	@Override
	public List<Misek> getMisek(String date, String date1, Partner partner) throws DAOException {
		return misekdao.getMisek(date, date1, partner);
	}

	@Override
	public List<AdminTicketDto> getMisekt(String date, String date1, Partner partner) throws DAOException {
		return misekdao.getMisekt(date, date1, partner);
	}

	@Override
	public int searchDrawNumK(Keno keno) throws DAOException {
		return misekdao.searchDrawNumK(keno);
	}

	@Override
	public List<Misek> searchMiseKdraw(Keno keno, int num) throws DAOException {
		return misekdao.searchMiseKdraw(keno, num);
	}

	@Override
	public List<Misek> searchWaitingBet(Partner partner, int drawnum) throws DAOException {
		return misekdao.searchWaitingBet(partner, drawnum);
	}

	@Override
	public List<Misek> searchWaitingKenoBet(Partner partner, int drawnum, EtatMise etat) throws DAOException {
		return misekdao.searchWaitingKenoBet(partner, drawnum, etat);
	}

	@Override
	public List<Misek> getMiseKCycle(Long misek, Long mise, Partner partner) throws DAOException {
		return misekdao.getMiseKCycle(misek, mise, partner);
	}

	@Override
	public double getMiseKCycleWin(long misek, long mise, Partner partner) throws DAOException {
		return misekdao.getMiseKCycleWin(misek, mise, partner);
	}

	@Override
	public int findId(Miset miset) throws DAOException {
		return misekdao.findId(miset);
	}

	@Override
	public int updateMiseRK(Caissier caissier, String date, String date1) throws DAOException {
		return misekdao.updateMiseRK(caissier, date, date1);
	}
	
	@Override
	public List<Misek> searchAllMisek(Partner partner) throws DAOException {
		return misekdao.searchAllMisek(partner);
	}

	@Override
	public List<EffChoicek> waitingKenoBet(Partner partner, int drawnum) throws DAOException {
		return misekdao.waitingKenoBet(partner, drawnum);
	}

	@Override
	public int updateAll(List<Misek> list) throws DAOException {
		return misekdao.updateAll(list);
	}

//	@Override
//	public Misek searchMisesK(Miset miset, int drawnum) throws DAOException {
//		return misekdao.searchMisesK(miset, drawnum);
//	}

}
