package com.pyramid.dev.daoimpl;

import com.pyramid.dev.dao.AirtimeDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Airtime;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.tools.DateUtils;
import com.pyramid.dev.tools.QueryHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class AirtimeDAOImpl implements AirtimeDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean create(Airtime airtime) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(airtime);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean update(Airtime airtime) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(airtime);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(Airtime airtime) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(airtime);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Airtime find(Caissier user) throws DAOException {
		Airtime credit = null;

		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Airtime> query = currentSession.createQuery(QueryHelper.SQL_F_CREDIT, Airtime.class);
			query.setParameter("caissier", user);
			//credit = query.getSingleResult();
			Optional<Airtime> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				credit = q.get();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return credit;
	}

	@Override
	public Airtime findByDate(Caissier user, Date date) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Airtime> find(Caissier caissier, String dat1, String dat2) throws DAOException {

		Date d = Date.from(DateUtils.parse(dat1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date f = Date.from(DateUtils.parse(dat2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		Session currentSession = sessionFactory.getCurrentSession();
		Query<Airtime> query = currentSession.createQuery(QueryHelper.SQL_UF_CREDIT, Airtime.class);
		query.setParameter("caissier", caissier)
				.setParameter("date1", d)
				.setParameter("date2", f);

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public double findCumulCredit(Caissier caissier, String dat1, String dat2) throws DAOException {
		Date d = Date.from(DateUtils.parse(dat1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		Date f = Date.from(DateUtils.parse(dat2).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

		Session currentSession = sessionFactory.getCurrentSession();
		Query<Double> query = currentSession.createQuery(QueryHelper.SQL_UF_S_CREDIT);
		query.setParameter("caissier", caissier)
				.setParameter("date1", d)
				.setParameter("date2", f);


		Optional<Double> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			Double sum = q.get();
			return sum.doubleValue();
		}

		return 0;
	}

}
