package com.jockjock.token.service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.jockjock.token.model.AuthUserDetail;
import com.jockjock.token.model.ResultMap;
import com.jockjock.token.service.AuthService;
import com.jockjock.token.util.JwtTokenUtil;

@Service("authService")
public class AuthServiceImpl implements AuthService{ 
	
	@Resource(name = "redisTemplate") 
	private ValueOperations<String, String> valueOperations;

	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Value("${spring.session.redis.namespace}")
	private String sessionKey;
	
	@Value("${spring.session.timeout}")
    private long session_timeout;

	@Override
	public ResultMap AuthenticationToken(AuthUserDetail authUserDetail, HttpServletRequest request) throws Exception{
		
		//token 검증
		/**
		 * 생략
		 * */
		
		//token 생성
		String token = jwtTokenUtil.<AuthUserDetail>generateToken(authUserDetail);
		
		//session save 
		String uuid = setSession(token, authUserDetail.isCheckAutoLogin());
		
		ResultMap map = new ResultMap("Y");
		map.put("token", token);
		map.put("uuid", uuid);
		return map;
	}
	
	
	
	
	@Override
	public void updateAuthenticationToken(String uuid, HttpServletResponse response) throws Exception {
		System.out.println("updateAuthenticationToken");
		
		String token = valueOperations.get(uuid);
		Map<String,Object> info = jwtTokenUtil.getBobyFromToken(token);
		String refresh_token = jwtTokenUtil.generateToken(info);
		
		if(valueOperations.get(uuid).isEmpty()) {
			valueOperations.set(uuid, refresh_token, Duration.ofSeconds(600));
		}else {
			valueOperations.set(uuid, refresh_token, 0);
		}
		
		response.setHeader("refresh_token", refresh_token);
	}




	private String setSession(String token, boolean isCheck_auto_login) {
		String uuid = UUID.randomUUID().toString();
		if(isCheck_auto_login) {
			valueOperations.set(uuid, token, Duration.ofSeconds(600));
		}else {
			valueOperations.set(uuid, token, Duration.ofSeconds(session_timeout));
		}
		
		
		return uuid;
	}
	

}
