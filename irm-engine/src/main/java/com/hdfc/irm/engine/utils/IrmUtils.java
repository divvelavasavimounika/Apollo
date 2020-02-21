package com.hdfc.irm.engine.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Stream;

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

	public static String readFromFile(String filePath) throws IOException {
		StringBuilder sb = new StringBuilder();

		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
			stream.forEach(s -> sb.append(s));
		}

		return sb.toString();

	}
}
