package com.jockjock.token.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

public class ResultMap extends HashMap<String, Object>{

    public ResultMap(){
		super.put("result", "Y");
	}
	
	public ResultMap(String result) {
		super.put("result", result);
	}
	
	public ResultMap(String result, String message) {
		super.put("result", result);
		super.put("message", message);
	}
}	
