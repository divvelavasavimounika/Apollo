package com.hdfc.irm;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IrmWebApplication {
	private static Logger logger = Logger.getLogger(IrmWebApplication.class);

	public static void main(String[] args) {
		DOMConfigurator.configure("config/log4j.xml");
		logger.info("starting IrmWebApplication");
		SpringApplication.run(IrmWebApplication.class, args);
		logger.info("IrmWebApplication has started");
	}
}
