package com.example.prateek.bimsadmin;

/**
 * Created by prateek on 7/10/16.
 */
public class Food {
    private String food, price, imageUrl, rating, availability;

        public Food() {
        }

        public Food(String food, String price, String imageUrl, String rating, String availability) {
            this.food = food;
            this.price = price;
            this.imageUrl = imageUrl;
            this.rating = rating;
            this.availability= availability;
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

        public void setImageUrl(String imageUrl){this.imageUrl = imageUrl;}

        public String getImageUrl(){ return  imageUrl;}

        public void setRating(String rating){this.rating = rating;}

        public String getRating(){ return rating;}

        public void setAvailability(String availability){this.availability = availability;}

        public String getAvailability(){ return availability;}




}
