package com.pyramid.dev.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.Misek_tempDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.KenoDTO;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class Misek_tempDAOImpl implements Misek_tempDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean create(Misek_temp misek) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(misek);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Misek_temp find(Long misek) throws DAOException {
		Misek_temp mtp = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Misek_temp> query = currentSession.createQuery("from Misek_temp where idmisek=:idmisek ", Misek_temp.class);
			query.setParameter("idmisek", misek);
			//mtp = query.getSingleResult();
			Optional<Misek_temp> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				mtp = q.get();
				return mtp;
			}
		}catch(Exception e) {
			System.err.println("MSTP - PAS DE TICKET TEMPORAIRES");
		}
		return mtp;
	}

	@Override
	public int update(Long misek) throws DAOException {
		// TODO Auto-generated method stub
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_MISEK_ID)
		.setParameter("idmisek", misek).executeUpdate();
		
	}

	@Override
	public boolean delete(Misek_temp misek) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(misek);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<Misek_temp> searchWaitingBet() throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Misek_temp> query = currentSession.createQuery(QueryHelper.SQL_F_M_MISEK_ID, Misek_temp.class);
		  
		List<Misek_temp> mtp = query.getResultList();
		return mtp;
	}

	@Override
	public List<Misek_temp> waitingDrawBet(int drawnum, Partner p) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Misek_temp> query = currentSession.createQuery(QueryHelper.SQL_F_TMP, Misek_temp.class);
		query.setParameter("drawnum", drawnum)
			 .setParameter("partner", p);
		
		List<Misek_temp> mtp = query.getResultList();
		return mtp;
	}

}
