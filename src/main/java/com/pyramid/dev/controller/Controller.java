package com.pyramid.dev.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyramid.dev.business.RefreshK;
import com.pyramid.dev.business.SuperGameManager;
import com.pyramid.dev.enums.EtatMise;
import com.pyramid.dev.enums.Jeu;
import com.pyramid.dev.enums.Room;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.AdminTicketDto;
import com.pyramid.dev.model.Airtime;
import com.pyramid.dev.model.BetTicketK;
import com.pyramid.dev.model.BonusSet;
import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.model.Caissier;
import com.pyramid.dev.model.CaissierDto;
import com.pyramid.dev.model.Config;
import com.pyramid.dev.model.EffChoicek;
import com.pyramid.dev.model.GameCycle;
import com.pyramid.dev.model.GameCycleDto;
import com.pyramid.dev.model.Groupe;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.KenoRes;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.MisekDto;
import com.pyramid.dev.model.Misek_temp;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Mouvement;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.model.PartnerDto;
import com.pyramid.dev.model.Profil;
import com.pyramid.dev.model.ResponseData;
import com.pyramid.dev.model.ShiftDay;
import com.pyramid.dev.model.Versement;
import com.pyramid.dev.responsecode.ResponseHolder;
import com.pyramid.dev.service.AirtimeService;
import com.pyramid.dev.service.CagnotteService;
import com.pyramid.dev.service.CaissierService;
import com.pyramid.dev.service.ConfigService;
import com.pyramid.dev.service.EffChoicekService;
import com.pyramid.dev.service.GameCycleService;
import com.pyramid.dev.service.GroupeService;
import com.pyramid.dev.service.KenoService;
import com.pyramid.dev.service.MisekService;
import com.pyramid.dev.service.Misek_tempService;
import com.pyramid.dev.service.MisetService;
import com.pyramid.dev.service.MouvementService;
import com.pyramid.dev.service.PartnerService;
import com.pyramid.dev.service.VersementService;
import com.pyramid.dev.tools.BetTicketKDTO;
import com.pyramid.dev.tools.BonusSetDTO;
import com.pyramid.dev.tools.CagnotteDTO;
import com.pyramid.dev.tools.CaissierDTO;
import com.pyramid.dev.tools.ControlDisplayKeno;
import com.pyramid.dev.tools.KenoDTO;
import com.pyramid.dev.tools.KenoResDTO;
import com.pyramid.dev.tools.Params;
import com.pyramid.dev.tools.PartnerDTO;
import com.pyramid.dev.tools.ShiftDTO;
import com.pyramid.dev.tools.Utile;
import com.pyramid.dev.tools.VersementDTO;

@RestController
@CrossOrigin()
@RequestMapping(value="/api/v1/supergames")
public class Controller {
	
	@Autowired
	PartnerService partnerservice;
	
	@Autowired
	KenoService kenoservice;
	
	@Autowired
	CaissierService caisservice;
	
	@Autowired
	MouvementService mvtservice;
	
	@Autowired
	ConfigService cfgservice;
	
	@Autowired
	MisetService mstservice;
	
	@Autowired
	GameCycleService gmcservice;
	
	@Autowired
	MisekService mskservice;
	
	@Autowired
	Misek_tempService mstpservice;
	
	@Autowired
	EffChoicekService efkservice;
	
	@Autowired
	VersementService verservice;
	
	@Autowired
	AirtimeService airtservice;
	
	@Autowired
	Misek_tempService mtpservice;
	
	@Autowired
	CagnotteService cagnotservice;
	
	@Autowired
	GroupeService grpeservice;
	
	@Autowired
	ConfigService configservice;
	
	@Autowired
	SuperGameManager supermanager;
	
	@Autowired
	ApplicationContext applicationContext;

//	private CaissierService caiservice;
	
	@PostMapping("partner")
//	@Produces({"application/json","application/xml"})
	public Response savePartner(@RequestBody Partner partner) {
		 return partnerservice.create(partner);
		
	}
	
	@PostMapping("partners")
//	@Produces({"application/json","application/xml"})
	public Response savePartners(@RequestBody PartnerDto partner) {
		Groupe grpe = new Groupe();
		grpe.setIdGroupe(1L);
		grpe = grpeservice.find(grpe);
		
		Partner p = new Partner();
		p.setCob(Room.CLOSED);
		p.setGroupe(grpe);
		p.setIdpartner(partner.getIdpartner());
		p.setCoderace(partner.getCoderace());
		p.setActif(1);
		p.setZone("YAOUNDE");
		Response res = partnerservice.create(p);
		
		if (res == null) {
			return Response.ok(PartnerDTO.getInstance().event(p).error("")).build();
		}
		
		Config conf = new Config();
		conf.setCoderace(p);
		configservice.create(conf);
		p.setConfig(conf);
		partnerservice.update(p);
		
		Keno ken = new Keno();
		ken.setDrawnumK(1);
		ken.setPartner(p);
		ken.setCoderace(p.getCoderace());
		kenoservice.create(ken);
		
		GameCycle gm = new GameCycle();
		gm.setPercent(95d);
		gm.setArrangement("3-4-5-7-9-10-11-17-18-20");
		gm.setTour(100);
		gm.setJeu(Jeu.K);
		gm.setCurr_percent(100);
		gm.setPosition(1);
		gm.setHitfrequence(10);
		gm.setJkpt(0);
		gm.setMise(0L);
		gm.setMisef(1L);
		gm.setPartner(p);
		gm.setPayout(0);
		gm.setRefundp(0);
		gm.setStake(0d);
		gm.setDate_fin("16-01-2021,00:17");
		gmcservice.create(gm);
	
		return res;
		
	}
	
	@GetMapping("list-partners")
	public List<Partner> allpartners() {
		 return partnerservice.getAllPartners();
	}
	
	@GetMapping("timekeno/{coderace}")
	public Response retrieveTimeKeno(@PathVariable("coderace") String coderace) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
			 int timekeno = partnerservice.retrieveTimeKeno(cds);
			 ob.put("time", timekeno);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("counter/{coderace}")
	public int retrieveTimeKenos(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 return partnerservice.retrieveTimeKeno(cds);
	}
	
	@GetMapping("max-draw/{coderace}")
	public Response getMaxIdDrawK(@PathVariable("coderace") String coderace) {
		 JSONObject ob = new JSONObject(); 
		 Partner p;
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 p = cds.getPartner();
		 Keno k = kenoservice.find_Max_draw(p);
		 if(k == null) {
			 return Response.ok(KenoDTO.getInstance().error("ERROR MAX DRAW RETRIEVE")).build();
		 }
		 ob.put("maxdraw", k.getIdKeno());
		 String eve = Utile.convertJsonToString(ob);
		 return Response.ok(KenoDTO.getInstance().event(k).sucess("")).build();
	}
	
	@GetMapping("drawcombi/{coderace}")
	public Response retrieveDrawCombi(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace); 
		 KenoRes kres = new KenoRes();
		//return partnerservice.retrieveDrawCombi(cds);
		 JSONObject ob = new JSONObject();
		 try {
			 kres.setDrawnumbK(cds.getDrawCombik());
			 
			 kres.setDrawnumK(cds.getDrawNumk());
			 kres.setMultiplicateur(cds.getMultiplix());
			 kres.setBonusKamount(cds.getBonuskamount());
			 kres.setGameState(cds.getGameState());
			 kres.setStr_draw_combi(cds.getStr_draw_combi());
			 kres.setHeureTirage(cds.getHeureTirage());
//			 if(cds.getGameState() > 2) {
//				 kres.setDrawnumK(cds.getDrawNumk() - 1);
//			 }
			 
			 return Response.ok(KenoResDTO.getInstance().event(kres).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(KenoResDTO.getInstance().error(e.getMessage())).build();
		 }
		 
		 //return kres;
	}
	
	@GetMapping("bonuskeno/{coderace}")
	public double retrieveBonusKeno(@PathVariable("coderace") String coderace) {
		double bonus; 
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 Partner p =  partnerservice.findById(cds.getPartner().getCoderace());
		 bonus = p.getBonuskamount();
		 return bonus;
		// return partnerservice.findById(cds.getPartner().getIdPartner());
	}
	
	@GetMapping("sumOdd/{coderace}")
	public Map<String, String> sumOddKeno(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 return cds.getAllDrawNumOdds();
		
	}
	
	@GetMapping("findbalance/{id}")
	public Response retrieveBalance(@PathVariable("id") Long id, Caissier caissier) {
		 JSONObject ob = new JSONObject();
		 double balance;
		 Caissier cais;
		 caissier.setIdCaissier(id);
		 //return balance;
		 try {
			 cais = caisservice.findById(caissier);
			 balance = mvtservice.findMvt(cais); 
			 ob.put("balance", balance);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("airtime/{partner}/{login}/{credit}")
	public Response pushAirtime(@PathVariable("partner") String partner,@PathVariable("login") String login, @PathVariable("credit") Double credit) {
		 JSONObject ob = new JSONObject();
		 double balance = 0;
		 Airtime airtime = new Airtime();
		 Caissier caissier = new Caissier();

		
		 try {
			 Partner p =  partnerservice.findById(partner);
			 caissier = caisservice.findByLoginIdPartner(login, p);
			 if(caissier == null) {
				 return Response.ok(ResponseData.getInstance().error("Caissier introuvable")).build();
			 }
			 
			    Airtime airtme = airtservice.find(caissier);
				if(airtme !=null) balance = airtme.getBalance();
					
				airtime.setCredit(credit);
				airtime.setDate(new Date());
				airtime.setDebit(0);
				airtime.setCaissier(caissier);
				airtime.setBalance(balance+credit);
				airtime.setLibelle("CREDIT EN CAISSE");
				airtime.setEta("NV");
				airtservice.create(airtime);
				
				double mvt = mvtservice.findMvt(caissier);
				mvtservice.updateMvt(caissier, mvt+credit);
				
			    ob.put("credit", credit);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("shift/{id}/{debut}/{fin}")
	public Response getShift(@PathVariable("id") Long id, @PathVariable("debut") String debut, @PathVariable("fin") String fin,  Caissier  caissier) {
		Caissier cais;
		double sum_in_k = 0;
		double sum_out_k = 0;
		int invk = 0;
		int invk_out = 0;
		double balance_init = 0;
		Airtime airtime;
		caissier.setIdCaissier(id);
		ShiftDay shiftday = new ShiftDay();
		 try {
			 cais = caisservice.findById(caissier);
			 sum_in_k = mskservice.getMiseRK(cais, debut, fin);
			 invk = mskservice.getIntvTicketK(cais, debut, fin);
			 sum_out_k = verservice.getVersementD(debut, cais, fin,Jeu.K.getValue());
			 invk_out = verservice.getPayTicket(cais, debut, fin, Jeu.K.getValue());
			 
			 airtime = airtservice.find(cais);
			 balance_init = airtime.getBalance();
			 
			 shiftday.setCashink(sum_in_k);
			 shiftday.setCashoutk(sum_out_k);
			 shiftday.setSlipk(invk);
			 shiftday.setVslipk(invk_out);
			 shiftday.setBalancein(balance_init);
			 
			 return Response.ok(ShiftDTO.getInstance().event(shiftday).sucess("")).build();
			 
	     } catch (Exception e) {
			System.out.println("SHIFT - "+e);
			e.printStackTrace();
			return Response.ok(ShiftDTO.getInstance().error(e.getMessage())).build();
		 }
		
		
	}
	
	@GetMapping("findticket/{coderace}/{barcode}")
	public Response verifyTicket(@PathVariable("coderace") String coderace, @PathVariable("barcode") String barcode) {
		 
		 double bonusWinAmount;
		 boolean bonusDown = false;
		 int bonusWinCode = -1;
		 int bonusTicketCode;
		 boolean iscagnot = false;
		 
		 try {
			 //recherche du ticket dans miset
			 System.out.println("Controler "+barcode);
			 barcode = barcode.length() == 13 ? barcode.substring(0, 12) : barcode;
			 Miset miset = mstservice.searchTicketT(barcode);
			 System.out.println("MISET: "+miset);
			// ticket non existant
			 if(miset == null) {
				 return Response.ok(BetTicketKDTO.getInstance().error(ResponseHolder.TCKINCON)).build();
			 }
			 
			 //le ticket est en attente de traitement
		      String typeJeu = miset.getTypeJeu().getValue();
		      
		      /*--- Keno traitment ---*/
			  List<EffChoicek> ticket = new ArrayList<EffChoicek>();
			  Misek mk = new Misek();
		     
	//		  System.out.println("Controler - typeJeu"+typeJeu);
		      switch(typeJeu) {
		      	case "Keno":
		      		BetTicketK betk = new BetTicketK();
		      		mk = mskservice.searchMisesK(miset);
		      	//	 System.out.println("Controler - mk "+mk);
		      		if(mk != null) {
		      			
		      		    //verification si ticket partenaire
		      			Partner p = partnerservice.find(mk.getCaissier().getPartner());
		      		
		      			int draw_numK, num_draw,xmulti, multi = 1;
		      			double xtiplicateur = 1;
		      		    boolean multiple = false, eval;
		      		    Keno k_keno = null;
		      		    Cagnotte cagnot = null;
		      		    String result = "",single_result="",str0="",str1="",result_multi;
		    		    String[] str_result;
		    		    EffChoicek tick = new EffChoicek();
		    		    List<EffChoicek> list_efchk = new ArrayList<EffChoicek>();
			    		List<String> resultMulti  = new ArrayList<String>();
		      		  
		      			
		      			if(coderace.equalsIgnoreCase(p.getCoderace())) {
		      				
		      			    //verifie si ticket deja payé
		      				 Versement vers = verservice.find_vers_miset(miset.getIdMiseT());
		      				 if(vers != null) {
		      					 System.out.println("controller-already vers "+vers.getMise());
		      				     BetTicketK b = new BetTicketK();
		      				     b.setVers(vers);
		      					 return Response.ok(BetTicketKDTO.getInstance().event(b).sucess("").error(ResponseHolder.TCKALRPAID)).build();
		      				 }
		      				 
		      				// Ticket reconnu et correct - Traitement.
		      				 
		      				xmulti = mk.getXmulti();
							bonusTicketCode = mk.getBonusCod();
							
							//on recupere les evenements du ticket
							ticket = efkservice.searchTicketK(mk);
							System.out.println("Controler - ticket "+ticket.size());
							
							if( !ticket.isEmpty() ){
								
								 betk.setCotejeu(Double.parseDouble(ticket.get(0).getCote()));
								 betk.setBarcode(barcode);
								 betk.setHeureMise(mk.getHeureMise());
								 betk.setDateMise(mk.getDateMise());
								 betk.setCaissier(mk.getCaissier().getIdCaissier());
								 betk.setIdMiseT(miset.getIdMiseT());
								 betk.setSummise(mk.getSumMise());
								 betk.setMultiplicite(ticket.size());
								// betk.setBonus(bonus);
								 //recherche du numero de tirage en cours
					    		 Keno _keno = kenoservice.find_Max_draw(p);
					    		 draw_numK = _keno.getDrawnumK();
					    		 
//					    		 Misek_temp misektp = mstpservice.find(mk.getIdMiseK());
//					    		 if(misektp != null) {
//					    			 multi = misektp.getMulti();
//					    		 }
					    		 multi = ticket.size();
					    		 
					    		 multiple = multi > 1 ? true : false; 
					    		 
					    		 num_draw = mskservice.getNumDraw(mk);
					    		// System.out.println("Controler - num_draw "+num_draw);
				    			 betk.setDrawnumk(num_draw);
				    			 int num_tirage_final = num_draw + multi - 1;
				    			 System.out.println("Numero tirage final: "+num_tirage_final);
				    			 
				    	// Recherche du resultat des differents elements
				    			 eval = true; //controle si le tirage a deja eu lieu
				    			 
					    		 for(int i=0;i< multi;i++){
					    			int num = i + num_draw;
					    			
					    			k_keno = kenoservice.searchResultK(num, p);
					    			cagnot = cagnotservice.find(p);
					    			
					    			tick = ticket.get(i);
				    				tick.setImisek(mk.getIdMiseK());
					    		
					    			if(k_keno != null){
					    				
					    				
					    				bonusWinAmount = k_keno.getBonusKamount();
					    				if(bonusWinAmount != 0) {
				    					   bonusDown = true;
				    					   bonusWinCode = k_keno.getBonusKcod();
					    					   
					    				}
					    				
					    				if(k_keno.getStarted() == 1){
					    					single_result = k_keno.getDrawnumbK();
					    					str_result = k_keno.getDrawnumbK().split("-");
					    					
					    					tick.setDrawresult(single_result);
					    					tick.setDrawnum(num);
					    					tick.setIdkeno(k_keno.getIdKeno());
					    					
					    					for(int ii=0;ii<str_result.length;ii++){
												   if(ii<10){
													   str0 = str0+" "+str_result[ii];
												   }
												   else if (ii>9 && ii<20){
													   str1 = str1+" "+str_result[ii];
												   }
											}
					    					
					    					
					    					double _montant_evt = 0;//le prix d'un evenement
								    		double gain_total = 0;
								    		
								    		double odd = supermanager.verifKeno(tick, single_result);
								    		
								    		if(odd != 0 && odd != -1){
								    		    tick.setCote(""+odd);
								    		    
								    		   _montant_evt = _montant_evt + Double.parseDouble(tick.getMtchoix());
								    		   gain_total = gain_total + odd * Double.parseDouble(tick.getMtchoix());
								    		  
								    		   if(xmulti != 0) {
								    			   gain_total = gain_total * xtiplicateur;
								    		   }
								    		   gain_total = (double)((int)(gain_total*100))/100;
								    		   
								    		   tick.setMtwin(gain_total);
								    		   tick.setState(true);
								    		   
							    				
											}
											else if(odd == 0){
												
												tick.setCote("0");
												tick.setMtwin(0);
												tick.setState(false);
											}
								    		
								    		 
								    	    if(bonusDown) {
								    		   if(bonusWinCode == bonusTicketCode) {
								    			   gain_total = gain_total + bonusWinAmount;
								    			   gain_total = (double)((int)(gain_total*100))/100;
								    		   }
								    		   else {
								    			   bonusDown = Boolean.FALSE;
								    		   }
								    		   
								    		   tick.setMtwin(gain_total);
									    	}
								    	    
								    	   
								    		betk.setSumWin(gain_total + betk.getSumWin());
								    		list_efchk.add(tick);
								    		
					    				}
					    				else {
					    					
					    					eval = false;
					    					tick.setDrawresult(" ");
					    					tick.setDrawnum(num);
					    					tick.setIdkeno(k_keno.getIdKeno());
					    					list_efchk.add(tick);
					    				}
					    			}
					    			else {
					    				// Tirage pas encore déroulé
					    				eval = false;
					    				
					    				tick.setDrawresult(" ");
				    					tick.setDrawnum(num);
				    					list_efchk.add(tick);
				    				
					    				//betk.setList_efchk(list_efchk);
					    				//return Response.ok(BetTicketKDTO.getInstance().event(betk).error(ResponseHolder.TCKNEVAL)).build();
					    			}
					    			
					    		 }
					    		 
					    		 betk.setList_efchk(list_efchk);
					    		 if (eval) {
					    			 return Response.ok(BetTicketKDTO.getInstance().event(betk).sucess("")).build();
					    		 }
					    		 else {
					    			 //return Response.ok(BetTicketKDTO.getInstance().event(betk).error(ResponseHolder.TCKNEVAL)).build();
					    			 return Response.ok(BetTicketKDTO.getInstance().event(betk).sucess("").error(ResponseHolder.TCKNEVAL)).build();
					    		 }
								 
							}
							else {
								//return Response.ok(BetTicketKDTO.getInstance().error(ResponseHolder.TCKCHXERR)).build();
								return Response.ok(BetTicketKDTO.getInstance().event(betk).sucess("").error(ResponseHolder.TCKCHXERR)).build();
							}
		      			}
		      			else {
		      				// Mauvais partenaire
		      				//return Response.ok(BetTicketKDTO.getInstance().error(ResponseHolder.TCKNRECON)).build();
		      				return Response.ok(BetTicketKDTO.getInstance().event(betk).sucess("").error(ResponseHolder.TCKNRECON)).build();
		      			}
		      			
		      		}
		      		else {
		      			//return Response.ok(BetTicketKDTO.getInstance().error(ResponseHolder.TCKNREG)).build();
		      			return Response.ok(BetTicketKDTO.getInstance().event(betk).sucess("").error(ResponseHolder.TCKNREG)).build();
		      		}
		      		
		      		//break;
		      	case "Spin":
		      		
		      		break;
		      	default:
		      		break;
		      }
			 
			 
			 
			 return Response.ok(BetTicketKDTO.getInstance().sucess("")).build();
		 } catch (Exception e) {
			 e.printStackTrace();
		//	e.printStackTrace();
			return Response.ok(BetTicketKDTO.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("versement/{barcode}/{montant}/{id}")
	public Response registerVersement(@PathVariable("barcode") String barcode, @PathVariable("id") Long id,  @PathVariable("montant") double montant, Caissier cais) {
		cais.setIdCaissier(id);
		Caissier caissier = caisservice.findById(cais);
		
		 Miset miset = mstservice.searchTicketT(barcode);
		 if(miset != null) {
			 
			//verifie si ticket deja payé
		//	 System.out.println("controller-already paid "+miset.getIdMiseT());
			 Versement vers = verservice.find_vers_miset(miset.getIdMiseT());
			 if(vers != null) {
				 return Response.ok(VersementDTO.getInstance().error("TICKET ALREADY PAID")).build();
			 }
			 
			 Versement versemt = new Versement();
			 String txtDate=new SimpleDateFormat("dd/MM/yyyy,H:m:s", Locale.FRANCE).format(new Date());
			long tms;
			try {
				tms = Utile.givetimestamp(txtDate);
				
				double mvtprice = mvtservice.findMvt(caissier);
				Mouvement mvnt = mvtservice.findByCaissier(caissier);
				mvnt.setMvt(mvtprice + montant);
				mvnt.setCaissier(caissier);
				boolean ajout = mvtservice.update(mvnt);
				
				if(ajout) {
					versemt.setCaissier(caissier);
					versemt.setDatV(txtDate);
					versemt.setHeureV(""+tms);
					versemt.setTypeVers(miset.getTypeJeu().getValue());
					versemt.setMontant(montant);
					versemt.setMise(miset.getIdMiseT());
					
					ajout = verservice.create(versemt);
					if(ajout) {
						return Response.ok(VersementDTO.getInstance().event(versemt).sucess("")).build();
					}
					else {
						return Response.ok(VersementDTO.getInstance().error("TICKET NOT PAID")).build();
					}
				}
				else {
					return Response.ok(VersementDTO.getInstance().error("TICKET NOT PAID")).build();
				}
				
			} catch (ParseException | DAOException e) {
				e.printStackTrace();
				return Response.ok(VersementDTO.getInstance().error("TICKET NOT PAID")).build();
			}
			 
		 }
		 else {
			 return Response.ok(VersementDTO.getInstance().error("TICKET INCONNU")).build();
		 }
		 
		
	}
	
	@GetMapping("bonusrate/{coderace}")
	public double retrieveBonusRate(@PathVariable("coderace") String coderace) {
		 double rate;
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 Config cfg = cfgservice.find(cds.getPartner());
		 rate = cfg.getBonusrate();
		 return rate;
	}
	
	@GetMapping("cagnotte/{coderace}")
	public Response retrieveCagnotte(@PathVariable("coderace") String coderace) {
		 
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 Cagnotte cg = cagnotservice.find(cds.getPartner());
		 	
		 if(cg == null)  return Response.ok(CagnotteDTO.getInstance().error("NOT FOUND")).build();
		 return Response.ok(CagnotteDTO.getInstance().event(cg).sucess("")).build();
	}
	
	@GetMapping("search-cagnotte/{coderace}")
	public Response searchCagnot(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 System.out.println("searchCagnot finish");
		 BonusSet bs = new BonusSet();
		 bs.setBarcode(cds.getBarcodeCagnot());
		 bs.setMise(cds.getMiseCagnot());
		 bs.setCoderace(coderace);
		 
		 return Response.ok(BonusSetDTO.getInstance().event(bs).sucess("")).build();
	}
	
	@GetMapping("barcode/{jeu}")
	public long retrieveBarcode(@PathVariable("jeu") int jeu) { // 0:M, 1:D, 2:L, 3:B, 4:S, 5:K
		 long barcode;
		 Jeu typeJeu = Jeu.styleJeu().get(jeu);
		 barcode = supermanager.searchBarcode(typeJeu);
		 return barcode;
	}
	
	@PostMapping("save-miset")
	public boolean saveMiset(@RequestBody Miset miset) {
		 return mstservice.create(miset);
	}
	
	@PostMapping("update-miset/{miset_id}")
	public boolean updateMiset(@RequestBody Miset miset,@PathVariable("miset_id") Long miset_id) {
		miset.setIdMiseT(miset_id);
		return mstservice.update(miset);
	}
	
	@PostMapping("save-misek")
	public boolean saveMisek(@RequestBody Misek misek) {
		 return mskservice.create(misek);
	}
	
	@PostMapping("update-misek/{misek_id}")
	public boolean updateMisek(@RequestBody Misek misek,@PathVariable("misek_id") Long misek_id) {
		misek.setIdMiseK(misek_id);
		return mskservice.update(misek);
	}
	
	@PostMapping("save-misektp")
	public boolean saveMisektp(@RequestBody Misek_temp misektp) {
		 return mstpservice.create(misektp);
	}
	
	@PostMapping("update-misektp/{misektp_id}")
	public int updateMisektp(@RequestBody Misek_temp misektp,@PathVariable("misektp_id") Long misektp_id) {
		misektp.setIdTmp(misektp_id);
		return mstpservice.update(misektp_id);
	}
	
	@PostMapping("save-effchk")
	public boolean saveEffChk(@RequestBody EffChoicek effchk) {
		 return efkservice.create(effchk);
	}
	
//	@PostMapping("update-effchk/{effchk_id}")
//	public boolean updateEffChk(@RequestBody EffChoicek effchk,@PathVariable("misektp_id") Long effchk_id) {
//		effchk.setIdeffchoicek(effchk_id);
//		return efkservice.update(effchk);
//	}
	
	@PostMapping("update-mvt/{cais_id}/{mvt}")
	public boolean updateMouvement(@RequestBody Mouvement mvnt,@PathVariable("cais_id") Long cais_id, @PathVariable("mvt") double mvt, Caissier cais) {
		cais.setIdCaissier(cais_id);
		Caissier caissier = caisservice.findById(cais);
		mvnt.setCaissier(caissier);
		mvnt.setMvt(mvt);
		return mvtservice.update(mvnt);
	}
	
	@PostMapping("save-user/{coderace}")
	public Response saveUser(@RequestBody CaissierDto cais, @PathVariable("coderace") String coderace) {
		 Response res;
		 Partner p = partnerservice.findById(coderace);
		
		 Long profil = cais.getProfil();
		 Profil prof = new Profil();
		 if(profil == 1) {
			prof.setId(1L);
	        prof.setLiblProfil("ADMINISTRATEUR");
		 }
		 else if(profil == 2) {
			prof.setId(2L);
	        prof.setLiblProfil("CAISSIER");
		 }
	
		Caissier c = new Caissier();
		c.setLoginc(cais.getLoginc());
		c.setMdpc(cais.getMdpc());
		c.setNomC(cais.getNomc());
		c.setProfil(prof);
		
		if(p != null) {
			c.setPartner(p);
			res = caisservice.create(c);
			
			if (res != null) {
				
				Airtime airtime = new Airtime();
				airtime.setBalance(0);
				airtime.setCaissier(c);
				airtime.setCredit(0);
				airtime.setDebit(0);
				airtime.setLibelle("");
				airtime.setDate(new Date());
				airtservice.create(airtime);
				
				Mouvement mouv = new Mouvement();
				mouv.setMvt(0);
				mouv.setCaissier(c);
				mvtservice.create(mouv);
				
			}
			else {
				return Response.ok(CaissierDTO.getInstance().error("Pas de partenaire")).build();
			}
		}
		
		return Response.ok(CaissierDTO.getInstance().error("Pas de partenaire")).build();
		
		 
	}
	
	@GetMapping("finduser/{partner}/{login}/{profil}")
	public Response retrieveUser(@PathVariable("partner") String partner,@PathVariable("login") String login, @PathVariable("profil") Long profil) {
		//System.out.println("controller-user: "+login);
		Partner part = new Partner();
		part.setCoderace(partner);
		Partner p = partnerservice.find(part);
		
		Profil prof = new Profil();
		if(profil == 1) {
			prof.setId(1L);
	        prof.setLiblProfil("ADMINISTRATEUR");
		}
		else if(profil == 2) {
			prof.setId(2L);
	        prof.setLiblProfil("CAISSIER");
		}
	
		Caissier c = new Caissier();
		c.setLoginc(login);
		if(p != null) c.setPartner(p);
		c.setProfil(prof);
	//	System.out.println("Login: "+c.getLoginC()+" Partner: "+c.getPartner().getIdPartner());
		Response resp = caisservice.find(c);
	//	System.out.println("Entity: "+resp.getEntity());
		return resp;
	}
	
	@PostMapping(value = "placeslip-keno/{partner}", produces = "application/json")
	public Response registerSlipK(@RequestBody BetTicketK betk, @PathVariable("partner") String partner) {
		boolean ajout;
		double bonusrate = 0;
		double amountbonus;
		Config cfg;
		
		Miset miset = new Miset();
		
		//recherche du code barre
		long barcode;
		barcode = supermanager.searchBarcode(Jeu.K);
		//barcode = Utile.barcodeKenoPool.get(0);
		//Utile.barcodeKenoPool.remove(0);
		
		System.out.println("COntroller-barcode: "+barcode);
		 
		miset.setBarcode(""+barcode);
		miset.setTypeJeu(Jeu.K);
		miset.setSummise(betk.getSummise());
		
		Partner part = partnerservice.findById(partner);
		betk.setBonusCod(1 + part.getBonuskcode());
//		PartnerDTO pdto = (PartnerDTO) res.getEntity();
//		Partner part = pdto.getPart();
		
		Caissier cais = new Caissier();
		cais.setIdCaissier(betk.getCaissier());
		Caissier c = caisservice.findById(cais);
	
		
		Keno ken = new Keno();
		
		ken.setIdKeno(betk.getKeno());
		KenoDTO kdto = (KenoDTO) kenoservice.find(ken).getEntity();
		Keno k = kdto.getKen();
		
		
		//recuperation du bonusrate
		cfg = cfgservice.find(part);
		if(cfg != null) {
			bonusrate = cfg.getBonusrate();
		}
		amountbonus = part.getBonuskamount();
		
		miset.setCoderace(part);
		//ajout dans miset
		ajout = mstservice.create(miset);
		
		
		if(ajout) {
			betk.setIdMiseT(miset.getIdMiseT());
			betk.setBarcode(""+barcode);
			
			Misek misek = new Misek();
			misek.setCaissier(c);
			misek.setHeureMise(betk.getHeureMise());
			misek.setSumMise(betk.getSummise());
			misek.setDateMise(betk.getDateMise());
			misek.setEtatMise(EtatMise.ATTENTE);
			misek.setDrawnumk(betk.getDrawnumk());
			misek.setBonusCod(betk.getBonusCod());
			misek.setMiset(miset);
			misek.setKeno(k);
			misek.setXmulti(betk.getXmulti());
			//ajout misek
			ajout = mskservice.create(misek);
			
			
			if(ajout) {
				int multiplicite = betk.getMultiplicite();
				if(multiplicite > 1) { //si ticket joué sur plusieurs tours
					double gain_min = 0;
					gain_min = (betk.getSummise()/multiplicite) * betk.getCotejeu();
					gain_min = (double)((int)(gain_min*100))/100;
					Misek_temp misektp = new Misek_temp();
					misektp.setMulti(multiplicite);
					misektp.setSumMise(gain_min);
					misektp.setEtatMise(1);
					misektp.setIdmisek(misek.getIdMiseK());
					ajout = mstpservice.create(misektp);
					
					if(!ajout) {
						return Response.ok(BetTicketKDTO.getInstance().error("ERREUR DE CREATION DU TICKET MULTIPLE")).build();
					}
				}
				
				double mtant;
				int event = betk.getEvent();
				mtant = betk.getSummise();
				mtant = mtant / event;
				EffChoicek effchoicek;
				List<EffChoicek> list_efchk = new ArrayList<EffChoicek>();
				
				for(int i=0;i<event;i++){
					effchoicek = new EffChoicek();
					
					effchoicek.setIdparil(betk.getParil());
					effchoicek.setMisek(misek);
					effchoicek.setKchoice(betk.getKchoice());
				//	effchoicek.setIdkeno(betk.getKeno() + i);
					effchoicek.setMtchoix(""+mtant);
					effchoicek.setCote(""+betk.getCotejeu());
					effchoicek.setState(false);
					effchoicek.setDrawnum(betk.getDrawnumk()+i);
					effchoicek.setMtwin(0);
					effchoicek.setImisek(misek.getIdMiseK());
					effchoicek.setDrawresult("");
					
					ajout = efkservice.create(effchoicek);
					
					if(ajout) list_efchk.add(effchoicek);
					//to implement - in case of error
				}
				betk.setList_efchk(list_efchk);
				//mise à jour du credit du caissier
				double mvtprice = mvtservice.findMvt(c);
				Mouvement mvnt = mvtservice.findByCaissier(c);
				mvnt.setMvt(mvtprice - betk.getSummise());
				mvnt.setCaissier(c);
				ajout = mvtservice.update(mvnt);
				if(!ajout) {
					return Response.ok(BetTicketKDTO.getInstance().error("ERREUR DE MISE A JOUR DU SOLDE CAISSE")).build();
				}
				amountbonus +=  mtant*bonusrate;
				part.setBonuskamount(amountbonus);
				part.setBonuskcode(betk.getBonusCod());
				partnerservice.update(part);
			//	partnerservice.update_bonusk(amountbonus, betk.getBonusCod(), part);
				
				return Response.ok(BetTicketKDTO.getInstance().event(betk).sucess("")).build();
			}
			else {
				return Response.ok(BetTicketKDTO.getInstance().error("ERREUR DE CREATION DU TICKET")).build();
			}
		}
		else {
			return Response.ok(BetTicketKDTO.getInstance().error("ERREUR DE CREATION DU BARCODE")).build();
		}
		
	}
	
	@GetMapping("finish-draw/{coderace}")
	public boolean finisDraw(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 System.out.println("COntroler finish");
		 cds.setDraw_finish(Boolean.TRUE);
		// cds.setCountDown(Boolean.TRUE);
		 cds.setGameState(1);
		 return cds.isDraw_finish(); 
	}
	  
	@GetMapping("start-draw/{coderace}")
	public boolean startDraw(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 System.out.println("start draw");
		 cds.setDraw_finish(Boolean.TRUE);
		 cds.setCountDown(Boolean.TRUE);
		 cds.setGameState(1);
		 return cds.isCountDown(); 
	}

	@GetMapping("end-draw/{coderace}")
	public int endDraw(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 System.out.println("end draw");
		 //cds.setDraw_finish(Boolean.TRUE);
		 //cds.setCountDown(Boolean.TRUE);
		 cds.setGameState(4);

		 return cds.getGameState();
	}

	@GetMapping("getstate/{coderace}/{state}")
	public Response retrieveState(@PathVariable("coderace") String coderace, @PathVariable("state") int state) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
			//  cds.setGameState(state);
			  if(state == 1){
				  System.out.println("[CONTROLLER FINISH DRAW STEP 1] "+cds.getCoderace());
				 cds.setDraw_finish(Boolean.TRUE);
				 cds.setDraw(Boolean.FALSE);
				 cds.setCountDown(Boolean.TRUE);
				 cds.setGameState(1);
//				 int drawnum_enCours = cds.getDrawNumk();
//				 Keno _keno = cds.lastDrawNum(cds.getPartner());
//				 _keno.setStarted(1);
//				 _keno.setDrawnumK(drawnum_enCours-1);
//				 supermanager.endDraw(_keno);
			  }
			  else if(state == 2 && cds.getGameState() != 2){
				  System.out.println("[CONTROLLER FINISH DRAW STEP 2] "+cds.getCoderace());
			  	cds.setCanbet(Boolean.FALSE);
			  	cds.setDraw(Boolean.TRUE);
			  	calculateBonus(coderace);
			  	searchCagnotte(coderace);
			  	cds.setGameState(2);
			  	
			  }
			  else if(state == 3 && cds.getGameState() != 3){
				  System.out.println("[CONTROLLER FINISH DRAW STEP 3] "+cds.getCoderace());
			  	cds.setGameState(3);
			  	int num_tirage = 1+cds.getDrawNumk();
		//		System.out.println("DRAW Ajout d'une nouvelle ligne de tirage "+coderace );
				boolean line = supermanager.addKenos(num_tirage, cds.getPartner());
				
		//		System.out.println("Nouvelle ligne de tirage added "+line );
				if(line){
				//	System.out.println("num added "+num_tirage );
					System.out.println("Mise a jour du cycle");
					System.out.println("[CONTROLLER MISE A JOUR DU CYCLE] "+cds.getCoderace());
					cycleAJour(cds);
					cds.setDrawNumk(num_tirage);
				}
			//	cds.setTimeKeno(UtileKeno.timeKeno);
				cds.setCanbet(true);
				//cds.setGameState(3);
				Utile.display_draw.put(cds.getCoderace(), cds);
			  	
			  }
			  else if(state == 4 && cds.getGameState() != 4){
					System.out.println("[CONTROLLER FINISH DRAW STEP 4] "+cds.getCoderace());
				  	cds.setCanbet(Boolean.TRUE);
				  	cds.setDraw(Boolean.FALSE);
				  	cds.setMiseAjour(Boolean.TRUE);
				  	cds.setGameState(4);
				  	
				  	supermanager.endDraw(cds.getDrawNumk(), cds.getPartner());
				  	
				  	if (!Utile.ref.alive()) {
				  	    System.out.println("[Refresh alive 4] "+cds.isMiseAjour());
				  		Utile.ref = new RefreshK(cds,cds.getPartner(),applicationContext);
				  		Utile.ref.start();
				  	}
			  }
			 ob.put("state", cds.getGameState());
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("search-bonus/{coderace}")
	public Response searchBonus(@PathVariable("coderace") String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		// System.out.println("searchBonus finish");
		 BonusSet bs = new BonusSet();
		 bs.setBonusk(cds.getBonus());
		 bs.setCode(cds.getBonuskcode());
		 bs.setMontant(cds.getBonuskamount());
		 bs.setCoderace(cds.getCoderace());
		 System.out.println("getBarcodeCagnot------------: "+cds.getBarcodeCagnot());
		 bs.setBarcode(cds.getBarcodeCagnot());
		 bs.setMise(cds.getMiseCagnot());
		 bs.setNumk(cds.getDrawNumk());
		 return Response.ok(BonusSetDTO.getInstance().event(bs).sucess("")).build();
	}
	
	
	private void calculateBonus(String coderace) {
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 Partner part = new Partner();
		 part.setCoderace(coderace);
		 Partner p = partnerservice.find(part);
		 boolean bonusk = supermanager.manageBonusK(p, cds);
		
		  if(bonusk){
			  cds.setBonus(1);
		  }
		  else{
			  cds.setBonus(0);
			  cds.setBonuskamount(0);
			  cds.setBonuskcode(0);
		  }
	}
	
	private void searchCagnotte(String coderace) {
		
		 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
		 
		 long isCagnot = supermanager.manageCagnotte(cds);
	//	 System.out.println("isCagnot------------------- "+isCagnot+"  °°°°°°°°° "+cds.getBarcodeCagnot()+" °°°°°°°°°° "+cds.getMiseCagnot());
		 if (isCagnot != -1L) {
			 //System.out.println("------------------HERE--------------------6");
			 cds.setIdCagnot(isCagnot);
			 cagnotservice.updateCagnot(isCagnot, cds.getBarcodeCagnot(), cds.getMiseCagnot());
		 }
		 else {
			 cds.setIdCagnot(0L);
		 }
		 
	}
	
	private void cycleAJour(ControlDisplayKeno cds) {
		gmcservice.updatePos(cds.getPos(), cds.getPartner(), Jeu.K);
	}

	@GetMapping("combinaison/{coderace}/{num}")
	public Response retrieveCombi(@PathVariable("coderace") String coderace, @PathVariable("num") int num) {
		 JSONObject ob = new JSONObject(); 
		 List<Misek> listTicket = new ArrayList<Misek>();
		 double miseTotale, sumdist, gMp, gmp, bonusrate;
		 double miseTotale_s, percent; 
		 Map<Miset, Misek> mapTicket = new HashMap<Miset, Misek>();
		 Map<Miset, Misek> map_wait = new HashMap<Miset, Misek>();
		 List<Misek> list_barcode;
		 int refill, xtour, position, tour, roundSize;
		 Long misef, idmisek_max;
		 GameCycle gmc;
		 String arrang;
		 String[] arrangement;
		 boolean dead_round;
		 String RESULT  = "";
	
		 
		 
		 
		 try {
			 ControlDisplayKeno cds = Utile.display_draw.get(coderace);
			 
			 Partner part = new Partner();
			 part.setCoderace(coderace);
			 Partner p = partnerservice.find(part);
			 
			 Config config = cfgservice.find(p);
		     bonusrate = config.getBonusrate() * 100;
		     
		 	 Keno _keno = cds.lastDrawNum(p);
			 
			 refill = cds.getRefill();
			 
			//recherche gmc
			gmc = gmcservice.findByGame(p, Jeu.K);
			
			position = gmc.getPosition();
			tour = gmc.getTour();
			roundSize = gmc.getHitfrequence();
			percent = gmc.getPercent();
			arrang = gmc.getArrangement();
			arrangement = arrang.split("-");
			
			cds.setPos(position);
			cds.setArrangement(gmc.getArrangement());
			cds.setRtp(gmc.getRefundp());
			cds.setTour(tour);
			 //----------------------------------

		//	 if(cds.getTimeKeno() < 10 && !search_draw){

			listTicket.clear(); 
			miseTotale = 0;
			miseTotale_s = 0;
	//		System.out.println("waiting bet partner: "+partner.getCoderace()+"  cds.getDrawNumk: "+cds.getDrawNumk());
			listTicket = mskservice.searchWaitingKenoBet(p, num);
			System.out.println("Controller - waiting bet: "+listTicket.size());
			mapTicket.clear();
			miseTotale = miseTotale + refill;
			int nvlepos = 0;
			boolean cycle_en_cour = true;
			boolean fini = false;
			if(listTicket != null) {
			  for(Misek m : listTicket) {
				  Miset mt = mstservice.findById(m.getMiset().getIdMiseT());
				  misef = m.getIdMiseK();
				 if(cycle_en_cour) {
					 miseTotale = miseTotale + mt.getSummise();
					 xtour = (int) (miseTotale/Params.MISE_MIN);
					 nvlepos = position + xtour;
					 if(nvlepos >= tour) {
						 cycle_en_cour = false;
						 miseTotale_s = miseTotale_s + (nvlepos - tour)*Params.MISE_MIN;
					 }
				 }
				 else {
					 miseTotale_s = miseTotale_s + mt.getSummise();
				 }
				  
				 mapTicket.put(mt, m);
			  }
			}
			//  System.out.println("mapTicket bet: "+mapTicket.size());
			  
				  List<Misek_temp>  misektp = mtpservice.searchWaitingBet();
				  int nombre = misektp.size();
			//	  System.out.println("temporaire "+nombre);
				  
				  if(nombre > 0) {
					  listTicket.clear();
					  for(Misek_temp mktp : misektp) {
						  Misek m = mskservice.searchMiseK(mktp.getIdmisek());
						  listTicket.add(m);
						  
					  }
					  for(Misek m : listTicket) {
						  Miset mt = mstservice.findById(m.getMiset().getIdMiseT());
						  
						 // System.out.println("listTicket bet: "+m.getIdmisek());
						  if(m.getDrawnumk() != cds.getDrawNumk()) {
							  mapTicket.put(mt, m);
						  }
				
					  }
				  }
			 			  
			  gMp = supermanager.verifTicketMax(mapTicket, p);
			  gmp = supermanager.verifTicketMin(mapTicket, p);
			  System.out.println("Gain Max Probable: "+gMp+" Gain Min Probable: "+gmp+" MiseTotale: "+miseTotale);
			  dead_round = false;
			  int xdist = 0;
			  sumdist = 0;
			  if(mapTicket.size() == 0) {
				  dead_round = true;
			  }
			  else {
				 // miseTotale = miseTotale + refill;
				  refill = 0;
				  
				  xtour = (int) (miseTotale/Params.MISE_MIN);
				  refill = (int) (miseTotale%Params.MISE_MIN);
				  System.out.println("refill: "+refill+" xtour Pos: "+xtour);
				 nvlepos = position + xtour;
				 int pp = position + 1;
				 System.out.println("Position: "+position+" Nvelle Pos: "+nvlepos);
				 while(pp <= nvlepos) {
					 for(int l=0;l<arrangement.length;l++) {
						 if(pp < Integer.parseInt(arrangement[l])) break;
						 if(pp == Integer.parseInt(arrangement[l])) {
							 xdist = xdist + 1;
							 break;
						 }
					 }
					 pp++;
				 }
				 						 
				 System.out.println("Percent: "+percent+" RoundSize: "+roundSize+" Tour: "+tour+" Bonus: "+bonusrate);
				 double rounded = supermanager.getRoundPayed(percent, roundSize, tour, bonusrate);
				 System.out.println("xdist: "+xdist+" rounded: "+rounded);
				 sumdist = (miseTotale_s*percent)/100 + xdist * rounded;
				 System.out.println("miseTotale_s: "+miseTotale_s+"   sumdist: "+sumdist);
				if(xdist != 0) {
					sumdist = sumdist + cds.getRtp();
				}
				else{
					if((position > Integer.parseInt(arrangement[arrangement.length-1]) && position <= tour && cds.getRtp() > 0) || (cds.getRtp() >= rounded)) {
						xdist = 1;
						sumdist = sumdist +cds.getRtp();
					}
					else if(miseTotale_s > 0) {
						xdist = 1;
						sumdist = sumdist + cds.getRtp();
					}
//									
				}
				
				position = nvlepos;
				cds.setPos(position);
				//gmcDao.updatePos(nvlepos, idPartner, "K");
				    
			  }
			  
			  
			  
			  
	//		System.out.println("Gain Max Probable: "+sumdist); 
			synchronized (this) {
				cds.setDrawCombik("");
				RESULT = "";
			//	System.out.println("refresh synchro: "+cds.getTimeKeno());
				System.out.println("DeadRound: "+dead_round); 
			//	ControlDisplayKeno control_draw = new ControlDisplayKeno(sumdist,mapTicket,gMp,gmp);
				cds.setSumdist(sumdist);
				cds.setMapTicket(mapTicket);
				cds.setgMp(gMp);
				cds.setGmp(gmp);
			//	do{
					if(dead_round) {
					//	cds.setDrawCombik(cds.buscarDraw());
						RESULT = cds.buscarDraw();
						System.out.println("RESULTAT: "+RESULT); 
						cds.setDrawCombik(RESULT);
					}
					else {
						cds.start();
						while(cds.getDrawCombik().equalsIgnoreCase("")) {
							System.out.println("contoller wait bet "+RefreshK.RESULT);
							Thread.sleep(1000);
						}
						
					}
					
				if(!dead_round) {
				  // gmcDao.updateRfp(cds.getRtp(), idPartner, "K");
				   System.out.println("contoller set pos "+cds.getPos());
			//	   gmcservice.updatePos(cds.getPos(), p, Jeu.K);
				   
				   for(Misek m : listTicket) {
					   if(m.getDrawnumk() != cds.getDrawNumk()) {
						   mtpservice.update(m.getIdMiseK());
					   }
					
				   }
				}
				
				if(cds.getPos() >= tour) {
					idmisek_max = mskservice.ifindId(p);							  
				} 
			  	
	//		}
			
			//MISE A JOUR DE GAME_CYCLE
			
			 if(!dead_round) {
				 gmcservice.updateRfp(cds.getRtp(), p, Jeu.K);
			 }
			 
			 String str_draw = "";					   
//				cds.setDrawCombik(RefreshK.RESULT);
				
	//		System.out.println("cds.DrawCombik(Refresh.RESULT) "+cds.getDrawCombik());
           
			str_draw = cds.getDrawCombik();
		//	Keno keno = new Keno();
			_keno.setDrawnumbK(cds.getDrawCombik());
			_keno.setMultiplicateur(cds.getMultiplix());
			_keno.setHeureTirage(DateFormatUtils.format(new Date(), "dd-MM-yyyy,HH:mm"));
			//keno.setHeureTirage(new SimpleDateFormat("dd/MM/yyyy,HH:mm", Locale.FRANCE).format(new Date()));
			_keno.setDrawnumK(cds.getDrawNumk());
			_keno.setCoderace(p.getCoderace());
	//		System.out.println("Keno mis a jour "+_keno.getDrawnumK()+" multi: "+_keno.getMultiplicateur()+" coderace: "+_keno.getCoderace()+" id: "+_keno.getIdKeno());
			int ligne = supermanager.addUpKeno(_keno);
	//		System.out.println("Keno mis a jour "+ligne);
			cds.setHeureTirage(_keno.getHeureTirage());
			
			cds.setBonuskamount(p.getBonuskamount());
			
			Utile.display_draw.put(cds.getCoderace(), cds);
			
			//		}
			 //----------------------------------------	
			 ob.put("combinaison", cds.getDrawCombik());
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
			 
			 
			}
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("stat-misek/{coderace}/{debut}/{fin}")
	public Response statsMisek(@PathVariable("coderace") String coderace, @PathVariable("debut") long t1, @PathVariable("fin") long t2) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 MisekDto mdto;
			 p = partnerservice.findById(coderace);
			 //System.out.println("[stat-misek] "+p);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 List<Misek> lm = mskservice.getMisek(""+t1, ""+t2, p);
			 //System.out.println("[stat-misek-lm.size()] "+lm.size());
			 List<MisekDto> lmdto = new ArrayList<>(lm.size());
			 for (Misek m : lm) {
				 mdto = new MisekDto();
				 mdto = mdto.transToMisek(m);
				 lmdto.add(mdto);
			 }
			 
			 ob.put("statK", lmdto);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("turnover/{coderace}")
	public Response getTurnover(@PathVariable("coderace") String coderace) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 CaissierDto mdto = new CaissierDto();
			 p = partnerservice.findById(coderace);
			// System.out.println("[stat-misek] "+p);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 List<Caissier> lm = caisservice.findByPartner(p);
			// System.out.println("[stat-misek-lm.size()] "+lm.size());
			 List<CaissierDto> lcdto = new ArrayList<>(lm.size());
			 for (Caissier m : lm) {
				 mdto = new CaissierDto();
				 mdto = mdto.transToCaissier(m);
				 lcdto.add(mdto);
			 }
			 
			 ob.put("turnover", lcdto);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("gamecycle/{coderace}")
	public Response getCycle(@PathVariable("coderace") String coderace) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 GameCycleDto mdto = new GameCycleDto();
			 p = partnerservice.findById(coderace);
			 //System.out.println("[stat-misek] "+p);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 List<GameCycle> lm = gmcservice.find(p);
			 List<GameCycleDto> lcdto = new ArrayList<>(lm.size());
			 for (GameCycle m : lm) {
				 mdto = new GameCycleDto();
				 mdto = mdto.transToGameCycle(m);
				 lcdto.add(mdto);
			 }
			 ob.put("cycle", lcdto);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("findmaxMisek/{coderace}")
	public Response getMaxMisek(@PathVariable("coderace") String coderace) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 Long lm = mskservice.ifindId(p);
			// System.out.println("[stat-misek-lm.size()] "+lm);
			
			 ob.put("max", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("totalMisek/{coderace}/{m1}/{m2}")
	public Response getSumMisek(@PathVariable("coderace") String coderace, @PathVariable("m1") Long m1, @PathVariable("m2") Long m2) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 double lm = mskservice.getMiseKCycle(m1, m2, p);
		//	 System.out.println("[stat-misek-lm.size()] "+lm);
			
			 ob.put("sumMise", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("totalWin/{coderace}/{m1}/{m2}")
	public Response getSumWin(@PathVariable("coderace") String coderace, @PathVariable("m1") Long m1, @PathVariable("m2") Long m2) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 double lm = mskservice.getMiseKCycleWin(m1, m2, p);
		//	 System.out.println("[stat-misek-lm.size()] "+lm);
			
			 ob.put("sumWin", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("misek/{m1}")
	public Response getMisek(@PathVariable("m1") Long m1) {
		 JSONObject ob = new JSONObject(); 
		 List<MisekDto> lm = new ArrayList<>(1);
		// System.out.println("m1 "+m1);
		 try {

			 MisekDto miskdto = new MisekDto();
			 Misek misk = mskservice.searchMiseK(m1);
			 if (misk == null) {
				 return Response.ok(ResponseData.getInstance().error("ticket absent")).build();
			 }
			// System.out.println("misk "+misk);
			 
			 miskdto = miskdto.transToMisek(misk);
			 lm.add(miskdto);
			 ob.put("misek", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("jackpot/{coderace}/{m1}/{m2}")
	public Response getjackpot(@PathVariable("coderace") String coderace, @PathVariable("m1") Long m1, @PathVariable("m2") Long m2) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			
			 double lm = kenoservice.findTotalBonusAmount(m1, m2, p);
			 ob.put("jackpot", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@PostMapping("ugamecycle/{coderace}")
	public Response updateCycle(@RequestBody GameCycleDto gm, @PathVariable("coderace") String coderace) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 
			if (StringUtils.equals(gm.getJeu(), "K")) {
				Jeu j = Jeu.K;
				
				int nbe = gmcservice.updateArchive(gm.getCurr_percent(), gm.getDate_fin(), gm.getArchive(), 
						 p, j, gm.getMisef(), gm.getStake(), gm.getPayout(), gm.getJkpt());
				
				ob.put("nbre", nbe);
				String eve = Utile.convertJsonToString(ob);
				return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
			}
			else {
				return Response.ok(ResponseData.getInstance().error("")).build();
			}
			 
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@PostMapping("gamecycle/{coderace}")
	public Response createCycle(@RequestBody GameCycleDto gm, @PathVariable("coderace") String coderace) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 GameCycle gmc = new GameCycle();
			 gmc.setPercent(gm.getPercent());
			 gmc.setTour(gm.getTour());
			 gmc.setHitfrequence(gm.getHitfrequence());
			 gmc.setRefundp(gm.getRefundp());
			 gmc.setArchive(gm.getArchive());
			 gmc.setArrangement(gm.getArrangement());
			 gmc.setDate_fin(gm.getDate_fin());
			 gmc.setJeu(Jeu.K);
			 gmc.setJkpt(gm.getJkpt());
			 gmc.setMise(gm.getMise());
			 gmc.setMisef(gm.getMisef());
			 gmc.setPosition(gm.getPosition());
			 
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 gmc.setPartner(p);
			 
			 boolean resp = gmcservice.create(gmc);
			 
			 
			if (resp) {
				ob.put("resp", resp);
				String eve = Utile.convertJsonToString(ob);
				return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
			}
			else {
				return Response.ok(ResponseData.getInstance().error("")).build();
			}
			 
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("miset/{coderace}/{t1}/{t2}")
	public Response getMiset(@PathVariable("coderace") String coderace, @PathVariable("t1") Long t1, @PathVariable("t2") Long t2) {
		 JSONObject ob = new JSONObject(); 
		 List<MisekDto> lm = new ArrayList<>();
		// System.out.println("m1 "+m1);
		 try {
			 
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 List<AdminTicketDto> ladmin = mskservice.getMisekt(String.valueOf(t1), String.valueOf(t2), p);
			// System.out.println("ladmin.size(): "+ladmin.size());
//			 for (AdminTicketDto m : ladmin) {
//				 System.out.println("AdminTicketDto: "+m);
//			 }
			 
			 if (ladmin != null) {
				 ob.put("tickets", ladmin);
				 String eve = Utile.convertJsonToString(ob);
				 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
			 }
			
		 } catch (Exception e) {
			e.printStackTrace();
			
		 }
		 
		 return Response.ok(ResponseData.getInstance().error("")).build();
   }
	
	@GetMapping("versements/{coderace}/{t1}/{t2}")
	public Response getVersement(@PathVariable("coderace") String coderace, @PathVariable("t1") Long t1,  @PathVariable("t2") Long t2) {
		JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 List<Versement> lm = verservice.getVersement(String.valueOf(t1), String.valueOf(t2), p);
			 for (Versement v : lm) {
				 v.setDatV(v.getDatV().replace('/', '-').substring(0,10));
//				 Caissier c = v.getCaissier();
//				 c.setMdpc("***");
				 v.setCaissier(null);
			 }
			
			 ob.put("vers", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
		 
		
	}
	
	@GetMapping("airtimes/{coderace}/{cais}/{dat1}/{dat2}")
	public Response getcumul(@PathVariable("coderace") String coderace, @PathVariable("cais") String cais, @PathVariable("dat1") String dat1, @PathVariable("dat2") String dat2) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 Caissier c = new Caissier();
			 c = caisservice.findByLoginIdPartner(cais, p);

			 if (c == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			
			 double lm = airtservice.findCumulCredit(c, dat1+" 00:00:00", dat2+" 23:59:00");
			 ob.put("cumul", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("miserk/{coderace}/{cais}/{dat1}/{dat2}")
	public Response getAllmiserk(@PathVariable("coderace") String coderace, @PathVariable("cais") String cais, @PathVariable("dat1") Long dat1, @PathVariable("dat2") Long dat2) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 Caissier c = new Caissier();
			 c = caisservice.findByLoginIdPartner(cais, p);
			 if (c == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			
			 double lm = mskservice.getMiseRK(c, String.valueOf(dat1), String.valueOf(dat2));
			 ob.put("miserk", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("versemens/{coderace}/{cais}/{dat1}/{dat2}")
	public Response getvers(@PathVariable("coderace") String coderace, @PathVariable("cais") String cais, @PathVariable("dat1") Long dat1, @PathVariable("dat2") Long dat2) {
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 Caissier c = new Caissier();
			 c = caisservice.findByLoginIdPartner(cais, p);
			 if (c == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			
			 double lm = verservice.getVersements(c, dat1, dat2);
			 ob.put("vers", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
   }
	
	@GetMapping("credit/{coderace}/{cais}/{dat1}/{dat2}")
	public Response getallAirtime(@PathVariable("coderace") String coderace, @PathVariable("cais") String cais, @PathVariable("dat1") String dat1, @PathVariable("dat2") String dat2) {
		JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 Caissier c = new Caissier();
			 c = caisservice.findByLoginIdPartner(cais, p);

			 if (c == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 //System.out.println("dat1: "+dat1+" dat2: "+dat2);
			 List<Airtime> lm = airtservice.find(c, dat1+" 00:00:00", dat2+" 23:59:00");
			 System.out.println("Airtime: "+lm.size());
			 ob.put("voucher", lm);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
		 
		
	}
	
	@PostMapping("cagnotte/{coderace}")
	public Response createCagnotte(@RequestBody Cagnotte gm, @PathVariable("coderace") String coderace) {

		 try {
			Partner p = null;
			p = partnerservice.findById(coderace);
			if (p == null) return Response.ok(CagnotteDTO.getInstance().error("NOT FOUND")).build();
			gm.setPartner(p);
			if(cagnotservice.create(gm)) {
				 return Response.ok(CagnotteDTO.getInstance().event(gm).sucess("")).build();
			}
			else {
				return Response.ok(CagnotteDTO.getInstance().error("NOT FOUND")).build();
			}
			 
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(CagnotteDTO.getInstance().error("NOT FOUND")).build();
		 }
   }
	
	@GetMapping("bonusk-cf/{coderace}/{bnkmin}/{bnkmax}/{rate}")
	public Response bonuskcfg(@PathVariable("coderace") String coderace, @PathVariable("bnkmin") double bnkmin, 
			@PathVariable("bnkmax") double bnkmax, @PathVariable("rate") double rate) {
		System.out.println("bnkmin: "+bnkmin);
		 JSONObject ob = new JSONObject(); 
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null) return Response.ok(ResponseData.getInstance().error("NOT FOUND")).build();
				
			 int cfgBonus = configservice.updateBonusK(rate, bnkmin, bnkmax, p);
			 ob.put("cfgBonus", cfgBonus);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
	}
	
	@GetMapping("bonus/{coderace}")
	public Response getBonus(@PathVariable("coderace") String coderace) {
		JSONObject ob = new JSONObject(); 
		
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 List<Keno> lm = kenoservice.getLastKBonus(p);
			 List<KenoRes> lknr = new ArrayList<>(lm.size());
			 KenoRes kenr = new KenoRes();
			 for (Keno k : lm) {
				 kenr = new KenoRes();
				 kenr.setBonuscod(k.getBonusKcod());
				 kenr.setBonusKamount(k.getBonusKamount());
				 kenr.setHeureTirage(k.getHeureTirage().replace(':', 'h').replace(',', '-'));
				 kenr.setDrawnumbK(k.getDrawnumbK());
				 kenr.setDrawnumK(k.getDrawnumK());
				 kenr.setMultiplicateur(k.getMultiplicateur());
				 kenr.setStr_draw_combi(k.getDrawnumbK());
				 lknr.add(kenr);
			 }
			 
			 ob.put("bonus", lknr);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
		 
		
	}
	
	@GetMapping("last-draw/{coderace}")
	public Response getLdraw(@PathVariable("coderace") String coderace) {
		JSONObject ob = new JSONObject(); 
		
		 try {
			 Partner p = null;
			 p = partnerservice.findById(coderace);
			 if (p == null ) return Response.ok(ResponseData.getInstance().error("")).build();
			 
			 List<Keno> lm = kenoservice.getLastKdraw(p);
			 List<KenoRes> lknr = new ArrayList<>(lm.size());
			 KenoRes kenr = new KenoRes();
			 
			 for (Keno k : lm) {
				 kenr = new KenoRes();
				 kenr.setBonuscod(k.getBonusKcod());
				 kenr.setBonusKamount(k.getBonusKamount());
				 kenr.setHeureTirage(k.getHeureTirage().replace(':', 'h').replace(',', '-'));
				 kenr.setDrawnumbK(k.getDrawnumbK());
				 kenr.setDrawnumK(k.getDrawnumK());
				 kenr.setMultiplicateur(k.getMultiplicateur());
				 kenr.setStr_draw_combi(k.getDrawnumbK());
				 lknr.add(kenr);
			 }
			 
			 ob.put("bonus", lknr);
			 String eve = Utile.convertJsonToString(ob);
			 return Response.ok(ResponseData.getInstance().event(eve).sucess("")).build();
		 } catch (Exception e) {
			e.printStackTrace();
			return Response.ok(ResponseData.getInstance().error(e.getMessage())).build();
		 }
		 
		
	}
	
//	 //The function receives a GET request, processes it and gives back a list of Todo as a response.
//    @GetMapping
//    public ResponseEntity<List<Todo>> getAllTodos() {
//        List<Todo> todos = todoService.getTodos();
//        return new ResponseEntity<>(todos, HttpStatus.OK);
//    }
//    //The function receives a GET request, processes it, and gives back a list of Todo as a response.
//    @GetMapping({"/{todoId}"})
//    public ResponseEntity<Todo> getTodo(@PathVariable Long todoId) {
//        return new ResponseEntity<>(todoService.getTodoById(todoId), HttpStatus.OK);
//    }
//    //The function receives a POST request, processes it, creates a new Todo and saves it to the database, and returns a resource link to the created todo.    @PostMapping
//    public ResponseEntity<Todo> saveTodo(@RequestBody Todo todo) {
//        Todo todo1 = todoService.insert(todo);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("todo", "/api/v1/todo/" + todo1.getId().toString());
//        return new ResponseEntity<>(todo1, httpHeaders, HttpStatus.CREATED);
//    }
//    //The function receives a PUT request, updates the Todo with the specified Id and returns the updated Todo
//    @PutMapping({"/{todoId}"})
//    public ResponseEntity<Todo> updateTodo(@PathVariable("todoId") Long todoId, @RequestBody Todo todo) {
//        todoService.updateTodo(todoId, todo);
//        return new ResponseEntity<>(todoService.getTodoById(todoId), HttpStatus.OK);
//    }
//    //The function receives a DELETE request, deletes the Todo with the specified Id.
//    @DeleteMapping({"/{todoId}"})
//    public ResponseEntity<Todo> deleteTodo(@PathVariable("todoId") Long todoId) {
//        todoService.deleteTodo(todoId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
