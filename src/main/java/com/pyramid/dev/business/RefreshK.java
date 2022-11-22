package com.pyramid.dev.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.TraceCycle;
import com.pyramid.dev.service.CaissierService;
import com.pyramid.dev.service.ConfigService;
import com.pyramid.dev.service.GameCycleService;
import com.pyramid.dev.service.KenoService;
import com.pyramid.dev.service.MisekService;
import com.pyramid.dev.service.Misek_tempService;
import com.pyramid.dev.service.MisetService;
import com.pyramid.dev.service.TraceCycleService;
import com.pyramid.dev.tools.ControlDisplayKeno;
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
    private TraceCycleService traceservice;
	private SuperGameManager supermanager;
	
	private static Thread thread;
	
	private int refill, xtour;
	private boolean search_draw;
	private boolean dead_round = true;
	private double miseTotale;
	private double miseTotale_s; //cycle suivant
	private String arrangement_pos;
	private Long misef;
	public static String RESULT  = "";
	private double sumdist, gMp, gmp;
	private List<Misek> listTicket = new ArrayList<Misek>();
	private List<Misek> totBet = new ArrayList<Misek>();
	private  Map<Miset, Misek> mapTicket = new HashMap<Miset, Misek>();
	private  Map<Miset, Misek> map_wait = new HashMap<Miset, Misek>();
	private List<Misek> list_barcode;
	private List<Misek> listMskTemp;
	List<Misek_temp> listTmp;
	private GameCycle gmc;
	private Long idmisek_max;
	private int drawCount, position, place, tour, roundSize;
	double percent, gainMax;
	
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
		traceservice = (TraceCycleService)applicationContext.getBean(TraceCycleService.class);
	}
	
	
	public RefreshK(){
		super();
	}
	
	@Override
	public void run() {
		int index;
		System.out.println("[REFRESHK - RUN]: "+partner);
		refill = 0;

	    //recherche gmc
		gmc = gmcservice.findByGame(partner, Jeu.K);
		
		position = gmc.getPosition();
		tour = gmc.getTour();
		roundSize = gmc.getHitfrequence();
		percent = gmc.getPercent();
		Misek mk;
		Keno _keno;
	//	System.out.println("caissiers: "+caissiers);
		
		while(true) {
			
			//System.out.println("caissiers: "+partner.toString());
			_keno = new Keno();
			_keno = this.cds.lastDrawNum(partner);
		//	System.out.println("_keno: "+_keno);
			cds.setDraw_finish(Boolean.FALSE);
			if (_keno != null)
				cds.setDrawNumk(_keno.getDrawnumK());
		
			if(!Utile.display_draw.containsKey(cds.getCoderace())) {
				Utile.display_draw.put(cds.getCoderace(), cds);
			}

			listTicket = mskservice.searchWaitingKenoBet(partner, cds.getDrawNumk(), EtatMise.ATTENTE);
			
			if(listTicket != null && !listTicket.isEmpty()) {
				index = listTicket.size();
				misef = listTicket.get(index-1).getIdMiseK();
			}
		//	System.out.println("misef: "+misef+" Partner: "+partner.getCoderace());
			
			cds.setDrawCount(140);
			
			while(cds.isDraw()){
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
				//	System.out.println("loop------ ");
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
						  listTmp = new ArrayList<Misek_temp>();
						  listMskTemp = new ArrayList<Misek>();
						  
						  list_barcode = mskservice.searchWaitingBet(partner, cds.getDrawNumk());
						  listTmp = mtpservice.waitingDrawBet(cds.getDrawNumk(), partner);
						  
						  place = 0;
						  if (!listTmp.isEmpty()) {
								for (Misek_temp f : listTmp) {
									
									mk = mskservice.searchMiseK(f.getIdmisek());
									place = list_barcode.lastIndexOf(mk);
									
									if (place != -1) {
										list_barcode.remove(place);
									}
									mk.setSumMise(f.getSumMise());
									list_barcode.add(mk);
									listMskTemp.add(mk);
								}
						  }
						  
						  for(Misek_temp m : listTmp) {
							  
							  mtpservice.update(m.getIdmisek());
							  
						  }
							
							
				     	  System.out.println("[REFRESH WAITING BET] = "+list_barcode.size());
						  map_wait.clear();
						 
						  for(Misek m : list_barcode) {
							  Miset mt = mstservice.findById(m.getMiset().getIdMiseT());
							  if(mt != null)
								  map_wait.put(mt, m);
						  }
						  
						  if(!map_wait.isEmpty()) {
							  dead_round = false;
							  supermanager.verifTicket(map_wait, partner);
						  }
						  else {
							  dead_round = true;
						  }
						 
						  if(!dead_round) {
							//recherche gmc
							  gmc = gmcservice.findByGame(partner, Jeu.K);
							  position = gmc.getPosition();
							  tour = gmc.getTour();
							  roundSize = gmc.getHitfrequence();
							  percent = gmc.getPercent();
							  
							  totBet = mskservice.searchWaitingKenoBet(partner, cds.getDrawNumk()-1, EtatMise.GAGNANT);
							  
							  gainMax = totBet.stream().map(m -> m.getSumWin()).reduce(0d, Double::sum);
							  
							  gainMax = gainMax + listMskTemp.stream().filter(m -> m.getDrawnumk() != (cds.getDrawNumk()-1) && m.getEtatMise() == EtatMise.GAGNANT)
							  				.map(x -> x.getSumWin())
							  				.reduce(0d, Double::sum);
							  
							  
							  System.out.println("Gagnant: "+gainMax+" Tour: "+(cds.getDrawNumk()-1));
							  
							  TraceCycle trc = traceservice.find(partner.getCoderace(), cds.getDrawNumk()-1);
							  if(trc != null) {
								  trc.setRealDist(gainMax);
								  traceservice.update(trc);
								  cds.setTrCycle(trc);
								  
								  if(trc.getRealDist() < trc.getSumDist()) {
									  gmcservice.updateRfp(cds.getRtp()+(trc.getSumDist() - trc.getRealDist()), partner, Jeu.K);
								  }
							  }
							  
						
							  System.out.println("[REFRESH CYCLE POS TOUR POSITION]: "+position+" - "+tour);
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
									//int taille = _gmc.size();
									double curr_percent = 0;
									double summise = 1;
									double sumWin = 0;
									double jkpt = 0;
									double refund;
									double preRefund;
						//			System.out.println("RefreshK  taille: "+taille);
									if(!_gmc.isEmpty()) {
										
										GameCycle gm = _gmc.get(0); 
										refund = cds.getRtp();
										preRefund = 0;
										if(_gmc.size() > 1) {
											preRefund = _gmc.get(1).getRefundp();
										}
										//System.out.println("gm.getMise(): "+gm.getMise()+" Misef: "+misef+" partner "+partner.getCoderace());
										summise = mskservice.getMiseKCycle(gm.getMise(),misef, partner);
										sumWin =  mskservice.getMiseKCycleWin(gm.getMise(),misef, partner);
										
										sumWin = (double)((int)(sumWin*100))/100;
						    			summise = (double)((int)(summise*100))/100;
										
										if(summise > 0) {
											curr_percent = (refund + sumWin - preRefund)/summise;
											curr_percent = (double)((int)(curr_percent*100))/100;
										}
										
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
			
			try {
				Thread.sleep(9000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
