package com.dbBrowser.models;

public class DBColumnsModel {

	
	private String Field;
	private String Type;
	private String Null;
	private String Key;
	private String Default;
	private String Extra;
	public String getField() {
		return Field;
	}
	public void setField(String field) {
		Field = field;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getNull() {
		return Null;
	}
	public void setNull(String null1) {
		Null = null1;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public String getDefault() {
		return Default;
	}
	public void setDefault(String default1) {
		Default = default1;
	}
	public String getExtra() {
		return Extra;
	}
	public void setExtra(String extra) {
		Extra = extra;
	}
	public DBColumnsModel(String field, String type, String null1, String key, String default1, String extra) {
		super();
		Field = field;
		Type = type;
		Null = null1;
		Key = key;
		Default = default1;
		Extra = extra;
	}
	public DBColumnsModel() {
		super();
	}

	
	
}
