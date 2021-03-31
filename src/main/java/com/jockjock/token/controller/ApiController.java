package com.jockjock.token.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class ApiController {

	/**
	 * <pre>
	 * 1. 개요 : api 세션 정보 테스트
	 * 2. 처리내용 :
	 * </pre>
	 *	@since 2021. 3. 31.
	 * @param request
	 * @return
	 */
	@RequestMapping("/api/userinfo")
	public ResponseEntity<ResultMap> jsonList(HttpServletRequest request){
		HttpSession session = request.getSession(true);
		
		ResultMap map = new ResultMap();
		map.put("user_id", session.getAttribute("user_id"));
		map.put("nick_name", session.getAttribute("nick_name"));
		map.put("auth_type", session.getAttribute("auth_type"));

		return new ResponseEntity<ResultMap>(map, HttpStatus.OK);
	}
	
	
}
