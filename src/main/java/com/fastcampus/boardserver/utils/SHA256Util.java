package com.fastcampus.boardserver.utils;

import java.security.MessageDigest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SHA256Util {

	public static final String ENCRYPTION_KEY = "SHA-256";

	public static String encryptSHA256(String password) {
		String SHA = null;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(ENCRYPTION_KEY);
			md.update(password.getBytes());
			byte[] byteData = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte byteDatum : byteData) {
				sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
			}
			SHA = sb.toString();
		} catch (Exception e) {
			log.error("encryptSHA256 ERROR : {}",e.getMessage());
			SHA = null;
		}

		return SHA;
	}
}
