package com.meetry.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MeetryBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetryBackendApplication.class, args);
	}

}
