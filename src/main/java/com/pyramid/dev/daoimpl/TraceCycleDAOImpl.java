package com.pyramid.dev.daoimpl;

import com.pyramid.dev.dao.TraceCycleDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.TraceCycle;
import com.pyramid.dev.tools.QueryHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TraceCycleDAOImpl implements TraceCycleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean create(TraceCycle trc) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(trc);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public List<TraceCycle> find(String coderace) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<TraceCycle> query = currentSession.createQuery(QueryHelper.SQL_F_TRACE_PARTNER, TraceCycle.class);
		query.setParameter("coderace", coderace);

		return query.getResultList();
	}

	@Override
	public int update(TraceCycle trc) throws DAOException {
		int status = 0;
		try {
			sessionFactory.getCurrentSession().update(trc);
			status = 1;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(TraceCycle trc) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(trc);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public TraceCycle find(String coderace, int keno) throws DAOException {

		TraceCycle trace = null;

		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<TraceCycle> query = currentSession.createQuery(QueryHelper.SQL_F_TRACE, TraceCycle.class);
			query.setParameter("keno",keno)
					.setParameter("coderace",coderace)
					.setParameter("keno1",keno)
					.setParameter("coderace1",coderace);

			Optional<TraceCycle> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				trace = q.get();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return trace;
	}

	@Override
	public List<TraceCycle> find(GameCycle gmc) throws DAOException {

		Session currentSession = sessionFactory.getCurrentSession();
		Query<TraceCycle> query = currentSession.createQuery(QueryHelper.SQL_F_TRACE_GMC, TraceCycle.class);
		query.setParameter("gmcycle", gmc);

		return query.getResultList();

	}

}
