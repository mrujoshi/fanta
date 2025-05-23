package com.fanta.fanta.configuration;

import org.springframework.ai.chroma.vectorstore.ChromaVectorStore;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrieverConfig {

    @Bean
    public DocumentRetriever documentRetriever(ChromaVectorStore chromaVectorStore
                                                ,@Value("${retriever.similarity.threshold:0.7}") double threshold
                                                , @Value("${retriever.top.k:5}") int topK) {
        return new VectorStoreDocumentRetriever(chromaVectorStore,threshold,topK,null);
    }
}