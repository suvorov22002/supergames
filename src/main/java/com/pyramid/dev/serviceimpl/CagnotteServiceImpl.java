package com.pyramid.dev.serviceimpl;

import java.util.List;

import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyramid.dev.dao.CagnotteDAO;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.CagnotteService;

@Transactional
@Service
@AllArgsConstructor
public class CagnotteServiceImpl implements CagnotteService {

	private final CagnotteDAO cagnotteDao;

	@Override
	public List <Cagnotte> find(Partner p) throws DAOException {
		return cagnotteDao.find(p);
	}

	@Override
	public int update(Cagnotte cgt) throws DAOException {
		return cagnotteDao.update(cgt);
	}

	@Override
	public int updateCagnot(long id, long code, long mise) {
		return cagnotteDao.updateCagnot(id, code, mise);
	}

	@Override
	public boolean create(Cagnotte cagnotte) throws DAOException {
		return cagnotteDao.create(cagnotte);
	}

	@Override
	public List<Cagnotte> findAllPendingCagnotte(Partner p) {
		return cagnotteDao.findAllPendingCagnotte(p);
	}

}
