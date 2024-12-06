package com.example.duan1_nhom3.model;public class KhachHang {

    private String tenNV;
    private String Diachi;
    private String SDT;
    private String Email;
    private String username;
    private String password;

    public KhachHang() {
    }

    public KhachHang(String tenKH, String diachi, String SDT, String email, String username, String password) {
        this.tenNV = tenNV;
        Diachi = diachi;
        this.SDT = SDT;
        Email = email;
        this.username = username;
        this.password = password;
    }

    public String getTenKH() {
        return tenNV;
    }

    public void setTenKH(String tenKH) {
        this.tenNV = tenNV;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
