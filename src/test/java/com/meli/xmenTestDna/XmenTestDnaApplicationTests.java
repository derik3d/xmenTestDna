package com.meli.xmenTestDna;

import org.junit.jupiter.api.Assertions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

//import com.meli.xmenTestDna.repository.IDnaSequenceRepository;
import com.meli.xmenTestDna.service.IMutantService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class XmenTestDnaApplicationTests {
	

    @Autowired
    private MockMvc mvc;

    //@Autowired
    //private IDnaSequenceRepository repo;

    @Autowired
    private IMutantService iMutantService;
    
    @Test
    void testMatrixOperations(){
    	
    	for(Entry<String,Integer> entry : getMutantMatrices().entrySet())
    		Assertions.assertTrue( iMutantService.isMutant(entry.getKey(), entry.getValue()) );
    	

    	for(Entry<String,Integer> entry : getHumanMatrices().entrySet())
    		Assertions.assertFalse( iMutantService.isMutant(entry.getKey(), entry.getValue()) );
    	
    }

	@Test
	void contextLoads() throws Exception {

		
		ResultActions perform0 = mvc.perform(get("/stats/")
			  .contentType(MediaType.APPLICATION_JSON));
			
			perform0.andExpect(status().isOk())
			  .andExpect(jsonPath("$.count_mutant_dna").value("0"))
			  .andExpect(jsonPath("$.count_human_dna").value("0"))
			  .andExpect(jsonPath("$.ratio").value(IsNull.nullValue()));
			
			

			ResultActions perform1 = mvc.perform(post("/mutant/")
				.content("{\"dna\":["
						+ "\"AGCT\","
						+ "\"AACT\","
						+ "\"AAAG\","
						+ "\"AGAA\"]}")
				.contentType(MediaType.APPLICATION_JSON));
			

			perform1.andExpect(status().isOk());
			
			
			

			ResultActions perform2 = mvc.perform(get("/stats/")
			  .contentType(MediaType.APPLICATION_JSON));
			
			perform2.andExpect(status().isOk())
			  .andExpect(jsonPath("$.count_mutant_dna").value("1"))
			  .andExpect(jsonPath("$.count_human_dna").value("0"))
			  .andExpect(jsonPath("$.ratio").value(IsNull.nullValue()));
			



			ResultActions perform3 = mvc.perform(post("/mutant/")
				.content("{\"dna\":["
						+ "\"AGCT\","
						+ "\"AGCT\","
						+ "\"GAAG\","
						+ "\"AGAA\"]}")
				.contentType(MediaType.APPLICATION_JSON));
			

			perform3.andExpect(status().isForbidden());


			ResultActions perform3cpy = mvc.perform(post("/mutant/")
				.content("{\"dna\":["
						+ "\"AGCT\","
						+ "\"AGCT\","
						+ "\"GAAG\","
						+ "\"AGAA\"]}")
				.contentType(MediaType.APPLICATION_JSON));
			

			perform3cpy.andExpect(status().isForbidden());
			

			
			
			ResultActions perform4 = mvc.perform(get("/stats/")
			  .contentType(MediaType.APPLICATION_JSON));
			
			perform4.andExpect(status().isOk())
			  .andExpect(jsonPath("$.count_mutant_dna").value("1"))
			  .andExpect(jsonPath("$.count_human_dna").value("1"))
			  .andExpect(jsonPath("$.ratio").value("1.0"));
			
			
	}
	
	HashMap<String,Integer> getMutantMatrices(){
    	HashMap<String,Integer> mutantMatrices = new HashMap<>();
    	mutantMatrices.put(""
    			+ "baaab"
    			+ "ababe"
    			+ "acbde"
    			+ "abcbe"
    			+ "brzce"
    			+ "",5);
    	mutantMatrices.put(""
    			+ "aaaaa"
    			+ "ababe"
    			+ "acade"
    			+ "abcbe"
    			+ "arzce"
    			+ "",5);
    	mutantMatrices.put(""
    			+ "aaaab"
    			+ "aaaae"
    			+ "acbde"
    			+ "abcbe"
    			+ "brzce"
    			+ "",5);
    	mutantMatrices.put(""
    			+ "aazab"
    			+ "aazae"
    			+ "aabde"
    			+ "aacbe"
    			+ "bazce"
    			+ "",5);
    	return mutantMatrices;
	}
	
	HashMap<String,Integer> getHumanMatrices(){
    	HashMap<String,Integer> humanMatrices = new HashMap<>();
    	humanMatrices.put(""
    			+ "AArcA"
    			+ "abade"
    			+ "bacde"
    			+ "abcde"
    			+ "rrbrr"
    			+ "",5);
    	return humanMatrices;
	}
	

}
