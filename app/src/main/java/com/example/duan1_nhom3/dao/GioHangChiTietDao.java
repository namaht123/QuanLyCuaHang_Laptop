package com.example.duan1_nhom3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.GioHangChiTiet;

import java.util.ArrayList;
import java.util.List;

public class GioHangChiTietDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    GioHangChiTiet hangChiTiet;
    public List<GioHangChiTiet> list;

    public GioHangChiTietDao(Context context) {
        dbHelper = new db(context);
    }
    public ArrayList<GioHangChiTiet> getList(int gioHangID){
        ArrayList<GioHangChiTiet> gioHangChiTietList =new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query ="SELECT DISTINCT GioHangChiTiet.*,SanPham.ten AS tenSanPham,SanPham.gia_tien," +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh=(SELECT id FROM ThuocTinh WHERE ten='Màu sắc'))AS mauSac," +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh=(SELECT id FROM ThuocTinh WHERE ten='Kích thước'))AS kichThuoc," +
                "(SELECT gia_tri_thuoc_tinh FROM ThuocTinhSanPham WHERE id_san_pham = SanPham.id AND id_thuoc_tinh=(SELECT id FROM ThuocTinh WHERE ten='Hãng'))AS Hang" +
                " FROM GioHangChiTiet  " +
                "INNER JOIN SanPham ON GioHangChiTiet.id_san_pham = SanPham.id " +
                "INNER JOIN ThuocTinhSanPham ON GioHangChiTiet.id_san_pham = ThuocTinhSanPham.id_san_pham " +
                "WHERE GioHangChiTiet.id_gio_hang=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(gioHangID)});
        if(cursor.moveToFirst()){
            do {
                GioHangChiTiet gioHangChiTiet=new GioHangChiTiet();
                gioHangChiTiet.setId(cursor.getInt(cursor.getColumnIndex("id")));
                gioHangChiTiet.setId_Gio_Hang(cursor.getInt(cursor.getColumnIndex("id_gio_hang")));
                gioHangChiTiet.setId_San_Pham(cursor.getInt(cursor.getColumnIndex("id_san_pham")));
                gioHangChiTiet.setMau(cursor.getString(cursor.getColumnIndex("mauSac")));
                gioHangChiTiet.setSize(cursor.getString(cursor.getColumnIndex("kichThuoc")));
                gioHangChiTiet.setSoLuong(cursor.getInt(cursor.getColumnIndex("so_luong")));
                gioHangChiTiet.setTenSP(cursor.getString(cursor.getColumnIndex("tenSanPham")));
                gioHangChiTiet.setHang(cursor.getString(cursor.getColumnIndex("Hang")));
                gioHangChiTiet.setGiaTien(cursor.getInt(cursor.getColumnIndex("gia_tien")));
                gioHangChiTietList.add(gioHangChiTiet);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return gioHangChiTietList;
    }
    public boolean checkSanPhamExistsInGioHangChiTiet(int idSanPham, int idGioHang) {
        boolean exists = false;
        if (db != null) {
            String query = "SELECT * FROM GioHangChiTiet WHERE id_san_pham = ? AND id_gio_hang = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idSanPham), String.valueOf(idGioHang)});
            if (cursor != null) {
                exists = cursor.getCount() > 0;
                cursor.close();
            }
        }
        return exists;
    }
    public boolean deleteSanPham(int sanPhamID){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        int reslt =db.delete("GioHangChiTiet","id_san_pham=?",new String[]{String.valueOf(sanPhamID)});
        db.close();
        return  reslt>0;

    }

    public boolean add(int idSanPham,int idGioHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id_gio_hang",idGioHang);
        values.put("id_san_pham",idSanPham);
        values.put("so_luong", 1);

        long result =db.insert("GioHangChiTiet",null,values);
        db.close();
        return result != -1;

    }
    public boolean updateSoLuong(GioHangChiTiet gioHangChiTiet){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put("so_luong",gioHangChiTiet.getSoLuong());
        String select= "id_san_pham=?";
        String[] selectArgs={String.valueOf(gioHangChiTiet.getId_San_Pham())};
       db.update("GioHangChiTiet",values,select,selectArgs);
       return false ;
    }
}
