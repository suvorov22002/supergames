package com.pyramid.dev.daoimpl;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.MouvementDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Airtime;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.Mouvement;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class MouvementDAOImpl implements MouvementDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean create(Mouvement mvnt) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(mvnt);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean update(Mouvement mvnt) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(mvnt);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Mouvement findByCaissier(Caissier caissier) throws DAOException {
		Mouvement mvt = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Mouvement> query = currentSession.createQuery(QueryHelper.SQL_F_CAIS_MVT, Mouvement.class);
			query.setParameter("caissier", caissier);
			
			//mvt = query.getSingleResult();
			Optional<Mouvement> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				mvt = q.get();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mvt;
	}

	@Override
	public double findMvt(Caissier caissier) throws DAOException {
		double balance = 0;
		//System.out.println("----- "+caissier.getLoginc());
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			balance =  (double) currentSession.createQuery(QueryHelper.SQL_F_MVT)
			.setParameter("caissier", caissier)
			.getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return balance;
	}

	@Override
	public int countMvt(Caissier caissier) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_F_CMVT)
		.setParameter("caissier", caissier)
		.getSingleResult();
	}

	@Override
	public boolean updateMvt(Caissier caissier, double credit) throws DAOException {

		boolean status = false;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			int res = currentSession.createQuery(QueryHelper.SQL_UF_MVT)
					.setParameter("caissier", caissier)
				    .setParameter("credit", credit)
			        .executeUpdate();
			     
			status = res != 0 ? true : false;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	

}
