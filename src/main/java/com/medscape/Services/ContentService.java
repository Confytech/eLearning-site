package com.medscape.Services;


import com.medscape.Models.Content;
import com.medscape.Repositories.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public Content save(Content content) {
        return contentRepository.save(content);
    }

    public List<Content> findAll() {
        return contentRepository.findAll();
    }

    public Content findById(Long id) {
        return contentRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        contentRepository.deleteById(id);
    }
}
