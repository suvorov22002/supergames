package com.pyramid.dev.daoimpl;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.PartnerDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.PartnerDto;
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.PartnerDTO;
import com.pyramid.dev.tools.QueryHelper;
import com.pyramid.dev.tools.Utile;

@Repository
@Component
public class PartnerDAOImpl implements PartnerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Response create(Partner partner) throws DAOException {
		boolean status = false;
		Partner part;
		try {
			sessionFactory.getCurrentSession().save(partner);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
			return null;
			//return Response.ok(PartnerDTO.getInstance().event(partner).error(e.getMessage())).build();
		}
		return Response.ok(PartnerDTO.getInstance().event(partner).sucess("")).build();
		//return Response.ok().entity(part).build();
	}

	@Override
	public Partner find(Partner partner) throws DAOException {
		Partner p = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Partner> query = currentSession.createQuery("from Partner WHERE coderace =:coderace ", Partner.class);
			query.setParameter("coderace", partner.getCoderace());
			p = query.getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public boolean update(Partner partner) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(partner);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public void delete(Partner partner) throws DAOException {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized int update_bonusk(double dbl, int bncd, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_BONUS_AMOUNT_PARTNER)
		.setParameter("bonuskamount", dbl)
		.setParameter("bonuskcode", bncd)
		.setParameter("coderace", partner.getIdpartner())
		.executeUpdate();
	}

	@Override
	public synchronized int update_bonusp(double dbl, int bncd, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_BONUSP_AMOUNT_PARTNER)
		.setParameter("bonuspamount", dbl)
		.setParameter("bonuspcode", bncd)
		.setParameter("coderace", partner)
		.getSingleResult();
	}

	@Override
	public int update_reset_bonusk(double dbl, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_BONUS_RESET_AMOUNT_PARTNER)
		.setParameter("bonuskamount", dbl)
		.setParameter("coderace", partner)
		.getSingleResult();
	}

	@Override
	public int update_reset_bonusp(double dbl, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_BONUSP_RESET_AMOUNT_PARTNER)
		.setParameter("bonuspamount", dbl)
		.setParameter("coderace", partner)
		.getSingleResult();
	}

	@Override
	public Partner findById(Long id) throws DAOException {
		Partner p = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Partner> query = currentSession.createQuery(QueryHelper.SQL_F_PARTNER_BY_ID, Partner.class);
			query.setParameter("idpartner", id);
			//p = query.getSingleResult();
			//return Response.ok(PartnerDTO.getInstance().event(p).sucess("")).build();
			Optional<Partner> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				p = q.get();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			//return Response.ok(PartnerDTO.getInstance().error(e.getMessage())).build();
		}
		return p;
	}

	@Override
	public List<Partner> getAllPartners() throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Partner> query = currentSession.createQuery("from Partner", Partner.class);
		List<Partner> list = query.getResultList();
		return list;
	}

	@Override
	public int update_cob(String cob, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_COB_PARTNER)
		.setParameter("cob", cob)
		.setParameter("coderace", partner)
		.getSingleResult();
	}

	@Override
	public List<Partner> getAllPartnersByGroup(String idgrp) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Partner> query = currentSession.createQuery(QueryHelper.SQL_F_PARTNER_BY_GROUP, Partner.class);
		query.setParameter("groupe", idgrp);
		List<Partner> list = query.getResultList();
		return list;
	}

	@SuppressWarnings("deprecation")
	@Override
	public PartnerDto find2(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<PartnerDto> query = currentSession.createQuery(QueryHelper.SQL_F_STAT_MISEK, PartnerDto.class);
		query.setParameter("coderace", partner);
		
		query.setResultTransformer( Transformers.aliasToBean( PartnerDto.class) );
		PartnerDto part = query.getSingleResult();
		
		return part;
	}

	@Override
	public int retrieveTimeKeno(ControlDisplayKeno cds) {
		if(Utile.display_draw.containsKey(cds.getCoderace())) {
			return Utile.display_draw.get(cds.getCoderace()).getTimeKeno();
		}
		return 0;
	}
	
	@Override
	public String retrieveDrawCombi(ControlDisplayKeno cds) {
		if(Utile.display_draw.containsKey(cds.getCoderace())) {
			return Utile.display_draw.get(cds.getCoderace()).getDrawCombik();
		}
		return "";
	}

}
