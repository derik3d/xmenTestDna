package com.meli.xmenTestDna.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TransformDataService implements ITransformDataService{

	public String makeSequence(String[] dna) {
		return Arrays.asList(dna).stream().collect(Collectors.joining()).toLowerCase();
	}

	public String getOptimizedSequenceHash(String optimizedSequence) throws NoSuchAlgorithmException {
			MessageDigest messageDigest;
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(optimizedSequence.getBytes());
			return new String(messageDigest.digest());
	}

}
