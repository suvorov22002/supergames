package com.pyramid.dev.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.spi.NativeQueryTupleTransformer;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.dao.MisekDAO;
import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.AdminTicketDto;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.tools.QueryHelper;

@Repository
public class MisekDAOImpl implements MisekDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean create(Misek misek) throws DAOException {
		boolean status = false;
		Misek mk = null;
		try {
			sessionFactory.getCurrentSession().save(misek);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
		
	}

	@Override
	public Misek find(String login, String pass) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Misek misek) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().update(misek);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public boolean delete(Misek misek) throws DAOException {
		boolean status = false;
		try {
			sessionFactory.getCurrentSession().delete(misek);
			status = true;
		}catch(DAOException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Long ifindId(Partner partner) throws DAOException {
		Long res = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			res =  (Long) currentSession.createQuery(QueryHelper.SQL_F_MAX_ID_CODERACE)
		    .setParameter("partner", partner)
			.getSingleResult();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(res == null) {
			return 0L;
		}
		else {
			return res;
		}
	}

	@Override
	public int findId() throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_F_MISEK_MAX_ID)
		.getSingleResult();
	}

	@Override
	public Misek searchMisesK(Miset miset) throws DAOException {
		Misek misk = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Misek> query = currentSession.createQuery(QueryHelper.SQL_F_MISEK_MISET, Misek.class);
			query.setParameter("miset", miset);
			  
			misk = query.getSingleResult();
		}
		catch(Exception e) {
			System.err.println(e);
		}
		
		return misk;
	}

	@Override
	public Misek searchMiseK(Long misek) throws DAOException {
		Misek misk = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Misek> query = currentSession.createQuery(QueryHelper.SQL_F_MISEK_ID, Misek.class);
			query.setParameter("idMiseK", misek);
			  
			//misk = query.getSingleResult();
			Optional<Misek> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				misk = q.get();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return misk;
	}
	
	@Override
	public List<Misek> searchAllMisek(Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Misek> query = currentSession.createQuery(QueryHelper.SQL_F_MAX_MISEK_CODERACE, Misek.class);
		query.setParameter("partner", partner);
		  
		List<Misek> misk = query.getResultList();
		return misk;
	}

	@Override
	public int getNumDraw(Misek misek) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_F_DRAW_NUM)
				.setParameter("misek", misek.getIdMiseK())
				.getSingleResult();
	}

	@Override
	public double getMiseRK(Caissier caissier, String date, String date1) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		double summise = 0;
		Object obj =  currentSession.createQuery(QueryHelper.SQL_F_COMPTA)
		.setParameter("heur1", date)
		.setParameter("heur2", date1)
		.setParameter("caissier", caissier)
		.getSingleResult();
		
		if(obj != null) summise = (double) obj;
		return summise;
	}

	@Override
	public int getIntvTicketK(Caissier caissier, String date, String date1) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Long n =  (Long) currentSession.createQuery(QueryHelper.SQL_F_COUNT_MISEK)
		.setParameter("heur1", date)
		.setParameter("heur2", date1)
		.setParameter("caissier", caissier)
		.getSingleResult();
		
		return n.intValue();
	}

	@Override
	public List<Misek> getMisek(String date, String date1, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Misek> query = currentSession.createQuery(QueryHelper.SQL_F_STAT_MISEK, Misek.class);
		query.setParameter("coderace", partner.getCoderace())
			 .setParameter("heur1", date)
		     .setParameter("heur2", date1);
		 
		
		List<Misek> misk = query.getResultList();
		return misk;
	}

	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	@Override
	public List<AdminTicketDto> getMisekt(String date, String date1, Partner partner) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query query = currentSession.createQuery(QueryHelper.SQL_F_STAT_MISEK_MISET);
		query.setParameter("coderace", partner)
			 .setParameter("heur1", date)
		     .setParameter("heur2", date1);
		
		List<AdminTicketDto> misk = null;
		AdminTicketDto adm;
		String key;
		query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		//query.setResultTransformer(new NativeQueryTupleTransformer());
		List<Map<String, Object>> aliasToValueMapList = query.list();
		if (aliasToValueMapList != null) {
			misk = new ArrayList<>();
		}
		
		for (Map<String, Object> obj : aliasToValueMapList) {
			
			adm = new AdminTicketDto();
			
			for (Map.Entry<String, Object> entry : obj.entrySet())  {
				//System.out.println(entry.getKey()+" - "+entry.getValue());
				key = entry.getKey();
				
				
				switch(key) {
					case "sumwin":
						adm.setSumwin((double)entry.getValue());
						break;
					case "datMise":
						adm.setDatMise(entry.getValue().toString().replace('/', '-').substring(0, 10));
						break;
					case "barcode":
						adm.setBarcode(entry.getValue().toString());
						break;
					case "typeJeu":
						adm.setTypeJeu(entry.getValue().toString());
						break;
					case "etatMise":
						adm.setEtatMise(entry.getValue().toString());
						break;
					case "summise":
						adm.setSummise((double)entry.getValue());
						break;
					default:
						break;
				}
			}
			misk.add(adm);
		}
		

		return misk;
	}

	@Override
	public int searchDrawNumK(Keno keno) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_F_ID_KENO)
		.setParameter("keno", keno)
		.getSingleResult();
	}

	@Override
	public List<Misek> searchMiseKdraw(Keno keno, int num) throws DAOException {
		System.out.println("NUM: "+keno.getDrawnumK()+" = "+num);
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Misek> query = currentSession.createQuery(QueryHelper.SQL_F_MISEK_DRAWNUMK, Misek.class);
		query.setParameter("drawnumk", num)
			 .setParameter("keno", keno);
		  
		List<Misek> misk = query.getResultList();
		return misk;
	}

	@Override
	public List<Misek> searchWaitingBet(Partner partner, int drawnum) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Misek> query =  currentSession.createQuery(QueryHelper.SQL_F_WAITING_BET,  Misek.class);
				query.setParameter("drawnumk", drawnum)
				     .setParameter("partner", partner)
				     .setParameter("etatmise", EtatMise.ATTENTE);
		
		List<Misek> misk = query.getResultList();
		return misk;
	}

	@Override
	public List<Misek> searchWaitingKenoBet(Partner partner, int drawnum) throws DAOException {
		
		List<Misek> misk = null;
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Query<Misek> query =  currentSession.createQuery(QueryHelper.SQL_F_WAITING_KENO_BET,  Misek.class);
			query.setParameter("drawnumk", drawnum)
			     .setParameter("etatmise",EtatMise.ATTENTE)
			     .setParameter("partner", partner);
	
			misk = query.getResultList();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return misk;
	}

	@Override
	public double getMiseKCycle(Long misek, Long mise, Partner partner) throws DAOException {
		double sum = 0;
		
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			Object o = currentSession.createQuery(QueryHelper.SQL_F_COMPTA_CYCLE)
			.setParameter("idMiseK", misek)
			.setParameter("idMiseK1", mise)
			.setParameter("partner", partner)
			.getSingleResult();
			
			if(o != null) sum = (double)o;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return sum;
	}

	@Override
	public double getMiseKCycleWin(long misek, long mise, Partner partner) throws DAOException {

		try {
			Session currentSession = sessionFactory.getCurrentSession();
//			return (double) currentSession.createQuery(QueryHelper.SQL_F_COMPTA_CYCLE_WIN)
//			.setParameter("idMiseK", misek)
//			.setParameter("idMiseK1", mise)
//			.setParameter("partner", partner)
//			.getSingleResult();
//			
			
			@SuppressWarnings("unchecked")
			Query<Double> query = currentSession.createQuery(QueryHelper.SQL_F_COMPTA_CYCLE_WIN);
			query.setParameter("idMiseK", misek)
				 .setParameter("idMiseK1", mise)
			     .setParameter("partner", partner);
			  
			//misk = query.getSingleResult();
			
			Optional<Double> q = query.uniqueResultOptional();
			if (q.isPresent()) {
				Double misk = q.get();
				return misk.doubleValue();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int findId(Miset miset) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_F_MAX_PARTNER_ID)
		.setParameter("miset", miset)
		.getSingleResult();
	}

	@Override
	public int updateMiseRK(Caissier caissier, String date, String date1) throws DAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return (int) currentSession.createQuery(QueryHelper.SQL_U_COMPTA)
		.setParameter("caissier", caissier)
		.setParameter("heur1", date)
		.setParameter("heur2", date1)
		.getSingleResult();
	}

}
