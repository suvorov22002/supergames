package com.pyramid.dev.enums;

import java.util.ArrayList;
import java.util.List;

public enum Room {
	CLOSED("closed"),
	OPENED("opened");

	private String value;
	private static List<Room> statutRoom = new ArrayList<>();

	static {
		statutRoom.add(CLOSED);
		statutRoom.add(OPENED);
	}

	private Room(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static List<Room> statutRoom(){
		return statutRoom;
	}
}

