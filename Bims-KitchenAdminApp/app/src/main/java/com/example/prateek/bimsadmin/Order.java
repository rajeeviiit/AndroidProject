package com.example.prateek.bimsadmin;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RajeevPC on 2/18/2017.
 */

public class Order {



    private String address, amount, name, number, mail, coordinates;
    public Object items;
//    private List<Food> item;

    public Order(String address, String name, String amount, String number, String mail, String coordinates) {
        this.address = address;
        this.name = name;
        this.amount = amount;
        this.number = number;
        this.mail = mail;
        this.coordinates = coordinates;
    }


    public Order() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

//    public List<Food> getItem() {
//        return item;
//    }
//
    public Object getItems(){
        return this.items;
    }

    public void setItem(Object item) {
        this.items = item;
    }

    public static class Item implements Parcelable{

        public String food;
        public String quantity;
        public String price;

        public Item(String food, String price, String quantity) {
            this.food = food;

            this.price = price;
            this.quantity = quantity;
        }

        protected Item(Parcel in) {
            food = in.readString();
            quantity = in.readString();
            price = in.readString();
        }

        public static final Creator<Item> CREATOR = new Creator<Item>() {
            @Override
            public Item createFromParcel(Parcel in) {
                return new Item(in);
            }

            @Override
            public Item[] newArray(int size) {
                return new Item[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(food);
            dest.writeString(quantity);
            dest.writeString(price);
        }
    }
//
//    }
}
