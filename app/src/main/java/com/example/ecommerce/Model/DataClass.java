package com.example.ecommerce.Model;

public class DataClass {
    private String Title;
    private String Desc;
    private String Price;
    private String Image;
    private String key;
    private String quantity;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public DataClass() {
    }

    public DataClass(String title, String desc, String price, String image) {
        Title = title;
        Desc = desc;
        Price = price;
        Image = image;
    }

    public DataClass(String title, String desc, String price, String image, String quantity) {
        Title = title;
        Desc = desc;
        Price = price;
        Image = image;
        this.quantity = quantity;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
