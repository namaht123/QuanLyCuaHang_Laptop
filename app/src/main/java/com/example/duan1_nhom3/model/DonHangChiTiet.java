package com.example.duan1_nhom3.model;

public class DonHangChiTiet {
    private int id,idSP,idDH,soLuong,gia;
private String tenSP,mau,kichThuoc;

    public DonHangChiTiet(int id, int idSP, int idDH, int soLuong, int gia, String tenSP, String mau, String kichThuoc) {
        this.id = id;
        this.idSP = idSP;
        this.idDH = idDH;
        this.soLuong = soLuong;
        this.gia = gia;
        this.tenSP = tenSP;
        this.mau = mau;
        this.kichThuoc = kichThuoc;
    }

    public DonHangChiTiet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public int getIdDH() {
        return idDH;
    }

    public void setIdDH(int idDH) {
        this.idDH = idDH;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }
}
