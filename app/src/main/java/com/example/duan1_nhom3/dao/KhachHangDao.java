package com.example.duan1_nhom3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.GioHang;
import com.example.duan1_nhom3.model.KhachHang;

import java.util.ArrayList;

public class KhachHangDao {
    private db dbHelper;
    private GioHangDao gioHangDao;
    private GioHang gioHang;
    public KhachHangDao(Context context){
        dbHelper=new db(context);
        gioHangDao=new GioHangDao(context);


    }
    //login
    public int CheckLogin(String usename,String password){
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM KhachHang WHERE username=? AND pass=?",new String[]{usename,password});
        return cursor.getCount();
    }
    public ArrayList<KhachHang> getDS(){
        ArrayList<KhachHang> list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM KhachHang",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new KhachHang(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6) ));

            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean kiemTraTonTai(String tenDangNhap, String matKhau) {
        ArrayList<KhachHang> ListND = new ArrayList<>();
        ListND = getDS();
        for (KhachHang nd : ListND) {
            if (tenDangNhap.equals(nd.getUsername()) && matKhau.equals(nd.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public  boolean DoiMK(String user,String password){

        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("pass",password);

        long check=sqLiteDatabase.update("KhachHang",contentValues,"username=?",new String[]{user});
        return check !=-1;
    }
    public boolean Register(String tenkh, String emailkh, String diachi, String SDT, String user, String pass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten", tenkh);
        contentValues.put("email", emailkh);
        contentValues.put("dia_chi", diachi);
        contentValues.put("sdt", SDT);
        contentValues.put("username", user);
        contentValues.put("pass", pass);

        // Thêm khách hàng mới vào bảng KhachHang
        long khachHangId = sqLiteDatabase.insert("KhachHang", null, contentValues);

        if (khachHangId != -1) {
            // Nếu thêm khách hàng thành công, tạo giỏ hàng mới cho khách hàng
            ContentValues gioHangValues = new ContentValues();
            gioHangValues.put("id_khach_hang", khachHangId);
            // Chèn giỏ hàng mới vào bảng GioHang
            long gioHangCheck = sqLiteDatabase.insert("GioHang", null, gioHangValues);

            // Trả về true nếu cả hai thao tác đều thành công
            return gioHangCheck != -1;
        } else {
            // Trả về false nếu không thể thêm khách hàng mới
            return false;
        }
    }
}
