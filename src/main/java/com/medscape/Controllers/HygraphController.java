package com.medscape.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.medscape.Services.HygraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/hygraph")
public class HygraphController {

    private static final Logger logger = Logger.getLogger(HygraphController.class.getName());

    private final HygraphService hygraphService;

    @Autowired
    public HygraphController(HygraphService hygraphService) {
        this.hygraphService = hygraphService;
    }

    @GetMapping("/fetch-content")
    public JsonNode fetchContent(@RequestParam("module") String module) {
        logger.info("Received request to fetch content from Hygraph for module: " + module);
        String formattedModule = formatModuleQuery(module);
        JsonNode content = hygraphService.fetchContentFromHygraph(formattedModule);
        logger.info("Returning fetched content.");
        return content;
    }

    // Helper method to format the module query
    private String formatModuleQuery(String module) {
        switch (module.toLowerCase()) {
            case "introductions":
                return "introductions { id title description content }";
            case "interviews":
                return "interviews { id title summary { html markdown raw } details { html markdown raw } }";
            case "tests":
                return "tests { id name description results }";
            case "orders":
                return "orders { id orderNumber status content }";
            case "diagnoses":
                return "diagnoses { id diagnosisName description treatment }";
            case "decisionreviews":
                return "decisionReviews { id reviewer decision notes }";
            case "casereviews":
                return "caseReviews { id caseTitle feedback outcome }";
            default:
                throw new IllegalArgumentException("Invalid module: " + module);
        }
    }
}
