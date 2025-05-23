package com.fanta.fanta.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.ai.document.Document;
import java.util.List;

public class textLoader {
    private static final int CHUNK_SIZE = 500; // characters per chunk

    public List<Document> loadText(String docsPath) {
        List<Document> documents = new ArrayList<>();

        try {
            Path docsDir = Paths.get(docsPath);

            if (!Files.isDirectory(docsDir)) {
                throw new IllegalArgumentException("Provided path is not a directory: " + docsPath);
            }

            // Walk through all text files
            Files.walk(docsDir)
                    .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".txt"))
                    .forEach(path -> {
                        try {
                            String content = Files.readString(path, StandardCharsets.UTF_8);
                            documents.addAll(splitIntoChunks(content, path.getFileName().toString()));
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to read file: " + path, e);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException("Error walking through documentation directory.", e);
        }

        return documents;
    }

    private List<Document> splitIntoChunks(String content, String filename) {
        List<Document> chunks = new ArrayList<Document>();

        int length = content.length();
        int start = 0;

        while (start < length) {
            int end = Math.min(start + CHUNK_SIZE, length);
            String chunkText = content.substring(start, end);

            Document chunk = new Document(
                    chunkText,
                    // Metadata (optional but useful)
                    Map.of(
                            "source", filename,
                            "start", String.valueOf(start),
                            "end", String.valueOf(end)
                    )
            );

            chunks.add(chunk);
            start = end;
        }

        return chunks;
    }
}
