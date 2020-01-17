package com.hdfc.irm.engine.utils;

import java.util.UUID;

public class IrmUtils {

	public static String uuId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();

	}
}
