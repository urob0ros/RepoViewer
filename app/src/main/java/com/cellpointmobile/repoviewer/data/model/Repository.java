package com.cellpointmobile.repoviewer.data.model;

/**
 * Repository model with their constructors, getters, setters
 */
public class Repository {
    private String name;
    private String language;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public Repository(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }
}
