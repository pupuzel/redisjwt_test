package com.jockjock.token;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import com.jockjock.token.domain.post.Post;
import com.jockjock.token.domain.post.PostDAO;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TestJPA {
	
	@Autowired
	private PostDAO postDAO;
	
	/**************************** TEST CASE ****************************/ 
	
	@Test
	@Rollback(false)
	void TestJPA() {
		List<Post> list = postDAO.findAll();
		list.stream().forEach( post -> {
			System.out.println(post.getId());
		});

	}



}
