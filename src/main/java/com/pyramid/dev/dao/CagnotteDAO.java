package com.pyramid.dev.dao;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Partner;

public interface CagnotteDAO {
	
	Cagnotte find(Partner p) throws DAOException;
	public int update(Cagnotte cgt) throws DAOException;
	public int updateCagnot(long id, long code, long mise);
	public boolean create(Cagnotte cagnotte) throws DAOException;
}
