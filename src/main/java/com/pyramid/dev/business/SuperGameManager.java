package com.pyramid.dev.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.EffChoicek;
import com.pyramid.dev.model.EffChoicep;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Misep;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.CagnotteService;
import com.pyramid.dev.service.CaissierService;
import com.pyramid.dev.service.ConfigService;
import com.pyramid.dev.service.EffChoicekService;
import com.pyramid.dev.service.GameCycleService;
import com.pyramid.dev.service.KenoService;
import com.pyramid.dev.service.MisekService;
import com.pyramid.dev.service.Misek_tempService;
import com.pyramid.dev.service.MisetService;
import com.pyramid.dev.service.PartnerService;
import com.pyramid.dev.serviceimpl.CagnotteServiceImpl;
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.Params;
import com.pyramid.dev.tools.Utile;

/**
 * 
 * @author rodrigue_toukam
 * @version 1.0
 *
 */
@Component
@Configurable
public class SuperGameManager {
	
	@Autowired
	KenoService kenoservice;
	
	@Autowired
	PartnerService partservice;
	
	@Autowired
	ConfigService cfgservice;
	
	@Autowired
	CaissierService caissierservice;
	
	@Autowired
	GameCycleService gameservice;
	
	@Autowired
	GameCycleService gmcservice;
	
	@Autowired
	MisekService mskservice;
	
	@Autowired
	MisetService mstservice;
	
	@Autowired
	Misek_tempService mtpservice;
	
	@Autowired
	EffChoicekService effchoicekservice;
	
	@Autowired
	CagnotteService cagnotservice;
	
	
	
	private static Map<String, Double> mapMin = new HashMap<String, Double>();
	
	public double verifTicketMax(Map<Miset, Misek> map_wait, Partner partner) {
	
			String barcode;
			Miset miset;
			double _montant_evt = 0;//le prix d'un evenement
 		   double gain_total = 0;
			for(Map.Entry mapentry : map_wait.entrySet()) {
				miset = (Miset) mapentry.getKey();
				barcode = miset.getBarcode();
				//System.out.println("key: "+barcode+" | "+misek);
			//}
			if(barcode == null || barcode == ""){
				//return;
			}
			else{
				//System.out.println("BARCODE: "+barcode);
			//	if(!isTesting){
					
					
					//Miset miset = misetDao.searchTicketT(barcode);
					if(miset != null){ //le ticket existe
					
							//le ticket est en attente de traitement
						String typeJeu = miset.getTypeJeu().value();
							
							/*--- Keno traitment ---*/
							List<EffChoicek> ticket = new ArrayList<EffChoicek>();
							Misek mk = new Misek();
							
							/*--- Spin traitment ---*/
							ArrayList<EffChoicep> ticketp = new ArrayList<EffChoicep>();
							Misep mp = new Misep();
							
							switch(typeJeu)
							{
								case "K": // traitement ticket keno
									mk = mskservice.searchMisesK(miset);
									if(mk != null){
										//on recupere les evenements du ticket
										//ticket = effchoicekservice.searchTicketK(""+mk.getIdmisek());
										ticket = effchoicekservice.searchTicketK(mk, mk.getDrawnumk());
										int xmulti = mk.getXmulti();
										//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
										//System.out.println("EFFH EVNTS: "+ticket.size());
										
										if( ticket.size() > 0 ){
											
											
										    
										    // recuperation de tous les evenement du ticket
											   
							    		   ArrayList<String> resultMulti  = new ArrayList<String>();
							    		   boolean multiple = false;
							    		   int multi = 1;
							    		   double xtiplicateur = 1;
							    		   Misek_temp misektp = mtpservice.find(mk.getIdMiseK());
							    		   if(misektp != null) {
							    			   multi = misektp.getMulti();
							    		   }
							    		   multiple = multi > 1 ? true : false;
							    		   
							    		   
							    		   // Verification du ticket
							    		
							    		   
							    		   for(int i=0;i<ticket.size();i++ ){
							    			   
							    			 
							    			//   System.out.println("_RESULT-DETAILS: "+ticket.size());
//							    			   if(multiple){
											    	   double odd = checkTicketKMax(ticket.get(i));
											    	   //setDetailTicket("cote", ""+odd);
											    	   
											    	   if(odd != 0 && odd != -1){
											    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
											    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//						
											    		   if(xmulti != 0) {
											    			   gain_total = gain_total * xtiplicateur;
											    		   }
											    		   gain_total = (double)((int)(gain_total*100))/100;
											    		  // return true;
														}
							    		   }
							    		   
										}
									}
								   break;
								default:
									break;
							       
							}
							
					}
					
			}
		  }
			
			return gain_total;
		}
	
	public double verifTicketMin(Map<Miset, Misek> map_wait, Partner partner) {
 		
	 	   mapMin.put("pair", 0.0);
	 	   mapMin.put("ipair", 0.0);
	 	   mapMin.put("pair20", 0.0);
	 	   mapMin.put("ipair20", 0.0);
	 	   mapMin.put("sum5", 0.0);
	 	   mapMin.put("isum5", 0.0);
	 	   mapMin.put("sum20", 0.0);
	 	   mapMin.put("isum20", 0.0);
	 	   mapMin.put("bleu", 0.0);
	 	   mapMin.put("rouge", 0.0);
	 	   mapMin.put("vert", 0.0);
	 	   mapMin.put("orange", 0.0);
	 	   mapMin.put("bleu20", 0.0);
		   mapMin.put("rouge20", 0.0);
		   mapMin.put("vert20", 0.0);
		   mapMin.put("orange20", 0.0);
		   mapMin.put("isum40", 0.0);
	 	   mapMin.put("sum40", 0.0);
	 	   
	 	    
				String barcode;
				Misek misek;
				Miset miset;
				double _montant_evt = 0;//le prix d'un evenement
	 		   double gain_total = 0;
				for(Map.Entry mapentry : map_wait.entrySet()) {
					miset = (Miset) mapentry.getKey();
					misek = (Misek) mapentry.getValue();
					barcode = miset.getBarcode();
					//System.out.println("key: "+barcode+" | "+misek);
				//}
				if(barcode == null || barcode == ""){
					//return;
				}
				else{
					//System.out.println("BARCODE: "+barcode);
				//	if(!isTesting){
						
						
						//Miset miset = misetDao.searchTicketT(barcode);
						if(miset != null){ //le ticket existe
						
								//le ticket est en attente de traitement
								String typeJeu = miset.getTypeJeu().getValue();
								
								/*--- Keno traitment ---*/
								List<EffChoicek> ticket = new ArrayList<EffChoicek>();
								Misek mk = new Misek();
								
								/*--- Spin traitment ---*/
								ArrayList<EffChoicep> ticketp = new ArrayList<EffChoicep>();
								Misep mp = new Misep();
								
								switch(typeJeu)
								{
									case "K": // traitement ticket keno
										mk = mskservice.searchMisesK(miset);
										if(mk != null){
											//on recupere les evenements du ticket
											//ticket = effchoicekservice.searchTicketK(""+mk.getIdmisek());
											ticket = effchoicekservice.searchTicketK(mk, mk.getDrawnumk());
											int xmulti = mk.getXmulti();
											//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
											//System.out.println("EFFH EVNTS: "+ticket.size());
											
											if( ticket.size() > 0 ){
												
												
											    
											    // recuperation de tous les evenement du ticket
												   
								    		   ArrayList<String> resultMulti  = new ArrayList<String>();
								    		   boolean multiple = false;
								    		   int multi = 1;
								    		   double xtiplicateur = 1;
								    		   Misek_temp misektp = mtpservice.find(mk.getIdMiseK());
								    		   if(misektp != null) {
								    			   multi = misektp.getMulti();
								    		   }
								    		   multiple = multi > 1 ? true : false;
								    		   
								    		   
								    		   // Verification du ticket
								    		   int bet;
								    		   double gain;
								    		   for(int i=0;i<ticket.size();i++ ){
								    			   bet = Integer.parseInt(ticket.get(i).getIdparil());
								    			 //   System.out.println("_RESULT-DETAILS: "+ticket.size());
//								    			   if(multiple){
								    				       
												    	   double odd = checkTicketKMin(ticket.get(i));
												    	   
												    	   //if(bet>11) {
												    		   
												    	   //}
												    	   
												    	   if(odd != 0 && odd != -1){
//												    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
//												    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//												    		   gain_total = (double)((int)(gain_total*100))/100;
												    		   gain = odd * Double.parseDouble(ticket.get(i).getMtchoix());
												    		   gain = (double)((int)(gain*100))/100;
												    		   twoSideKnife(bet, gain);
															}
//								    			   }
//								    			   else{
//								    				   
//								    				   double odd = checkTicketKMin(ticket.get(i));
//											    	  // setDetailTicket("cote", ""+odd);
//											    	   
//											    	   if(odd != 0 && odd != -1){
////											    		   
////											    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
////											    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
////											    		   gain_total = (double)((int)(gain_total*100))/100;
//											    		   gain = odd * Double.parseDouble(ticket.get(i).getMtchoix());
//											    		   gain = (double)((int)(gain*100))/100;
//											    		   twoSideKnife(bet, gain);
////											    		   
//														}
//													
//								    			   }
								    		   }
								    		   
											}
										}
									   break;
									default:
										break;
								       
								}
								
				//			}
						}
						
				//	}
				}
			  }
				gain_total = twoSideKnifeSum();
				return gain_total;
			}
	
	private double twoSideKnifeSum() {
  		double sum = 0;
  		sum = sum + Math.min(mapMin.get("pair"), mapMin.get("ipair"));
  		sum = sum + Math.min(mapMin.get("pair20"), mapMin.get("ipair20"));
  		sum = sum + Math.min(mapMin.get("sum5"), mapMin.get("isum5"));
  		sum = sum + Math.min(mapMin.get("sum20"), mapMin.get("isum20"));
  		sum = sum + Math.min(Math.min(mapMin.get("vert"), mapMin.get("bleu")), Math.min(mapMin.get("rouge"), mapMin.get("orange")));
  		sum = sum + Math.min(Math.min(mapMin.get("vert20"), mapMin.get("bleu20")), Math.min(mapMin.get("rouge20"), mapMin.get("orange20")));
  		sum = sum + Math.min(mapMin.get("sum40"), mapMin.get("isum40"));
  		return sum;
  	}
	
	private void twoSideKnife(int pari, double sum) {
 		 switch(pari) {
 		 	case 12:
 		 		mapMin.replace("pair", sum + mapMin.get("pair"));
 		 		break;
 		 	case 13:
 		 		mapMin.replace("ipair", sum + mapMin.get("ipair"));
 		 		break;
 		 	case 14:
 		 		mapMin.replace("pair20", sum + mapMin.get("pair20"));
 		 		break;
 		 	case 15:
 		 		mapMin.replace("ipair20", sum + mapMin.get("ipair20"));
 		 		break;
 		 	case 16:
 		 		mapMin.replace("sum5", sum + mapMin.get("sum5"));
 		 		break;
 		 	case 17:
 		 		mapMin.replace("isum5", sum + mapMin.get("isum5"));
 		 		break;
 		 	case 18:
 		 		mapMin.replace("sum20", sum + mapMin.get("sum20"));
 		 		break;
 		 	case 19:
 		 		mapMin.replace("isum20", sum + mapMin.get("isum20"));
 		 		break;
 		 	case 20:
 		 		mapMin.replace("vert", sum + mapMin.get("vert"));
 		 		break;
 		 	case 21:
 		 		mapMin.replace("bleu", sum + mapMin.get("bleu"));
 		 		break;
 		 	case 22:
 		 		mapMin.replace("rouge", sum + mapMin.get("rouge"));
 		 		break;
 		 	case 23:
 		 		mapMin.replace("orange", sum + mapMin.get("orange"));
 		 		break;
 		 	case 24:
 		 		mapMin.replace("vert20", sum + mapMin.get("vert20"));
 		 		break;
 		 	case 25:
 		 		mapMin.replace("bleu20", sum + mapMin.get("bleu20"));
 		 		break;
 		 	case 26:
 		 		mapMin.replace("rouge20", sum + mapMin.get("rouge20"));
 		 		break;
 		 	case 27:
 		 		mapMin.replace("orange20", sum + mapMin.get("orange20"));
 		 		break;
 		 	case 28:
 		 		mapMin.replace("sum40", sum + mapMin.get("sum40"));
 		 		break;
 		 	case 29:
 		 		mapMin.replace("isum40", sum + mapMin.get("isum40"));
 		 		break;
 		 }
 	 }
	
	public double getRoundPayed(double percent, int roundSize, int n_cycle, double bonusrate) {
		double sum = 0;
		
		sum = (Params.MISE_MIN * (percent-bonusrate))/roundSize;
		return sum;
	}
	
	public double verifTicketSum(Map<Miset, Misek> map_wait, String coderace, String result, String multix) {

		 double _montant_evt = 0;//le prix d'un evenement
		 double gain_total = 0;
		 double multiplicateur = Double.parseDouble(multix);
			String barcode;
			Misek misek;
			Miset miset;
			//System.out.println("MAP SIZE: "+map_wait.size()+" XM: "+multiplicateur);
			for(Map.Entry mapentry : map_wait.entrySet()) {
				miset = (Miset) mapentry.getKey();
				misek = (Misek) mapentry.getValue();
				barcode = miset.getBarcode();
			//	System.out.println("key: "+barcode+" | "+misek+" | "+miset);
			//}
					//Miset miset = misetDao.searchTicketT(barcode);
					if(miset != null){ //le ticket existe
					
							//le ticket est en attente de traitement
						    String typeJeu = miset.getTypeJeu().value();
						  
							/*--- Keno traitment ---*/
							List<EffChoicek> ticket = new ArrayList<EffChoicek>();
							Misek mk = new Misek();
							
							/*--- Spin traitment ---*/
							ArrayList<EffChoicep> ticketp = new ArrayList<EffChoicep>();
							Misep mp = new Misep();
				//		System.out.println("key: "+barcode+" | "+misek+" | "+miset+" | typeJeu= "+typeJeu);
							switch(typeJeu)
							{
								case "Keno": // traitement ticket keno
									mk = mskservice.searchMisesK(miset);
									if(mk != null){
										//on recupere les evenements du ticket
										//ticket = effchoicekservice.searchTicketK(""+mk.getIdmisek());
				//						System.out.println("MK EVNTS: "+ mk.getDrawnumk());
										ticket = effchoicekservice.searchTicketK(mk, mk.getDrawnumk());
										int xmulti = mk.getXmulti();
										//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
				//						System.out.println("EFFH EVNTS: "+ticket.size());
										
										if( ticket.size() > 0 ){
											boolean winner = true;
											//winT = true;
										    boolean isEnd = true;
										    int tested = 0;
										    
										    // recuperation de tous les evenement du ticket
										  //     for(int i=0;i<ticket.size();i++){
										    	   
							    		   boolean multiple = false;
							    		   int multi = 1;
							    		   String single_result="";
							    		  
							    		   Misek_temp misektp = mtpservice.find(mk.getIdMiseK());
							    		   if(misektp != null) {
							    			   multi = misektp.getMulti();
							    		   }
							    		   
							    		   multiple = multi > 1 ? true : false;
							    		   
//							    		  
							    		   // Verification du ticket
							    		   
							    		   for(int i=0;i<ticket.size();i++ ){
							    			   double gain = 0;
							    			   _montant_evt = 0;
							    			//   System.out.println("_RESULT-DETAILS: "+ticket.size());
//							    			   if(multiple){
							    				   double odd = verifKeno(ticket.get(i), result);
											    	   
											    	   if(odd != 0 && odd != -1){
//											    		   
											    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
											    		   gain = gain + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//											    		   
											    		   if(xmulti != 0) {
											    			   gain = gain * multiplicateur;
											    		   }
											    		   gain = (double)((int)(gain*100))/100;
											    		  // return true;
														}
											    	   			    			   
							    			   gain_total = gain_total + gain;
							    		   }
							    		   
										}
									}
								   break;
								default:
									break;
							       
							}
						
					}
		  }
			
		  return gain_total;
	}
	
	public boolean addKenos(int draw_num, Partner partner) {
		System.out.println("draw_num "+draw_num);
		Keno k = kenoservice.findDraw(partner, draw_num);
		if(k != null) {
			return true;
		}
		Keno keno = new Keno();
		keno.setDrawnumK(draw_num);
		keno.setPartner(partner);
		keno.setCoderace(partner.getCoderace());
        //CrÃ©ation d'une requÃªte paramÃ©trÃ©e. 
		return kenoservice.create(keno);
	}
	 
	public int addUpKeno(Keno keno){
		return kenoservice.update(keno);
	}
	
	public int endDraw(int draw_num, Partner partner){
		int num = draw_num-10;
		return kenoservice.updateDrawEnd(num, partner);
	}
	
	public boolean endDraw(Keno keno){
		return kenoservice.updateDrawEnd(keno);
	}
	
	public boolean manageBonusK(Partner partner, ControlDisplayKeno cds){
		double dbl = 0;
		long timeBefore,timeAfter;
		// recherche s'il ya eu des paris le tour
		List<Misek> tail;
		//System.out.println("NUMERO: "+cds.getDrawNumk());
		Partner p = partservice.find(partner);
		
		timeBefore = System.currentTimeMillis();
		Keno k = kenoservice.find_Max_draw_bis(partner);
		timeAfter = System.currentTimeMillis() - timeBefore;
		System.out.println("SEEK KENO TIME: "+timeAfter);
		timeAfter = 0;
		
		int num_tirage = cds.getDrawNumk();
		timeBefore = System.currentTimeMillis();
		tail = mskservice.searchMiseKdraw(k ,num_tirage);
		timeAfter = System.currentTimeMillis() - timeBefore;
		System.out.println("SEEK TICKET TIME: "+timeAfter);
		//Partner partner = partservice.find(getCoderace());
		System.out.println("TAIL: "+tail.size()+" Tirage: "+num_tirage+" Keno: "+k.getDrawnumK());
		if(tail.size() < 2){ //on compte le nombre de ticket tirÃ©
			return false;
		}
		Config config = cfgservice.find(p);
		double lower = config.getBnkmin();
		double higher = config.getBnkmax();
		
		dbl = (double) (Math.random() * (higher-lower)) + lower;
		
	//	System.out.println(p);
	//	System.out.println("MBONUSP: "+dbl+" BONUSP: "+p.getBonuskamount()+" -- "+p.getIdPartner());
		if(p.getBonuskamount() > dbl){	
			if(giveBonusK(tail, p, cds)){
				return true;
			}
			else{
				return false;
			}
		}
		
		return false;
	}
	
	private boolean giveBonusK(List<Misek> tail, Partner partner, ControlDisplayKeno cds){
		Long idp;
		int nb,add,winnerBonus=0;
		int codebonus;
		//On recupere l'identifiant du tirage
	//	System.out.println("client: "+getCoderace()+" num: "+getDrawNumk());
		idp = kenoservice.getIdKenos(partner, cds.getDrawNumk()-1);
		
		nb = tail.size();
		winnerBonus = Utile.generate(nb);	// recherche au radom du ticket vainqueur
		System.out.println("CODE: "+tail.get(winnerBonus).getBonusCod());
		codebonus = tail.get(winnerBonus).getBonusCod();
		
		Utile.bonusK_down = partner.getBonuskamount();
		Utile.bonusK_down = (double)((int)(Utile.bonusK_down*100))/100;
		System.out.println("Bonus Win: "+Utile.bonusK_down);
		
		Utile.bonus_codeK = codebonus;
		cds.setBonuskamount(Utile.bonusK_down);
		cds.setBonuskcode(Utile.bonus_codeK);
		
		double freeslipk = 0;
		Utile.bonusKmin = 0;//
	//	freeslipk = mstservice.findFreeSlipByPartner("freekeno", partner);
		Utile.bonusKmin = 0.1 * Utile.bonusK_down + freeslipk;
		Utile.bonusKmin = (double)((int)(Utile.bonusKmin*100))/100;
		add = partservice.update_bonusk(Utile.bonusKmin, (int)codebonus, partner); //mise Ã  jour de la ligne du partner - bonus Ã  zero
	//	mstservice.updateFree("freekeno", -freeslipk, partner);
		if(add > 0){
			int _add = kenoservice.setCodeBonusK(Utile.bonusK_down, Utile.bonus_codeK, idp); //mise Ã  jour de la ligne dans spin
			if(_add > 0){
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public void verifTicket(Map<Miset, Misek> map_wait, Partner coderace) {
		
			String barcode;
			Misek misek;
			Miset miset;
			for(Map.Entry mapentry : map_wait.entrySet()) {
				miset = (Miset) mapentry.getKey();
				misek = (Misek) mapentry.getValue();
				barcode = miset.getBarcode();
				//System.out.println("key: "+barcode+" | "+misek);
			//}
			if(barcode == null || barcode == ""){
				//return;
			}
			else{
				//System.out.println("BARCODE: "+barcode);
			//	if(!isTesting){
					
					
					//Miset miset = misetDao.searchTicketT(barcode);
					if(miset != null){ //le ticket existe
					
							//le ticket est en attente de traitement
							String typeJeu =  miset.getTypeJeu().value();
							
							/*--- Keno traitment ---*/
							List<EffChoicek> ticket = new ArrayList<EffChoicek>();
							Misek mk = new Misek();
							
							/*--- Spin traitment ---*/
							ArrayList<EffChoicep> ticketp = new ArrayList<EffChoicep>();
							Misep mp = new Misep();
							
							switch(typeJeu)
							{
								case "Keno": // traitement ticket keno
									mk = mskservice.searchMisesK(miset);
									if(mk != null){
										//on recupere les evenements du ticket
										ticket = effchoicekservice.searchTicketK(mk);
										int xmulti = mk.getXmulti();
										//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
										//System.out.println("EFFH EVNTS: "+ticket.size());
										
										if( ticket.size() > 0 ){
											boolean winner = true;
											//winT = true;
										    boolean isEnd = true;
										    int tested = 0;
										    
										    // recuperation de tous les evenement du ticket
										  //     for(int i=0;i<ticket.size();i++){
										    	   
							    		   EffChoicek tick = new EffChoicek();
							    		   ArrayList<String> resultMulti  = new ArrayList<String>();
							    		   boolean multiple = false;
							    		   int multi = 1;
							    		   int num_draw;
							    		   int draw_numK; //numero de tirage en cours
							    		   String str0="",str1="";
							    		   String single_result="";
							    		   String result_multi;
							    		   double xtiplicateur = 1;
							    		   Keno k_keno = null;
							    		   //recherche du numero de tirage en cours
							    		  // Partner part = partnerDao.findById(Integer.parseInt(""+caissier.getPartner()));
							    		   Keno _keno = kenoservice.find_Max_draw(coderace);
							    		   
							    		   draw_numK = _keno.getDrawnumK();
							    		   if(_keno.getStarted() == 0){ //tirage non terminÃ©
							    			   
							    		   }
							    		   else if(_keno.getStarted() == 1){//tirage terminÃ©
							    			   
							    		   }
							    		   
							    		   Misek_temp misektp = mtpservice.find(mk.getIdMiseK());
							    		  
							    		   if(misektp != null) {
							    			   multi = misektp.getMulti();
							    		   }
							    		   multiple = multi > 1 ? true : false;
							    		   
							    		   if(multiple){
							    			    num_draw = mskservice.getNumDraw(ticket.get(0).getMisek());
							    			    
							    			 //   setDrawData("draw_num", ""+num_draw);
												//resultMulti.add(""+multi);
												
												int num_tirage_final = num_draw + multi - 1;
												//System.out.println("DRAWNUMK: "+draw_numK+" NUM8TIRAGE: "+num_tirage_final+" BOOL: "+isDrawEnd);
												
										    	   if(draw_numK <= num_tirage_final){
													  
												      return;
												   }
													// recuperation des evnts dans effchoicek
													for(int i=0;i< multi;i++){
														int num = i + num_draw;
														k_keno = kenoservice.searchResultK(num,coderace);
														if(k_keno != null){
														xtiplicateur = Double.parseDouble(k_keno.getMultiplicateur());
														  if(k_keno.getStarted() == 1 ){
															result_multi = k_keno.getDrawnumbK();
															resultMulti.add(result_multi);
														  }
														  else{
															  result_multi = "";
														      resultMulti.add(result_multi);
														  }
														}
														else{
															result_multi = "";
															resultMulti.add(result_multi);
														}
													}
											//	}
											   
							    		   }
							    		   else{
							    			   
							    			   //on cherche le numero de tirage correspondant
							    			   num_draw = mskservice.getNumDraw(ticket.get(0).getMisek());
							    		//	   setDrawData("draw_num", ""+num_draw);
							    			  
							    			   //on cherche le resultat du tirage dans la table keno
							    			   k_keno = kenoservice.searchResultK(num_draw, coderace);
							    			//   System.out.println("SINGLE: "+num_draw+" || "+k_keno.getStarted());
							    			   if(k_keno != null){
							    				   xtiplicateur = Double.parseDouble(k_keno.getMultiplicateur());
							    				   if(k_keno.getStarted() == 1){
							    					  // System.out.println("SINGLE: "+num_draw+" || "+k_keno.getStarted());
							    					   single_result = k_keno.getDrawnumbK();
							    					   
							    					  // setDrawData("draw_result", result);
							    				   }
							    				   else{
							    					
												        //return false;
							    				   }  
							    			   }
							    			   
							    		   }
							    		   
							    		   // Verification du ticket
							    		   double _montant_evt = 0;//le prix d'un evenement
							    		   double gain_total = 0;
							    		   
							    		   for(int i=0;i<ticket.size();i++ ){
							    			   
							    			 
							    			//   System.out.println("_RESULT-DETAILS: "+ticket.size());
							    			   if(multiple){
							    				   String[] _str;
												   String _str0 = "",_str1="";
												   //String _result = "<html>";
												   String _result = "";
												   
												   _str = resultMulti.get(i).split("-");
												  
//												   for(int _i=0;_i<_str.length;_i++){
//													   if(_i<10){
//														   _str0 = _str0+" "+_str[_i];
//													   }
//													   else if (_i>9 && _i<20){
//														   _str1 = _str1+" "+_str[_i];
//													   }
//												   }
//												   _result = _result + _str0 + _str1;
											   	  
											    	   
											    	   double odd = verifKeno(ticket.get(i), resultMulti.get(i));
											    	   //setDetailTicket("cote", ""+odd);
											    	   
											    	   if(odd != 0 && odd != -1){
//											    		   setDetailTicket("etat", "true");
//											    		   setDetailTicket("cote", ""+odd);
//											    		   verst.setTypeVers("K");
											    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
											    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
//											    		   misek.setEtatMise("gagnant");
//											    		   misek.setSumWin(gain_total);
											    		   if(xmulti != 0) {
											    			   gain_total = gain_total * xtiplicateur;
											    		   }
											    		   gain_total = (double)((int)(gain_total*100))/100;
											    		  // return true;
														}
											    	    else if(odd == 0){
														  
//														   misek.setEtatMise("perdant");
//											    		   misek.setSumWin(gain_total);
														   //return false;
														}
//														else{
//														   setDetailTicket("etat", "pending");
//														}
//											    	   
											    	  
											    	   
							    			   }
							    			   else{
							    				   
							    				   double odd = verifKeno(ticket.get(i), single_result);
										    	  // setDetailTicket("cote", ""+odd);
										    	   
										    	   if(odd != 0 && odd != -1){
//										    		   setDetailTicket("etat", "true");
//										    		   setDetailTicket("cote", ""+odd);
//										    		   verst.setTypeVers("K");
										    		   _montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
										    		   gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
										    		   if(xmulti != 0) {
										    			   gain_total = gain_total * xtiplicateur;
										    		   }
										    		   gain_total = (double)((int)(gain_total*100))/100;
//										    		   misek.setEtatMise("gagnant");
//										    		   misek.setSumWin(gain_total);
													}
													else if(odd == 0){
													  
//													   misek.setEtatMise("perdant");
//										    		   misek.setSumWin(gain_total);
													}
													
							    				  
							    			   }
							    		   }
							    		   
//							    		   int num_tirage_final = num_draw + multi - 1;
//								    	   if(draw_numK <= num_tirage_final && multiple){
//											   resultat = "Ticket non evaluÃ©, En cours";
//											   setErreurs(FIELD_CODE, resultat);
//										      return false;
//										   }
//								    	   
//							    		   setDrawData("prix_total", ""+_montant_evt);
//							    		   setDrawData("gain_total", ""+gain_total);
							    		   if(gain_total == 0){
							    			   misek.setEtatMise(EtatMise.PERDANT);
								    		   misek.setSumWin(gain_total);
							    		   }
							    		   else{
							    			   misek.setEtatMise(EtatMise.GAGNANT);
								    		   misek.setSumWin(gain_total);
							    		   }
										  
										}
									}
								   break;
								default:
									break;
							       
							}
							
			//			}
					}
					
			//	}
			}
		  }
			
		  //mise à jour de letat des tickets en bd
			for(Map.Entry mapentry : map_wait.entrySet()) {
				miset = (Miset) mapentry.getKey();
				misek = (Misek) mapentry.getValue();
				barcode = miset.getBarcode();
			//	System.out.println("key: "+barcode+" | "+misek);
				
				mskservice.update(misek);
			}
		}
	
	public ArrayList<Integer> getHitFrequency(int hf, int n_cycle) {

		ArrayList<Integer> roundList = new ArrayList<>();
		int[] round;
		ArrayList<String> combis = new ArrayList<String>();
		ArrayList<String> combi = new ArrayList<String>();
		
		int nbS = (int)(hf*n_cycle)/100;
		round = new int[nbS];
		

    	for(int i=1;i<(1+n_cycle);i++)
    		combis.add(""+i);
    	for(int j=0;j<n_cycle;j++){
    		int index = Utile.generate(combis.size());
    		combi.add(combis.get(index));
    		combis.remove(index);
    	}
    	
    	for(int ii=0;ii<nbS;ii++) round[ii] = Integer.parseInt(combi.get(ii));
//    	for(int nb : round) {
//    		System.out.print(" "+nb);
//		}
    	//System.out.println("");
    	Arrays.sort(round);
    	for(int nb : round) {
			roundList.add(nb);
		}
    	
		return roundList;
	}
	
   private double checkTicketKMax(EffChoicek t){
 		
 		int nbD = 0, sum = 0;
 		int num;
 		double cote = 0,rd;
 		int pari;
 		String[] choice = {""};
 		
 		pari = Integer.parseInt(t.getIdparil());
 		
 		if(pari < 12)
 			choice = t.getKchoice().split("-");
 		//	rd = Double.parseDouble(t.getMtchoix());
 		
 		switch(pari){
 			case 1:
 				cote = Utile.num10[10];
 				break;
 			case 2:
 				cote = Utile.num9[9];
 				break;
 			case 3:
 				cote = Utile.num8[8];
 				break;
 			case 4:
 				cote = Utile.num7[7];
 				break;
 			case 5:
 				cote = Utile.num6[6];
 				break;
 			case 6:
 				cote = Utile.num5[5];
 				break;
 			case 7:
 				cote = Utile.num4[4];
 				break;
 			case 8:
 				cote = Utile.num3[3];
 				break;
 			case 9:
 				cote = Utile.num2[2];
 				break;
 			case 10:
 					cote = Utile.numOut[choice.length];
 				break;
 			case 11:
 					cote = Utile.numAll[choice.length];
 				break;
 			case 12:
 					cote = Utile.numSpec[1];
 				break;
 			case 13:
 					cote = Utile.numSpec[1];
 				break;
 			case 14:
 					cote = Utile.numSpec[1];
 				break;
 			case 15:
 					cote = Utile.numSpec[1];
 				break;
 			case 16:
 					cote = Utile.numSpec[1];
 				break;
 			case 17:
 					cote = Utile.numSpec[1];
 				break;
 			case 18:
 					cote = Utile.numSpec[1];
 				break;
 			case 19:
 					cote = Utile.numSpec[1];
 				break;
 			case 20://1ere couleur verte
 					cote = Utile.numSpec[2];
 			    break;
 			case 21://couleur bleu
 					cote = Utile.numSpec[2];
 				break;
 			case 22://couleur rouge
 					cote = Utile.numSpec[2];
 				break;
 			case 23://couleur orange
 					cote = Utile.numSpec[2];
 				break;
 			case 24://derniere couleur verte
 					cote = Utile.numSpec[2];
 				break;
 			case 25://derniere couleur bleu
 					cote = Utile.numSpec[2];
 				break;
 			case 26://derniere couleur rouge
 					cote = Utile.numSpec[2];
 				break;
 			case 27://derniere couleur orange
 					cote = Utile.numSpec[2];
 				break;
 			default:
 				break;
 			}
 			
 			return cote;
 		}
   
   private static double checkTicketKMin(EffChoicek t){
		  
	    double cote = 0;
		int pari;
		pari = Integer.parseInt(t.getIdparil());
		
		if(pari < 12) {
			cote = 0;
		}
		
		//	rd = Double.parseDouble(t.getMtchoix());
		
		switch(pari){
			
			case 12:
					cote = Utile.numSpec[1];
				break;
			case 13:
					cote = Utile.numSpec[1];
				break;
			case 14:
					cote = Utile.numSpec[1];
				break;
			case 15:
					cote = Utile.numSpec[1];
				break;
			case 16:
					cote = Utile.numSpec[1];
				break;
			case 17:
					cote = Utile.numSpec[1];
				break;
			case 18:
					cote = Utile.numSpec[1];
				break;
			case 19:
					cote = Utile.numSpec[1];
				break;
			case 20://1ere couleur verte
					cote = Utile.numSpec[2];
			    break;
			case 21://couleur bleu
					cote = Utile.numSpec[2];
				break;
			case 22://couleur rouge
					cote = Utile.numSpec[2];
				break;
			case 23://couleur orange
					cote = Utile.numSpec[2];
				break;
			case 24://derniere couleur verte
					cote = Utile.numSpec[2];
				break;
			case 25://derniere couleur bleu
					cote = Utile.numSpec[2];
				break;
			case 26://derniere couleur rouge
					cote = Utile.numSpec[2];
				break;
			case 27://derniere couleur orange
					cote = Utile.numSpec[2];
				break;
			default:
				break;
			}
			
			return cote;
		}
   
   public double verifKeno(EffChoicek effchoicek, String result){
		String[] stand; // recupere le resultat de la course
		
		stand = result.split("-");
		return checkTicketK(effchoicek, stand);
   }
   
   private static double checkTicketK(EffChoicek t, String[] draw){
		
		int nbD = 0, sum = 0;
		int num;
		double cote = 0,rd;
		int pari;
		String[] choice = {""};
		
		if(draw.length < 20){
			return -1;
		}
		
		pari = Integer.parseInt(t.getIdparil());
		
		if(pari < 12)
			choice = t.getKchoice().split("-");
		//	rd = Double.parseDouble(t.getMtchoix());
		
		switch(pari){
			case 1:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num10[nbD];
				break;
			case 2:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num9[nbD];
				break;
			case 3:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num8[nbD];
				break;
			case 4:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num7[nbD];
				break;
			case 5:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num6[nbD];
				break;
			case 6:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num5[nbD];
				break;
			case 7:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num4[nbD];
				break;
			case 8:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num3[nbD];
				break;
			case 9:
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							break;
						}
					}
				}
				cote = Utile.num2[nbD];
				break;
			case 10:
				boolean trouv = true;
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							trouv = false;
							break;
						}
					}
					
					if(!trouv){
						break;
					}
					else{
						nbD++;
					}
				}
				
				if(trouv){
					cote = Utile.numOut[nbD];
				}
				else{
					cote = 0;
				}
				break;
			case 11:
				boolean truv = false;
				for(int n=0;n<choice.length;n++){
					for(int in=0;in<draw.length;in++){
						if(choice[n].equalsIgnoreCase(draw[in])){
							nbD++;
							truv = true;
							break;
						}
						else{
							truv = false;
						}
					}
					
					if(!truv){
						break;
					}
				}
				
				if(!truv){
					cote = 0;
				}
				else{
					cote = Utile.numAll[nbD];
				}
				
				break;
			case 12:
				num = Integer.parseInt(draw[0]);
				if(num % 2 == 0)
					cote = Utile.numSpec[1];
				break;
			case 13:
				num = Integer.parseInt(draw[0]);
				if(num % 2 != 0)
					cote = Utile.numSpec[1];
				break;
			case 14:
				num = Integer.parseInt(draw[19]);
				if(num % 2 == 0)
					cote = Utile.numSpec[1];
				break;
			case 15:
				num = Integer.parseInt(draw[19]);
				if(num % 2 != 0)
					cote = Utile.numSpec[1];
				break;
			case 16:
				for(int i=0;i<5;i++)
					sum = sum + Integer.parseInt(draw[i]);
				if(sum < 203 )
					cote = Utile.numSpec[1];
				else
					cote = 0;
				break;
			case 17:
				for(int i=0;i<5;i++)
					sum = sum + Integer.parseInt(draw[i]);
				if(sum > 202 )
					cote = Utile.numSpec[1];
				else
					cote = 0;
				break;
			case 18:
				for(int i=0;i<20;i++)
					sum = sum + Integer.parseInt(draw[i]);
				if(sum < 806 )
					cote = Utile.numSpec[1];
				else
					cote = 0;
				break;
			case 19:
				for(int i=0;i<20;i++)
					sum = sum + Integer.parseInt(draw[i]);
				if(sum > 805 )
					cote = Utile.numSpec[1];
				else
					cote = 0;
				break;
			case 20://1ere couleur verte
				num = Integer.parseInt(draw[0]);
				if(0 < num  && num < 21)
					cote = Utile.numSpec[2];
			    break;
			case 21://couleur bleu
				num = Integer.parseInt(draw[0]);
				if(20 < num  && num < 41)
					cote = Utile.numSpec[2];
				break;
			case 22://couleur rouge
				num = Integer.parseInt(draw[0]);
				if(40 < num  && num < 61)
					cote = Utile.numSpec[2];
				break;
			case 23://couleur orange
				num = Integer.parseInt(draw[0]);
				if(60 < num  && num < 81)
					cote = Utile.numSpec[2];
				break;
			case 24://derniere couleur verte
				num = Integer.parseInt(draw[19]);
				if(0 < num  && num < 21)
					cote = Utile.numSpec[2];
				break;
			case 25://derniere couleur bleu
				num = Integer.parseInt(draw[19]);
				if(20 < num  && num < 41)
					cote = Utile.numSpec[2];
				break;
			case 26://derniere couleur rouge
				num = Integer.parseInt(draw[19]);
				if(40 < num  && num < 61)
					cote = Utile.numSpec[2];
				break;
			case 27://derniere couleur orange
				num = Integer.parseInt(draw[19]);
				if(60 < num  && num < 81)
					cote = Utile.numSpec[2];
				break;
			default:
				break;
			}
			
			return cote;
		}
   
   public synchronized long searchBarcode(Jeu jeu){
		// TODO Auto-generated method stub
		long lower = 900000000000L;
		long higher = 999999999999L;
		long dbl;
		Miset mt;
		
		do{  
		    dbl = (long) (Math.random() * (higher-lower)) + lower;
			mt = mstservice.findBarcode(dbl, jeu);
		}
		while(mt != null);
		
		return dbl; 
	}

    public long manageCagnotte(ControlDisplayKeno cds) {
    	
    	System.out.println(cagnotservice + " __ " +cds);
    	Cagnotte cagnot = cagnotservice.find(cds.getPartner());
    	
    	if (cagnot == null ) return -1L;
    	System.out.println(cagnot.getBarcode() + " __ " +cagnot.getBarcode()+"  JEU:  "+cagnot.getJeu());
    	
//    	cds.setBarcodeCagnot("0"+);
    	
    	if (cagnot.getJeu() == null || StringUtils.isBlank(cagnot.getJeu())) {
    		return -1L;
    	}
    	else if (cagnot.getBarcode() == 0L) {
    		// recherche s'il ya eu des paris le tour
    		List<Misek> tail;
    		int num_tirage = cds.getDrawNumk();
    		Keno k = kenoservice.find_Max_draw_bis(cds.getPartner());
    		tail = mskservice.searchMiseKdraw(k ,num_tirage);
    		
    		if(tail.size() < 2){ //on compte le nombre de ticket tirÃ©
    			return -1L;
    		}
    		
    		if (giveCagnotte(tail, cds.getPartner(),cds)) {
    			return cagnot.getIdCagnotte();
    		}
    	}
			
    	
		return -1L;
    }
    
    private boolean giveCagnotte(List<Misek> tail, Partner partner, ControlDisplayKeno cds){
		Long idp;
		int nb,winnerBonus=0;
		Long codemise;
		//On recupere l'identifiant du tirage

		try {
			idp = kenoservice.getIdKenos(partner, cds.getDrawNumk()-1);
			
			nb = tail.size();
			winnerBonus = Utile.generate(nb);	// recherche au radom du ticket vainqueur
			System.out.println("CODE CAGNOTTE: "+tail.get(winnerBonus).getIdMiseK());
			codemise = tail.get(winnerBonus).getIdMiseK();
			
			Miset miset = mstservice.findById(tail.get(winnerBonus).getMiset().getIdMiseT());
			String barcode = miset.getBarcode();
			String barc = tail.get(winnerBonus).getMiset().getBarcode();
			System.out.println("BARCODE CAGNOTTE: "+barcode+" | "+barc);
			
			cds.setBarcodeCagnot(Long.valueOf(barc));
			cds.setMiseCagnot(codemise);
		}
		catch (Exception e) {
			return false;
		}
		
		return true;
	}
 
}