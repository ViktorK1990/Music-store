package com.example.orders.models;

import java.util.List;

public class Category {
    private String id;
    private String title, img;
    private List<Item> items;

    public Category() {
    }

    public Category(String id,String title, String img, List<Item> items) {
        this.title = title;
        this.img = img;
        this.items = items;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}


