package com.jockjock.token;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jockjock.token.domain.post.Post;
import com.jockjock.token.domain.post.PostDAO;


//JUnit4에서는 RunWith(MockitoJUnitRunner.class)를,
//JUnit5에서는 ExtendWith를 쓰도록 되어있다

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@ActiveProfiles("test")
class TestJPA {
	
	@Autowired
	private PostDAO postDAO;
	
	@PersistenceContext 
	private EntityManager em;
	
	/**************************** TEST CASE ****************************/ 
	@Test
	void TestJPA() {
		String jpql = "select p from Post p";
		TypedQuery<Post> query = em.createQuery(jpql, Post.class);
	
		var resultList = query.getResultList();
		resultList.stream().forEach( o -> {
			System.out.println(o.toString());
		});
	}



}
