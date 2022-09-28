package com.pyramid.dev.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.KenoDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Airtime;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.KenoDTO;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class KenoDAOImpl implements KenoDAO {
	
	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean create(Keno keno) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(keno);
			status = true;
		}catch(Exception e) {
			System.err.println("KENO - "+e);
		}
		return status;
	}

	@Override
	public Response find(Keno keno) throws DAOException {
		Keno ken = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Keno> query = currentSession.createQuery("from Keno where idKeno=:idKeno", Keno.class);
			query.setParameter("idKeno", keno.getIdKeno());
			//Keno ken = query.getSingleResult();
			Optional<Keno> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				ken = q.get();
				return Response.ok(KenoDTO.getInstance().event(ken).sucess("")).build();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return Response.ok(KenoDTO.getInstance().error(e.getMessage())).build();
		}
		
		return Response.ok(KenoDTO.getInstance().error("")).build();
	}

	@Override
	public Keno find_Max_draw(Partner partner) throws DAOException {
		
		Keno ken = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_MAX_DRAW, Keno.class);
			query.setParameter("coderace", partner)
			     .setParameter("coderace1", partner);
			//ken = query.getSingleResult();
			Optional<Keno> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				ken = q.get();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ken;
	}
	
	@Override
	public Keno find_Max_draw_bis(Partner partner) throws DAOException {
		Keno ken = null;
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_MAX_PREVIOUS_DRAW, Keno.class);
		query.setParameter("coderace", partner)
		     .setParameter("coderace1", partner);
		//Keno ken = query.getSingleResult();
		Optional<Keno> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			ken = q.get();
		}
		
		return ken;
	}

	@Override
	public int update(Keno keno) throws DAOException {
		
			
		int status = 0;
		try {
			
			sessionFactory.getCurrentSession().saveOrUpdate(keno);
			status = 1;
//			Session currentSession = sessionFactory.getCurrentSession();
//			return (int) currentSession.createQuery(QueryHelper.SQL_U_KENO)
//			.setParameter("drawnumbK", keno.getDrawnumbK())
//			.setParameter("multi", keno.getMultiplicateur())
//			.setParameter("heur", keno.getHeureTirage())
//			.setParameter("drawnum", keno.getDrawnumK())
//			.setParameter("coderace",keno.getCoderace())
//			.executeUpdate();
			
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(Keno keno) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(keno);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double findBonusAmount(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Double> query = currentSession.createQuery(QueryHelper.SQL_F_BONUS_AMOUNT_KENO)
		.setParameter("coderace", partner);
		
		Optional<Double> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			Double cycle = q.get();
			return cycle.doubleValue();
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateBonus(double bonus, int drawnumber, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Integer> query = currentSession.createQuery(QueryHelper.SQL_U_BONUS_AMOUNT_KENO)
		.setParameter("bonus", bonus)
		.setParameter("bonus", drawnumber)
		.setParameter("coderace", partner);
		
		Optional<Integer> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			Integer cycle = q.get();
			return cycle.intValue();
		}
		
		return 0;
	}

	@Override
	public List<Keno> getLastKdraw(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_PREVIOUS_FIVE_DRAW, Keno.class);
		query.setParameter("coderace", partner)
			 .setFirstResult(0)
		     .setMaxResults(5);
		  
		List<Keno> ken = query.getResultList();
		return ken;
	}
	
	@Override
	public List<Keno> getAllLastKdraw(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_PREVIOUS_FIVE_DRAW, Keno.class);
		query.setParameter("coderace", partner)
			 .setFirstResult(0)
		     .setMaxResults(100);
		  
		List<Keno> ken = query.getResultList();
		return ken;
	}


	@Override
	public synchronized Keno getMaxIdDrawK(Partner partner) throws DAOException {
		Keno ken = null;
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_MAX_ID, Keno.class);
		query.setParameter("coderace", partner);
		  
		//Keno ken = query.getSingleResult();
		Optional<Keno> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			ken = q.get();
		}
		return ken;
	}

	@Override
	public Keno searchResultK(int drawnum, Partner partner) throws DAOException {
		
		Keno ken = null;
		
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_DRAW, Keno.class);
			query.setParameter("drawnum",drawnum)
			     .setParameter("coderace",partner);
			  
			//ken = query.getSingleResult();
			Optional<Keno> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				ken = q.get();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ken;
	}

	@Override
	public int updateDrawEnd(int drawnumber, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_DRAW_END)
		.setParameter("drawnum", drawnumber)
		.setParameter("coderace",partner)
		.executeUpdate();
	}
	
	@Override
	public boolean updateDrawEnd(Keno keno) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(keno);
			status = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getIdKenos(Partner partner, int drawnumk) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Long> query = currentSession.createQuery(QueryHelper.SQL_F_ID)
		.setParameter("drawnum", drawnumk)
		.setParameter("coderace", partner);
		
		Optional<Long> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			Long cycle = q.get();
			return cycle.longValue();
		}
		
		return 0L;
	}

	@Override
	public int setCodeBonusK(double amount, int code, long idkeno) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_KENO_BONUS)
		.setParameter("bonusamount", amount)
		.setParameter("bonuscode", code)
		.setParameter("idKeno", idkeno)
		.executeUpdate();
	}

	@Override
	public List<Keno> getLastKBonus(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_PREVIOUS_BONUS, Keno.class);
		query.setParameter("coderace", partner)
			 .setFirstResult(0)
		     .setMaxResults(3);
		  
		List<Keno> ken = query.getResultList();
		
		return ken;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double findTotalBonusAmount(Long id1, Long id2, Partner partner) throws DAOException {
		
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Double> query = currentSession.createQuery(QueryHelper.SQL_F_SUM_BONUS);
			query.setParameter("idkeno", id1)
			.setParameter("idkeno1", id2)
			.setParameter("coderace", partner);
			
			Optional<Double> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				Double cycle = q.get();
				return cycle.doubleValue();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public List<Keno> find_Last_draw(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_PREVIOUS_TWEL_DRAW, Keno.class);
		query.setParameter("coderace", partner)
			 .setFirstResult(0)
		     .setMaxResults(12);
		  
		List<Keno> ken = query.getResultList();
		return ken;
	}

	@Override
	public Keno find_Single_draw(Partner partner) throws DAOException, EmptyResultDataAccessException {
		Keno ken = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_SINGLE_DRAW, Keno.class);
			query.setParameter("coderace", partner);
			    
			List<Keno> lkeno = query.getResultList();
			if (!lkeno.isEmpty()) {
				ken = lkeno.get(0);
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return ken;
		}
		
		return ken;
		
	}

	@Override
	public Keno find_Max_draw_num(Partner partner, int num) throws DAOException {
		Keno ken = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Keno> query = currentSession.createQuery(QueryHelper.SQL_F_MAX_DRAW_NUM, Keno.class);
			query.setParameter("coderace", partner)
				 .setParameter("drawnum", num);
			//ken = query.getSingleResult();
			
			Optional<Keno> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				ken = q.get();
			}
		}catch(Exception e) {
			//System.err.print(e);
			e.printStackTrace();
		}
		return ken;
	}

}
