package com.pyramid.dev.service;

import java.util.List;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Partner;

public interface CagnotteService {
	
	List <Cagnotte> find(Partner p) throws DAOException;
	public int update(Cagnotte cgt) throws DAOException;
	public int updateCagnot(long id, long code, long mise);
	public boolean create(Cagnotte cagnotte) throws DAOException;
	public List<Cagnotte> findAllPendingCagnotte(Partner p);
}
