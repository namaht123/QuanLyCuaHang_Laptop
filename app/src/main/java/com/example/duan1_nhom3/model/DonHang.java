package com.example.duan1_nhom3.model;

import java.util.Date;

public class DonHang {
private int id,idKH,idNV,trangThai,tongTien;
private Date ngayMua;
private String tenKH,tenNV;

    public DonHang(int id, int idKH, int idNV, int trangThai, int tongTien, Date ngayMua, String tenKH, String tenNV) {
        this.id = id;
        this.idKH = idKH;
        this.idNV = idNV;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.ngayMua = ngayMua;
        this.tenKH = tenKH;
        this.tenNV = tenNV;
    }

    public DonHang() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdKH() {
        return idKH;
    }

    public void setIdKH(int idKH) {
        this.idKH = idKH;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(Date ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }
}
