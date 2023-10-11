package com.example.orders.models;

import java.util.List;

public class Orders {
    private String phone, name, orders;

    public Orders(String phone, String name, String orders) {
        this.phone = phone;
        this.orders = orders;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

}
