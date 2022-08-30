package com.pyramid.dev.dao;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.dao.EmptyResultDataAccessException;

import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Partner;

public interface KenoDAO {
	
	boolean create(Keno keno) throws DAOException;
	Response find(Keno keno) throws DAOException;
	Keno find_Max_draw(Partner partner) throws DAOException;
	Keno find_Max_draw_bis(Partner partner) throws DAOException;
	int update(Keno keno) throws DAOException;
	boolean delete(Keno keno) throws DAOException;
	double findBonusAmount(Partner partner) throws DAOException;
	int updateBonus(double bonus, int drawnumber, Partner partner) throws DAOException;
	List<Keno> getLastKdraw(Partner partner) throws DAOException;
	Keno getMaxIdDrawK(Partner partner) throws DAOException;
	Keno searchResultK(int drawnum, Partner partner) throws DAOException; 
	int updateDrawEnd(int drawnumber, Partner partner) throws DAOException;
	boolean updateDrawEnd(Keno keno) throws DAOException;
	Long getIdKenos(Partner partner, int drawnumk) throws DAOException;
	int setCodeBonusK(double amount, int code, long idkeno) throws DAOException;
	List<Keno> getLastKBonus(Partner partner) throws DAOException;
	double findTotalBonusAmount(Long id1, Long id2, Partner partner) throws DAOException;
	List<Keno> find_Last_draw(Partner partner) throws DAOException;
	Keno find_Single_draw(Partner partner) throws DAOException,EmptyResultDataAccessException;
	Keno find_Max_draw_num(Partner partner, int num) throws DAOException;
	List<Keno> getAllLastKdraw(Partner partner) throws DAOException;
}
