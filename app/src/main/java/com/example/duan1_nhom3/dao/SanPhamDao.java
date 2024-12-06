package com.example.duan1_nhom3.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.SanPham;

import java.util.ArrayList;

public class SanPhamDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public SanPhamDao(Context context) {
        dbHelper = new db(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<SanPham> getAllProductsWithDetails() {
        ArrayList<SanPham> productList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT SanPham.id, SanPham.ten, SanPham.so_luong, SanPham.gia_tien, SanPham.trang_thai, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id " +
                "AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Màu sắc')) AS mauSac, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id " +
                "AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Kích thước')) AS kichThuoc, " +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id " +
                "AND id_thuoc_tinh = (SELECT id FROM ThuocTinh WHERE ten = 'Hãng')) AS hang " +
                "FROM SanPham", null);

        if (cursor.moveToFirst()) {
            do {
                SanPham product = new SanPham();
                product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                product.setName(cursor.getString(cursor.getColumnIndex("ten")));
                product.setQuantity(cursor.getInt(cursor.getColumnIndex("so_luong")));
                product.setPrice(cursor.getInt(cursor.getColumnIndex("gia_tien")));
                product.setColor(cursor.getString(cursor.getColumnIndex("mauSac")));
                product.setSize(cursor.getString(cursor.getColumnIndex("kichThuoc")));
                product.setBrand(cursor.getString(cursor.getColumnIndex("hang")));
                product.setStatus(cursor.getInt(cursor.getColumnIndex("trang_thai"))); // Thêm trường trạng thái

                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return productList;
    }
    public int getSoLuongSanPham(int idSanPham) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int soLuong = 0;
        String query = "SELECT so_luong FROM SanPham WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idSanPham)});
        if (cursor.moveToFirst()) {
            soLuong = cursor.getInt(cursor.getColumnIndex("so_luong"));
        }
        cursor.close();
        db.close();
        return soLuong;
    }


    public long addProduct(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put("ten", sanPham.getName());
        values.put("so_luong", sanPham.getQuantity());
        values.put("gia_tien", sanPham.getPrice());
        values.put("trang_thai",1);

        // Thêm dữ liệu vào bảng SanPham
        long productId = db.insert("SanPham", null, values);

        // Thêm dữ liệu vào bảng ThuocTinhSanPham cho kích thước
        ContentValues sizeValues = new ContentValues();
        sizeValues.put("id_san_pham", productId);
        sizeValues.put("id_thuoc_tinh", getIdForAttribute("Kích thước"));
        sizeValues.put("gia_tri_thuoc_tinh", sanPham.getSize());
        db.insert("ThuocTinhSanPham", null, sizeValues);

        // Thêm dữ liệu vào bảng ThuocTinhSanPham cho màu sắc
        ContentValues colorValues = new ContentValues();
        colorValues.put("id_san_pham", productId);
        colorValues.put("id_thuoc_tinh", getIdForAttribute("Màu sắc"));
        colorValues.put("gia_tri_thuoc_tinh", sanPham.getColor());
        db.insert("ThuocTinhSanPham", null, colorValues);

        // Thêm dữ liệu vào bảng ThuocTinhSanPham cho hãng
        ContentValues brandValues = new ContentValues();
        brandValues.put("id_san_pham", productId);
        brandValues.put("id_thuoc_tinh", getIdForAttribute("Hãng"));
        brandValues.put("gia_tri_thuoc_tinh", sanPham.getBrand());
        db.insert("ThuocTinhSanPham", null, brandValues);

        return productId;
    }

    // Phương thức này sẽ trả về id của thuộc tính dựa trên tên thuộc tính
    @SuppressLint("Range")
    private int getIdForAttribute(String attributeName) {
        Cursor cursor = db.rawQuery("SELECT id FROM ThuocTinh WHERE ten = ?", new String[]{attributeName});
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        return id;
    }

    public void deleteSanPham(int sanPhamId) {
        db.delete("SanPham", "id=?", new String[] {String.valueOf(sanPhamId)});
    }
    public void updateProduct(SanPham sanPham) {
        ContentValues values = new ContentValues();
        values.put("ten", sanPham.getName());
        values.put("so_luong", sanPham.getQuantity());
        values.put("gia_tien", sanPham.getPrice());
        values.put("trang_thai", sanPham.getStatus());

        // Xác định điều kiện cập nhật (WHERE clause) cho bảng SanPham
        String selectionSanPham = "id=?";
        String[] selectionArgsSanPham = {String.valueOf(sanPham.getId())};

        // Thực hiện cập nhật dữ liệu trong bảng SanPham
        db.update("SanPham", values, selectionSanPham, selectionArgsSanPham);

        // Cập nhật thông tin màu sắc, kích thước và hãng của sản phẩm trong bảng ThuocTinhSanPham
        ContentValues valuesThuocTinhSanPham = new ContentValues();
        valuesThuocTinhSanPham.put("gia_tri_thuoc_tinh", sanPham.getColor());
        // Thực hiện cập nhật màu sắc
        db.update("ThuocTinhSanPham", valuesThuocTinhSanPham, "id_san_pham=? AND id_thuoc_tinh=(SELECT id FROM ThuocTinh WHERE ten='Màu sắc')", selectionArgsSanPham);

        // Cập nhật thông tin kích thước
        valuesThuocTinhSanPham.clear();
        valuesThuocTinhSanPham.put("gia_tri_thuoc_tinh", sanPham.getSize());
        db.update("ThuocTinhSanPham", valuesThuocTinhSanPham, "id_san_pham=? AND id_thuoc_tinh=(SELECT id FROM ThuocTinh WHERE ten='Kích thước')", selectionArgsSanPham);

        // Cập nhật thông tin hãng
        valuesThuocTinhSanPham.clear();
        valuesThuocTinhSanPham.put("gia_tri_thuoc_tinh", sanPham.getBrand());
        db.update("ThuocTinhSanPham", valuesThuocTinhSanPham, "id_san_pham=? AND id_thuoc_tinh=(SELECT id FROM ThuocTinh WHERE ten='Hãng')", selectionArgsSanPham);
    }

}
