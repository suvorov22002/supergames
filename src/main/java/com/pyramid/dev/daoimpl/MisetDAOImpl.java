package com.pyramid.dev.daoimpl;

import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.MisetDAO;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class MisetDAOImpl implements MisetDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean create(Miset miset) throws DAOException {
		boolean status = false;
		Miset mt = null;
		try {
			sessionFactory.getCurrentSession().save(miset);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		//return status;
		return status;
	}

	@Override
	public Miset find(Miset miset) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Miset> query = currentSession.createQuery("from Miset where idMiseT:=idMiseT", Miset.class);
		query.setParameter("idMiseT", miset.getIdMiseT());
		Miset mt = query.getSingleResult();
		return mt;
	}

	@Override
	public boolean update(Miset miset) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(miset);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(Miset miset) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(miset);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public synchronized int findId(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_F_MISET_MAX_ID)
		.setParameter("coderace", partner)
		.getSingleResult();
	}

	@Override
	public Miset searchTicketT(String barcode) throws DAOException {
		Miset mt = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Miset> query = currentSession.createQuery(QueryHelper.SQL_F_BARCODE, Miset.class);
			query.setParameter("barcode", barcode);
			//mt = query.getSingleResult();
			
			Optional<Miset> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				mt = q.get();
				return mt;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return mt;
		
	}

	@Override
	public Miset searchTicketTAlrPay(String barcode, Long miset) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Miset> query = currentSession.createQuery(QueryHelper.SQL_F_BARC_MISET, Miset.class);
		query.setParameter("barcode", barcode)
		     .setParameter("idMiseT", miset);
		Miset mt = query.getSingleResult();
		return mt;
	}

	@Override
	public Miset findById(Long idmiset) throws DAOException {
		
		Miset mt = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Miset> query = currentSession.createQuery(QueryHelper.SQL_F_MISET_ID, Miset.class);
			query.setParameter("idMiseT", idmiset);
			     
			mt = query.getSingleResult();
		}
		catch(Exception e) {
			System.err.println(e);
		}
		
		return mt;
	}

	@Override
	public double findFreeSlipByPartner(String game, Partner coderace) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateFree(String game, double step, Partner coderace) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createFree(Partner coderace) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Miset findBarcode(Long code, Jeu jeu) throws DAOException {
		Miset mt = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Miset> query = currentSession.createQuery(QueryHelper.SQL_F_EXITS_BARCODE, Miset.class);
			query.setParameter("barcode", ""+code)
				 .setParameter("jeu", jeu);
			     
			mt = query.getSingleResult();
		}catch(Exception e) {
			System.err.println("Exception-findBarcode: "+e);
		}
		return mt;
	}

	@Override
	public long searchBarcode(Jeu jeu) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
