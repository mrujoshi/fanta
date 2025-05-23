package com.fanta.fanta.controller;

import com.fanta.fanta.service.RAGService;
import org.springframework.ai.chroma.vectorstore.ChromaVectorStore;
import org.springframework.ai.document.Document;
import com.fanta.fanta.service.DataLoaderService;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class RAGController {
    @Autowired
    private RAGService ragService;

    @Autowired
    DataLoaderService documentLoaderService;

    @PostMapping
    public String chat(@RequestBody String question) {
        return ragService.query(question);
    }

    @GetMapping("/load-docs")
    public List<Document> loadDocs(@RequestParam String source) {
        return documentLoaderService.loadData(source);
    }
}
