package com.cellpointmobile.repoviewer.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Repository model with their constructors, getters, setters
 */
public class Repository extends RealmObject {
    @PrimaryKey
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

    public Repository(){}

    public Repository(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }
}
