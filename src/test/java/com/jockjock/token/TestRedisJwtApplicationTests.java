package com.jockjock.token;

import java.time.Duration;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class TestRedisJwtApplicationTests {
	
	@Resource(name="redisTemplate") 
	private ValueOperations<String, String> valueOperations;


	@Test
	void contextLoads() {
		//valueOperations.set("test", "jocks",Duration.ofSeconds(30));
		//System.out.println(valueOperations.get("test"));
		
		valueOperations.set("test", "1234", 0);
		System.out.println(valueOperations.get("test"));
	}

}
