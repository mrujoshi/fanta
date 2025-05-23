package com.fanta.fanta.service;

import org.jsoup.Jsoup;
import org.springframework.ai.document.Document;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class htmlLoader {
    //from URL
    public Document loadFromUrl(String url) {
        try {
            // Jsoup fetches and parses the HTML from the URL
            String textContent = Jsoup.connect(url).get().text();

            Document document = new Document(textContent);
            document.getMetadata().put("source", url);

            return document;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load documentation from URL: " + url, e);
        }
    }

    //from file
    public List<Document> loadFromFile(String docsPath) {
        List<Document> documents = new ArrayList<Document>();
        Path docDir = Paths.get(docsPath);

        if (!Files.exists(docDir) || !Files.isDirectory(docDir)) {
            throw new IllegalArgumentException("Invalid documentation path: " + docsPath);
        }

        try {
            Files.walk(docDir)
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".html"))
                    .forEach(htmlFile -> {
                        try {
                            String htmlContent = Files.readString(htmlFile);
                            String textContent = Jsoup.parse(htmlContent).text();  // Strip HTML to plain text

                            Document document = new Document(textContent);
                            document.getMetadata().put("source", htmlFile.toString());

                            documents.add(document);
                        } catch (IOException e) {
                            System.err.println("Failed to read file: " + htmlFile + ", error: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Failed to load documentation from path: " + docsPath, e);
        }

        return documents;
    }
}
