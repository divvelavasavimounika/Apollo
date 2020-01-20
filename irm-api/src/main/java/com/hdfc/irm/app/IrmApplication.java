package com.hdfc.irm.app;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.hdfc.irm")
public class IrmApplication {

	private static Logger logger = Logger.getLogger(IrmApplication.class);

	public static void main(String[] args) {
		logger.info("starting IrmApplication");
		SpringApplication.run(IrmApplication.class, args);
		logger.info("IrmApplication has started");
	}

}
