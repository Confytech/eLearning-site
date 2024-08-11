package com.medscape.Controllers;


import com.medscape.Models.Content;
import com.medscape.Services.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping
    public Content createContent(@RequestBody Content content) {
        return contentService.save(content);
    }

    @GetMapping
    public List<Content> getAllContent() {
        return contentService.findAll();
    }

    @GetMapping("/{id}")
    public Content getContentById(@PathVariable Long id) {
        return contentService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteContent(@PathVariable Long id) {
        contentService.deleteById(id);
    }
}
