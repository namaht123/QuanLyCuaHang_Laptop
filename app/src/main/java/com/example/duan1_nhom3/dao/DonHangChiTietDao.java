package com.example.duan1_nhom3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.DonHangChiTiet;

import java.util.ArrayList;

public class DonHangChiTietDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public DonHangChiTietDao(Context context) {
        dbHelper = new db(context);
    }

    public ArrayList<DonHangChiTiet> getList(int donHangId) {
        ArrayList<DonHangChiTiet> chiTietDonHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Thực hiện câu truy vấn với DISTINCT để loại bỏ các bản ghi trùng lặp
        String query = "SELECT DISTINCT ChiTietDonHang.*, SanPham.ten AS tenSanPham, SanPham.gia_tien, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Màu sắc')) AS mauSac, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Kích thước')) AS kichThuoc " +
                "FROM ChiTietDonHang " +
                "INNER JOIN SanPham ON ChiTietDonHang.id_san_pham = SanPham.id " +
                "INNER JOIN ThuocTinhSanPham ON ChiTietDonHang.id_san_pham = ThuocTinhSanPham.id_san_pham " +
                "WHERE ChiTietDonHang.id_don_hang = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(donHangId)});

        if (cursor.moveToFirst()) {
            do {
                DonHangChiTiet chiTietDonHang = new DonHangChiTiet();
                chiTietDonHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                chiTietDonHang.setIdDH(cursor.getInt(cursor.getColumnIndex("id_don_hang")));
                chiTietDonHang.setIdSP(cursor.getInt(cursor.getColumnIndex("id_san_pham")));
                chiTietDonHang.setSoLuong(cursor.getInt(cursor.getColumnIndex("so_luong")));
                chiTietDonHang.setGia(cursor.getInt(cursor.getColumnIndex("gia_tien")) * cursor.getInt(cursor.getColumnIndex("so_luong")));
                chiTietDonHang.setTenSP(cursor.getString(cursor.getColumnIndex("tenSanPham")));
                chiTietDonHang.setMau(cursor.getString(cursor.getColumnIndex("mauSac")));
                chiTietDonHang.setKichThuoc(cursor.getString(cursor.getColumnIndex("kichThuoc")));
                chiTietDonHangList.add(chiTietDonHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return chiTietDonHangList;
    }

    public boolean deleteByDonHangId(int donHangId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("ChiTietDonHang", "id_don_hang=?", new String[]{String.valueOf(donHangId)});
        db.close();
        return result > 0;
    }

    public int getTotalPriceByDonHangId(int donHangId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT SUM(gia_tien) AS total_price FROM ChiTietDonHang WHERE id_don_hang = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(donHangId)});

        int totalPrice = 0;
        if (cursor.moveToFirst()) {
            totalPrice = cursor.getInt(cursor.getColumnIndex("total_price"));
        }

        cursor.close();
        db.close();

        return totalPrice;
    }

    public void updateProductQuantities(ArrayList<DonHangChiTiet> chiTietDonHangList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (DonHangChiTiet chiTietDonHang : chiTietDonHangList) {
            int maSanPham = chiTietDonHang.getIdSP();
            int soLuongChiTiet = chiTietDonHang.getSoLuong();
            int soLuongHienTai = getProductQuantityFromDatabase(db, maSanPham);
            int soLuongMoi = soLuongHienTai - soLuongChiTiet;
            updateProductQuantityInDatabase(db, maSanPham, soLuongMoi);
        }

        db.close();
    }

    private int getProductQuantityFromDatabase(SQLiteDatabase db, int maSanPham) {
        int soLuongHienTai = 0;
        String query = "SELECT so_luong FROM SanPham WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maSanPham)});
        if (cursor.moveToFirst()) {
            soLuongHienTai = cursor.getInt(cursor.getColumnIndex("so_luong"));
        }
        cursor.close();
        return soLuongHienTai;
    }

    private void updateProductQuantityInDatabase(SQLiteDatabase db, int maSanPham, int soLuongMoi) {
        ContentValues values = new ContentValues();
        values.put("so_luong", soLuongMoi);
        db.update("SanPham", values, "id = ?", new String[]{String.valueOf(maSanPham)});
    }
}


