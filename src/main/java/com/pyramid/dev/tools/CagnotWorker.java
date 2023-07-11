package com.pyramid.dev.tools;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.pyramid.dev.model.Cagnotte;
import com.pyramid.dev.service.CagnotteService;

public class CagnotWorker {
	
	private static TimerTask task;
	private static java.util.Timer timer;
	
	private static ControlDisplayKeno cds;
	
	private static CagnotteService cagnotservice;
	
	
	
	public CagnotWorker(ControlDisplayKeno cds, CagnotteService cagnotservice) {
		super();
		this.cds = cds;
		this.cagnotservice = cagnotservice;
	}


	public static void initChecking(){
		try{
			
			if(task != null) task.cancel();
			if(timer != null) timer.cancel();
				

			task = new TimerTask(){
				@Override
				public void run(){
					
					try {
					//	if(Params.mapHeure.containsKey(new SimpleDateFormat("HH").format(new Date()))) {
							process(cds);
					//	}
							
					}catch(Exception e){
						//e.printStackTrace();
					}
				}	
			};

			timer = new java.util.Timer(true);
			int sec = 60;
			int min = 15;
			timer.schedule(task, DateUtils.addMinutes(new Date(), 5) , min*sec*1000);	

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
		
	/**
	 * 
	 */
	public static void runChecking(){
		try{
			
			if(task != null) task.cancel();
			if(timer != null) timer.cancel();
			
			task = new TimerTask(){
				@Override
				public void run(){
					
					try {
						//if(Params.mapHeure.containsKey(new SimpleDateFormat("HH").format(new Date()))) {
							System.out.println("SEARCHING CAGNOTTE");
							process(cds);
						//}
							
					}catch(Exception e){
						e.printStackTrace();
					}
				}	
			};

			timer = new java.util.Timer(true);
			int sec = 60;
			int min = 1;
			timer.schedule(task, DateUtils.addSeconds(new Date(), 5) , min*sec*1000);	

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 */
	public static void cancelChecking(){
		try{
			
			if(task != null) task.cancel();
			if(timer != null) timer.cancel();
						
			task = null;
			timer = null;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void process(ControlDisplayKeno cds) {
		
//	//	System.out.println(cagnotservice + " __ " +cds);
//    	Cagnotte cagnot = cagnotservice.find(cds.getPartner());
//    	
//    	if (cagnot == null ) return;
//    	String heur = cagnot.getHeur() + ":00";
//    	Date dat = cagnot.getDay();
//    	String[] li_dat = dat.split("-");
//    	Collections.reverse(Arrays.asList(li_dat));
//    	dat = "";
//    	for (String ss : li_dat) {
//    		dat = dat + "/" + ss;
//    	}
//    	
//    	dat = dat.substring(1);
//    //	System.out.println("dat __ " +dat + "," + heur);
//    	
//    	String txtDate = new SimpleDateFormat("dd/MM/yyyy,H:m:s", Locale.FRANCE).format(new Date());
//			long tms0,tms;
//			try {
//				tms = Utile.givetimestamp(dat + "," + heur);
//				tms0 = Utile.givetimestamp(txtDate);
//				
//				if(tms < tms0) {
//					// mise à jour de la tombée de la cagnotte
//					//System.out.println("- "+cagnot.getLot()+"\n- "+cagnot.getJeu());
//					if (cagnot.getJeu() == null || StringUtils.isBlank(cagnot.getJeu())) {
//						cagnot.setJeu("FALL");
//						cagnotservice.update(cagnot);
//					}
//					else if (cagnot.getBarcode() == 0){
//						System.out.println("NOT PAID");
//					}
//					else {
//						System.out.println("ALREADY PAID");
//					}
//	
//				}
//			}
//			catch(Exception e) {
//				e.printStackTrace();
//			}
//    	
		return;
	}
	
}
