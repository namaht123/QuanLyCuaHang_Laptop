package com.example.duan1_nhom3.model;

public class GioHangChiTiet {
    public int id,id_Gio_Hang,id_San_Pham,giaTien,soLuong;
    public String tenSP,Mau,Size,Hang;

    public GioHangChiTiet() {
    }

    public GioHangChiTiet(int id, int id_Gio_Hang, int id_San_Pham, int giaTien, int soLuong, String tenSP, String mau, String size, String hang) {
        this.id = id;
        this.id_Gio_Hang = id_Gio_Hang;
        this.id_San_Pham = id_San_Pham;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
        this.tenSP = tenSP;
        Mau = mau;
        Size = size;
        Hang = hang;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_Gio_Hang() {
        return id_Gio_Hang;
    }

    public void setId_Gio_Hang(int id_Gio_Hang) {
        this.id_Gio_Hang = id_Gio_Hang;
    }

    public int getId_San_Pham() {
        return id_San_Pham;
    }

    public void setId_San_Pham(int id_San_Pham) {
        this.id_San_Pham = id_San_Pham;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMau() {
        return Mau;
    }

    public void setMau(String mau) {
        Mau = mau;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getHang() {
        return Hang;
    }

    public void setHang(String hang) {
        Hang = hang;
    }
}
