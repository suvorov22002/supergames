package com.pyramid.dev.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.pyramid.dev.business.RefreshK;
import com.pyramid.dev.business.SuperGameManager;
import com.pyramid.dev.exception.DAOException;
import com.pyramid.dev.model.Keno;
import com.pyramid.dev.model.Misek;
import com.pyramid.dev.model.Miset;
import com.pyramid.dev.model.Partner;
import com.pyramid.dev.service.KenoService;

@Component
public class ControlDisplayKeno implements Runnable{
		
	
	SuperGameManager supermanager;
	
	
	private KenoService kenoservice;
	
	private String coderace;
	private Partner partner;
	private  int timeKeno;
	private int gameState = 1; //etat du jeu 
	private String str_draw_combi = "";
	private  boolean isDraw = false; //controle si le tirage est en cours
	private  boolean miseAjour = false; //controle si le tirage est en cours
	private  boolean countDown = false; //controle le compteur de temps avant tirage
	private  String drawCombik = "";
	private  int drawNumk = 0;
	private int drawCount = 25;
	private int rang;
	private int bonus = 0;
	private int bonuskcode;
	private int end = 0;
	private int pos;
	private double bonusrate;
	private double rtp;
	private String multiplix;
	private String arrangement = "";
	private int tour;
	private boolean canbet = true; //mise possible
	private double sumdist;
	private Map<Miset, Misek> mapTicket;
	private double gMp;
	private double gmp;
	private Map<String, String> rescue;
	private double bonuskamount;
	private double bonusPamount;
	private String heureTirage;
	private boolean draw_finish = false;
	private int refill;
	private List<String> allDraw;
	private Map<String, String> allDrawNumOdds;
	private Long barcodeCagnot = 0L;
	private Long miseCagnot = 0L;
	private Long idCagnot = 0L;

	
	private static Thread thread;
	
	public ControlDisplayKeno(String coderace) {
		this.coderace = coderace;
	}

	public ControlDisplayKeno() {
		super();
	}
	
	
	
	public ControlDisplayKeno(ApplicationContext applicationContext) {
		super();
		this.kenoservice = (KenoService)applicationContext.getBean(KenoService.class);
		this.supermanager = (SuperGameManager)applicationContext.getBean(SuperGameManager.class);
	}

	public void run() {
		RefreshK.RESULT = buscarDraw(sumdist,mapTicket,gMp,gmp);
		this.drawCombik = RefreshK.RESULT;
		System.out.println("Refresh.RESULT "+this.drawCombik);
		setDrawCombik(RefreshK.RESULT);
	}

	public String getCoderace() {
		return coderace;
	}

	public void setCoderace(String coderace) {
		this.coderace = coderace;
	}

	public int getTimeKeno() {
		return timeKeno;
	}

	public void setTimeKeno(int timeKeno) {
		this.timeKeno = timeKeno;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public int getDrawNumk() {
		return drawNumk;
	}

	public void setDrawNumk(int drawNumk) {
		this.drawNumk = drawNumk;
	}

	public int getDrawCount() {
		return drawCount;
	}

	public void setDrawCount(int drawCount) {
		this.drawCount = drawCount;
	}

	public int getRang() {
		return rang;
	}

	public void setRang(int rang) {
		this.rang = rang;
	}
	
	
	
	public boolean isDraw() {
		return isDraw;
	}

	public void setDraw(boolean isDraw) {
		this.isDraw = isDraw;
	}

	public int getRefill() {
		return refill;
	}

	public void setRefill(int refill) {
		this.refill = refill;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public double getRtp() {
		return rtp;
	}

	public void setRtp(double rtp) {
		this.rtp = rtp;
	}

	public String getArrangement() {
		return arrangement;
	}

	public void setArrangement(String arrangement) {
		this.arrangement = arrangement;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(int tour) {
		this.tour = tour;
	}

	public boolean isCountDown() {
		return countDown;
	}

	public void setCountDown(boolean countDown) {
		this.countDown = countDown;
	}

	public boolean isCanbet() {
		return canbet;
	}

	public void setCanbet(boolean canbet) {
		this.canbet = canbet;
	}
	
	public String getDrawCombik() {
		return drawCombik;
	}

	public void setDrawCombik(String drawCombik) {
		this.drawCombik = drawCombik;
	}
	
	public double getSumdist() {
		return sumdist;
	}

	public void setSumdist(double sumdist) {
		this.sumdist = sumdist;
	}

	public Map<Miset, Misek> getMapTicket() {
		return mapTicket;
	}

	public void setMapTicket(Map<Miset, Misek> mapTicket) {
		this.mapTicket = mapTicket;
	}

	public double getgMp() {
		return gMp;
	}

	public void setgMp(double gMp) {
		this.gMp = gMp;
	}

	public double getGmp() {
		return gmp;
	}

	public void setGmp(double gmp) {
		this.gmp = gmp;
	}
	
	public String getMultiplix() {
		return multiplix;
	}

	public void setMultiplix(String multiplix) {
		this.multiplix = multiplix;
	}
	
	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public double getBonusrate() {
		return bonusrate;
	}

	public void setBonusrate(double bonusrate) {
		this.bonusrate = bonusrate;
	}

	public String getStr_draw_combi() {
		return str_draw_combi;
	}

	public void setStr_draw_combi(String str_draw_combi) {
		this.str_draw_combi = str_draw_combi;
	}
	

	public double getBonuskamount() {
		return bonuskamount;
	}

	public void setBonuskamount(double bonuskamount) {
		this.bonuskamount = bonuskamount;
	}

	public double getBonusPamount() {
		return bonusPamount;
	}

	public void setBonusPamount(double bonusPamount) {
		this.bonusPamount = bonusPamount;
	}
	

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	public String getHeureTirage() {
		return heureTirage;
	}

	public void setHeureTirage(String heureTirage) {
		this.heureTirage = heureTirage;
	}
	
	

	public boolean isDraw_finish() {
		return draw_finish;
	}

	public void setDraw_finish(boolean draw_finish) {
		this.draw_finish = draw_finish;
	}
	
	public int getBonuskcode() {
		return bonuskcode;
	}

	public void setBonuskcode(int bonuskcode) {
		this.bonuskcode = bonuskcode;
	}
	

	public boolean isMiseAjour() {
		return miseAjour;
	}

	public void setMiseAjour(boolean miseAjour) {
		this.miseAjour = miseAjour;
	}
	
	public Long getBarcodeCagnot() {
		return barcodeCagnot;
	}

	public void setBarcodeCagnot(Long barcodeCagnot) {
		this.barcodeCagnot = barcodeCagnot;
	}

	public Long getMiseCagnot() {
		return miseCagnot;
	}

	public void setMiseCagnot(Long miseCagnot) {
		this.miseCagnot = miseCagnot;
	}
	
	public Long getIdCagnot() {
		return idCagnot;
	}

	public void setIdCagnot(Long idCagnot) {
		this.idCagnot = idCagnot;
	}

	public List<String> getAllDraw() {
		return allDraw;
	}

	public void setAllDraw(List<String> allDraw) {
		this.allDraw = allDraw;
	}

	public Map<String, String> getAllDrawNumOdds() {
		return allDrawNumOdds;
	}

	public void setAllDrawNumOdds(Map<String, String> allDrawNumOdds) {
		this.allDrawNumOdds = allDrawNumOdds;
	}
	
	public Map<String, String> getRescue() {
		return rescue;
	}

	public void setRescue(Map<String, String> rescue) {
		this.rescue = rescue;
	}

	public Keno lastDrawNum(Partner partner){
		Keno keno = null;
		try {
			keno = kenoservice.find_Max_draw(partner);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return keno;
	}
	
    private String buscarDraw(double sumdist,Map<Miset, Misek> mapTicket,double gMp,double gmp){
		
		List<String> combis = new ArrayList<String>();
    	List<String> combi = new ArrayList<String>();
    	double sumDistTotale = 0;
    	double n;
    	boolean trouve = false;
    	String str = ""; 
    	String _str = "";
	    System.out.println("GMP: "+gMp+" GmP: "+gmp+" DIST: "+sumdist);	
	//    System.out.println("mapTicket: "+mapTicket.size());	
	//    if(sumdist <= gmp || sumdist==0) {
	    	
	    if(sumdist <= 0) {
    		System.out.println("----- NOTHING TO DISTIBUTE -- OR 2KNIFESIDES: "+ gmp);
    		if (gmp != 0) {
	    		multiplix = Utile.multiplicateur[3]; 
	    	}
    		
    		do {
    			combis.clear();
    			combi.clear();
    			str = "";
    			for(int i=1;i<81;i++)
    	    		combis.add(""+i);
    	    	for(int j=0;j<80;j++){
    	    		int index = Utile.generate(combis.size());
    	    		combi.add(combis.get(index));
    	    		combis.remove(index);
    	    	}
    	    	
    	    	
    	    	for(int k=0;k<19;k++){
    	    		str = str + combi.get(k) + "-";
    	    	}
    	    	str = str + combi.get(19);
    	    	
    	    	if (gmp == 0) {
    	    		n = Utile.generate(3);
        			multiplix = Utile.multiplicateur[(int)n]; 
    	    	}
    	    	
    			sumDistTotale = supermanager.verifTicketSum(mapTicket, coderace, str, multiplix);
    			_str = str;
    			if(gmp != 0) { //two sides knife
    				if(sumDistTotale == gmp) {
    					//System.out.println("this.rtpAAA "+this.rtp);
        				trouve = true;
        				this.rtp = this.rtp + sumdist - sumDistTotale;
        				
        			}
    			}
    			else {
    				
    				if(sumDistTotale == 0) {
    					trouve = true;
    					//this.rtp = sumdist;
    				}
    			}
    			
    		}
    		while(!trouve);
    		//System.out.println("this.rtp "+this.rtp);
    	}    
	  //  else if(sumdist > gMp) {
	    else if(sumdist > 0) {
	    		System.out.println("HERE 1");	
	    		long timeBefore = System.currentTimeMillis();
	    		double max = 0;
	    		double min = gMp;
	    		long timeAfter;
	    		boolean fail = true;
	    		
	    		do {
	    			combis.clear();
	    			combi.clear();
	    			str = "";
	    			for(int i=1;i<81;i++)
	    	    		combis.add(""+i);
	    	    	for(int j=0;j<80;j++){
	    	    		int index = Utile.generate(combis.size());
	    	    		combi.add(combis.get(index));
	    	    		combis.remove(index);
	    	    	}
	    	    	
	    	    	
	    	    	for(int k=0;k<19;k++){
	    	    		str = str + combi.get(k) + "-";
	    	    	}
	    	    	str = str + combi.get(19);
	    	    	n = Utile.generate(4);
	    			multiplix = Utile.multiplicateur[(int)n]; 
	    			//System.out.println("sumDistTotale coderace "+str);
	    			sumDistTotale = supermanager.verifTicketSum(mapTicket, coderace, str, multiplix);
	    			
	    			if (gmp != 0) {
	    				System.out.println("gmp != 0 "+gmp+ " sumdist: "+sumdist);
	    				if(sumdist <= gmp) {
	    					if (sumDistTotale == gmp) {
	    						trouve = true;
	    	    				this.rtp = sumdist - max;
	    	    				timeAfter = System.currentTimeMillis() - timeBefore;
	    	    				_str = str;
	    	    				sumDistTotale = max;
	    					}
	    					// wha I should do
	    					
	    				}
	    				else {
	    					timeAfter = System.currentTimeMillis() - timeBefore;
	    					if(sumDistTotale <= sumdist && sumDistTotale >= max) {
	    						_str = str;
	    	    				max = sumDistTotale;
	    					}
	    					
	    					if(timeAfter > 8000) {
	    	    				trouve = true;
	    	    				this.rtp = sumdist - max;
	    	    				System.out.println("timeAfter "+timeAfter+" max: "+max);
	    	    				sumDistTotale = max;
	    	    			}  
	    				}
	    			}
	    			else {
	    			//	System.out.println("gmp = 0 "+gmp+ " sumdist: "+sumDistTotale);
	    				if (sumDistTotale == 0 && fail) { // tirage par defaut
	    					_str = str;
	    					fail = false;
	    				}
	    				timeAfter = System.currentTimeMillis() - timeBefore;
    					if(sumDistTotale <= sumdist && sumDistTotale >= max) {
    						_str = str;
    	    				max = sumDistTotale;
    					}
    					
    					if(timeAfter > 8000) {
    	    				trouve = true;
    	    				this.rtp = sumdist - max;
    	    				System.out.println("timeAfter "+timeAfter+" max: "+max);
    	    				sumDistTotale = max;
    	    			}  
	    			}	
	    		}
	    		while(!trouve);
	    		System.out.println("sumDistTotale "+sumDistTotale);
	    		
	    	}	
	    	this.rtp = (double)((int)(this.rtp*100))/100;
	    	//System.out.println("DISTRIBUTION: "+sumDistTotale+" ReFund: "+this.rtp);
	    setMultiplix(multiplix);
		return _str;
	}

	public String buscarDraw() {
		List<String> combis = new ArrayList<String>();
		List<String> combi = new ArrayList<String>();
		for(int i=1;i<81;i++)
	    		combis.add(""+i);
	    	for(int j=0;j<80;j++){
	    		int index = Utile.generate(combis.size());
	    		combi.add(combis.get(index));
	    		combis.remove(index);
	    	}
	    	
	    	String str = "";
	    	for(int k=0;k<19;k++){
	    		str = str + combi.get(k) + "-";
	    	}
	    	str = str + combi.get(19);
	    	double n = Utile.generate(4);
			multiplix = Utile.multiplicateur[(int)n]; 
			setMultiplix(multiplix);
			return str;
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
	
}
