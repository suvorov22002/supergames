package com.pyramid.dev.responsecode;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
//import javax.inject.Singleton;


import org.springframework.stereotype.Component;

@Component
//@Singleton
public class ResponseHolder {
	public static String CommissionError = "019";
	public static String SUCESS = "200";
	public static String SERVICE_UNAVAILABLE = "503";
	public static String EXPECTATION_FAILED = "502";
	public static String INTERNAL_SERVER_ERROR = "503";
	public static String INVALID_TRANSFER = "015";
	public static String INVALID_FEES_TRANSFER = "020";
	public static String FAIL = "000";
	public static String TCKCHXERR = "030";
	public static String TCKINCON = "031";
	public static String TCKNRECON = "032";
	public static String TCKALRPAID = "033";
	public static String TCKNEVAL = "034";
	public static String TCKNREG = "035";
	
	
	public static Map<String, String> mapMessage = new HashMap<String, String>();
	
	public ResponseHolder(){
		
	}

	@PostConstruct
	public void PostConstruct(){
		
		mapMessage.put("404","NOT_FOUND");
		mapMessage.put("404","NOT_FOUND");
		mapMessage.put("503","SERVICE_UNAVAILABLE");
		mapMessage.put("502","Bad Gateway");
		mapMessage.put("504","Gateway Timeout");
		mapMessage.put("505","HTTP Version Not Supported");
		mapMessage.put("402","Payment Required");
		mapMessage.put("401","Unauthorized");
		mapMessage.put("200","OK");
		mapMessage.put("000","TRANSFER_NOT_FOUND");
		mapMessage.put("100","KO");
//		mapMessage.put("001","BLOCKING_THRESHOLD");
//		mapMessage.put("002","SENDER_LIMIT_BLOCKING");
//		mapMessage.put("003","SENDER_DAY_BLOCKING");
//		mapMessage.put("004","ACCESS_LIMITATION_ERROR");
//		mapMessage.put("005","COUNTRY_NOT_FOUND");
//		mapMessage.put("006","EMPTY_PARTNER_ID");
//		mapMessage.put("007","SUPER_AGENT_INEXISTANT");
//		mapMessage.put("008","AGENT_INEXISTANT");
//		mapMessage.put("009","LOCALITY_NOT_FOUND");
//		mapMessage.put("010","AMOUNT_CANNOT_BE_EMPTY");
//		mapMessage.put("011","WRONG_USER");
//		mapMessage.put("013","DISEABLE_LOGIN_ERROR");
//		mapMessage.put("014","ACCOUNT_LOCKED_ERROR");
//		mapMessage.put("015","INVALID_TRANSFER");
//		mapMessage.put("016","TRANSFER_WRONG_RECEIVE_AGENT");
//		mapMessage.put("017","TRANSFER_WRONG_RECEIVE_CITY");
//		mapMessage.put("018","TRANSFER_WRONG_RECEIVE_COUNTRY");
//		mapMessage.put("019","PRICING_NOT_FOUND");
//		mapMessage.put("020","INVALID_FEES_TRANSFER");	
//		mapMessage.put("021","TRANSFER EXIST");
//		mapMessage.put("022","BALANCE");
//		mapMessage.put("023","ACCOUNT CLOSE");
//		mapMessage.put("024","BLOCKING CUSTOMER");
//		mapMessage.put("025","BLOCKING ACCOUNT");
		
		mapMessage.put("030","TICKET CHOIX ERROR");
		mapMessage.put("031","TICKET INCONNU");
		mapMessage.put("032","TICKET NON RECONNU");
		mapMessage.put("033","TICKET ALREADY PAID");
		mapMessage.put("034","TICKET NON EVALUE");
		mapMessage.put("035","TICKET NON ENREGISTRE");
		
	}
}
