package com.digitalbrain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private static final Logger log = LoggerFactory.getLogger(AiService.class);

    @Value("${ollama.base-url}")
    private String ollamaBaseUrl;

    @Value("${ollama.model}")
    private String ollamaModel;

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Call Ollama and return a structured result map with:
     * title, summary, category, tags (JSON string), content, suggested_action
     */
    public Map<String, Object> processItem(String content, String type) {
        String prompt = """
                You are a personal knowledge management assistant.
                Analyze this content and return ONLY valid JSON, no extra text:
                {
                  "title": "short descriptive title",
                  "summary": "2-3 sentence summary",
                  "category": "one of study|tech|idea|task|reference",
                  "tags": ["array", "of", "2-4", "relevant tags"],
                  "content": "full note in markdown",
                  "suggested_action": "what the user should do with this"
                }
                Content type: %s
                Content: %s
                """.formatted(type, content);

        try {
            // Build Ollama /api/chat request body
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", ollamaModel);
            requestBody.put("stream", false);

            ArrayNode messages = objectMapper.createArrayNode();
            ObjectNode userMessage = objectMapper.createObjectNode();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.add(userMessage);
            requestBody.set("messages", messages);

            String bodyJson = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ollamaBaseUrl + "/api/chat"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(bodyJson))
                    .timeout(Duration.ofMinutes(3))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            JsonNode root = objectMapper.readTree(responseBody);
            String rawText = root.path("message").path("content").asText().trim();

            // Strip markdown code fences if present
            if (rawText.contains("```json")) {
                rawText = rawText.split("```json")[1].split("```")[0].trim();
            } else if (rawText.contains("```")) {
                rawText = rawText.split("```")[1].split("```")[0].trim();
            }

            JsonNode parsed = objectMapper.readTree(rawText);

            Map<String, Object> result = new HashMap<>();
            result.put("title", parsed.path("title").asText(content.substring(0, Math.min(50, content.length()))));
            result.put("summary", parsed.path("summary").asText(""));
            result.put("category", parsed.path("category").asText("reference"));
            result.put("content", parsed.path("content").asText("# Note\n\n" + content));
            result.put("suggested_action", parsed.path("suggested_action").asText(""));

            // Serialize tags back to JSON string
            JsonNode tagsNode = parsed.path("tags");
            String tagsJson = tagsNode.isMissingNode() ? "[]" : objectMapper.writeValueAsString(tagsNode);
            result.put("tags", tagsJson);

            return result;

        } catch (Exception e) {
            log.warn("⚠️  AI processing failed: {}", e.getMessage());
            // Fallback result
            Map<String, Object> fallback = new HashMap<>();
            fallback.put("title", content.substring(0, Math.min(50, content.length())).trim());
            fallback.put("summary", "Could not generate summary automatically.");
            fallback.put("category", "reference");
            fallback.put("tags", "[]");
            fallback.put("content", "# Note\n\n" + content);
            fallback.put("suggested_action", "Review this item manually.");
            return fallback;
        }
    }
}
