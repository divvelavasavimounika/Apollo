package com.hdfc.irm.engine.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class LoggerUtils {

	public static String getStackStrace(Exception e) {
		String stackTrace = "";
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			stackTrace = sw.toString();
		} catch (Exception e1) {
			// TODO: handle exception
		}
		return stackTrace;
	}

	public static void debug(Logger logger, String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
	}
}
