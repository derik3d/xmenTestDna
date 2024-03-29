package com.meli.xmenTestDna.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.xmenTestDna.entity.common.StatsResponseBody;
import com.meli.xmenTestDna.entity.dto.DnaSequenceDAO;
import com.meli.xmenTestDna.repository.IDnaSequenceRepository;

@Service
public class MainService implements IMainService {
	
	@Autowired
	ITransformDataService iTransformDataService;
	@Autowired
	IMutantService iMutantService;
	@Autowired
	IDnaSequenceRepository iDnaSequenceRepository;

	@Override
	public boolean isMutant(String... dna) throws NoSuchAlgorithmException{

		//matrix size
		Integer N = dna.length;
		//optimize dna sequence
		String madeSequence = iTransformDataService.makeSequence(dna);
		//get hash of optimized sequence
		String getSequenceHash = iTransformDataService.getOptimizedSequenceHash(madeSequence);
		//look for the sequence on db to save work
		Optional<DnaSequenceDAO> findByIdMadeSequence = iDnaSequenceRepository.findById(getSequenceHash);
		
		//if already processed, return answer
		if(findByIdMadeSequence.isPresent()) {
			return findByIdMadeSequence.get().getMutant();
		}
		
		//if not, process sequence and calculate if is mutant or not
		Boolean mutant = iMutantService.isMutant(madeSequence, N);
		
		//save on db
		DnaSequenceDAO dnaSequenceDAO = new DnaSequenceDAO();
		dnaSequenceDAO.setHash(getSequenceHash);
		dnaSequenceDAO.setSequence(madeSequence);
		dnaSequenceDAO.setMutant(mutant);
		iDnaSequenceRepository.save(dnaSequenceDAO);
		//return if mutant finally
		return mutant;
	}

	@Override
	public StatsResponseBody getStats() {
		
		//get number of mutants
		Integer countByMutant = iDnaSequenceRepository.countByMutant(true);
		//get number of humans
		Integer countByHuman = iDnaSequenceRepository.countByMutant(false);
		//calculate ratio mutants humans if there are more than one human
		Double ratio = countByHuman!=0?countByMutant.doubleValue() / countByHuman.doubleValue():null;
		StatsResponseBody statsResponseBody = new StatsResponseBody();
		statsResponseBody.setCount_mutant_dna(countByMutant);
		statsResponseBody.setCount_human_dna(countByHuman);
		statsResponseBody.setRatio(ratio);
		//build response
		return  statsResponseBody;
	}

}
