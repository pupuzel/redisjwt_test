package com.jockjock.token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class TestRedisJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestRedisJwtApplication.class, args);
	}

}
