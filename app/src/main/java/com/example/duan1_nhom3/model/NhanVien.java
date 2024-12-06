package com.example.duan1_nhom3.model;public class NhanVien {
    private int id;
    private String tenNV;
    private String email;
    private String SDT;
    private String diachi;
    private String username;
    private String password;
    private String chucvu;

    private String trangthai;

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public NhanVien() {
    }

    public NhanVien(int id, String tenNV, String email, String SDT, String diachi, String username, String password, String chucvu, String trangthai) {
        this.id = id;
        this.tenNV = tenNV;
        this.email = email;
        this.SDT = SDT;
        this.diachi = diachi;
        this.username = username;
        this.password = password;
        this.chucvu = chucvu;
        this.trangthai = trangthai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
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

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }
}
