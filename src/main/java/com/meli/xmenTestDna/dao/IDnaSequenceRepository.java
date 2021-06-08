package com.meli.xmenTestDna.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.meli.xmenTestDna.entity.dto.DnaSequenceDAO;
@Repository
public interface IDnaSequenceRepository extends CrudRepository<DnaSequenceDAO,String>{
	Optional<DnaSequenceDAO> findById(String id);
	Integer countByMutant(Boolean value);
}
