package com.meli.xmenTestDna.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.xmenTestDna.entity.common.MutantResponseBody;
import com.meli.xmenTestDna.entity.common.StatsResponseBody;
import com.meli.xmenTestDna.service.IMainService;

@RestController
public class MutantController {
	
	@Autowired
	IMainService iMainService;
	
	@PostMapping("/mutant/")
	public ResponseEntity<MutantResponseBody> isMutant(String[] dna) {
		if(iMainService.isMutant(dna)) {
			return new ResponseEntity<MutantResponseBody>(new MutantResponseBody("Congrats, you're a mutant"), HttpStatus.OK);
		}else {
			return new ResponseEntity<MutantResponseBody>(new MutantResponseBody("You're a plain human"), HttpStatus.FORBIDDEN );
		}
	}
	
	@GetMapping("/stats/")
	public ResponseEntity<StatsResponseBody> getStats(){		
		return new ResponseEntity<StatsResponseBody>(iMainService.getStats(),HttpStatus.OK);
	}

}
