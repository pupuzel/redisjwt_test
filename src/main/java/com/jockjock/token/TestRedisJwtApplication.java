package com.jockjock.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import com.jockjock.token.domain.post.Post;
import com.jockjock.token.domain.post.PostDAO;

@ServletComponentScan
@SpringBootApplication
public class TestRedisJwtApplication {
	
	@Autowired
	private PostDAO postDAO;
	
	@Bean
	public ApplicationRunner applicationRun() {
		return (args) -> {
			postDAO.save(new Post("jock","첫번째 게시판", "ㅎㅇㅎㅇ"));
			postDAO.save(new Post("songWs","두번째 게시판", "ㅎㅇㅎㅇ"));
			postDAO.save(new Post("jjdo1994","세번째 게시판", "ㅎㅇㅎㅇ"));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(TestRedisJwtApplication.class, args);
	}

}
