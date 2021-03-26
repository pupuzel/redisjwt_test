package com.jockjock.token.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jockjock.token.model.AuthUserDetail;
import com.jockjock.token.model.ResultMap;

public interface AuthService { 
	
	ResultMap AuthenticationToken(AuthUserDetail authUserDetail, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	ResultMap AuthenticationRemove(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	void updateAuthenticationToken(String uuid, HttpServletResponse response) throws Exception;
}
