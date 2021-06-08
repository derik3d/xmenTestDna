package com.meli.xmenTestDna.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.xmenTestDna.dao.IDnaSequenceDAO;
import com.meli.xmenTestDna.entity.common.StatsResponseBody;
import com.meli.xmenTestDna.entity.dto.DnaSequenceDTO;

@Service
public class MainService implements IMainService {
	
	ITransformDataService iTransformDataService;
	IMutantService iMutantService;
	IDnaSequenceDAO iDnaSequenceDAO;

	@Override
	public boolean isMutant(String... dna) {

		//optimize dna sequence
		String madeSequence = iTransformDataService.makeSequence(dna);
		//get hash of optimized sequence
		String getSequenceHash = iTransformDataService.getOptimizedSequenceHash(madeSequence);
		//look for the sequence on db to save work
		Optional<DnaSequenceDTO> findByIdSequenceOptimized = iDnaSequenceDAO.findById(madeSequence);
		
		//if already processed, return answer
		if(findByIdSequenceOptimized.isPresent()) {
			return findByIdSequenceOptimized.get().getMutant();
		}
		
		//if not, process sequence and calculate if is mutant or not
		Boolean mutant = iMutantService.isMutant(madeSequence);
		
		//save on db
		iDnaSequenceDAO.save(new DnaSequenceDTO(getSequenceHash, madeSequence, mutant));
		
		//return if mutant finally
		return mutant;
	}

	@Override
	public StatsResponseBody getStats() {
		
		//get number of mutants
		Integer countByMutant = iDnaSequenceDAO.countByMutant(true);
		//get number of humans
		Integer countByHuman = iDnaSequenceDAO.countByMutant(false);
		//calculate ratio mutants humans
		Double ratio = countByMutant.doubleValue() / countByHuman.doubleValue();
		
		//build response
		return new StatsResponseBody(countByMutant, countByHuman, ratio) ;
	}

}
