package com.fanta.fanta;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Collections;
import static org.mockito.Mockito.when;


@SpringBootTest
class FantaApplicationTests {

	@Configuration
	static class TestConfig {
		@Bean
		public VectorStore vectorStore() {
			VectorStore mock = Mockito.mock(VectorStore.class);
			when(mock.similaritySearch(Mockito.anyString()))
					.thenReturn(Collections.emptyList());
			when(mock.similaritySearch(Mockito.any(org.springframework.ai.vectorstore.SearchRequest.class)))
					.thenReturn(Collections.emptyList());
			return mock;
		}
	}

	@Autowired
	private VectorStore vectorStore;


	@Test
	void contextLoads() {
	}

}
