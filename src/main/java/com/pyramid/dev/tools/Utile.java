package com.pyramid.dev.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pyramid.dev.business.RefreshK;


public class Utile {
	
	public static int timekeno = 185;
	public static List<ControlDisplayKeno> _display_drawLK;
	public static  Map<String, ControlDisplayKeno> display_draw;
	public static String multiplicateur[] = {"5", "1", "2", "0.5"};
	public static int bonusK_amount = 0;
	public static double bonusK_down;
	public static int bonus_codeK;
	public static double bonusKmin = 0.0;
	public static double bonusrate;
	
	public static double num10[] = {0,0,0,1,2,3,5,10,200,2000,10000};
	public static double num9[] = {0,0,0,1,2,3,25,100,1500,9000};
	public static double num8[] = {0,0,0,1,4,15,50,1200,8000};
	public static double num7[] = {0,0,0,1,3,30,220,3000};
	public static double num6[] = {0,0,1,2,10,60,800};
	public static double num5[] = {0,0,1,3,30,500};
	public static double num4[] = {0,0,2,10,100};
	public static double num3[] = {0,1,3,50};
	public static double num2[] = {0,1,10};
	public static double numAll[] = {0,4.1,14.5,60.5,275,1400,6500};
	public static double numOut[] = {0,1.7,2.1,2.5,3.2,4.2,5.5,7.5,10,13.5,18.5};
	public static double numSpec[] = {1.85,1.87,3.80};
	
	public static RefreshK ref = new RefreshK();
	
	public static int _checkExistingSameDisplayCoderace(String coderace){
		
		if(display_draw.containsKey(coderace)) {
			return 1;
		}
		
		return -1;
	}
	
	public static int generate(int n){
		int lower = 0;
		int higher = n;
		int dbl;
		  
		dbl = (int) (Math.random() * (higher-lower)) + lower;
		return dbl;
	}
	
	public static String convertJsonToString(JSONArray data) {
		//final Type DATA_TYPE_JSON = new TypeToken<JSONObject>() {}.getType();
		//String data = new Gson().toJson(json, DATA_TYPE_JSON);  "{\"ncp\":\"02979551051\",\"clc\":\"55\",\"nomrest\":\"KONCHOU FRANCIS ARMEL                                              \",\"age\":\"00001\"}"
		return data.toString().replaceAll("\\\"","");
	}

	public static String convertJsonToString(JSONObject data) {
		//final Type DATA_TYPE_JSON = new TypeToken<JSONObject>() {}.getType();
		//String data = new Gson().toJson(json, DATA_TYPE_JSON);
		return data.toString().replaceAll("\\\"","");
	}


	public JSONArray convertResultSetToJsonArrays(ResultSet resultSet) throws SQLException
	{                  
		JSONArray json = new JSONArray();
		ResultSetMetaData metadata = resultSet.getMetaData();
		int numColumns = metadata.getColumnCount();

		while(resultSet.next()){
			JSONObject obj = new JSONObject(); 
			for (int i = 1; i <= numColumns; ++i){
				String column_name = metadata.getColumnName(i);
				Object value = resultSet.getObject(column_name);
				if(StringUtils.endsWithIgnoreCase("inti", column_name)) {
					String rep = StringUtils.replace(value.toString(),";","");
					rep = StringUtils.replace(value.toString(),",","");
					rep = StringUtils.replace(value.toString(),","," ");
					obj.put(column_name,rep );
				}else {
					obj.put(column_name,value);
				}
			}
			json.put(obj);
		}
		return json;
	}


	public JSONObject convertResultSetToJsonObject(ResultSet resultSet) throws SQLException
	{
		JSONObject json = new JSONObject();
		ResultSetMetaData metadata = resultSet.getMetaData();
		int numColumns = metadata.getColumnCount();

		if(resultSet.next()){
			JSONObject obj = new JSONObject(); 
			for (int i = 1; i <= numColumns; ++i){
				String column_name = metadata.getColumnName(i);
				obj.put(column_name, resultSet.getObject(column_name));
			}
			json = obj;
		}
		return json;
	}
	
	public static long givetimestamp(String str) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss");
		
		Date date = (Date)formatter.parse(str);
		
		long output = date.getTime()/1000L;
		
		String d_str = Long.toString(output);
		
		long timestamp = Long.parseLong(d_str)*1000;
		
		return timestamp;
	}
	
}
