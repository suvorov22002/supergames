package com.pyramid.dev.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.CaissierService;
import com.pyramid.dev.service.ConfigService;
import com.pyramid.dev.service.GameCycleService;
import com.pyramid.dev.service.KenoService;
import com.pyramid.dev.service.MisekService;
import com.pyramid.dev.service.Misek_tempService;
import com.pyramid.dev.service.MisetService;
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.Params;
import com.pyramid.dev.tools.Utile;

@Component
public class RefreshK implements Runnable {
	
	private CaissierService caissierservice;
    private	ConfigService cfgservice;
    private GameCycleService gmcservice;
    private MisekService mskservice;
    private MisetService mstservice;
    private Misek_tempService mtpservice;
    private KenoService kenoservice;
	private SuperGameManager supermanager;
	
	private static Thread thread;
	
	private int refill, xtour;
	private boolean search_draw;
	private boolean dead_round = false;
	private double miseTotale;
	private double miseTotale_s; //cycle suivant
	private String arrangement_pos;
	private Long misef;
	public static String RESULT  = "";
	private double sumdist, gMp, gmp;
	private List<Misek> listTicket = new ArrayList<Misek>();
	private  Map<Miset, Misek> mapTicket = new HashMap<Miset, Misek>();
	private  Map<Miset, Misek> map_wait = new HashMap<Miset, Misek>();
	private List<Misek> list_barcode;
	private GameCycle gmc;
	private int drawCount;
	private Long idmisek_max;
	
	
	private Partner partner;
	private ControlDisplayKeno cds;
//	private ApplicationContext applicationContext;
	
	public RefreshK(ControlDisplayKeno cds, Partner partner,ApplicationContext applicationContext){
		this.cds = cds;
		this.partner = partner;
		cfgservice = (ConfigService)applicationContext.getBean(ConfigService.class);
		caissierservice = (CaissierService)applicationContext.getBean(CaissierService.class);
		gmcservice = (GameCycleService)applicationContext.getBean(GameCycleService.class);
		mskservice = (MisekService)applicationContext.getBean(MisekService.class);
		mstservice = (MisetService)applicationContext.getBean(MisetService.class);
		mtpservice = (Misek_tempService)applicationContext.getBean(Misek_tempService.class);
		kenoservice = (KenoService)applicationContext.getBean(KenoService.class);
		supermanager = (SuperGameManager)applicationContext.getBean(SuperGameManager.class);
	}
	
	
	public RefreshK(){
		super();
	}
	
	@Override
	public void run() {

		System.out.println("[REFRESHK - RUN]: "+partner);
		Keno __keno = this.cds.lastDrawNum(partner);
		if(!__keno.getMultiplicateur().equalsIgnoreCase("0")) {
			int num_tirage = 1+__keno.getDrawnumK();
			supermanager.addKenos(num_tirage, partner);
		}
		
		refill = 0;

	    //recherche gmc
		gmc = gmcservice.findByGame(partner, Jeu.K);
		
		int position = gmc.getPosition();
		int tour = gmc.getTour();
		int roundSize = gmc.getHitfrequence();
		double percent = gmc.getPercent();
		
		int compteur_combi = -7;
		int index_combi = 0;
		String[] str_draw_combi = null;
		String str_combi = "";
		
	//	System.out.println("caissiers: "+caissiers);
		while(true) {
		//	  System.out.println("CountDown(): "+cds.isCountDown());
			Keno _keno = null;
			
			//do {
				_keno = this.cds.lastDrawNum(partner);
				//System.out.println("refresh max draw"+partner.getCoderace());
		//	}
		//	while(_keno == null);

			cds.setDraw_finish(Boolean.FALSE);
		//	cds.setDraw(Boolean.FALSE);
			
			cds.setDrawNumk(_keno.getDrawnumK());
		
			if(!Utile.display_draw.containsKey(cds.getCoderace())) {
				Utile.display_draw.put(cds.getCoderace(), cds);
			}
			
			listTicket = mskservice.searchWaitingKenoBet(partner, cds.getDrawNumk());
			if(listTicket != null)
				  for(Misek m : listTicket) {
					  Miset mt = mstservice.findById(m.getMiset().getIdMiseT());
					  misef = m.getIdMiseK();
				  }
			
	
			str_draw_combi = cds.getDrawCombik().split("-");
			cds.setDrawCount(140);
		//	System.out.println("cds.isDraw out "+cds.isDraw());
			while(cds.isDraw()){
			//	System.out.println("cds.isDraw() "+cds.isDraw());
				++compteur_combi;
			//	System.out.println("cds.getDrawCount(): "+cds.getDrawCount()+ " | "+ cds.isDraw_finish());
				try {
					if(drawCount > 100){
						//cds.setGameState(2);
						cds.setTimeKeno(Utile.timekeno);
						cds.setCanbet(false);
					}
					else {
						cds.setCanbet(true);
						//cds.setGameState(3);
					}
					
					//System.out.println("cds.setGameState(2): "+cds.getGameState()+" drawCount: "+drawCount);
					//drawCount--;
					cds.setDrawCount(cds.getDrawCount()-1);
					drawCount = cds.getDrawCount();
					Utile.display_draw.put(cds.getCoderace(), cds);
				//	System.out.println("TIME DRAW "+drawCount);
//					if(drawCount == -1){ //implementation dans le controller
//						int num_tirage = 1+cds.getDrawNumk();
//						System.out.println("DRAW Ajout d'une nouvelle ligne de tirage "+partner.getCoderace()+" | "+drawCount );
//						boolean line = supermanager.addKenos(num_tirage, partner);
//						
//				//		System.out.println("Nouvelle ligne de tirage added "+line );
//						if(line){
//							System.out.println("num added "+num_tirage );
//							cds.setDrawNumk(num_tirage);
//						}
//					//	cds.setTimeKeno(UtileKeno.timeKeno);
//						cds.setCanbet(true);
//						//cds.setGameState(3);
//						Utile.display_draw.put(cds.getCoderace(), cds);
//						
//						String str_draw = cds.getDrawCombik();
//						cds.getAllDraw().remove(99);
//						cds.getAllDraw().add(0, str_draw);
////						Utile.allDraw.remove(99);
////						Utile.allDraw.add(0, str_draw);
//						
//						String[] passDraw;
//						passDraw  =cds.getAllDraw().get(0).split("-");
//						for(int j=0; j<passDraw.length; j++) {
//							String key = passDraw[j];
//							//int value = Utile.allDrawNumOdds.get(key);
//							String value = cds.getAllDrawNumOdds().get(key);
//							
//							cds.getAllDrawNumOdds().put(key, ""+(Integer.parseInt(value) + 1));
//						}
//						
//					}
					
					if(drawCount < 0 || cds.isDraw_finish()){
						cds.setDraw(false);
					//	cds.setCountDown(true);
						cds.setTimeKeno(Utile.timekeno);
					//	cds.setGameState(4);
						cds.setDraw_finish(false);
						Utile.display_draw.put(cds.getCoderace(), cds);
						
					}
				
			
//					if(drawCount == 10 ){ //verification du bonus
//					 boolean bonusk = supermanager.manageBonusK(partner, cds);
//					  //System.out.println("REFRESH BONUS: "+bonusk);
//					  if(bonusk){
//						  cds.setBonus(1);
//					  }
//					  else{
//						  cds.setBonus(0);
//						  cds.setBonuskamount(0);
//						  cds.setBonuskcode(0);
//					  }
//					}
					Thread.sleep(1000);
					
				} catch (InterruptedException e) {
			//		// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//	 System.out.println("drawcount: "+drawCount+" __ "+cds.getTimeKeno());
			}
		//	System.out.println("[REFRESH SEARCH WAITING SLIPS] "+cds.getCoderace()+" -- "+cds.isMiseAjour());
			if(cds.isMiseAjour()) {
				
				cds.setStr_draw_combi("");
				System.out.println("[REFRESH KENO MIS A JOUR]: "+_keno.getDrawnumK()+" multi: "+_keno.getMultiplicateur()+" coderace: "+_keno.getCoderace()+" id: "+_keno.getIdKeno());
				
					      _keno.setStarted(1);
						//  supermanager.endDraw(_keno);
						  supermanager.endDraw(_keno.getDrawnumK(), _keno.getPartner());
						  
						  cds.setEnd(1); //fin du tour
						  list_barcode = new ArrayList<Misek>();
						  list_barcode = mskservice.searchWaitingBet(partner, cds.getDrawNumk());
				     	  System.out.println("[REFRESH WAITING BET]"+list_barcode.size());
						  map_wait.clear();
						  for(Misek m : list_barcode) {
							  Miset mt = mstservice.findById(m.getMiset().getIdMiseT());
							  if(mt != null)
								  map_wait.put(mt, m);
						  }
						  supermanager.verifTicket(map_wait, partner);
						  if(!dead_round) {
							//recherche gmc
							  gmc = gmcservice.findByGame(partner, Jeu.K);
							  position = gmc.getPosition();
							  System.out.println("[REFRESH CYCLE POS TOUR]: "+position+" - "+tour);
							  if(position >= tour) {
								  idmisek_max = mskservice.ifindId(partner);
									//ArrayList<Integer> roundList = Params.getHitFrequency(gmc.getHitfrequence(), gmc.getTour());
									    List<Integer> roundList = supermanager.getHitFrequency(gmc.getHitfrequence(), gmc.getTour());	
										String posi = "";
										for(int nb : roundList) {
											
											posi = posi +"-"+ nb;
										}
										posi = posi.substring(1);
										arrangement_pos = posi;
										
									
									
									List<GameCycle> _gmc = gmcservice.find(partner);
									int taille = _gmc.size();
									double curr_percent = 1;
									double summise = 1;
									double sumWin = 0;
									double jkpt = 0;
						//			System.out.println("RefreshK  taille: "+taille);
									if(taille > 1) {
										GameCycle gm = _gmc.get(0); 
										//System.out.println("gm.getMise(): "+gm.getMise()+" Misef: "+misef+" partner "+partner.getCoderace());
										summise = mskservice.getMiseKCycle(gm.getMise(),misef, partner);
						//				System.out.println("RefreshK  summise: "+summise);
										sumWin = cds.getBonusrate()*summise + mskservice.getMiseKCycleWin(gm.getMise(),misef, partner);
										curr_percent = sumWin/summise;
										curr_percent = (double)((int)(curr_percent*100))/100;
										//jkpt = UtileKeno.bonusrate*summise;
						    			
						    			sumWin = (double)((int)(sumWin*100))/100;
						    			summise = (double)((int)(summise*100))/100;
						    			
						    			//recherche du jackpot
						    			Misek m1 = mskservice.searchMiseK(gm.getMise());
						    			Misek m2 = mskservice.searchMiseK(misef);
						    			if(m1 != null && m2 != null) {
						    				Long k1 = m1.getKeno().getIdKeno();
							    			Long k2 = m2.getKeno().getIdKeno();
							    			jkpt  = kenoservice.findTotalBonusAmount(k1, k2, partner);
							    			jkpt = (double)((int)(jkpt*100))/100;
						    				
						    			}
						    			else {
						    				jkpt = 0;
						    			}
						    			
									}
									
									
									//idmisek_max = misekDao.ifindId(IN);
									int add = gmcservice.updateArchive(curr_percent, DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"), 1, partner, Jeu.K, misef, summise, sumWin, jkpt);

										  GameCycle gamecycle = new GameCycle();
										  gamecycle.setRefundp(cds.getRtp());
										  gamecycle.setPosition(0);
										  gamecycle.setPartner(partner);
										  gamecycle.setPercent(percent);
										  gamecycle.setTour(tour);
										  gamecycle.setArrangement(arrangement_pos);
										  gamecycle.setHitfrequence(roundSize);
										  gamecycle.setJeu(Jeu.K);
										  gamecycle.setArchive(0);
										  gamecycle.setMise(misef);
										  gamecycle.setMisef(idmisek_max);
										  gamecycle.setDate_fin(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));
										 
									//	  if(add != 0) {
										  gmcservice.create(gamecycle);
											  cds.setRtp(0);
									//	  }
										  
								} 
						  }
						  
						  cds.setMiseAjour(Boolean.FALSE);
			}
			
		}
	}
	
	private String getName() {
		// TODO Auto-generated method stub
		return this.partner.getCoderace();
	}

	public void start(){
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop(){
		thread.stop();
	}
	public void suspend(){
		thread.suspend();
	}
	public void resume(){
		thread.resume();
	}
	
	public boolean alive(){
		return thread.isAlive();
	}

}
