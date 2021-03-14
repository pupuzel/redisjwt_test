package com.jockjock.token.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jockjock.token.model.AuthUserDetail;
import com.jockjock.token.model.ResultMap;
import com.jockjock.token.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@RestController
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/authenticate/login")
	public ResponseEntity<ResultMap> login(@RequestBody @Valid AuthUserDetail authUserDetail, HttpServletRequest request) throws Exception{
		return new ResponseEntity<ResultMap>(authService.AuthenticationToken(authUserDetail, request), HttpStatus.OK);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResultMap> modelValid(MethodArgumentNotValidException e) {
		BindingResult bindingResult = e.getBindingResult();
		List<ObjectError> erros = bindingResult.getAllErrors(); 
		
		String message = erros.get(0).getDefaultMessage();
		ResultMap result = new ResultMap("N",message);
		
		return new ResponseEntity<ResultMap>(result, HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResultMap> exception(Exception e) {
		log.info("ExceptionHandler -------> ", e);
		ResultMap result = new ResultMap("N", "서버 오류");
		
		return new ResponseEntity<ResultMap>(result, HttpStatus.OK);
	}
	
}
