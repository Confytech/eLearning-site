package com.medscape.Models;

import java.util.List;

public class Module {

    private String id;
    private String title;
    private String description;
    private List<Media> media;

    // Constructors, getters, and setters

    public static class Media {
        private String url;

        // Constructors, getters, and setters
    }
}
