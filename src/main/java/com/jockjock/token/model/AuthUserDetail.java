package com.jockjock.token.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;

@Data
public class AuthUserDetail implements Serializable {

	private static final long serialVersionUID = 3733402245200968832L;
	
	@NotEmpty
	private String id;
	
	@NotEmpty
	private String access_token;
	
	@NotEmpty
	private String auth_type;
	
	@NotEmpty
	private String nick_name;
	
	private String age;
	
	private String gender;
	
	private String profile_image;
	
	private boolean isCheckAutoLogin;

}
