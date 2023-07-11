package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.GameCycleDAO;
import com.pyramid.dev.dao.KenoDAO;
import com.pyramid.dev.dao.MisekDAO;
import com.pyramid.dev.dao.Misek_tempDAO;
import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.GameCycleService;
import com.pyramid.dev.tools.Utile;

@Service
@Transactional
public class GameServiceImpl implements GameCycleService {
	
	@Autowired
	GameCycleDAO gamecycledao;
	
	@Autowired
	MisekDAO misekdao;
	
	@Autowired
	Misek_tempDAO mtpdao;
	
	@Autowired
	private KenoDAO kenodao;
	
	@Override
	public boolean create(GameCycle gmc) throws DAOException {
		return gamecycledao.create(gmc);
	}

	@Override
	public List<GameCycle> find(Partner partner) throws DAOException {
		
		List<GameCycle> listGameCycle = gamecycledao.find(partner);
		
		if (!listGameCycle.isEmpty()) {
			
			Long maxMise = misekdao.ifindId(partner);
			Long minMise = listGameCycle.get(0).getMise();
			List<Misek> totalmisek = misekdao.getMiseKCycle(minMise, 1+maxMise, partner);
			
			double somMiseIn = totalmisek.stream().filter(b -> !b.getEtatMise().equals(EtatMise.ATTENTE))
					   .mapToDouble(Misek::getSumMise).sum();
			
			double refund = listGameCycle.get(0).getRefundp();
			double winTotal;
			double curr_percent;
			double jkpt;
			
			listGameCycle.get(0).setStake(somMiseIn);
			
			double somWin = totalmisek.stream().filter(b -> !b.getEtatMise().equals(EtatMise.ATTENTE))
					   .mapToDouble(Misek::getSumWin).sum();
			
			listGameCycle.get(0).setPayout(somWin);
			
			Misek m1 = misekdao.searchMiseK(minMise);
			Misek m2 = misekdao.searchMiseK(maxMise);
			
			 if(m1 != null && m2 != null) {
				   Long k1 = m1.getKeno().getIdKeno();
				   Long k2 = m2.getKeno().getIdKeno();
				   jkpt = kenodao.findTotalBonusAmount(k1, k2, partner);
				   //jkpt = Long.valueOf(Utile.df.format(jkpt));
			 }
			 else {
				   jkpt = 0;
				   
			 }
			 listGameCycle.get(0).setJkpt(jkpt);
			
			 
			 winTotal = somWin + refund + jkpt;
				
			if (somMiseIn > 0) {
			   curr_percent = winTotal/somMiseIn;
			   //System.out.println("curr_percent "+curr_percent);
			   //curr_percent = Double. valueOf(Utile.df.format(curr_percent));
		    }
		    else {
			   curr_percent = 0;
		    }
			curr_percent = (double)((int)(curr_percent*100))/100;
			listGameCycle.get(0).setCurr_percent(curr_percent);
		}
		
		
		
		return listGameCycle;
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
	public int updateRfp(int rfp, Partner partner, Jeu jeu) throws DAOException {
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
