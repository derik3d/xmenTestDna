package com.meli.xmenTestDna.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.meli.xmenTestDna.entity.common.MutantResponseBody;
import com.meli.xmenTestDna.entity.common.StatsResponseBody;
import com.meli.xmenTestDna.entity.common.MutantRequestBody;
import com.meli.xmenTestDna.service.IMainService;

@RestController
public class MutantController {
	
	@Autowired
	IMainService iMainService;
	
	@PostMapping("/mutant/")
	public ResponseEntity<MutantResponseBody> isMutant(@RequestBody MutantRequestBody mutantRequestBody) {
		
		
		try {
			if(iMainService.isMutant(mutantRequestBody.getDna())) {
				MutantResponseBody mutantResponseBody = new MutantResponseBody();
				mutantResponseBody.setResult("Congrats, you're a mutant");
				return new ResponseEntity<MutantResponseBody>(mutantResponseBody , HttpStatus.OK);
			}else {
				MutantResponseBody mutantResponseBody = new MutantResponseBody();
				mutantResponseBody.setResult("You're a plain human");
				return new ResponseEntity<MutantResponseBody>(mutantResponseBody , HttpStatus.FORBIDDEN );
			}
		} catch (NoSuchAlgorithmException e) {
			MutantResponseBody mutantResponseBody = new MutantResponseBody();
			mutantResponseBody.setResult ("Error servidor NoSuchAlgorithmException");
			return new ResponseEntity<MutantResponseBody>(mutantResponseBody, HttpStatus.INTERNAL_SERVER_ERROR );
		}
	}
	
	@GetMapping("/stats/")
	public ResponseEntity<StatsResponseBody> getStats(){		
		return new ResponseEntity<StatsResponseBody>(iMainService.getStats(),HttpStatus.OK);
	}

}
