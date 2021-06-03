package com.meli.xmenTestDna.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StatsResponseBody {
	private Integer count_mutant_dna;
	private Integer count_human_dna;
	private Double ratio;
}
