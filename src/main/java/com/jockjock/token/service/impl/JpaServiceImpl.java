package com.jockjock.token.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jockjock.token.domain.post.Post;
import com.jockjock.token.domain.post.PostDAO;
import com.jockjock.token.model.ResultMap;
import com.jockjock.token.service.JpaService;

@Transactional(rollbackOn = Exception.class)
@Service("jpaService")
public class JpaServiceImpl implements JpaService{

	@Autowired
	private PostDAO postDAO;
	
		
	@Override
	public ResultMap selectAll() throws Exception {
		var map = new ResultMap();
		map.put("resultList", postDAO.findAll());
		
		return map;
	}


	@Override
	public ResultMap saveTranTest() throws Exception {
		
		postDAO.save(new Post("test1","testest", "ㅎㅇㅎㅇ"));
		
		if(1==1) throw new Exception();
		
		postDAO.save(new Post("test1","testest", "ㅎㅇㅎㅇ"));
		postDAO.save(new Post("test1","testest", "ㅎㅇㅎㅇ"));
		
		return new ResultMap();
	} 
	


	

}
