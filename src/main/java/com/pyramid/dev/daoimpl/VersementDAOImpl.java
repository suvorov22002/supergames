package com.pyramid.dev.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.VersementDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.Versement;
import com.pyramid.dev.service.CaissierService;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class VersementDAOImpl implements VersementDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	CaissierService caisservice;

	@Override
	public boolean create(Versement versement) throws DAOException {
		boolean status = false;

		try {
			sessionFactory.getCurrentSession().save(versement);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		//return status;
		return status;
	}

	@Override
	public Versement find(Long idmiset) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Versement findById(Versement versement) throws DAOException {
		Versement vers = null;

		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Versement> query = currentSession.createQuery("from Versement where idvers =:idvers", Versement.class);
			query.setParameter("idvers", versement.getIdvers());
			vers = query.getSingleResult();
		} catch(Exception e) {
			System.err.println("VERSEMENT-ERROR: "+e.getMessage());
		}

		return vers;
	}

	@Override
	public boolean update(Versement versement) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(versement);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(Versement versement) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(versement);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Versement find_vers_miset(Long idmiset) throws DAOException {
		Versement vers = null;
		//System.out.println("find_vers_miset "+idmiset);
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Versement> query = currentSession.createQuery(QueryHelper.SQL_F_VERS_MISET, Versement.class);
			query.setParameter("mise", idmiset);
			//vers = query.getSingleResult();
			Optional<Versement> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				vers = q.get();
			}
		} catch(Exception e) {
			System.err.println("MISET-ERROR: "+e);
		}
		return vers;
	}

	@Override
	public double getVersementD(String date, Caissier caissier, String date1, String jeu) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		double vers = 0;
		Object obj = currentSession.createQuery(QueryHelper.SQL_F_COMPTA_V)
				.setParameter("debut", date)
				.setParameter("fin", date1)
				.setParameter("jeu", jeu)
				.setParameter("caissier", caissier)
				.getSingleResult();

		if(obj != null) vers = (double) obj;
		return vers;
	}

	@Override
	public double getVersements(Caissier caissier, Long date1, Long date2) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		double vers = 0;
		Object obj = currentSession.createQuery(QueryHelper.SQL_F_COMPTA_VERS)
				.setParameter("debut", String.valueOf(date1))
				.setParameter("fin", String.valueOf(date2))
				.setParameter("caissier", caissier)
				.getSingleResult();

		if(obj != null) vers = (double) obj;
		return vers;
	}

	@Override
	public int getPayTicket(Caissier caissier, String date, String date1, String jeu) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Long n = (Long) currentSession.createQuery(QueryHelper.SQL_F_COUNT_VERS)
				.setParameter("debut", date)
				.setParameter("fin", date1)
				.setParameter("jeu", jeu)
				.setParameter("caissier", caissier)
				.getSingleResult();

		return n.intValue();
	}

	@Override
	public ArrayList<Versement> getVersementk(String min, String max, String jeu) throws DAOException {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public int updateVersementD(String date, Caissier caissier, String date1) throws DAOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Versement> getVersement(String date, String date1, Partner partner) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Versement> query = currentSession.createQuery(QueryHelper.SQL_F_STAT_VERSE, Versement.class);
		List<Versement> lvers = new ArrayList<>();
		List<Caissier> lcais = caisservice.findByPartner(partner);
		for (Caissier cais : lcais) {
			query.setParameter("heur1", date)
					.setParameter("heur2", date1)
					.setParameter("caissier", cais);

			List<Versement> vers = query.getResultList();
			if (vers != null && !vers.isEmpty()) {
				lvers.addAll(vers);
			}
		}
		return lvers;
	}

}
