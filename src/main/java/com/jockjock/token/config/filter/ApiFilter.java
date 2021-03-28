package com.jockjock.token.config.filter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jockjock.token.service.AuthService;
import com.jockjock.token.util.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/api/*")
public class ApiFilter extends OncePerRequestFilter{
	
	@Resource(name = "redisTemplate") 
	private ValueOperations<String, String> valueOperations;
	
	@Resource(name = "authService") 
	private AuthService authService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;	
	
	/**
	 * SC_BAD_REQUEST = 400 잘못된 요청
	 * SC_UNAUTHORIZED = 401 인증 실패
	 * SC_FORBIDDEN = 403 인가 실패 (재로그인 요청)
	 * */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(request.getHeader("X-Requested-With") == null || !request.getHeader("X-Requested-With").equals("Axios")){
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}else{
			 String token = getcookieValue(request, "authorization");
			 String uuid = getcookieValue(request, "u_uuid");
			 
			 //세션 토큰 유효성 검사
			 if(authorizationValidation(request, response, token, uuid)) {
				 filterChain.doFilter(request, response);
			 }

		}
	}
	
	
	private void setSession(HttpServletRequest request, Map<String,Object> map) {
			HttpSession session = request.getSession(true);
			session.setAttribute("user_id", map.get("id")+"_"+map.get("auth_type"));
			session.setAttribute("nick_name", map.get("nick_name"));
			session.setAttribute("auth_type", map.get("auth_type"));
			session.setAttribute("check_auto_login", map.get("check_auto_login"));
			session.setAttribute("profile_img", map.get("profile_img"));
	}
	
	private String getcookieValue(HttpServletRequest request, String key) {
		String value = "";
		 if(request.getCookies() != null ){
			for(Cookie cookie : request.getCookies()){
				if(cookie.getName().equals(key)){
					value = cookie.getValue();
					break;
				}
			}
		 }
		 
		 return value;
	}
	
	private boolean authorizationValidation(HttpServletRequest request, HttpServletResponse response, String token, String uuid) {
		 // 토큰 및 uuid 가 없다면 (401 인증실패)
		if(token.isEmpty() || uuid.isEmpty()){
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		// 토큰이 유효하지 않다면 (401 인증실패)
		if(!jwtTokenUtil.isValidateToken(token)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		
		// 세션이 Redis에 존재하지 않을 경우 (403 인가실패)
		if(valueOperations.get(uuid) == null || valueOperations.get(uuid).isEmpty()) { 
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		
		
		Map<String,Object> info = jwtTokenUtil.getBobyFromToken(token);
		boolean isCheckAutoLogin = (boolean) info.get("checkAutoLogin");
		
		// 토큰이 만료되었지만 자동 로그인일 경우 token refresh
		if(jwtTokenUtil.isTokenExpired(token) && isCheckAutoLogin) {
			try {
				authService.updateAuthenticationToken(uuid, response);
				return true;
			}catch (Exception e) {
				log.info("Exception : ", e);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return false;
			}
		// 토큰이 만료되고 자동 로그인일 아닐경우 (403 인가실패)
		}else if(jwtTokenUtil.isTokenExpired(token) && !isCheckAutoLogin){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return false;
		
		}else {
			setSession(request, info); 
			return true; 
		}
			
	}
	
	


	
}
