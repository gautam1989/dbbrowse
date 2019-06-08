package com.dbBrowser.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



public class DBConnectionDetails {

	
	String name;
	String hostname;
	String port;
	String databaseName;
	String username;
	String password;
	String schema;
	
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DBConnectionDetails(String name, String hostname, String port, String databaseName, String username,
			String password,String schema) {
		super();
		this.name = name;
		this.hostname = hostname;
		this.port = port;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		this.schema = schema;
	}
	public DBConnectionDetails() {
		super();
	}

	
	
	
}
