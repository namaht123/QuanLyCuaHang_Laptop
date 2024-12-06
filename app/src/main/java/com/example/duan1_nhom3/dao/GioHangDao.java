
package com.example.duan1_nhom3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.GioHang;
import com.example.duan1_nhom3.model.SanPham;

import java.util.ArrayList;

public class GioHangDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper sqLiteOpenHelper;
    public GioHangDao(Context context){
        sqLiteOpenHelper= new db(context);
    }
    public ArrayList<GioHang> getList(){
        ArrayList<GioHang> gioHangArrayList=new ArrayList<>();
        SQLiteDatabase db= sqLiteOpenHelper.getReadableDatabase();
        String query="SELECT  GioHang.id,GioHang.id_khach_hang from GioHang " +
                "LEFT JOIN KhachHang ON GioHang.id_khach_hang=KhachHang.id " ;
        Cursor cursor= db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                GioHang gioHang =new GioHang();
                gioHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                gioHang.setId_Khach_Hang(cursor.getInt(cursor.getColumnIndex("id_khach_hang")));

            }while (cursor.moveToNext());
        }
        cursor.close();

        return gioHangArrayList;
    }
    public boolean delete(int i){
        SQLiteDatabase db=sqLiteOpenHelper.getWritableDatabase();
        int result= db.delete("GioHang","id=?",new String[]{String.valueOf(i)});
        db.close();
        return result>0;
    }
    public boolean add(int id,int id_khach_hang ){
        SQLiteDatabase db=sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("id_khach_hang",id_khach_hang);
        long result = db.insert("GioHang", null, values);
        db.close();

        return result != -1;

    }
    public int layIdGioHangTuUsernameVaMatKhau(String username, String password) {
        int idGioHang = -1;

        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        String query = "SELECT gh.id " +
                "FROM GioHang gh " +
                "JOIN KhachHang kh ON gh.id_khach_hang = kh.id " +
                "WHERE kh.username = ? AND kh.pass = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            idGioHang = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return idGioHang;
    }
    public ArrayList<SanPham> getList(int gioHangID){
        ArrayList<SanPham> sanPhamList = new ArrayList<>();
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
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
                SanPham sanPham = new SanPham();
                sanPham.setId(cursor.getInt(cursor.getColumnIndex("id_san_pham")));
                sanPham.setName(cursor.getString(cursor.getColumnIndex("tenSanPham")));
                sanPham.setPrice(cursor.getInt(cursor.getColumnIndex("gia_tien")));
                sanPham.setColor(cursor.getString(cursor.getColumnIndex("mauSac")));
                sanPham.setQuantity(cursor.getInt(cursor.getColumnIndex("so_luong")));
                sanPham.setSize(cursor.getString(cursor.getColumnIndex("kichThuoc")));
                sanPham.setBrand(cursor.getString(cursor.getColumnIndex("Hang")));
                sanPhamList.add(sanPham);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sanPhamList;
    }

    public int getUserIdByUsernameAndPassword(String username, String password) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        int userId = -1;
        String query = "SELECT id FROM KhachHang WHERE username = ? AND pass = ?";

        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("id"));
        }
        cursor.close();
        db.close();
        return userId;
    }
    public int tinhTongTien(ArrayList<SanPham> listSanPham) {
        int tongTien = 0;
        for (SanPham sanPham : listSanPham) {
            tongTien += sanPham.getPrice() * sanPham.getQuantity();
        }
        return tongTien;
    }
    public boolean xoaHetGioHangChiTiet(int idDonHang) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        int result = db.delete("GioHangChiTiet", "id_gio_hang=?", new String[]{String.valueOf(idDonHang)});
        db.close();
        return result > 0;
    }


}
