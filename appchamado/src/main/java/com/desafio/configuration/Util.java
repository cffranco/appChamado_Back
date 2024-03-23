package com.desafio.configuration;

import java.util.Base64;

public class Util {

	public static String criptografiaBase64Encoder(String pValor) {
	    return new String(Base64.getEncoder().encode(pValor.getBytes()));
	}
	
	public static String descriptografiaBase64Decode(String pValor) {
	    return new String(Base64.getDecoder().decode(pValor.getBytes()));
	}
}
