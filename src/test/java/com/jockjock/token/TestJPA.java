package com.jockjock.token;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
	
	/**************************** TEST CASE ****************************/ 
	
	@Test
	@Rollback(false)
	void TestJPA() {
		
		Post post = new Post("test","test","test");
		postDAO.save(post);
		
		int updateCount = postDAO.updateContent(2L, "바뀐내용");
		System.out.println("--------updateCount : "+updateCount+"-----------");
		
		String content = postDAO.findById(2L).get().getContent();
		System.out.println("--------content : "+content+"-----------");
	}



}
