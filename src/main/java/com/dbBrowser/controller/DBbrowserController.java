package com.dbBrowser.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dbBrowser.models.DBColumnsModel;
import com.dbBrowser.models.DBConnectionDetails;
import com.dbBrowser.repository.DbbrowserDAO;
import com.google.gson.Gson;

@RestController
public class DBbrowserController {


	@Autowired
	Gson gson;


	@Autowired
	DbbrowserDAO dbDbbrowserDAO;



	@PostMapping(value = "/addConnection", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addConnection(@RequestBody DBConnectionDetails dbConnectionDetails) {

		Map<String,String> connectionSuccessMap =dbDbbrowserDAO.inserConnectionDetails(dbConnectionDetails); 
		if(null!=connectionSuccessMap)
			return ResponseEntity.ok(gson.toJson(connectionSuccessMap));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}


	@DeleteMapping(value = "/deleteConnection", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteConnection(@RequestParam String connectionName){
		Map<String,String> connectionDeleteMap =dbDbbrowserDAO.deleteConnectionDetails(connectionName); 
		if(null!=connectionDeleteMap)
			return ResponseEntity.ok(gson.toJson(connectionDeleteMap));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}

	
	@GetMapping(value = "/listConnection", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listConnection(@RequestParam String connectionName){
		List<DBConnectionDetails> connectionDeleteMap =dbDbbrowserDAO.listConnection(connectionName); 
		if(null!=connectionDeleteMap)
			return ResponseEntity.ok(gson.toJson(connectionDeleteMap));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}
	
	@PostMapping(value = "/updateConnection", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateConnection(@RequestBody DBConnectionDetails dbConnectionDetails) {

		Map<String,String> connectionSuccessMap =dbDbbrowserDAO.updateConnection(dbConnectionDetails); 
		if(null!=connectionSuccessMap)
			return ResponseEntity.ok(gson.toJson(connectionSuccessMap));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}
	
	@GetMapping(value = "/listSchemas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listSchemas(@RequestParam String connectionName){
		List<String> scehamList =dbDbbrowserDAO.listSchemasForConnectionName(connectionName); 
		if(null!=scehamList)
			return ResponseEntity.ok(gson.toJson(scehamList));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}
	 
	@GetMapping(value = "/listTables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listTables(@RequestParam String connectionName){
		List<String> tableNameList =dbDbbrowserDAO.listTablesForConnectionName(connectionName); 
		if(null!=tableNameList)
			return ResponseEntity.ok(gson.toJson(tableNameList));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}
	 
	@GetMapping(value = "/listCols", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listCols(@RequestParam String connectionName,@RequestParam String tableName){
		List<DBColumnsModel> colsmodels =dbDbbrowserDAO.listCols(connectionName,tableName); 
		if(null!=colsmodels)
			return ResponseEntity.ok(gson.toJson(colsmodels));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}
	
	
	@GetMapping(value = "/listData", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> listData(@RequestParam String connectionName,@RequestParam String tableName,@RequestParam Integer rowsToFetch){
		if(null == rowsToFetch || rowsToFetch == 0)
			rowsToFetch = 10;
		List<Map<String, Object>> data =dbDbbrowserDAO.listData(connectionName,tableName,rowsToFetch); 
		if(null!=data)
			return ResponseEntity.ok(gson.toJson(data));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
	}
	
	@Bean
	public Gson getgson() {
		return new Gson();
	}


}
