package com.norm.timemall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class TimemallApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimemallApplication.class, args);
	}

}
