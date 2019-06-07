package com.dbBrowser.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.dbBrowser.models.DBConnectionDetails;

@Component
public class DbbrowserDAO {

	Logger logger = LoggerFactory.getLogger(DbbrowserDAO.class);

	final String INSERT_QUERY = "insert into DBConnectionDetails (name,hostname,port,databaseName,username,password) values (:name,:hostname,:port,:databaseName,:username,:password)";
	final String DELETE_QUERY = "delete from DBConnectionDetails where name=:name";


	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public Map<String,String> inserConnectionDetails(DBConnectionDetails dbConnectionDetails) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", dbConnectionDetails.getName());
		paramMap.put("hostname", dbConnectionDetails.getHostname());
		paramMap.put("port", dbConnectionDetails.getPort());
		paramMap.put("databaseName", dbConnectionDetails.getDatabaseName());
		paramMap.put("username", dbConnectionDetails.getUsername());
		paramMap.put("password", dbConnectionDetails.getPassword());
		Map<String,String> map = null;        
		try {
			int update = namedParameterJdbcTemplate.update(INSERT_QUERY, paramMap);
			if(update>0) {
				map = new HashMap<String, String>();
				map.put("RecordInsertStatus","success");
			}
			logger.info("Database Connection persisted");
		}catch (Exception e) {
			logger.error("Error in persisting dbconnection: "+e.getMessage());
		}
		return map;
	}

	public Map<String,String> deleteConnectionDetails(String connectionName){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", connectionName);
		Map<String,String> map = null;        
		try {
			int update = namedParameterJdbcTemplate.update(DELETE_QUERY, paramMap);
			map = new HashMap<String, String>();
			map.put("RecordDeleteStatus","success");
			map.put("RecordDeleteCount",String.valueOf(update));
			logger.info("Database Connection deleted");
		}catch (Exception e) {
			logger.error("Error in deleting dbconnection: "+e.getMessage());
		}
		return map;
	}

}
