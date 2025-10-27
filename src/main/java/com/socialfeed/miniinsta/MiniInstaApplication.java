package com.socialfeed.miniinsta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MiniInstaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniInstaApplication.class, args);
	}

}
