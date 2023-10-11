package com.example.orders.models;

public class Cart {

    private int amount;
    private String tittle;


    public Cart(){}
    public Cart(String tittle, int amount) {
        this.tittle = tittle;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @Override
    public String toString() {
        return tittle + ": " + amount + " шт.";
    }
}
