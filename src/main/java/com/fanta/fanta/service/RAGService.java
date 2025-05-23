package com.fanta.fanta.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;


@Service
public class RAGService {

    @Autowired
    private DocumentRetriever retriever; // Finds similar chunks
    @Autowired
    private ChatClient chatClient; // Connects to LLM

    public String query(String question) {
        List<Document> contextDocs = retriever.retrieve(new Query(question));
        String augmentedPrompt = buildPrompt(question, contextDocs);
        ChatResponse response = chatClient.prompt(new Prompt(augmentedPrompt)).call().chatResponse();
        return response.getResult().getOutput().getText();
    }

    private String buildPrompt(String question, List<Document> contextDocs) {
        // Include contextDocs text into the user query prompt
        return "Based on the following docs: " + contextDocs.toString() +
                "\nAnswer the question: " + question;
    }
}
