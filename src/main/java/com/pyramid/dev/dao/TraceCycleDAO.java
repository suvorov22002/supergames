package com.pyramid.dev.dao;

import java.util.List;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.TraceCycle;

public interface TraceCycleDAO {
	
	boolean create(TraceCycle trc) throws DAOException;
	List<TraceCycle> find(String coderace) throws DAOException;
	TraceCycle find(String coderace, int keno) throws DAOException;
	List<TraceCycle> find(GameCycle gmc) throws DAOException;
	int update(TraceCycle trc) throws DAOException;
	boolean delete(TraceCycle trc) throws DAOException;
}
