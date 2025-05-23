package com.fanta.fanta.vectorRepository;

import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VectorRepository {
    @Autowired
    private VectorStore vectorStore;

    public void saveChunks(List<Document> documents) {
        vectorStore.add(documents);
    }
}
