package com.pyramid.dev.daoimpl;

import com.pyramid.dev.dao.ConfigDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.QueryHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ConfigDAOImpl implements  ConfigDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean create(Config config) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(config);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Config find(Partner partner) throws DAOException {
		Config cfg = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Config> query = currentSession.createQuery(QueryHelper.SQL_F_CONFIG, Config.class);
			query.setParameter("coderace", partner);
			//cfg = query.getSingleResult();
			Optional<Config> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				cfg = q.get();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cfg;
	}

	@Override
	public boolean update(Config config) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(config);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean updateBonus(String step, String coderace) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Config config) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int updateBonusK(double bonusrate, double bnkmin, double bnkmax, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.createQuery(QueryHelper.SQL_U_CONFIG_BONUSK)
				.setParameter("bonusrate", bonusrate)
				.setParameter("bnkmin", bnkmin)
				.setParameter("bnkmax", bnkmax)
				.setParameter("coderace", partner)
				.executeUpdate();
	}

	@Override
	public int updateBonusP(double bonusrate, double bnpmin, double bnpmax, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.createQuery(QueryHelper.SQL_U_CONFIG_BONUSP)
				.setParameter("bonusrate", bonusrate)
				.setParameter("bnpmax", bnpmax)
				.setParameter("bnpmin", bnpmin)
				.setParameter("coderace", partner)
				.executeUpdate();
	}

}
