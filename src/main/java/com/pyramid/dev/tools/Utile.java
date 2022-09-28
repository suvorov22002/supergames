package com.pyramid.dev.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pyramid.dev.business.RefreshK;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Miset;


public class Utile {
	
	public static int timekeno = 185;
	public static List<ControlDisplayKeno> _display_drawLK;
	public static  Map<String, ControlDisplayKeno> display_draw;
	public static String multiplicateur[] = {"5", "1", "2", "0.5"};
	public static int bonusK_amount = 0;
	public static double bonusK_down;
	public static int bonus_codeK;
	public static double bonusKmin = 0.0;
	public static double bonusrate;
	
	public static double num10[] = {0,0,0,1,2,3,5,10,200,2000,10000};
	public static double num9[] = {0,0,0,1,2,3,25,100,1500,9000};
	public static double num8[] = {0,0,0,1,4,15,50,1200,8000};
	public static double num7[] = {0,0,0,1,3,30,220,3000};
	public static double num6[] = {0,0,1,2,10,60,800};
	public static double num5[] = {0,0,1,3,30,500};
	public static double num4[] = {0,0,2,10,100};
	public static double num3[] = {0,1,3,50};
	public static double num2[] = {0,1,10};
	public static double numAll[] = {0,4.1,14.5,60.5,275,1400,6500};
	public static double numOut[] = {0,1.7,2.1,2.5,3.2,4.2,5.5,7.5,10,13.5,18.5};
	public static double numSpec[] = {1.85,1.87,3.80};
	
	public static RefreshK ref = new RefreshK();
	
	public static int _checkExistingSameDisplayCoderace(String coderace){
		
		if(display_draw.containsKey(coderace)) {
			return 1;
		}
		
		return -1;
	}
	
	public static int generate(int n){
		int lower = 0;
		int higher = n;
		int dbl;
		  
		dbl = (int) (Math.random() * (higher-lower)) + lower;
		return dbl;
	}
	
	public static String convertJsonToString(JSONArray data) {
		//final Type DATA_TYPE_JSON = new TypeToken<JSONObject>() {}.getType();
		//String data = new Gson().toJson(json, DATA_TYPE_JSON);  "{\"ncp\":\"02979551051\",\"clc\":\"55\",\"nomrest\":\"KONCHOU FRANCIS ARMEL                                              \",\"age\":\"00001\"}"
		return data.toString().replaceAll("\\\"","");
	}

	public static String convertJsonToString(JSONObject data) {
		//final Type DATA_TYPE_JSON = new TypeToken<JSONObject>() {}.getType();
		//String data = new Gson().toJson(json, DATA_TYPE_JSON);
		return data.toString().replaceAll("\\\"","");
	}


	public JSONArray convertResultSetToJsonArrays(ResultSet resultSet) throws SQLException
	{                  
		JSONArray json = new JSONArray();
		ResultSetMetaData metadata = resultSet.getMetaData();
		int numColumns = metadata.getColumnCount();

		while(resultSet.next()){
			JSONObject obj = new JSONObject(); 
			for (int i = 1; i <= numColumns; ++i){
				String column_name = metadata.getColumnName(i);
				Object value = resultSet.getObject(column_name);
				if(StringUtils.endsWithIgnoreCase("inti", column_name)) {
					String rep = StringUtils.replace(value.toString(),";","");
					rep = StringUtils.replace(value.toString(),",","");
					rep = StringUtils.replace(value.toString(),","," ");
					obj.put(column_name,rep );
				}else {
					obj.put(column_name,value);
				}
			}
			json.put(obj);
		}
		return json;
	}


	public JSONObject convertResultSetToJsonObject(ResultSet resultSet) throws SQLException
	{
		JSONObject json = new JSONObject();
		ResultSetMetaData metadata = resultSet.getMetaData();
		int numColumns = metadata.getColumnCount();

		if(resultSet.next()){
			JSONObject obj = new JSONObject(); 
			for (int i = 1; i <= numColumns; ++i){
				String column_name = metadata.getColumnName(i);
				obj.put(column_name, resultSet.getObject(column_name));
			}
			json = obj;
		}
		return json;
	}
	
	public static long givetimestamp(String str) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
		
		Date date = (Date)formatter.parse(str);
		
		long output = date.getTime()/1000L;
		
		String d_str = Long.toString(output);
		
		long timestamp = Long.parseLong(d_str)*1000;
		
		return timestamp;
	}
	
//	public static void updateCoupon(ControlDisplayKeno cds) {
//		if(cds.isMiseAjour()) {
//			
//			cds.setStr_draw_combi("");
//			System.out.println("[REFRESH KENO MIS A JOUR]: "+_keno.getDrawnumK()+" multi: "+_keno.getMultiplicateur()+" coderace: "+_keno.getCoderace()+" id: "+_keno.getIdKeno());
//			
//				      _keno.setStarted(1);
//					//  supermanager.endDraw(_keno);
//					  supermanager.endDraw(_keno.getDrawnumK(), _keno.getPartner());
//					  
//					  cds.setEnd(1); //fin du tour
//					  list_barcode = new ArrayList<Misek>();
//					  list_barcode = mskservice.searchWaitingBet(partner, cds.getDrawNumk());
//			     	  System.out.println("[REFRESH WAITING BET]"+list_barcode.size());
//					  map_wait.clear();
//					  for(Misek m : list_barcode) {
//						  Miset mt = mstservice.findById(m.getMiset().getIdMiseT());
//						  if(mt != null)
//							  map_wait.put(mt, m);
//					  }
//					  supermanager.verifTicket(map_wait, partner);
//					  if(!dead_round) {
//						//recherche gmc
//						  gmc = gmcservice.findByGame(partner, Jeu.K);
//						  position = gmc.getPosition();
//						  System.out.println("[REFRESH CYCLE POS TOUR]: "+position+" - "+tour);
//						  if(position >= tour) {
//							  idmisek_max = mskservice.ifindId(partner);
//								//ArrayList<Integer> roundList = Params.getHitFrequency(gmc.getHitfrequence(), gmc.getTour());
//								    List<Integer> roundList = supermanager.getHitFrequency(gmc.getHitfrequence(), gmc.getTour());	
//									String posi = "";
//									for(int nb : roundList) {
//										
//										posi = posi +"-"+ nb;
//									}
//									posi = posi.substring(1);
//									arrangement_pos = posi;
//									
//								
//								
//								List<GameCycle> _gmc = gmcservice.find(partner);
//								int taille = _gmc.size();
//								double curr_percent = 1;
//								double summise = 1;
//								double sumWin = 0;
//								double jkpt = 0;
//					//			System.out.println("RefreshK  taille: "+taille);
//								if(taille > 1) {
//									GameCycle gm = _gmc.get(0); 
//									//System.out.println("gm.getMise(): "+gm.getMise()+" Misef: "+misef+" partner "+partner.getCoderace());
//									summise = mskservice.getMiseKCycle(gm.getMise(),misef, partner);
//					//				System.out.println("RefreshK  summise: "+summise);
//									sumWin = cds.getBonusrate()*summise + mskservice.getMiseKCycleWin(gm.getMise(),misef, partner);
//									curr_percent = sumWin/summise;
//									curr_percent = (double)((int)(curr_percent*100))/100;
//									//jkpt = UtileKeno.bonusrate*summise;
//					    			
//					    			sumWin = (double)((int)(sumWin*100))/100;
//					    			summise = (double)((int)(summise*100))/100;
//					    			
//					    			//recherche du jackpot
//					    			Misek m1 = mskservice.searchMiseK(gm.getMise());
//					    			Misek m2 = mskservice.searchMiseK(misef);
//					    			if(m1 != null && m2 != null) {
//					    				Long k1 = m1.getKeno().getIdKeno();
//						    			Long k2 = m2.getKeno().getIdKeno();
//						    			jkpt  = kenoservice.findTotalBonusAmount(k1, k2, partner);
//						    			jkpt = (double)((int)(jkpt*100))/100;
//					    				
//					    			}
//					    			else {
//					    				jkpt = 0;
//					    			}
//					    			
//								}
//								
//								
//								//idmisek_max = misekDao.ifindId(IN);
//								int add = gmcservice.updateArchive(curr_percent, DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"), 1, partner, Jeu.K, misef, summise, sumWin, jkpt);
//
//									  GameCycle gamecycle = new GameCycle();
//									  gamecycle.setRefundp(cds.getRtp());
//									  gamecycle.setPosition(0);
//									  gamecycle.setPartner(partner);
//									  gamecycle.setPercent(percent);
//									  gamecycle.setTour(tour);
//									  gamecycle.setArrangement(arrangement_pos);
//									  gamecycle.setHitfrequence(roundSize);
//									  gamecycle.setJeu(Jeu.K);
//									  gamecycle.setArchive(0);
//									  gamecycle.setMise(misef);
//									  gamecycle.setMisef(idmisek_max);
//									  gamecycle.setDate_fin(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));
//									 
//								//	  if(add != 0) {
//									  gmcservice.create(gamecycle);
//										  cds.setRtp(0);
//								//	  }
//									  
//							} 
//					  }
//					  
//					  cds.setMiseAjour(Boolean.FALSE);
//		}
//	}
	
}
