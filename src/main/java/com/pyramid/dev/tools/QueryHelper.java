package com.pyramid.dev.tools;

public class QueryHelper {
	
	/* KenoDAO queries */
	public static final String SQL_F_MAX_DRAW = "from Keno k where k.partner=:coderace and k.drawnumK in "
			+ "(select max(drawnumK) from Keno where partner=:coderace1)";
	
	public static final String SQL_F_MAX_PREVIOUS_DRAW = "From Keno K Where K.partner=:coderace And"
			+ " K.drawnumK = (Select max(drawnumK) from Keno where partner=:coderace1 And multiplicateur!='0')";
			
	public static final String SQL_F_BONUS_AMOUNT_KENO = "Select K.bonusKamount from Keno K Where K.partner =: coderace"; 
	public static final String SQL_U_BONUS_AMOUNT_KENO = "Update Keno K set K.bonusKamount=:bonus Where K.drawnumK=:drawnum And K.partner =: coderace ";
	public static final String SQL_F_PREVIOUS_FIVE_DRAW = "FROM Keno WHERE multiplicateur != '0' AND partner=:coderace "
			+ "ORDER BY drawnumK DESC ";
	public static final String SQL_F_MAX_ID = "From Keno WHERE idKeno IN (Select MAX(idKeno) From Keno where partner=:coderace)";
	public static final String SQL_F_DRAW = "From Keno Where drawnumK=:drawnum and partner=:coderace";
	public static final String SQL_U_DRAW_END = "Update Keno Set started=1 Where drawnumK >:drawnum and partner=:coderace and multiplicateur != '0'";
	public static final String SQL_F_ID = "Select idKeno FROM Keno where partner=:coderace AND drawnumk=:drawnum";
	public static final String SQL_U_KENO_BONUS = "Update Keno Set bonusKamount=:bonusamount , bonusKcod=:bonuscode Where idKeno=:idKeno " ; 
	public static final String SQL_F_PREVIOUS_BONUS = "From Keno where bonusKcod !=0 and partner=:coderace "
			+ "Order By idKeno Desc ";	
	public static final String SQL_F_SUM_BONUS = "SELECT sum( bonusKamount ) FROM Keno WHERE idKeno >:idkeno AND idKeno <=:idkeno1 and partner=:coderace ";
	
	public static final String SQL_F_ALL_DRAW = "From Keno WHERE partner=:coderace";		
	//public static final String SQL_F_DRAW = "From Keno WHERE partner=:coderace and drawnumK=:drawnum ";
	public static final String SQL_U_KENO = "UPDATE Keno SET drawnumbK=:drawnumbK ,"
		      + " multiplicateur = :multi , heureTirage = :heur "
		      + "WHERE drawnumK = :drawnum AND coderace = :coderace "; 
	
	
	/* ConfigDAO queries */
	
	public static final String SQL_F_CONFIG = "FROM Config Where coderace =:coderace ";
	public static final String SQL_U_CONFIG_BONUS = "Update Config set stepbonus=? where coderace = ? ";
	public static final String SQL_U_CONFIG_BONUSK = "Update Config Set bonusrate=:bonusrate, bnkmin=:bnkmin, bnkmax=:bnkmax where coderace =:coderace ";
	public static final String SQL_U_CONFIG_BONUSP = "Update Config Set bonusrate=:bonusrate, bnpmin=:bnpmin, bnpmax=:bnpmax where coderace  =:coderace ";
	
	/* CaissierDAO queries*/
	
	public static final String SQL_F_LOGIN_PASS = "FROM Caissier WHERE loginc=:loginC and partner =: partner"; 
	public static final String SQL_CAIS_F_ID = "From Caissier where idCaissier =:idCaissier ";
	public static final String SQL_U_CAISSIER_STATE = "UPDATE Caissier Set statut=:statut WHERE loginc=:loginC ";  
	public static final String SQL_F_LOGIN_PARTNER = "FROM Caissier WHERE partner IN ( From Partner where coderace =:coderace ) "; 
	public static final String SQL_F_LOGIN_CODERACE = "From Caissier where loginc=:login and partner =:id ";
	
	/* GameCycleDAO queries*/
	public static final String SQL_F_GAMECYCLE = "From GameCycle Where partner =:partner Order By idcycle desc ";
	public static final String SQL_U_GAMECYCLE = "Update GameCycle Set percent =:percent , tour =:tour , hitfrequence =:hitfrequence , refundp =:refundp , "
			+ "position =:position , arrangement =:arrangement , jeu =:jeu Where idpartner =:id ";
	public static final String SQL_U_GAMECYCLE_RTP = "Update GameCycle Set refundp =:refundp Where idpartner =:id and archive = 0 and jeu =:jeu ";
	public static final String SQL_U_GAMECYCLE_POS = "Update GameCycle Set position =:position Where idpartner =:id and archive = 0 and jeu =:jeu ";
	public static final String SQL_F_GAMECYCLE_JEU = "From GameCycle Where partner =:id And jeu =:jeu and archive = 0 ";
	public static final String SQL_U_GAMECYCLE_ARCHIVE = "Update GameCycle Set curr_percent =:curr_percent , date_fin =:date_fin , archive =:archive , misef =:misef , stake =:stake , "
			+ "payout =:payout , jkpt =:jkpt Where idpartner =:id and jeu =:jeu and archive = 0 ";
	public static final String SQL_F_GAMECYCLE_ALL = "From GameCycle Where idpartner =:id order by idcycle ";
	
	/* MouvementDAO queries */
	
	public static final String SQL_F_MVT = "Select mvt from Mouvement Where caissier =:caissier ";
	public static final String SQL_F_CAIS_MVT = "From Mouvement Where caissier =:caissier ";
	public static final String SQL_F_CMVT = "Select count(*) from Mouvement Where caissier =: caissier ";
	public static final String SQL_UF_MVT = "Update Mouvement set mvt =:credit where caissier =:caissier ";
	
	/* AirtimeDAO queries*/
	
	public static final String SQL_F_CREDIT = "From Airtime Where caissier =:caissier And idairtime = (Select Max(idairtime) "
			+ "From Airtime Where caissier =:caissier )";
	//public  static final String SQL_UF_CREDIT = "Select idairtime, date, credit, debit, balance, caissier, libelle, eta"
	//		+ " From Airtime where caissier =:caissier and libelle Like 'CREDIT EN CAISSE' and date between :date1 And :date2 Group By date";
	public  static final String SQL_UF_CREDIT = "Select idairtime, date, sum(credit) as credit, debit, balance, caissier"
			+ " From Airtime where caissier =:caissier and date between :date1 And :date2 Group By date";
	public static final String SQL_UF_S_CREDIT = "Select sum(credit) From Airtime where caissier =:caissier and date between :date1 And :date2 ";
	
	
	
	/* PartnerDAO queries */
	
	public static final String SQL_U_BONUS_AMOUNT_PARTNER = "Update Partner SET bonuskamount =:bonuskamount , bonuskcode=:bonuskcode "
			+ " WHERE coderace =:coderace ";
	public static final String SQL_U_BONUSP_AMOUNT_PARTNER = "Update Partner SET bonuspamount =:bonuspamount , bonuspcode=:bonuspcode"
			+ " WHERE coderace =:coderace ";
	public static final String SQL_U_BONUS_RESET_AMOUNT_PARTNER = "Update Partner SET bonuskamount =:bonuskamount WHERE coderace =:coderace ";
	public static final String SQL_U_BONUSP_RESET_AMOUNT_PARTNER = "Update Partner SET bonuskamount =:bonuspamount WHERE coderace =:coderace ";
	public static final String SQL_F_PARTNER_BY_ID = "From Partner WHERE coderace =:partner ";
	public static final String SQL_U_COB_PARTNER = "UPDATE Partner SET COB =:cob WHERE coderace =:coderace ";
	public static final String SQL_F_PARTNER_BY_GROUP = "From Partner WHERE groupe =:groupe ";
	public static final String SQL_F_PARTNER_CONF = "Select P.coderace as coderace, bnkmin, bnkmax, bnpmin, bnpmax, bndmin, "
			+ "bndmax, bonuskamount, bonusbamount, bonusdamount, bonuspamount, bonusramount "
			+ "from Config C, Partner P where C.coderace = P.coderace and P.coderace =:coderace";
	
	/* MisekDAO queries */
	public static final String SQL_U_MISEK = "Update Misek Set etatMise =:etatMise , sumWin =:sumWin Where idMiseK =:idMisek And miset =:miset ";
	public static final String SQL_F_MAX_ID_CODERACE = "SELECT MAX(idMiseK) FROM Misek Where caissier in "
			+ "(From Caissier Where partner=:partner)";
	public static final String SQL_F_MAX_MISEK_CODERACE = "FROM Misek Where caissier in "
			+ "(From Caissier Where partner=:partner) order by idMiseK asc";
	public static final String SQL_F_MISEK_MAX_ID = "SELECT MAX(idMiseK) FROM Misek";
	public static final String SQL_F_MISEK_MISET = "FROM Misek WHERE miset =:miset ";
	public  static final String SQL_F_MISEK_ID = "FROM Misek WHERE idMiseK =:idMiseK ";
//	public static final String SQL_F_DRAW_NUM = "SELECT DISTINCT m.drawnumk from Misek m,EffChoicek f WHERE m.idMiseK =:misek AND "
//			+ "m.idMiseK = f.idMiseK ";
	public static final String SQL_F_DRAW_NUM = "SELECT DISTINCT m.drawnumk from Misek m WHERE m.idMiseK =:misek ";
	
	public static final String SQL_F_COMPTA = "SELECT SUM(sumMise) FROM  Misek WHERE  caissier =:caissier AND  heureMise BETWEEN :heur1 AND :heur2 AND archive = 0 ";
	public static final String SQL_F_COUNT_MISEK = "SELECT COUNT(*) FROM Misek WHERE caissier =:caissier AND"
			+ " heureMise BETWEEN :heur1 AND :heur2 AND archive = 0 ";
	public static final String SQL_F_STAT_MISEK = "FROM Misek WHERE heureMise BETWEEN :heur1 AND :heur2 "
			+ "AND keno IN (From Keno Where coderace=:coderace )"
			+ "order by heureMise desc";
	public static final String SQL_F_STAT_MISEK_MISET = "SELECT k.dateMise as datMise, t.barcode as barcode, t.typeJeu as typeJeu, t.summise as summise,k.sumWin as sumwin, k.etatMise as etatMise "
			+ "FROM Miset t, Misek k, Keno e WHERE heureMise BETWEEN :heur1 AND :heur2 "
			+ "AND e.partner =:coderace "
			+ "AND t.idMiseT = k.miset "
			+ "AND e.idKeno = k.keno "
			+ "order by heureMise desc";
	public static final String SQL_F_ID_KENO = "SELECT idMiseK FROM Misek WHERE keno =:keno ";
	public static final String SQL_F_MISEK_DRAWNUMK = "FROM Misek WHERE drawnumk =:drawnumk And keno=:keno";
	public static final String SQL_F_WAITING_BET = "From Misek Where etatMise =:etatmise And drawnumk < :drawnumk And caissier in "
			+ "(From Caissier Where partner=:partner)";
	public static final String SQL_F_WAITING_KENO_BET = "From Misek where etatMise =:etatmise And drawnumk =:drawnumk And caissier in"
			+ "(From Caissier Where partner=:partner) order by idmisek asc ";//ticket joué pour un tour
	

	//public static final String SQL_F_WAITING_EFFCHK = "Select e From EffChoicek e INNER JOIN e.misek m where m.etatMise =:etatmise And m.drawnumk =:drawnumk And m.caissier in"
	//		+ "(From Caissier Where partner=:partner)";//ticket joué pour un tour
	public static final String SQL_F_WAITING_EFFCHK = "Select e From EffChoicek e, Misek m where e.misek = m And m.etatMise =:etatmise And e.drawnum =:drawnumk And m.caissier in"
			+ "(From Caissier Where partner=:partner)";//ticket joué pour un tour
	
	public static final String SQL_F_WAITING_KENO_BET_2 = "order by idMiseK ASC";
	public static final String SQL_F_COMPTA_CYCLE = "SELECT SUM(sumMise) FROM  Misek WHERE  idMiseK > :idMiseK And idmisek < :idMiseK1 And caissier in "
			+ "(From Caissier Where partner=:partner)";
	public static final String SQL_F_COMPTA_CYCLE_WIN = "SELECT SUM(sumWin) FROM Misek WHERE  idMiseK > :idMiseK And idMiseK < :idMiseK1 And caissier in "
			+ "(From Caissier Where partner=:partner)";
	public static final String SQL_F_MAX_PARTNER_ID = "SELECT MAX(idMiseK) FROM Misek WHERE miset=:miset ";
	public static final String SQL_U_COMPTA = "Update  Misek Set archive = 1 WHERE  caissier =:caissier AND  heureMise BETWEEN :heur1 AND :heur2 AND archive = 0 ";
	
	/* MisetDAO queries */
	
	public static final String SQL_F_MISET_MAX_ID = "SELECT MAX(idMiseT) FROM Miset Where coderace =:coderace";
	public static final String SQL_F_BARCODE = "FROM Miset WHERE barcode =:barcode ";
	public static final String SQL_F_BARC_MISET = "FROM Miset WHERE barcode =:barcode AND idMiseT =:idMiseT ";
	public static final String SQL_F_MISET_ID = "FROM Miset WHERE idMiseT =:idMiseT ";
	
	/* MisekTemp queries */
	public static final String SQL_U_MISEK_ID = "Update Misek_temp Set etatMise = etatMise + 1 where idmisek=:idmisek ";
	public static final String SQL_F_M_MISEK_ID = "From Misek_temp Where multi != etatMise ";
	public static final String SQL_F_TMP = "From Misek_temp t Where multi != etatMise and t.idmisek in "
			+ "(Select m.idMiseK From Misek m Where caissier in (From Caissier Where partner =:partner))";
	
	
	/* EffChoicekDAO queries */
	
	public static final String SQL_F_EFFCHOICE_MISEK = "FROM EffChoicek WHERE misek =:misek ";
	public static final String SQL_F_EFFCHOICE_KENO_MISEK = "FROM EffChoicek WHERE misek =:misek and drawnum =:drawnum ";
	public static final String SQL_F_MISEKTP_ID = "From Misek_temp where idmisek=:idmisek ";
	
	/* UtilDAO queries*/
	
	public static final String SQL_F_EXITS_BARCODE = "FROM Miset WHERE barcode =:barcode and typeJeu=:jeu";
	
	/* VersementDAO queries*/
	
	public static final String SQL_F_VERS_MISET = "FROM Versement WHERE mise =:mise "; 
	public static final String SQL_F_COMPTA_V = "SELECT SUM(montant) FROM Versement WHERE heureV BETWEEN :debut AND :fin AND typeVers =:jeu "
			+ "AND caissier =:caissier AND archive = 0 ";
	
	public static final String SQL_F_COMPTA_VERS = "SELECT SUM(montant) FROM Versement WHERE heureV BETWEEN :debut AND :fin "
			+ "AND caissier =:caissier AND archive = 0 ";
	
	public static final String SQL_F_COUNT_VERS = "SELECT COUNT(*) FROM Versement WHERE caissier=:caissier AND typeVers =:jeu AND"
			+ " heureV BETWEEN :debut AND :fin AND archive = 0 ";
	
	public static final String SQL_F_STAT_VERSE= "FROM Versement WHERE heureV BETWEEN :heur1 AND :heur2 "
			+ "AND caissier =: caissier AND archive = 0 ";
			
	
	/* CagnotteDAO queries */
	
	public static final String SQL_F_CAGNOTTE = "From Cagnotte Where partner =:partner and idCagnotte = (Select max(idCagnotte) from Cagnotte where partner =: partner1 ) ";
	
	public static final String SQL_U_CAGNOTTE = " Update Cagnotte Set barcode =: barcode, mise =: mise Where idCagnotte =: id";
	public static final String SQL_C_CAGNOTTE = "Insert into Cagnotte Set lot =:lot , jour =:jour , heure =:heur , partner =:partner ";
	
}
