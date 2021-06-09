package com.meli.xmenTestDna.service;

import java.security.NoSuchAlgorithmException;

import com.meli.xmenTestDna.entity.common.StatsResponseBody;

public interface IMainService {
	public boolean isMutant(String... dna) throws NoSuchAlgorithmException;
	public StatsResponseBody getStats();
}
