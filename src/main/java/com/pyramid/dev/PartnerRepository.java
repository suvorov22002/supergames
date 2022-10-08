package com.pyramid.dev;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pyramid.dev.model.Partner;

//@Repository
//public interface PartnerRepository extends JpaRepository<Partner, Long> {
	
	public interface PartnerRepository{
	
	public Partner findByCoderace(String coderace); 
	
	@Query("UPDATE Partner p SET p.bonuskamount = ?1, p.bonuskcode = ?2 WHERE p.idPartner = ?3 ")
	public int updateBonusk(double dbl, int bncd, Long id);
	
	@Query("UPDATE Partner p SET p.bonuskamount = ?1 WHERE p.coderace = ?2 ")
	public int updateResetBonusk(double dbl, String coderace);
}
