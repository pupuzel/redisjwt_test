package com.jockjock.token.service.impl;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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
	public ResultMap AuthenticationToken(AuthUserDetail authUserDetail, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//token 검증
		/**
		 * 생략
		 * */
		
		//token 생성
		String token = jwtTokenUtil.<AuthUserDetail>generateToken(authUserDetail);
		
		//session save 
		String uuid = setSession(token, authUserDetail.isCheckAutoLogin());
		
		//set cookie
		Cookie u_uuid = new Cookie("u_uuid", uuid);
		u_uuid.setPath("/");
		u_uuid.setHttpOnly(true);
		
		Cookie authorization = new Cookie("authorization", token);
		authorization.setPath("/");
		authorization.setHttpOnly(true);
		
		response.addCookie(authorization);
		response.addCookie(u_uuid);
		
		ResultMap map = new ResultMap();
		map.put("token", token);
		map.put("uuid", uuid);
		return map;
	}
	
	
	
	
	@Override
	public ResultMap AuthenticationRemove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uuid = "";
		
		 if(request.getCookies() != null ){
			for(Cookie cookie : request.getCookies()){
				if(cookie.getName().equals("u_uuid")){
					uuid = cookie.getValue();
					break;
				}
			}
		 }
		 
		 valueOperations.set(uuid, "", 1 , TimeUnit.MILLISECONDS);
		
		return new ResultMap();
	}




	@Override
	public void updateAuthenticationToken(String uuid, HttpServletResponse response) throws Exception {
		System.out.println("updateAuthenticationToken");
		
		String token = valueOperations.get(uuid);
		Map<String,Object> info = jwtTokenUtil.getBobyFromToken(token);
		String refresh_token = jwtTokenUtil.generateToken(info);
		
		if(valueOperations.get(uuid).isEmpty()) {
			valueOperations.set(uuid, refresh_token, Duration.ofSeconds(session_timeout));
		}else {
			valueOperations.set(uuid, refresh_token, 0);
		}
		
		Cookie authorization = new Cookie("authorization", refresh_token);
		authorization.setPath("/");
		authorization.setHttpOnly(true);
		response.addCookie(authorization);
	}




	private String setSession(String token, boolean isCheck_auto_login) {
		String uuid = UUID.randomUUID().toString();
		if(isCheck_auto_login) {
			valueOperations.set(uuid, token, Duration.ofSeconds(session_timeout));
		}else {
			valueOperations.set(uuid, token, Duration.ofSeconds(session_timeout));
		}
		
		
		return uuid;
	}
	

}
