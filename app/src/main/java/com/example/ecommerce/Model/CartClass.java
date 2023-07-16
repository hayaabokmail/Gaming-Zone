package com.example.ecommerce.Model;

public class CartClass {
    private String Title;

    private String Price;
    private String Image;
    private String key;
    private String desc;
    private String quantity;

    public CartClass() {
    }

    public CartClass(String title, String price, String image, String key, String desc, String quantity) {
        Title = title;
        Price = price;
        Image = image;
        this.key = key;
        this.desc = desc;
        this.quantity = quantity;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
