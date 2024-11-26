package com.example.quanlylaptop.Model;

public class Product {
    private int id;
    private String name;
    private String code;
    private double price;
    private String imageUrl;

    // Constructor cũ (với ID)
    public Product(int id, String name, String code, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Constructor mới (không cần ID, ID sẽ được gán tự động từ cơ sở dữ liệu)
    public Product(String name, String code, double price, String imageUrl) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getter và Setter cho các thuộc tính (nếu cần)
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }
}


