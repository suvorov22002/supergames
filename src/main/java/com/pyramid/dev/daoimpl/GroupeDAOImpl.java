package com.pyramid.dev.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.GroupeDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Groupe;

@Repository
public class GroupeDAOImpl implements GroupeDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean create(Groupe grpe) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().save(grpe);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Groupe find(Groupe grpe) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Groupe> query = currentSession.createQuery("from Groupe where idGroupe=:idgroupe", Groupe.class);
		query.setParameter("idgroupe", grpe.getIdGroupe());
		Groupe groupe = query.getSingleResult();
		return groupe;
	}

	@Override
	public boolean update(Groupe grpe) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(grpe);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(Groupe grpe) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(grpe);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

}
