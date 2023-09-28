package com.example.soumission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SoumissionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoumissionApplication.class, args);
	}

}
