package com.meli.xmenTestDna;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.meli.xmenTestDna.repository.IDnaSequenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
		@AutoConfigureMockMvc
class XmenTestDnaApplicationTests {
	

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IDnaSequenceRepository repo;

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
			

			
			
			ResultActions perform4 = mvc.perform(get("/stats/")
			  .contentType(MediaType.APPLICATION_JSON));
			
			perform4.andExpect(status().isOk())
			  .andExpect(jsonPath("$.count_mutant_dna").value("1"))
			  .andExpect(jsonPath("$.count_human_dna").value("1"))
			  .andExpect(jsonPath("$.ratio").value("1"));
			
			
	}

}
