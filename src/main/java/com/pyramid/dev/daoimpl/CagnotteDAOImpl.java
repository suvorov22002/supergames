package com.pyramid.dev.daoimpl;

import java.util.Optional;

import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.CagnotteDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class CagnotteDAOImpl implements CagnotteDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Cagnotte find(Partner p) throws DAOException {
		
		Cagnotte cagnotte = null;
		
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Cagnotte> query = currentSession.createQuery(QueryHelper.SQL_F_CAGNOTTE, Cagnotte.class);
			query.setParameter("partner", p)
				 .setParameter("partner1", p);
			
			Optional<Cagnotte> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				cagnotte = q.get();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return cagnotte;
	}

	@Override
	public int update(Cagnotte cgt) throws DAOException {
		int status = 0;
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(cgt);
			status = 1;			
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int updateCagnot(long id, long code, long mise) {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_CAGNOTTE)
		.setParameter("barcode", code)
		.setParameter("mise", mise)
		.setParameter("id", id)
		.executeUpdate();
		
	}

	@Override
	public boolean create(Cagnotte cagnotte) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(cagnotte);
			status = true;
		}catch(Exception e) {
			System.err.println("Create Cagnotte - "+e);
		}
		return status;
	}

}
