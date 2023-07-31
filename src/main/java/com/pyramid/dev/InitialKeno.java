package com.pyramid.dev;

import com.pyramid.dev.business.RefreshK;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.*;
import com.pyramid.dev.service.*;
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.Utile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class InitialKeno {
	
	private static Log logger = LogFactory.getLog(InitialKeno.class);
	
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
	MisetService mstservice;
	
	
	@PostConstruct
	public void intializeKeno() {
		logger.info("[INITIAL SYSTEM]");
//		Utile.keno = 185;
		
		Utile.display_draw = new HashMap<String, ControlDisplayKeno>();
		Utile.barcodeKenoPool = new ArrayList<>(1000);
		
		List<Partner> partners = partnerservice.getAllPartners();
		List<Partner> partnerActifs = partners.stream().filter(p -> p.getActif() == 1)
				.collect(Collectors.toList());
		logger.info("[INITIAL KENO - ALL]: "+partners.size());
		
		if (partners.isEmpty()) {
			Groupe grpe = new Groupe();
//			grpe.setIdGroupe(0L);
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
		

		for (Partner partner : partnerActifs) {

			//Verification des configurations de bases
			//** Entrée dans la table Keno **
			String coderace = partner.getCoderace();
			List<Keno> keno = kenoservice.findAllDraw(partner);
			logger.info("[INITIAL KENO - KENO COUNT]: "+keno.size());
			
			if(keno.isEmpty()) {
				//On ajoute une entrée pour le partner dans la table Keno
				Keno ken = new Keno();
				ken.setDrawnumK(1);
				ken.setPartner(partner);
				ken.setCoderace(coderace);
				kenoservice.create(ken);

			}
			else {
				Keno k = keno.get(keno.size()-1);
				if(!k.getMultiplicateur().equalsIgnoreCase("0")) {
					int numTirage = 1 + k.getDrawnumK();
					Keno ken = kenoservice.findDraw(partner, numTirage);
					if(ken == null) {
						ken = new Keno();
						ken.setDrawnumK(numTirage);
						ken.setPartner(partner);
						ken.setCoderace(partner.getCoderace());
						kenoservice.create(ken);
					}
					
				}
			}
			
			//** Entré dans game_cycle
			GameCycle gmc = cycleservice.findByGame(partner, Jeu.K);
			//logger.info("Initial game cycle: "+gmc);
			if(gmc == null) {
				//Ajout d'une entrée dans gameCycle
				logger.info("[INITIAL KENO - GAME CYCLE POUR]: "+coderace);
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
				logger.info("[INITIAL KENO - CONFIG POUR]: " + coderace);
				Config conf = new Config();
				conf.setCoderace(partner);
				configservice.create(conf);
				partner.setConfig(conf);
				partnerservice.update(partner);
			}
			
			
		  //***** Recherche de tous les caissiers d'un partenaires *****//
			List<Caissier> caissiers = caissierservice.findByPartner(partner);
			logger.info("[INITIAL KENO - ALL CAISSIERS]: "+coderace+" Total = "+caissiers.size());

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
			
			logger.info("[INITIAL KENO - ALL INSTANCES]");
			
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
				logger.info("[INITIAL KENO - START GAME]");
				ref.start();
//				CagnotWorker cgntw = new CagnotWorker(cds, cagnotservice);
//				cgntw.runChecking();
				
			}
			else {
				cds.setRang(rang);
			}
			
			//logger.info("Nombre de display connectÃ©s: "+Utile.display_draw.size()+" coderace: "+cds.getCoderace());

		}
	}


}
