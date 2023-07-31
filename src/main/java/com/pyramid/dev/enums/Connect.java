package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum Connect {
	CONNECTED("Connected"),
	NOTCONNECTED("NotConnected");

	private String value;
	private static List<Connect> statutCaisse = new ArrayList<>();

	static {
		statutCaisse.add(CONNECTED);
		statutCaisse.add(NOTCONNECTED);
	}

	private Connect(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public String getValue() {
		return value;
	}

	private void setValue(String value) {
		this.value = value;
	}

	public static List<Connect> statutCaisse(){
		return statutCaisse;
	}
}
