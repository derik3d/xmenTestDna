package com.meli.xmenTestDna.entity.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DnaSequenceDTO {

	@Id
	String hash;

	@Column
	String sequence;

	@Column
	Boolean mutant;

}