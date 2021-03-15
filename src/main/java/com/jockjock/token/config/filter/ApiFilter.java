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
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(request.getHeader("X-Requested-With") == null || !request.getHeader("X-Requested-With").equals("Axios")){
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}else{
			 String token = "";
			 String uuid = "";
			 
			 if(request.getCookies() != null ){
				for(Cookie cookie : request.getCookies()){
					if(cookie.getName().equals("authorization")){
						token = cookie.getValue();
					}
					if(cookie.getName().equals("u_uuid")){
						uuid = cookie.getValue();
					}
				}
			 }
			
			if(token.isEmpty() || uuid.isEmpty()){
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			}else{
				if(jwtTokenUtil.isValidateToken(token)) {
					Map<String,Object> info = jwtTokenUtil.getBobyFromToken(token);
					
					if(jwtTokenUtil.isTokenExpired(token)) {
						
						boolean isCheckAutoLogin = (boolean) info.get("checkAutoLogin");
						if(isCheckAutoLogin) {
							if(valueOperations.get(uuid).isEmpty()) {
								response.setStatus(HttpServletResponse.SC_FORBIDDEN);
							}else {
								try {
									authService.updateAuthenticationToken(uuid, response);
									filterChain.doFilter(request, response);
								}catch (Exception e) {
									log.info("Exception : ", e);
									response.setStatus(HttpServletResponse.SC_FORBIDDEN);
								}
								
							}
						}else {
							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						}
						
					}else{
						filterChain.doFilter(request, response);
					}
					
				}else{
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
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
	
	

	
}
