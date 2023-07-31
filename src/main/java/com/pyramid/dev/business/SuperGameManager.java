package com.pyramid.dev.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.Params;
import com.pyramid.dev.tools.Utile;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author rodrigue_toukam
 * @version 1.0
 *
 */
@Slf4j
@Component
@Configurable
public class SuperGameManager {

	//private static Log log = LogFactory.getLog(SuperGameManager.class);

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


	private static Map<String, Double> mapMin = new HashMap<>();

	public double verifTicketMax(Map<Miset, Misek> map_wait) {

		String barcode;
		Miset miset;
		//double _montant_evt = 0;//le prix d'un evenement
		double gain_total = 0;
		Misek mk;

		for(Map.Entry mapentry : map_wait.entrySet()) {

			miset = (Miset) mapentry.getKey();
			barcode = miset.getBarcode();

			//log.info("BARCODE: "+barcode);
			if(!StringUtils.isBlank(barcode) && miset != null){

				//le ticket est en attente de traitement
				String typeJeu = miset.getTypeJeu().value();

				/*--- Keno traitment ---*/
				List<EffChoicek> ticket;

				String jeu = typeJeu.substring(0, 1);
				//log.info("jeu: "+jeu);

				switch(jeu) {
					case "K": // traitement ticket keno
						//mk = mskservice.searchMisesK(miset);
						mk = (Misek) mapentry.getValue();
						//log.info("mk MAX EVNTS: "+mk);
						if(mk != null){
							//on recupere les evenements du ticket
							//ticket = effchoicekservice.searchTicketK(""+mk.getIdmisek());
							ticket = effchoicekservice.searchTicketK(mk, mk.getDrawnumk());
							//log.info("EFFH MAX EVNTS: "+ticket.size());
							int xmulti = mk.getXmulti();
							//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
							//log.info("EFFH EVNTS: "+ticket.size());

							if(!ticket.isEmpty()){

								// recuperation de tous les evenement du ticket

//								List<String> resultMulti  = new ArrayList<String>();
//								boolean multiple = false;
//								int multi = 1;

								// multiplicateur par defaut
								double xtiplicateur = Double.parseDouble(Utile.multiplicateur[3]);
								double odd;
								// Verification du ticket

								for(EffChoicek t : ticket) {

									odd = checkTicketKMax(t);

									if(odd != 0 && odd != -1){
										//_montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
										gain_total = gain_total + odd * Double.parseDouble(t.getMtchoix());

										if(xmulti != 0) {
											gain_total = gain_total * xtiplicateur;
										}
										gain_total = (double)((int)(gain_total*100))/100;

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

		return gain_total;
	}

	public double verifTicketMin(Map<Miset, Misek> map_wait) {

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
		String typeJeu;
		Misek mk;
		Miset miset;
		List<EffChoicek> ticket = new ArrayList<>();

		double gain_total = 0;
		for(Map.Entry mapentry : map_wait.entrySet()) {
			miset = (Miset) mapentry.getKey();
			mk = (Misek) mapentry.getValue();
			barcode = miset.getBarcode();
			//	log.info("key: "+barcode+" | "+mk.getDrawnumk());
			//}
			if(!StringUtils.isEmpty(barcode) && miset != null){
				//return;
				//Miset miset = misetDao.searchTicketT(barcode);
				//le ticket est en attente de traitement
				typeJeu = miset.getTypeJeu().getValue();

				/*--- Keno traitment ---*/
				ticket.clear();
				//Misek mk = new Misek();

				/*--- Spin traitment ---*/

				//log.info("typeJeu: "+typeJeu);

				switch(typeJeu) {
					case "Keno": // traitement ticket keno
						//mk = mskservice.searchMisesK(miset);
						if(mk != null){
							//on recupere les evenements du ticket
							//ticket = effchoicekservice.searchTicketK(""+mk.getIdmisek());
							ticket = effchoicekservice.searchTicketK(mk, mk.getDrawnumk());

							//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
							//log.info("EFFH EVNTS: "+ticket.size());

							if(!ticket.isEmpty()){


								// Verification du ticket
								int bet;
								double gain;

								for(int i=0;i<ticket.size();i++ ){

									bet = Integer.parseInt(ticket.get(i).getIdparil());
									//   log.info("_RESULT-DETAILS: "+ticket.size());
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

				//	}
			}
		}
		gain_total = twoSideKnifeSum();
		return gain_total;
	}

	private double twoSideKnifeSum() {
		double sum = 0;
		sum = sum + Math.min(mapMin.get("pair"), mapMin.get("ipair"));
		//	System.out.println("Two knifes sum: "+sum);
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

	public double getRoundPayed(double percent, int roundSize) {

		//return (Params.MISE_MIN * percent * (100 - bonusrate))/(100 * roundSize);
		return (Params.MISE_MIN * percent)/roundSize;

	}

	public double verifTicketSum(Map<Miset, Misek> map_wait, String result, String multix) {

		//double _montant_evt = 0;//le prix d'un evenement
		double gain_total = 0;
		double multiplicateur = Double.parseDouble(multix);
		double gain;
		double odd ;
		int xmulti;

		Miset miset;
		Misek mk;
		//log.info("MAP SIZE: "+map_wait.size()+" XM: "+multiplicateur);
		for(Map.Entry mapentry : map_wait.entrySet()) {
			miset = (Miset) mapentry.getKey();
			mk = (Misek) mapentry.getValue();
			//	log.info("key: "+barcode+" | "+misek+" | "+miset);
			//}
			//Miset miset = misetDao.searchTicketT(barcode);
			if(miset != null){ //le ticket existe

				//le ticket est en attente de traitement
				String typeJeu = miset.getTypeJeu().value();

				/*--- Keno traitment ---*/
				List<EffChoicek> ticket;
				//Misek mk = new Misek();

				/*--- Spin traitment ---*/


				//		log.info("key: "+barcode+" | "+misek+" | "+miset+" | typeJeu= "+typeJeu);
				switch(typeJeu) {
					case "Keno": // traitement ticket keno
						//mk = mskservice.searchMisesK(miset);
						if(mk != null){
							//on recupere les evenements du ticket
							//ticket = effchoicekservice.searchTicketK(""+mk.getIdmisek());
							//						log.info("MK EVNTS: "+ mk.getDrawnumk());
							ticket = effchoicekservice.searchTicketK(mk, mk.getDrawnumk());
							xmulti = mk.getXmulti();
							//Misek _mk = misekDao.searchMiseK(""+mk.getIdmisek());
							//						log.info("EFFH EVNTS: "+ticket.size());

							for(EffChoicek tick : ticket){
								gain = 0;
								//_montant_evt = 0;
								odd = verifKeno(tick, result);

								if(odd != 0 && odd != -1){

									//_montant_evt = _montant_evt + Double.parseDouble(ticket.get(i).getMtchoix());
									gain = gain + odd * Double.parseDouble(tick.getMtchoix());
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
						break;
					default:
						break;

				}

			}
		}

		return gain_total;
	}

	public boolean addKenos(int draw_num, Partner partner) {
		log.info("draw_num "+draw_num);
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
		if(num < 0) num = 0;
		System.out.println("End Draw: " + partner.toString());
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
		//log.info("NUMERO: "+cds.getDrawNumk());
		Partner p = partservice.find(partner);

//		timeBefore = System.currentTimeMillis();
		Keno k = kenoservice.find_Max_draw_bis(partner);
		if(k == null) return false;
//		timeAfter = System.currentTimeMillis() - timeBefore;
//		log.info("SEEK KENO TIME: "+timeAfter);

		int num_tirage = cds.getDrawNumk();
		timeBefore = System.currentTimeMillis();
		tail = mskservice.searchMiseKdraw(k ,num_tirage);
		timeAfter = System.currentTimeMillis() - timeBefore;
//		log.info("SEEK TICKET TIME: "+timeAfter);
		//Partner partner = partservice.find(getCoderace());
		if(tail.size() < 2){ //on compte le nombre de ticket tirÃ©
			return false;
		}
		Config config = cfgservice.find(p);
		double lower = config.getBnkmin();
		double higher = config.getBnkmax();

		dbl = (double) (Math.random() * (higher-lower)) + lower;

		//	log.info(p);
		//	log.info("MBONUSP: "+dbl+" BONUSP: "+p.getBonuskamount()+" -- "+p.getIdPartner());
		if(p.getBonuskamount() > dbl){

			return giveBonusK(tail, p, cds);

		}

		return false;
	}

	private boolean giveBonusK(List<Misek> tail, Partner partner, ControlDisplayKeno cds){
		Long idp;
		int nb;
		int add;
		int winnerBonus = 0;
		int codebonus;
		//On recupere l'identifiant du tirage
		//	log.info("client: "+getCoderace()+" num: "+getDrawNumk());
		idp = kenoservice.getIdKenos(partner, cds.getDrawNumk());

		nb = tail.size();
		winnerBonus = Utile.generate(nb);    // recherche au radom du ticket vainqueur
		log.info("CODE: "+tail.get(winnerBonus).getBonusCod());
		codebonus = tail.get(winnerBonus).getBonusCod();

		Utile.bonusK_down = partner.getBonuskamount();
		Utile.bonusK_down = (double)((int)(Utile.bonusK_down*100))/100;
		log.info("Bonus Win: "+Utile.bonusK_down);

		Utile.bonus_codeK = codebonus;
		cds.setBonuskamount(Utile.bonusK_down);
		cds.setBonuskcode(Utile.bonus_codeK);

		double freeslipk = 0;
		Utile.bonusKmin = 0;//
		//	freeslipk = mstservice.findFreeSlipByPartner("freekeno", partner);
		Utile.bonusKmin = 0.1 * Utile.bonusK_down + freeslipk;
		Utile.bonusKmin = (double)((int)(Utile.bonusKmin*100))/100;
		add = partservice.update_bonusk(Utile.bonusKmin, codebonus, partner); //mise Ã  jour de la ligne du partner - bonus Ã  zero
		//	mstservice.updateFree("freekeno", -freeslipk, partner);
		if(add > 0){
			int _add = kenoservice.setCodeBonusK(Utile.bonusK_down, Utile.bonus_codeK, idp); //mise Ã  jour de la ligne dans spin

			return _add > 0;

		} else{
			return false;
		}
	}

	public double verifTicket(Map<Miset, Misek> map_wait, Partner coderace) {

		String barcode;
		Misek misek = null;
		Miset miset;
		Misek mk;
		String typeJeu;
		List<EffChoicek> ticket;
		List<Misek> listMisek = new ArrayList<>();
		double gain_total = 0;
		double gainMax = 0;

		boolean multiple = false;
		int multi = 1;
		int num_tirage_final = 0;
		int draw_numK; //numero de tirage en cours
		String single_result="";

		double xtiplicateur = 1;
		Keno k_keno = null;

		for(Map.Entry mapentry : map_wait.entrySet()) {

			miset = (Miset) mapentry.getKey();
			mk = (Misek) mapentry.getValue();
			barcode = miset.getBarcode();

			log.info("key: "+barcode+" | "+mk.toString());
			//}
			if(!StringUtils.isBlank(barcode) && miset != null){

				//le ticket est en attente de traitement
				typeJeu =  miset.getTypeJeu().value();

				/*--- Keno traitment ---*/

				//mk = new Misek();
				Keno _keno = kenoservice.find_Max_draw(coderace);
				draw_numK = _keno.getDrawnumK();

				switch(typeJeu) {

					case "Keno": // traitement ticket keno


						int xmulti = mk.getXmulti();
						//on recupere les evenements du ticket
						ticket = effchoicekservice.searchTicketK(mk);

						if( !ticket.isEmpty() ){

							//   multiple = !ticket.isEmpty()  ? true : false;
							multi = ticket.size();
							multiple = multi > 1 ? true : false;
							num_tirage_final = ticket.get(multi - 1).getDrawnum();

							// recherche du numero de tirage correspondant
							//num_draw = mskservice.getNumDraw(ticket.get(0).getMisek());

							gain_total = 0;
							double odd;

							for(int i=0;i< multi;i++){

								final int num = i + num_tirage_final;

								// recherche de la mise correspondante

								Optional<Misek> optionalMisek = mskservice.searchMisesK(miset).stream().filter(t -> t.getDrawnumk() == num).findFirst();

								if(optionalMisek.isPresent()) {
									misek = optionalMisek.get();
								} else{
									continue;
								}


								k_keno = kenoservice.searchResultK(num, coderace);

								if(k_keno != null){

									//mise à jour du keno
									misek.setKeno(k_keno);

									if(k_keno.getStarted() == 1){

										single_result = k_keno.getDrawnumbK();
										xtiplicateur = Double.parseDouble(k_keno.getMultiplicateur());

										// verification du ticket
										odd = verifKeno(ticket.get(i), single_result);

										if(odd != 0 && odd != -1){

											//	   System.out.println("MULTIPLICATEUR MULTI: "+xmulti+" KENO MULTI: "+xtiplicateur+" GAIN: "+gain_total);
											// verifie si le multiplicateur a été activé sur le ticket
											if(xmulti != 0) {
												gain_total = gain_total + (xtiplicateur * odd * Double.parseDouble(ticket.get(i).getMtchoix()));
											} else {
												gain_total = gain_total + odd * Double.parseDouble(ticket.get(i).getMtchoix());
											}

											gain_total = (double) ((int) (gain_total * 100)) /100;

											gainMax = gainMax + gain_total;
											misek.setSumWin(gain_total);

										}
									}
								}

							}

							if (multiple) {
								System.out.println("draw_numK: "+draw_numK + " num_tirage_final: "+num_tirage_final+" misek.getSumWin(): "+misek.getSumWin());

								if(draw_numK > num_tirage_final) {
									if(misek.getSumWin() != 0){
										misek.setEtatMise(EtatMise.GAGNANT);
									} else {
										misek.setEtatMise(EtatMise.PERDANT);
									}
								}
							} else {
								if(gain_total == 0){
									assert misek != null;
									misek.setEtatMise(EtatMise.PERDANT);

								} else{
									misek.setEtatMise(EtatMise.GAGNANT);

								}
							}

							listMisek.add(misek);
						}
						break;
					default:
						break;

				}

			}
		}

		//mise à jour de letat des tickets en bd

		if (!listMisek.isEmpty()) {
			mskservice.updateAll(listMisek);
		}

		return gainMax;
	}

	public List<Integer> getHitFrequency(int hf, int nCycle) {

		ArrayList<Integer> roundList = new ArrayList<>();
		int[] round;
		ArrayList<String> combis = new ArrayList<>();
		ArrayList<String> combi = new ArrayList<>();

		int nbS = (hf*nCycle)/100;
		round = new int[nbS];


		for(int i=1;i<(1+nCycle);i++)
			combis.add(String.valueOf(i));
		for(int j=0;j<nCycle;j++){
			int index = Utile.generate(combis.size());
			combi.add(combis.get(index));
			combis.remove(index);
		}

		for(int ii=0;ii<nbS;ii++) round[ii] = Integer.parseInt(combi.get(ii));
//    	for(int nb : round) {
//    		System.out.print(" "+nb);
//		}
		//log.info("");
		Arrays.sort(round);
		for(int nb : round) {
			roundList.add(nb);
		}

		return roundList;
	}

	private double checkTicketKMax(EffChoicek t){

		double cote = 0;
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
			case 28:// 1er sup 40.5
				cote = Utile.numSpec[2];
				break;
			case 29:// 2e inf 40
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
		   case 28:// 1er inf 40.5
			   cote = Utile.numSpec[2];
			   break;
		   case 29:// 1er sup 40
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

	   int nbD = 0;
	   int sum = 0;
	   int num;
	   double cote = 0;

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
				   } else{
					   nbD++;
				   }
			   }

			   if(trouv){
				   cote = Utile.numOut[nbD];
			   } else{
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
					   } else{
						   truv = false;
					   }
				   }

				   if(!truv){
					   break;
				   }
			   }

			   if(!truv){
				   cote = 0;
			   } else{
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
		   case 28://1er numero < 40,5
			   num = Integer.parseInt(draw[0]);
			   if(num <= 40)
				   cote = Utile.numSpec[2];
			   break;
		   case 29://derniere couleur orange
			   num = Integer.parseInt(draw[0]);
			   if(40 < num)
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

		List <Cagnotte> listcagnot = cagnotservice.find(cds.getPartner());
		log.info("MAnager Cagnotte __ " +listcagnot);

		if (listcagnot == null || listcagnot.isEmpty() ) return -1L;
		log.info("Manager __ " +listcagnot.size());
		Optional<Cagnotte> optcagnot = listcagnot.stream().filter(cg -> cg.getBarcode() == 0L).findFirst();

		if(optcagnot.isPresent()) {
			Cagnotte cagnot = optcagnot.get();

			List<Misek> tail;
			int num_tirage = cds.getDrawNumk();
			Keno k = kenoservice.find_Max_draw_bis(cds.getPartner());
			tail = mskservice.searchMiseKdraw(k ,num_tirage);
			log.info("Cagnotte tail __ " + tail.size());

			if(tail.size() < 2){ //on compte le nombre de ticket tirÃ©
				log.info("Cagnotte __ pas assez de pari");
				return -1L;
			}

			if (giveCagnotte(tail, cds.getPartner(),cds)) {
				return cagnot.getIdCagnotte();
			}
		}


		return -1L;
    }
    
    private boolean giveCagnotte(List<Misek> tail, Partner partner, ControlDisplayKeno cds){

		int nb;
		int winnerBonus=0;
		Long codemise;
		//On recupere l'identifiant du tirage

		try {
			kenoservice.getIdKenos(partner, cds.getDrawNumk()-1);

			nb = tail.size();
			winnerBonus = Utile.generate(nb);    // recherche au radom du ticket vainqueur
			log.info("CODE CAGNOTTE: "+tail.get(winnerBonus).getIdMiseK());
			codemise = tail.get(winnerBonus).getIdMiseK();

			Miset miset = mstservice.findById(tail.get(winnerBonus).getMiset().getIdMiseT());
			String barcode = miset.getBarcode();
			String barc = tail.get(winnerBonus).getMiset().getBarcode();
			log.info("BARCODE CAGNOTTE: "+barcode+" | "+barc);

			cds.setBarcodeCagnot(Long.valueOf(barc));
			cds.setMiseCagnot(codemise);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
 
}