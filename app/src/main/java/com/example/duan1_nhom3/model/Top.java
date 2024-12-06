package com.example.duan1_nhom3.model;

public class Top {
    private String tenSanPham;
    private int soLuongBan;
    private int gia;
    private String thuongHieu;

    public Top() {
    }


    public Top(String tenSanPham, int soLuongBan, int gia, String thuongHieu) {
        this.tenSanPham = tenSanPham;
        this.soLuongBan = soLuongBan;
        this.gia = gia;
        this.thuongHieu = thuongHieu;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getThuongHieu() {
        return thuongHieu;
    }

    public void setThuongHieu(String thuongHieu) {
        this.thuongHieu = thuongHieu;
    }
}
