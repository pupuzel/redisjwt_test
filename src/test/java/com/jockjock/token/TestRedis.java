package com.jockjock.token;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK) // @SpringBootTest는 @SpringBootApplication이 붙은 애너테이션을 찾아 context를 찾는다
@ActiveProfiles("dev")
@AutoConfigureMockMvc
class TestRedis {
	
	@Autowired
    MockMvc mockMvc; // url 요청 테스트

	@Resource(name="redisTemplate")
	private ValueOperations<String, String> valueOperations;
	
	/**************************** TEST CASE ****************************/ 

	
	@Test
	void TestRedis() {
		//valueOperations.set("test", "jocks",Duration.ofSeconds(30));
		//System.out.println(valueOperations.get("test"));
		
		valueOperations.set("test", "1234", 0);
		System.out.println(valueOperations.get("test"));
	}

}
