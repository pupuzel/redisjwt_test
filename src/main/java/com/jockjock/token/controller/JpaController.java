package com.jockjock.token.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jockjock.token.model.ResultMap;
import com.jockjock.token.service.JpaService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class JpaController {
		
	@Resource(name ="jpaService")
	private JpaService jpaService;

	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultMap> exception(Exception e) {
		log.info("ExceptionHandler -------> ", e);
		ResultMap result = new ResultMap("N", "서버 오류");
		
		return new ResponseEntity<ResultMap>(result, HttpStatus.OK);
	}
		

	@RequestMapping("/jpa/selectAll")
	public ResponseEntity<ResultMap> postList(HttpServletRequest request) throws Exception{
		return new ResponseEntity<ResultMap>(jpaService.selectAll(), HttpStatus.OK);
	}
	
	
	@RequestMapping("/jpa/saveTranTest")
	public ResponseEntity<ResultMap> saveTranTest(HttpServletRequest request) throws Exception{
		return new ResponseEntity<ResultMap>(jpaService.saveTranTest(), HttpStatus.OK);
	}
		
	
}