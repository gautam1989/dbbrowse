package com.dbBrowser.controller;

import java.util.HashMap;
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

	@Bean
	public Gson getgson() {
		return new Gson();
	}


}
