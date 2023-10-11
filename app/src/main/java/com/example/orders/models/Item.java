package com.example.orders.models;

import java.util.List;

public class Item {
    private String tittle, price, info, img;

    public Item() {}

    public Item(String tittle, String price, String info, String img) {
        this.tittle = tittle;
        this.price = price;
        this.info = info;
        this.img = img;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
