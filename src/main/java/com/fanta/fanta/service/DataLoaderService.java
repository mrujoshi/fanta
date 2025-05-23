package com.fanta.fanta.service;

import com.fanta.fanta.vectorRepository.VectorRepository;
import groovy.util.logging.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DataLoaderService {
    @Autowired
    VectorRepository vectorRepository;


    public List<Document> loadData(String path){
        List<Document> documents = path.startsWith("http://") || path.startsWith("https://")
                ? List.of(new htmlLoader().loadFromUrl(path))
                : new textLoader().loadText(path);
        vectorRepository.saveChunks(documents);
        System.out.println("embeddings saved successfully " + path);
        return documents;
    }
}
