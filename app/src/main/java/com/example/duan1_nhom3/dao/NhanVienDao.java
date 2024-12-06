package com.example.duan1_nhom3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.NhanVien;

import java.util.ArrayList;

public class NhanVienDao {
    private db dbHelper;
    public NhanVienDao(Context context){
        dbHelper=new db(context);
    }
    //login
    public boolean CheckLogin(String usename,String password){
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM NhanVien WHERE tendangnhap=? AND matkhau=?",new String[]{usename,password});
        return cursor.getCount()>0;

    }
    public ArrayList<NhanVien> getDS(){
        ArrayList<NhanVien> list=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM NhanVien",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new NhanVien(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8)));

            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean kiemTraTonTai(String tenDangNhap, String matKhau) {
        ArrayList<NhanVien> ListNV = new ArrayList<>();
        ListNV = getDS();
        for (NhanVien nv : ListNV) {
            if (tenDangNhap.equals(nv.getUsername()) && matKhau.equals(nv.getPassword())) {
                return true;
            }
        }
        return false;
    }
    public  boolean DoiMK(String user,String password){

        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("pass",password);
        long check=sqLiteDatabase.update("NhanVien",contentValues,"username=?",new String[]{user});
        return check !=-1;
    }
    public  boolean themNV(NhanVien nhanVien){
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ten",nhanVien.getTenNV());
        contentValues.put("email",nhanVien.getEmail());
        contentValues.put("sdt",nhanVien.getSDT());
        contentValues.put("dia_chi",nhanVien.getDiachi());
        contentValues.put("username",nhanVien.getUsername());
        contentValues.put("pass",nhanVien.getPassword());
        contentValues.put("chuc_vu",nhanVien.getChucvu());
        contentValues.put("trangthai",nhanVien.getTrangthai());
        long check=sqLiteDatabase.insert("NhanVien",null,contentValues);
        return check !=-1;
    }
    public  boolean updateNV(NhanVien nhanVien){
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",nhanVien.getId());
        contentValues.put("ten",nhanVien.getTenNV());
        contentValues.put("email",nhanVien.getEmail());
        contentValues.put("sdt",nhanVien.getSDT());
        contentValues.put("dia_chi",nhanVien.getDiachi());
        contentValues.put("username",nhanVien.getUsername());
        contentValues.put("pass",nhanVien.getPassword());
        contentValues.put("chuc_vu",nhanVien.getChucvu());
        contentValues.put("trangthai",nhanVien.getTrangthai());
        long check=sqLiteDatabase.update("NhanVien",contentValues,"id=?",new String[]{String.valueOf(nhanVien.getId())});
        return check !=-1;
    }
    public  boolean DoiTrangThai(int id,String trangthai){
        SQLiteDatabase sqLiteDatabase=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("trangthai",trangthai);
        long check=sqLiteDatabase.update("NhanVien",contentValues,"id=?",new String[]{String.valueOf(id)});
        return check !=-1;
    }
}
