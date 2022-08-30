package com.pyramid.dev.tools;

import java.util.HashMap;
import java.util.Map;

public class Params {
	
	public static int MISE_MIN = 200;
	public static int MISE_MAX;
	public static double GAIN_MAX = 100000;
	public static int MISE_KENO_MAX = 10000;
	public static double GAIN_KENO_MAX = 100000;
	
	public static int MISE_SPIN_MAX = 10000;
	public static double GAIN_SPIN_MAX = 1000000;
	public static Map<String, String> mapHeure = new HashMap<String, String>();
	public static Map<String, String> mapHeureOuv = new HashMap<String, String>();
	public static final String ALGO_CHIFFREMENT = "SHA-256";
}
