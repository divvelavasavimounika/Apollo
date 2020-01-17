package com.hdfc.irm.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hdfc.irm")
public class IrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(IrmApplication.class, args);
	}

}
