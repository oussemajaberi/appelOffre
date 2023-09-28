package com.example.appelOff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.example.appelOff.Client")
public class AppelOffApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppelOffApplication.class, args);
	}

}
