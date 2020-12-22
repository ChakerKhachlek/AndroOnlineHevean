package com.example.onlineheaven.model;

public class Anime {

    Integer id;
    String name;
    String description;
    String release_date;
    String rating;
    String image_url;
    String file_url;
    Integer category_id;

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }


    public Anime(Integer id, String name, String description, String release_date, String rating, String image_url, String file_url, Integer category_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.release_date = release_date;
        this.rating = rating;
        this.image_url = image_url;
        this.file_url = file_url;
        this.category_id = category_id;
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

    public void setName(String animeName) {
        this.name = animeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }


}
