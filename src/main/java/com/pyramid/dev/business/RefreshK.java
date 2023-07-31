package com.pyramid.dev.business;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
	private GameCycle gmc;
	private Long idmisek_max;
	private int drawCount, position, place, tour, roundSize;
	double percent;
	
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
			//System.out.println("misef: "+misef+" Partner: "+partner.getCoderace());
			
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
			      System.out.println("End Draw refreesh: " + _keno.getPartner().toString());
				//  supermanager.endDraw(_keno);
				  supermanager.endDraw(_keno.getDrawnumK(), _keno.getPartner());
				  
				  cds.setEnd(1); //fin du tour
				  list_barcode = new ArrayList<Misek>();
				  listMskTemp = new ArrayList<Misek>();
				  
				  // Recherchez des tickets en attente dans Misek
				  list_barcode = mskservice.searchWaitingBet(partner, cds.getDrawNumk());

				  map_wait.clear();
				 
				  for(Misek m : list_barcode) {
					 // Miset mt = mstservice.findById(m.getMiset().getIdMiseT());
					  Miset mt = mstservice.searchTicketT(m.getMiset().getBarcode());
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
					 
//							  TraceCycle trc = traceservice.find(partner.getCoderace(), cds.getDrawNumk()-1);
//							  if(trc != null) {
//								  trc.setRealDist(gainMax);
//								  traceservice.update(trc);
//								  cds.setTrCycle(trc);
//							  }
					  
					  System.out.println("[REFRESH CYCLE POS TOUR POSITION]: "+position+" - "+tour+" Return: " + cds.getRtp());
					  
					  double curr_percent = 0;
					  double summise = 1;
					  double sumWin = 0;
					  double jkpt = 0;
					  double refund;
					  double ecart;
					  double percent;
					  double reste;
					  
					  // tour terminé
					  if(position >= tour) {
						  
						  List<GameCycle> _gmc = gmcservice.find(partner);
						  if(!_gmc.isEmpty()) {
							  
							  GameCycle gm = _gmc.get(0); 
							  refund = cds.getRtp();
							  
							  summise = gm.getStake();
							  sumWin =  gm.getPayout();
								
							  sumWin = (double)((int)(sumWin*100))/100;
				    		  summise = (double)((int)(summise*100))/100;
				    		 	  
				    		  jkpt = gm.getJkpt();
				    	//	  System.out.println("jkpt: " + jkpt);
				    		  
				    		  ecart = (jkpt * 100)/summise;
				    		  
				    		  percent = gm.getPercent();
				    		  curr_percent = gm.getCurr_percent();
				    		  System.out.println("(percent-ecart): " + (percent-ecart) + "Current percent: " + (100*curr_percent));
				    		  
				    		  if((100*curr_percent) < percent) {
				    			  
				    			  reste = ((summise * percent)/100) - sumWin;
				    			  System.out.println("reste: " + reste);
				    			 
				    			  refund = reste > 0 ? (refund + reste) : refund;
				    			//  System.out.println("refund: " + refund);
				    			  cds.setRtp((int) refund);
				    			  
				    			  gmcservice.updateRfp(cds.getRtp(), partner, Jeu.K);
				    		  }
				    		  
				    		  if( cds.getRtp() < 5*Params.MISE_MIN ) {
				    			  
				    			   idmisek_max = mskservice.ifindId(partner);
				    			   List<Integer> roundList = supermanager.getHitFrequency(gmc.getHitfrequence(), gmc.getTour());
				    			   arrangement_pos = StringUtils.join(roundList, "-");
				    			   
				    			   List<Misek> totalmisek = mskservice.getMiseKCycle(gm.getMise(),misef, partner); 
				    			   summise = totalmisek.stream().filter(b -> !b.getEtatMise().equals(EtatMise.ATTENTE))
				    					   .mapToDouble(Misek::getSumMise).sum();
				    			   //sumWin =  mskservice.getMiseKCycleWin(gm.getMise(),misef, partner);
				    			   sumWin = totalmisek.stream().filter(b -> !b.getEtatMise().equals(EtatMise.ATTENTE))
				    					   .mapToDouble(Misek::getSumWin).sum();
				    			   
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
					    			

				    			   if(summise > 0) {
										curr_percent = (sumWin)/summise;
										curr_percent = (double)((int)(curr_percent*100))/100;
								   }
										
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
								
									  gmcservice.create(gamecycle);
									  cds.setRtp(0);
				    		  }
						  }
						  
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
