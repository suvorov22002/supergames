package com.pyramid.dev.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.CaissierDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.CaissierDto;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.CaissierDTO;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class CaissierDAOImpl implements CaissierDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Response create(Caissier caissier) throws DAOException {
		//System.out.println(caissier);
		CaissierDto caisdto = new CaissierDto();
		try {
			   sessionFactory.getCurrentSession().save(caissier);
			   caisdto.transToCaissier(caissier);
		}catch(DAOException e) {
			e.printStackTrace();
			//return Response.ok(CaissierDTO.getInstance().error(e.getMessage())).build();
			return null;
		}
		return Response.ok(CaissierDTO.getInstance().event(caisdto).sucess("")).build();
	}

	@Override
	public Response find(Caissier caissier) throws DAOException {
		Caissier cais = null;
		CaissierDto caisdto = new CaissierDto();
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Caissier> query = currentSession.createQuery(QueryHelper.SQL_F_LOGIN_PASS, Caissier.class);
			query.setParameter("loginC", caissier.getLoginc())
				 .setParameter("partner", caissier.getPartner());
			//cais = query.getSingleResult();
			
			Optional<Caissier> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				cais = q.get();
				caisdto.transToCaissier(cais);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return Response.ok(CaissierDTO.getInstance().error("USER NOT FOUND")).build();
		}
		return Response.ok(CaissierDTO.getInstance().event(caisdto).sucess("")).build();
	}

	@Override
	public boolean update(Caissier caissier) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(caissier);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Caissier findById(Caissier caissier) throws DAOException {
		Caissier cais =  null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Caissier> query = currentSession.createQuery(QueryHelper.SQL_CAIS_F_ID, Caissier.class);
			query.setParameter("idCaissier", caissier.getIdCaissier());
			//cais = query.getSingleResult();
			Optional<Caissier> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				cais = q.get();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			//return Response.ok(CaissierDTO.getInstance().error(e.getMessage())).build();
		}
		
		return cais;
	}

	@Override
	public boolean delete(Caissier caissier) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(caissier);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public int updateState(Caissier caissier) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_CAISSIER_STATE)
		.setParameter("statut", caissier.getStatut())
		.setParameter("loginC", caissier.getLoginc())
		.getSingleResult();
	}

	@Override
	public Caissier findByLogin(String login) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Caissier cais = null;
		Query<Caissier> query = currentSession.createQuery(QueryHelper.SQL_F_LOGIN_PASS, Caissier.class);
		query.setParameter("loginC", login);
		//Caissier cais = query.getSingleResult();
		Optional<Caissier> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			cais = q.get();
		}
		return cais;
	}

	@Override
	public List<Caissier> findByPartner(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Caissier> query = currentSession.createQuery(QueryHelper.SQL_F_LOGIN_PARTNER, Caissier.class);
		query.setParameter("coderace", partner.getCoderace());
		  
		List<Caissier> cais = query.getResultList();
		return cais;
	}

	@Override
	public Caissier findByLoginIdPartner(String login, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Caissier> query = currentSession.createQuery(QueryHelper.SQL_F_LOGIN_CODERACE, Caissier.class);
		query.setParameter("login", login)
		     .setParameter("id", partner);
		Caissier cais = null;
		//Caissier cais = query.getSingleResult();
		Optional<Caissier> q = query.uniqueResultOptional();
		if (q.isPresent()) {
			cais = q.get();
		}
		
		return cais;
	}

}
