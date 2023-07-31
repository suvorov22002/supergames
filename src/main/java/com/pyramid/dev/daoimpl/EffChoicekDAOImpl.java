package com.pyramid.dev.daoimpl;

import com.pyramid.dev.dao.EffChoicekDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.EffChoicek;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.tools.QueryHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EffChoicekDAOImpl implements EffChoicekDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public boolean create(EffChoicek effchoicek) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(effchoicek);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public EffChoicek find(Misek misek) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EffChoicek update(EffChoicek effchoicek) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(EffChoicek effchoicek) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EffChoicek> searchTicketK(Misek misek) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<EffChoicek> query = currentSession.createQuery(QueryHelper.SQL_F_EFFCHOICE_MISEK, EffChoicek.class);
		query.setParameter("misek", misek);

		return query.getResultList();
	}

	@Override
	public List<EffChoicek> searchTicketK(Misek misek, int drawnum) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<EffChoicek> query = currentSession.createQuery(QueryHelper.SQL_F_EFFCHOICE_KENO_MISEK, EffChoicek.class);
		query.setParameter("misek", misek)
				.setParameter("drawnum", drawnum);

		return query.getResultList();
	}

}
