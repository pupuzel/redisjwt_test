package com.jockjock.token.config.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(urlPatterns = "/authenticate/*")
public class AuthenticateFilter extends OncePerRequestFilter{
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if(request.getHeader("X-Requested-With") == null || !request.getHeader("X-Requested-With").equals("Axios")){
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}else{
			 filterChain.doFilter(request, response);
		}
	}
	

	
}
