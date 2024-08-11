package com.medscape.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Service
public class HygraphService {

    private static final Logger logger = Logger.getLogger(HygraphService.class.getName());

    private final String apiUrl;
    private final String apiToken;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HygraphService(
            @Value("${hygraph.api.url}") String apiUrl,
            @Value("${hygraph.api.token}") String apiToken,
            RestTemplate restTemplate,
            ObjectMapper objectMapper) {
        this.apiUrl = apiUrl;
        this.apiToken = apiToken;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public JsonNode fetchContentFromHygraph(String moduleQuery) {
        try {
            // Ensure token is present
            if (apiToken == null || apiToken.isEmpty()) {
                throw new IllegalStateException("API token is not set.");
            }

            logger.info("Fetching content from Hygraph with URL: " + apiUrl + " for module: " + moduleQuery);

            // Define the GraphQL query
            String query = "{ \"query\": \"{ " + moduleQuery + " }\" }";

            // Set up headers with authorization token
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiToken);

            // Create the request entity
            HttpEntity<String> entity = new HttpEntity<>(query, headers);

            // Execute the POST request
            String response = restTemplate.postForObject(apiUrl, entity, String.class);

            logger.info("Response received: " + response);

            // Parse the response
            JsonNode rootNode = objectMapper.readTree(response).path("data");
            return rootNode;
        } catch (Exception e) {
            // Log the error and return null or handle the exception as needed
            e.printStackTrace();
            logger.severe("Error fetching content: " + e.getMessage());
            return null;
        }
    }
}
