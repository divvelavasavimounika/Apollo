package com.hdfc.irm;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IrmApplication {

	private static Logger logger = Logger.getLogger(IrmApplication.class);

	public static void main(String[] args) {
		DOMConfigurator.configure("config/log4j.xml");
		logger.info("starting IrmApplication");
		SpringApplication.run(IrmApplication.class, args);
		logger.info("IrmApplication has started");
	}

}
