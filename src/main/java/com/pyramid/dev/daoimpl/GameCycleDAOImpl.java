package com.pyramid.dev.daoimpl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.GameCycleDAO;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class GameCycleDAOImpl implements GameCycleDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean create(GameCycle gmc) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(gmc);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<GameCycle> find(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<GameCycle> query = currentSession.createQuery(QueryHelper.SQL_F_GAMECYCLE, GameCycle.class);
		query.setParameter("partner", partner);
		  
		List<GameCycle> cycle = query.getResultList();
		return cycle;
	}

	@Override
	public int update(GameCycle gmc) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_GAMECYCLE)
		.setParameter("percent", gmc.getPercent())
		.setParameter("tour", gmc.getTour())
		.setParameter("hitfrequence", gmc.getHitfrequence())
		.setParameter("refundp", gmc.getRefundp())
		.setParameter("position", gmc.getPosition())
		.setParameter("arrangement", gmc.getArrangement())
		.setParameter("jeu", gmc.getJeu())
		.setParameter("id", gmc.getPartner())
		.executeUpdate();
	}

	@Override
	public boolean delete(GameCycle gmc) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(gmc);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int updateRfp(int rfp, Partner partner, Jeu jeu) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_GAMECYCLE_RTP)
		.setParameter("refundp",rfp)
		.setParameter("id", partner)
		.setParameter("jeu", jeu)
		.executeUpdate();
	}

	@Override
	public int updatePos(int pos, Partner partner, Jeu jeu) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_GAMECYCLE_POS)
		.setParameter("position",pos)
		.setParameter("id", partner)
		.setParameter("jeu", jeu)
		.executeUpdate();
		
	}

	@Override
	public GameCycle findByGame(Partner partner, Jeu jeu) throws DAOException {
		GameCycle cycle = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<GameCycle> query = currentSession.createQuery(QueryHelper.SQL_F_GAMECYCLE_JEU, GameCycle.class);
			query.setParameter("id",partner)
			     .setParameter("jeu",jeu);
			  
			//cycle = query.getSingleResult();
			Optional<GameCycle> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				cycle = q.get();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return cycle;
		}
		
		return cycle;
	}

	@Override
	public int updateArchive(double percent, String date, int archive, Partner partner, Jeu jeu, long misef,
			double stake, double payout, double jkpt) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_GAMECYCLE_ARCHIVE)
		.setParameter("curr_percent",percent)
		.setParameter("id", partner)
		.setParameter("jeu", jeu)
		.setParameter("date_fin", date)
		.setParameter("archive", archive)
		.setParameter("misef", misef)
		.setParameter("stake", stake)
		.setParameter("payout", payout)
		.setParameter("jkpt", jkpt)
		.executeUpdate();
		
	}

	@Override
	public List<GameCycle> findAll(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<GameCycle> query = currentSession.createQuery(QueryHelper.SQL_F_GAMECYCLE_ALL, GameCycle.class);
		query.setParameter("partner", partner);
		  
		List<GameCycle> cycle = query.getResultList();
		return cycle;
	}

}
