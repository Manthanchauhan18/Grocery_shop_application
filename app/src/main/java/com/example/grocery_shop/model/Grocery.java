package com.example.grocery_shop.model;

public class Grocery
{
    String gid;
    String imageUrl;
    String gName;
    int price;
    int amount;

    public Grocery(){

    }

    public Grocery(String gid, String imageUrl, String gName, int price,int amount) {

        this.gid = gid;
        this.imageUrl = imageUrl;
        this.gName = gName;
        this.price = price;
        this.amount = amount;

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
