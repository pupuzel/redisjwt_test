package com.jockjock.token.service;

import com.jockjock.token.domain.post.Post;
import com.jockjock.token.model.ResultMap;

public interface JpaService {

	ResultMap selectAll() throws Exception;
	
	ResultMap saveTranTest() throws Exception;
}
