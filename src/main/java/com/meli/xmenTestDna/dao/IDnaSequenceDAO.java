package com.meli.xmenTestDna.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.meli.xmenTestDna.entity.dto.DnaSequenceDTO;
@Repository
public interface IDnaSequenceDAO extends CrudRepository<DnaSequenceDTO,String>{
	Optional<DnaSequenceDTO> findById(String id);
	Integer countByMutant(Boolean value);
}
