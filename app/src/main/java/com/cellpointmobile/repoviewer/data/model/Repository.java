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
    private int starCount;
    private int forkCount;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    public int getForkCount() {
        return forkCount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public Repository(){}

    public Repository(String name, String description, String language, int starCount, int forkCount) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.starCount = starCount;
        this.forkCount = forkCount;
    }
}
