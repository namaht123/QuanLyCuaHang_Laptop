package com.example.duan1_nhom3.model;

public class SanPham {
    private int id;
    private String name;
    private int quantity;
    private int price;
    private String color;
    private String size;
    private String brand;
    private int status; // Trường trạng thái

    public SanPham() {
    }

    public SanPham(int id, String name, int quantity, int price, String color, String size, String brand, int status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.status = status;
    }

    public SanPham(String name, int quantity, int price, String color, String size, String brand, int status) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.color = color;
        this.size = size;
        this.brand = brand;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
