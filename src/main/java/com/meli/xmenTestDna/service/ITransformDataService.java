package com.meli.xmenTestDna.service;

import java.security.NoSuchAlgorithmException;

public interface ITransformDataService {

	String makeSequence(String[] dna);
	String getOptimizedSequenceHash(String optimizedSequence) throws NoSuchAlgorithmException;

}
