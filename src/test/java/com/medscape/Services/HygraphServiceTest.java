package com.medscape.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HygraphServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private HygraphService hygraphService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        hygraphService = new HygraphService(
                "https://api.hygraph.com/v2/your-endpoint",
                "your-api-token",
                restTemplate,
                objectMapper
        );
    }

    @Test
    public void testFetchContentFromHygraph_Success() throws Exception {
        // Prepare mock data
        String moduleQuery = "interviews { id title summary { html markdown raw } details { html markdown raw } }";
        String mockResponse = "{ \"data\": { \"interviews\": [{ \"id\": \"1\", \"title\": \"Test Interview\" }] } }";
        JsonNode mockJsonNode = mock(JsonNode.class);

        // Mock RestTemplate response
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        // Mock ObjectMapper response
        when(objectMapper.readTree(mockResponse)).thenReturn(mockJsonNode);
        when(mockJsonNode.path("data")).thenReturn(mockJsonNode);

        // Call the method to test
        JsonNode result = hygraphService.fetchContentFromHygraph(moduleQuery);

        // Verify
        assertNotNull(result);
        verify(restTemplate, times(1)).postForObject(anyString(), any(HttpEntity.class), eq(String.class));
        verify(objectMapper, times(1)).readTree(mockResponse);
    }

    @Test
    public void testFetchContentFromHygraph_Error() throws Exception {
        // Prepare mock data
        String moduleQuery = "invalidQuery { id }";
        String mockResponse = "Invalid response";

        // Mock RestTemplate to throw an exception
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new RuntimeException("Error in fetching data"));

        // Call the method to test
        JsonNode result = hygraphService.fetchContentFromHygraph(moduleQuery);

        // Verify
        assertNull(result);
        verify(restTemplate, times(1)).postForObject(anyString(), any(HttpEntity.class), eq(String.class));
        verify(objectMapper, times(0)).readTree(anyString());
    }
}
