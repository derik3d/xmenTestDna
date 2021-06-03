package com.meli.xmenTestDna.service;

public interface ITransformDataService {

	String optimizeSequence(String[] dna);
	String getOptimizedSequenceHash(String optimizedSequence);

}
