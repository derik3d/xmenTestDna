package com.meli.xmenTestDna.service;

import com.meli.xmenTestDna.entity.common.StatsResponseBody;

public interface IMainService {
	public boolean isMutant(String... dna);
	public StatsResponseBody getStats();
}
