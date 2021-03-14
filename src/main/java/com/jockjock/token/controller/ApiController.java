package com.jockjock.token.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jockjock.token.model.ResultMap;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ApiController {

	@RequestMapping("/api/userInfo")
	public ResponseEntity<ResultMap> jsonList(){
		ResultMap map = new ResultMap("Y");
		map.put("name", "족족몬");
		map.put("age", 28);
		map.put("addr", "부천시");
		
		return new ResponseEntity<ResultMap>(map, HttpStatus.OK);
	}
}
