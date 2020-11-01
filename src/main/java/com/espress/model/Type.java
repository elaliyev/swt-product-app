package com.espress.model;

public enum Type {
	DevKit(0), Socs(1), Module(2);

	private int type;

	Type(int type) {
		this.type = type;
	}

	public static Type fromId(int id) {
		for (Type type : values()) {
			if (type.type == id) {
				return type;
			}
		}
		return null;
	}

}
