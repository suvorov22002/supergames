package com.pyramid.dev.business;

import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.model.*;
import com.pyramid.dev.service.*;
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.Params;
import com.pyramid.dev.tools.Utile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
@Slf4j
public class RefreshK implements Runnable {

	private GameCycleService gmcservice;
    private MisekService mskservice;
    private MisetService mstservice;
    private KenoService kenoservice;
	private SuperGameManager supermanager;

	private static Thread thread;


	private Long misef;
	public static String RESULT  = "";

	private  Map<Miset, Misek> map_wait = new HashMap<>();


	private int drawCount;

	double percent;

	private Partner partner;
	private ControlDisplayKeno cds;
//	private ApplicationContext applicationContext;

	public RefreshK(ControlDisplayKeno cds, Partner partner,ApplicationContext applicationContext){
		this.cds = cds;
		this.partner = partner;
		gmcservice = applicationContext.getBean(GameCycleService.class);
		mskservice = applicationContext.getBean(MisekService.class);
		mstservice = applicationContext.getBean(MisetService.class);
		kenoservice = applicationContext.getBean(KenoService.class);
		supermanager = applicationContext.getBean(SuperGameManager.class);

	}


	public RefreshK(){
		super();
	}

	@Override
	public void run() {
  boolean dead_round = true;
  String arrangement_pos;
  List<Misek> listTicket;
  List<Misek> list_barcode = null;
  GameCycle gmc;
  Long idmisek_max;
  int roundSize;
  int tour;

		int index;
		log.info("[REFRESHK - RUN]: "+partner);

		//recherche gmc
		gmc = gmcservice.findByGame(partner, Jeu.K);

		int position = gmc.getPosition();
		tour = gmc.getTour();
		roundSize = gmc.getHitfrequence();
		percent = gmc.getPercent();

		Keno _keno;
		//	System.out.println("caissiers: "+caissiers);

		while(true) {

			//System.out.println("caissiers: "+partner.toString());
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
					} else {
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
					e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				//	 System.out.println("drawcount: "+drawCount+" __ "+cds.getTimeKeno());
			}
			//	System.out.println("[REFRESH SEARCH WAITING SLIPS] "+cds.getCoderace()+" -- "+cds.isMiseAjour());
			if(cds.isMiseAjour()) {

				cds.setStr_draw_combi("");
				log.info("[REFRESH KENO MIS A JOUR]: "+_keno.getDrawnumK()+" multi: "+_keno.getMultiplicateur()+" coderace: "+_keno.getCoderace()+" id: "+_keno.getIdKeno());

				_keno.setStarted(1);
				log.info("End Draw refreesh: " + _keno.getPartner().toString());
				//  supermanager.endDraw(_keno);
				supermanager.endDraw(_keno.getDrawnumK(), _keno.getPartner());

				cds.setEnd(1); //fin du tour

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
				} else {
					dead_round = true;
				}

				if(!dead_round) {
					//recherche gmc
					gmc = gmcservice.findByGame(partner, Jeu.K);
					position = gmc.getPosition();
					tour = gmc.getTour();
					roundSize = gmc.getHitfrequence();
					percent = gmc.getPercent();

					mskservice.searchWaitingKenoBet(partner, cds.getDrawNumk()-1, EtatMise.GAGNANT);

					log.info("[REFRESH CYCLE POS TOUR POSITION]: "+ position +" - "+tour+" Return: " + cds.getRtp());

					double currPercent = 0;
					double summise = 1;
					double sumWin = 0;
					double jkpt = 0;
					double refund;
					double ecart;
					double ipercent;
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

							ipercent = gm.getPercent();
							currPercent = gm.getCurr_percent();
							log.info("(percent-ecart): " + (ipercent-ecart) + "Current percent: " + (100*currPercent));

							if((100*currPercent) < ipercent) {

								reste = ((summise * ipercent)/100) - sumWin;

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

								} else {
									jkpt = 0;
								}


								if(summise > 0) {
									currPercent = (sumWin)/summise;
									currPercent = (double)((int)(currPercent*100))/100;
								}

								gmcservice.updateArchive(currPercent, DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"), 1, partner, Jeu.K, misef, summise, sumWin, jkpt);

								GameCycle gamecycle = new GameCycle();
								gamecycle.setRefundp(cds.getRtp());
								gamecycle.setPosition(0);
								gamecycle.setPartner(partner);
								gamecycle.setPercent(ipercent);
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
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	private String getName() {
		return this.partner.getCoderace();
	}

	public void start(){
		thread = new Thread(this);
		thread.start();
	}

	public void stop(){
		thread.stop();
	}

//	public void suspend(){
//		thread.suspend();
//	}
//
//	public void resume(){
//		thread.resume();
//	}

	public boolean alive(){
		return thread.isAlive();
	}

}
