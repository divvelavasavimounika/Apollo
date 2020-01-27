package com.hdfc.irm.engine.utils;

import java.util.Base64;
import java.util.UUID;

public class IrmUtils {

	public static String uuId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}

	public static String encodeString(String str) {
		if (str == null)
			return null;
		else
			return Base64.getEncoder().encodeToString(str.getBytes());
	}
}
