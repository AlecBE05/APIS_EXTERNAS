package com.codigo.ms_openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsOpenfeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOpenfeignApplication.class, args);
	}

}
