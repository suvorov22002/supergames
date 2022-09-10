package com.pyramid.dev;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pyramid.dev.business.RefreshK;
import com.pyramid.dev.business.SuperGameManager;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.model.Airtime;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.Groupe;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Mouvement;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.AirtimeService;
import com.pyramid.dev.service.CagnotteService;
import com.pyramid.dev.service.CaissierService;
import com.pyramid.dev.service.ConfigService;
import com.pyramid.dev.service.GameCycleService;
import com.pyramid.dev.service.GroupeService;
import com.pyramid.dev.service.KenoService;
import com.pyramid.dev.service.MouvementService;
import com.pyramid.dev.service.PartnerService;
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.Utile;

@Component
public class InitialKeno {
	
	@Autowired
	PartnerService partnerservice;
	
	@Autowired
	KenoService kenoservice;
	
	@Autowired
	ConfigService configservice;
	
	@Autowired
	CaissierService caissierservice;
	
	@Autowired
	GameCycleService cycleservice;
	
	@Autowired
	AirtimeService airtimeservice;
	
	@Autowired
	MouvementService mvtservice;
	
	@Autowired
	CagnotteService cagnotservice;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	GroupeService grpeservice;
	
	@Autowired
	static SuperGameManager supergmanager;
	
	@PostConstruct
	public void intializeKeno() {
		System.out.println("[INITIAL SYSTEM]");
//		Utile.keno = 185;
		
		Utile.display_draw = new HashMap<String, ControlDisplayKeno>();
		
		List<Partner> partners = partnerservice.getAllPartners();
		System.out.println("[INITIAL KENO - ALL]: "+partners.size());
		
		if (partners.isEmpty()) {
			Groupe grpe = new Groupe();
			grpe.setIdGroupe(0L);
			grpe.setNomgroupe("ramatbet");
			grpe.setState("ramatbet");
			grpe.setZone("ramatbet");
			grpeservice.create(grpe);
			
			Partner p = new Partner();
			p.setCoderace("ramatbet");
			p.setActif(1);
			p.setGroupe(grpe);
			p.setZone("yaounde");
			p.setIdpartner(1L);
			partnerservice.create(p);
		}
//		RefreshK ref = new RefreshK();
//		ref.start();
		
		
		for (Partner partner : partners) {
		  if(partner.getActif() == 1) {
			//Verification des configurations de bases
			//** Entrée dans la table Keno **
			String coderace = partner.getCoderace();
			Keno keno = kenoservice.find_Single_draw(partner);
			System.out.println("[INITIAL KENO - KENO COUNT]: "+keno);
			Cagnotte cgt = cagnotservice.find(partner);
			System.out.println("[INITIAL KENO - CAGNOT COUNT]: "+cgt);
			
			if(keno == null) {
				//On ajoute une entrée pour le partner dans la table Keno
				Keno ken = new Keno();
				ken.setDrawnumK(1);
				ken.setPartner(partner);
				ken.setCoderace(coderace);
				boolean nb_race = kenoservice.create(ken);
				if(nb_race) {
					System.out.println("[INITIAL KENO - INIT KENO]: "+coderace);
				}
			}
			
			//** Entré dans game_cycle
			GameCycle gmc = cycleservice.findByGame(partner, Jeu.K);
			//System.out.println("Initial game cycle: "+gmc);
			if(gmc == null) {
				//Ajout d'une entrée dans gameCycle
				System.out.println("[INITIAL KENO - GAME CYCLE POUR]: "+coderace);
				GameCycle gm = new GameCycle();
				gm.setPercent(95d);
			//	gm.setArchive(0);
				gm.setArrangement("3-4-5-7-9-10-11-17-18-20");
				gm.setTour(100);
				gm.setJeu(Jeu.K);
				gm.setCurr_percent(100);
				gm.setPosition(1);
				gm.setHitfrequence(10);
				gm.setJkpt(0);
				gm.setMise(0L);
				gm.setMisef(1L);
				gm.setPartner(partner);
				gm.setPayout(0);
				gm.setRefundp(0);
				gm.setStake(0d);
				gm.setDate_fin("16-01-2021,00:17");
			
				cycleservice.create(gm);
			}
			
			//** Entrée dans la table Config
			Config cfg = configservice.find(partner);
			
			if(cfg == null) {
				System.out.println("[INITIAL KENO - CONFIG POUR]: "+coderace);
				Config conf = new Config();
				conf.setCoderace(partner);
				configservice.create(conf);
				partner.setConfig(conf);
				partnerservice.update(partner);
			}
			
			
		  //***** Recherche de tous les caissiers d'un partenaires *****//
			List<Caissier> caissiers = caissierservice.findByPartner(partner);
			System.out.println("[INITIAL KENO - ALL CAISSIERS]: "+coderace+" Total = "+caissiers.size());

			for(Caissier cais : caissiers) {
				Airtime air = airtimeservice.find(cais);
				if(air == null) {
					Airtime airtime = new Airtime();
					airtime.setBalance(0);
					airtime.setCaissier(cais);
					airtime.setCredit(0);
					airtime.setDebit(0);
					airtime.setLibelle("");
					airtime.setDate(new Date());
					airtimeservice.create(airtime);
					
				}
				
				Mouvement mvt = mvtservice.findByCaissier(cais);
				if(mvt == null) {
					Mouvement mouv = new Mouvement();
					mouv.setMvt(0);
					mouv.setCaissier(cais);
					mvtservice.create(mouv);
				}
			}
			
			System.out.println("[INITIAL KENO - ALL INSTANCES]");
			
			ControlDisplayKeno cds = new ControlDisplayKeno(applicationContext);
			cds.setPartner(partner);
			cds.setCoderace(coderace);
			cds.setCountDown(Boolean.FALSE);
			int rang = Utile._checkExistingSameDisplayCoderace(coderace);
			if(rang == -1){
				Utile.display_draw.put(coderace, cds);
				rang = Utile.display_draw.size();
				cds.setRang(rang);
				cds.setTimeKeno(Utile.timekeno);
				
				RefreshK ref = new RefreshK(cds,partner,applicationContext);
				System.out.println("[INITIAL KENO - START GAME]");
				ref.start();
//				CagnotWorker cgntw = new CagnotWorker(cds, cagnotservice);
//				cgntw.runChecking();
				
			}
			else {
				cds.setRang(rang);
			}
			
			//System.out.println("Nombre de display connectÃ©s: "+Utile.display_draw.size()+" coderace: "+cds.getCoderace());
			
		 }
		}
	}
	
	private void countAllNumOdds(Partner partner, ControlDisplayKeno cds) {
		List<Keno> allDraws= kenoservice.getAllLastKdraw(partner);
		List<String> allDraw = new ArrayList<String>();
		Map<String, String> allDrawNumOdds = new HashMap<String, String>();
//		Utile.allDraw.clear();
//		Utile.allDrawNumOdds.clear();
		
		
		for(Keno k : allDraws) {
			allDraw.add(k.getDrawnumbK());
		}
		cds.setAllDraw(allDraw);
		
		// Initialisation des coefficients
		for(int i = 0; i < 80; i++) {
			allDrawNumOdds.put(""+(i+1), ""+0);
		}
		
		
		if(allDraw.size() > 0) {
			String[] passDraw;
			for(String ss : allDraw) {
				passDraw  = ss.split("-");
				for(int j=0; j<passDraw.length; j++) {
					String key = passDraw[j];
					String value = allDrawNumOdds.get(key);
					try {
					//	System.out.println("Value: "+value);
						allDrawNumOdds.put(key, ""+(Integer.parseInt(value) + 1));
					}
					catch(NumberFormatException e) {
						e.printStackTrace();
						allDrawNumOdds.put(key,   "1");
					}
					
				}
			}
			
		}
		
		cds.setAllDrawNumOdds(allDrawNumOdds);
	}
	
   
}
