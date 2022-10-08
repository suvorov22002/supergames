package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pyramid.dev.dao.KenoDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.KenoService;

@Service
@Transactional
public class KenoServiceImpl implements KenoService {
	
	@Autowired
	private KenoDAO kenodao;
	
	@Override
	public boolean create(Keno keno) throws DAOException {
		return kenodao.create(keno);
	}

	@Override
	public Response find(Keno keno) throws DAOException {
		return kenodao.find(keno);
	}

	@Override
	public Keno find_Max_draw(Partner partner) throws DAOException {
		return kenodao.find_Max_draw(partner);
	}

	@Override
	public Keno find_Max_draw_bis(Partner partner) throws DAOException {
		return kenodao.find_Max_draw_bis(partner);
	}

	@Override
	public int update(Keno keno) throws DAOException {
		return kenodao.update(keno);
	}

	@Override
	public boolean delete(Keno keno) throws DAOException {
		return kenodao.delete(keno);
	}

	@Override
	public double findBonusAmount(Partner partner) throws DAOException {
		return kenodao.findBonusAmount(partner);
	}

	@Override
	public int updateBonus(double bonus, int drawnumber, Partner partner) throws DAOException {
		return kenodao.updateBonus(bonus, drawnumber, partner);
	}

	@Override
	public List<Keno> getLastKdraw(Partner partner) throws DAOException {
		return kenodao.getLastKdraw(partner);
	}

	@Override
	public Keno getMaxIdDrawK(Partner partner) throws DAOException {
		return kenodao.getMaxIdDrawK(partner);
	}

	@Override
	public Keno searchResultK(int drawnum, Partner partner) throws DAOException {
		return kenodao.searchResultK(drawnum, partner);
	}

	@Override
	public int updateDrawEnd(int drawnumber, Partner partner) throws DAOException {
		return kenodao.updateDrawEnd(drawnumber, partner);
	}
	
	@Override
	public boolean updateDrawEnd(Keno keno) throws DAOException {
		return kenodao.updateDrawEnd(keno);
	}

	@Override
	public Long getIdKenos(Partner partner, int drawnumk) throws DAOException {
		return kenodao.getIdKenos(partner, drawnumk);
	}

	@Override
	public int setCodeBonusK(double amount, int code, long idkeno) throws DAOException {
		return kenodao.setCodeBonusK(amount, code, idkeno);
	}

	@Override
	public List<Keno> getLastKBonus(Partner partner) throws DAOException {
		return kenodao.getLastKBonus(partner);
	}

	@Override
	public double findTotalBonusAmount(Long id1, Long id2, Partner partner) throws DAOException {
		return kenodao.findTotalBonusAmount(id1, id2, partner);
	}

	@Override
	public List<Keno> find_Last_draw(Partner partner) throws DAOException {
		return kenodao.find_Last_draw(partner);
	}

	@Override
	public List<Keno> findAllDraw(Partner partner) throws DAOException, EmptyResultDataAccessException {
		return kenodao.findAllDraw(partner);
	}

	@Override
	public Keno findDraw(Partner partner, int num) throws DAOException {
		return kenodao.findDraw(partner, num);
	}

	@Override
	public List<Keno> getAllLastKdraw(Partner partner) throws DAOException {
		return kenodao.getAllLastKdraw(partner);
	}

}
