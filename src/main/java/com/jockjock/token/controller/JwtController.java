package com.jockjock.token.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jockjock.token.domain.post.PostDAO;
import com.jockjock.token.model.ResultMap;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class JwtController {
		
	@Autowired
	private PostDAO postDAO;
		

	@RequestMapping("/jwt/selectAll")
	public ResponseEntity<ResultMap> postList(HttpServletRequest request){
		ResultMap map = new ResultMap();
		map.put("resultList", postDAO.findAll());
		
		return new ResponseEntity<ResultMap>(map, HttpStatus.OK);
	}

}