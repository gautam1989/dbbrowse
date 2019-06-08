package com.dbBrowser.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.dbBrowser.models.DBColumnsModel;
import com.dbBrowser.models.DBConnectionDetails;

@Component
public class DbbrowserDAO {

	Logger logger = LoggerFactory.getLogger(DbbrowserDAO.class);

	final String INSERT_QUERY = "insert into DBConnectionDetails (name,hostname,port,databaseName,username,password) values (:name,:hostname,:port,:databaseName,:username,:password)";
	final String DELETE_QUERY = "delete from DBConnectionDetails where name=:name";
	final String FIND_CONNECTION = "select name,hostname,port,databaseName,username,password from $tablename where name = :name";
	final String UPDATE_CONN_QUERY = "update DBConnectionDetails set name=:name ,hostname=:hostname,port=:port,databaseName=:databaseName,username=:username,password=:password where name=:name";
	final String SHOW_SCHEMA = "show schemas";
	final String SHOW_TABLES = "show tables";
	final String SHOW_COLS= "show columns from %s";
	final String FIND_DATA = "select * from $tablename limit $rows";


	final String MAIN_TABLE_NAME="DBConnectionDetails";
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	JdbcTemplate jdbcTemplate;

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

	public Map<String,String> updateConnection(DBConnectionDetails dbConnectionDetails) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", dbConnectionDetails.getName());
		paramMap.put("hostname", dbConnectionDetails.getHostname());
		paramMap.put("port", dbConnectionDetails.getPort());
		paramMap.put("databaseName", dbConnectionDetails.getDatabaseName());
		paramMap.put("username", dbConnectionDetails.getUsername());
		paramMap.put("password", dbConnectionDetails.getPassword());
		Map<String,String> map = null;        

		try {
			int update = namedParameterJdbcTemplate.update(UPDATE_CONN_QUERY, paramMap);
			if(update>0) {
				map = new HashMap<String, String>();
				map.put("RecordUpdatetStatus","success");
			}else {
				map.put("RecordUpdatetStatus","nothing updated");
			}

			logger.info("Database Connection updated");
		}catch (Exception e) {
			logger.error("Error in persisting dbconnection: "+e.getMessage());
		}
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DBConnectionDetails> listConnection(String connectionName) {
		try {
			return namedParameterJdbcTemplate.query(FIND_CONNECTION.replace("$tablename", MAIN_TABLE_NAME),new MapSqlParameterSource(
					"name", connectionName), new BeanPropertyRowMapper(DBConnectionDetails.class));
		}catch (Exception e) {
			logger.error("Error in listing dbconnection: "+e.getMessage());
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> listSchemasForConnectionName(String connectionName) {
		List<String> schemalist = new ArrayList<String>();
		try {
			List<DBConnectionDetails> connectionList = namedParameterJdbcTemplate.query(FIND_CONNECTION.replace("$tablename", MAIN_TABLE_NAME),new MapSqlParameterSource(
					"name", connectionName), new BeanPropertyRowMapper(DBConnectionDetails.class));

			for(DBConnectionDetails dbConnectionDetails: connectionList) {
				JdbcTemplate jdbcTemplate = getJdbcTemplateForConnection(dbConnectionDetails);
				List tempschemalist = jdbcTemplate.queryForList(SHOW_SCHEMA, String.class);
				schemalist.addAll(tempschemalist);
			}
		}catch (Exception e) {
			logger.error("Error in listing scehmas: "+e.getMessage());
		}


		return schemalist;
	}

	private JdbcTemplate getJdbcTemplateForConnection(DBConnectionDetails dbConnectionDetails) {
		return new JdbcTemplate(getDataSource(dbConnectionDetails));
	}


	private static DriverManagerDataSource getDataSource(DBConnectionDetails dbConnectionDetails) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl(createurl(dbConnectionDetails));
		dataSource.setUsername(dbConnectionDetails.getUsername());
		dataSource.setPassword(dbConnectionDetails.getPassword());
		return dataSource;
	}
	
	private static String createurl(DBConnectionDetails dbConnectionDetails) {
		return String.format("jdbc:mysql://%s:%s/%s", dbConnectionDetails.getHostname(),dbConnectionDetails.getPort(),dbConnectionDetails.getDatabaseName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> listTablesForConnectionName(String connectionName) {
		List<String> tablelist = new ArrayList<String>();
		try {
			List<DBConnectionDetails> connectionList = namedParameterJdbcTemplate.query(FIND_CONNECTION.replace("$tablename", MAIN_TABLE_NAME),new MapSqlParameterSource(
					"name", connectionName), new BeanPropertyRowMapper(DBConnectionDetails.class));

			for(DBConnectionDetails dbConnectionDetails: connectionList) {
				JdbcTemplate jdbcTemplate = getJdbcTemplateForConnection(dbConnectionDetails);
				List temptablelist = jdbcTemplate.queryForList(SHOW_TABLES, String.class);
				tablelist.addAll(temptablelist);
			}
		}catch (Exception e) {
			logger.error("Error in listing tables: "+e.getMessage());
		}


		return tablelist;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<DBColumnsModel> listCols(String connectionName, String tableName) {
		List<DBColumnsModel> cols = new ArrayList<DBColumnsModel>();
		try {
		List<DBConnectionDetails> connectionList = namedParameterJdbcTemplate.query(FIND_CONNECTION.replace("$tablename", MAIN_TABLE_NAME),new MapSqlParameterSource(
				"name", connectionName), new BeanPropertyRowMapper(DBConnectionDetails.class));
		for(DBConnectionDetails dbConnectionDetails: connectionList) {
			JdbcTemplate jdbcTemplate = getJdbcTemplateForConnection(dbConnectionDetails);
			List<DBColumnsModel> tempcols = jdbcTemplate.query(String.format(SHOW_COLS, tableName), new BeanPropertyRowMapper(DBColumnsModel.class));
			cols.addAll(tempcols);
		}
		}catch (Exception e) {
			logger.error("Error in listing cols: "+e.getMessage());
		}
		
		return cols;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, Object>> listData(String connectionName, String tableName, Integer rowsToFetch) {
		List<Map<String, Object>> datacomplete = new ArrayList<Map<String,Object>>();
		try {
			List<DBConnectionDetails> connectionList = namedParameterJdbcTemplate.query(FIND_CONNECTION.replace("$tablename", MAIN_TABLE_NAME),new MapSqlParameterSource(
					"name", connectionName), new BeanPropertyRowMapper(DBConnectionDetails.class));
			for(DBConnectionDetails dbConnectionDetails: connectionList) {
				JdbcTemplate jdbcTemplate = getJdbcTemplateForConnection(dbConnectionDetails);
				List<Map<String, Object>> tempdata = jdbcTemplate.queryForList(FIND_DATA.replace("$tablename", tableName).replace("$rows", rowsToFetch.toString()));
				datacomplete.addAll(tempdata);
			}
			return datacomplete;
		}catch (Exception e) {
			logger.error("Error in listing data: "+e.getMessage());
		}
		return datacomplete;
		
	}
	

}
