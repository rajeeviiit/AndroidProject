package com.example.prateek.bimsapp;

/**
 * Created by prateek on 6/11/16.
 */
public class FoodQuantity {

    private String food;
    private String price;
    private String quantity;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public FoodQuantity() {
    }

//        public Food(String food, String price) {
//            this.food = food;
//            this.price = price;
//        }

    public FoodQuantity(String food, String price, String quantity, String url){
        this.food = food;
        this.price = price;
        this.quantity = quantity;
        this.url = url;

    }

    public String getFood() {
        return food;
    }

    public void setFood(String name) {
        this.food = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity(){ return quantity;}

    public void setQuantity(String quantity){ this.quantity = quantity;}

}
