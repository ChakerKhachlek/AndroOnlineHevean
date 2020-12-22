package com.example.onlineheaven.model;

import java.util.List;

public class Category {
    Integer id;
    String name;
    List<Anime> animes;

    public List<Anime> getAnimes() {
        return animes;
    }

    public void setAnimes(List<Anime> animeList) {
        this.animes = animeList;
    }

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
