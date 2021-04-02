package com.jockjock.token.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
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
	
	/*	
		em.find();    // 엔티티 조회
		em.persist(); // 엔티티 저장
		em.remove();  // 엔티티 삭제
		em.flush();   // 영속성 컨텍스트 내용을 데이터베이스에 반영
		em.detach();  // 엔티티를 준영속 상태로 전환
		em.merge();   // 준영속 상태의 엔티티를 영속상태로 변경
		em.clear();   // 영속성 컨텍스트 초기화
		em.close();   // 영속성 컨텍스트 종료
	*/
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public ResultMap saveTranTest() throws Exception {
		
		Post test = postDAO.save(new Post("test1","testest", "ㅎㅇㅎㅇ"));
		
		em.clear();
		
		test.setContent("바꾼 내용");
	
		return new ResultMap();
	} 
	
	@Override
	public ResultMap selectAll() throws Exception {
		var map = new ResultMap(); 
		map.put("resultList", postDAO.findAll());
		
		return map;
	}



	


	

}
