package com.example.prateek.bimsapp;

import java.util.List;

/**
 * Created by prateek on 3/2/17.
 */

public class Order {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public List<Food> getItem() {
        return item;
    }

    public void setItem(List item) {
        this.item = item;
    }

    private String address;
    private String amount;
    private String number;
    private String mail;
    private String coordinates;
    private List<Food> item;
}
