package com.example.duan1_nhom3.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.DonHang;
import com.example.duan1_nhom3.model.DonHangChiTiet;
import com.example.duan1_nhom3.model.NhanVien;
import com.example.duan1_nhom3.model.SanPham;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DonHangDao {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    public DonHangDao(Context context) {
        dbHelper = new db(context);
    }
    public ArrayList<DonHang> getList() {
        ArrayList<DonHang> donHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn SQL kết hợp với JOIN để lấy thông tin đầy đủ của đơn hàng
        String query = "SELECT DonHang.id, DonHang.id_khach_hang, DonHang.id_nhan_vien, DonHang.ngay_mua, DonHang.trang_thai, DonHang.tong_tien, KhachHang.ten AS ten_khach_hang, NhanVien.ten AS ten_nhan_vien " +
                "FROM DonHang " +
                "LEFT JOIN KhachHang ON DonHang.id_khach_hang = KhachHang.id " +
                "LEFT JOIN NhanVien ON DonHang.id_nhan_vien = NhanVien.id";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                donHang.setIdKH(cursor.getInt(cursor.getColumnIndex("id_khach_hang")));
                donHang.setIdNV(cursor.getInt(cursor.getColumnIndex("id_nhan_vien")));
                String ngayMuaString = cursor.getString(cursor.getColumnIndex("ngay_mua"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                try {
                    Date ngayMuaDate = dateFormat.parse(ngayMuaString);
                    donHang.setNgayMua(ngayMuaDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                donHang.setTrangThai(cursor.getInt(cursor.getColumnIndex("trang_thai")));
                donHang.setTongTien(cursor.getInt(cursor.getColumnIndex("tong_tien")));
                donHang.setTenKH(cursor.getString(cursor.getColumnIndex("ten_khach_hang")));
                donHang.setTenNV(cursor.getString(cursor.getColumnIndex("ten_nhan_vien")));
                donHangList.add(donHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return donHangList;
    }
    public ArrayList<DonHang> getListDonHangByLogin(String username, String password) {
        ArrayList<DonHang> donHangList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Truy vấn SQL để lấy ra danh sách đơn hàng dựa trên tên đăng nhập và mật khẩu của khách hàng
        String query = "SELECT DonHang.id, DonHang.id_khach_hang, DonHang.id_nhan_vien, DonHang.ngay_mua, DonHang.trang_thai, DonHang.tong_tien, KhachHang.ten AS ten_khach_hang, NhanVien.ten AS ten_nhan_vien " +
                "FROM DonHang " +
                "LEFT JOIN KhachHang ON DonHang.id_khach_hang = KhachHang.id " +
                "LEFT JOIN NhanVien ON DonHang.id_nhan_vien = NhanVien.id " +
                "WHERE KhachHang.username = ? AND KhachHang.pass = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            do {
                DonHang donHang = new DonHang();
                donHang.setId(cursor.getInt(cursor.getColumnIndex("id")));
                donHang.setIdKH(cursor.getInt(cursor.getColumnIndex("id_khach_hang")));
                donHang.setIdNV(cursor.getInt(cursor.getColumnIndex("id_nhan_vien")));
                String ngayMuaString = cursor.getString(cursor.getColumnIndex("ngay_mua"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                try {
                    Date ngayMuaDate = dateFormat.parse(ngayMuaString);
                    donHang.setNgayMua(ngayMuaDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                donHang.setTrangThai(cursor.getInt(cursor.getColumnIndex("trang_thai")));
                donHang.setTongTien(cursor.getInt(cursor.getColumnIndex("tong_tien")));
                donHang.setTenKH(cursor.getString(cursor.getColumnIndex("ten_khach_hang")));
                donHang.setTenNV(cursor.getString(cursor.getColumnIndex("ten_nhan_vien")));

                donHangList.add(donHang);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return donHangList;
    }

    public boolean delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("DonHang", "id=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    public boolean updateNameAndStatus(int id, int idNV, int trangThai) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_nhan_vien", idNV);
        values.put("trang_thai", trangThai);
        int result = db.update("DonHang", values, "id=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
    public boolean add(int idKH, int tongTien, int trangThai,String date, ArrayList<SanPham> SanPham) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_khach_hang", idKH);
        values.put("tong_tien", tongTien);
        values.put("ngay_mua", date);
        values.put("trang_thai", trangThai);
        long result = db.insert("DonHang", null, values);


        if (result != -1 && SanPham != null) {
            // Lấy ID của đơn hàng vừa thêm vào
            String query = "SELECT last_insert_rowid()";
            Cursor cursor = db.rawQuery(query, null);
            int donHangId = -1;
            if (cursor.moveToFirst()) {
                donHangId = cursor.getInt(0);
            }
            cursor.close();

            // Thêm dữ liệu vào bảng DonHangChiTiet
            if (donHangId != -1) {
                for (SanPham sp : SanPham) {
                    ContentValues chiTietValues = new ContentValues();
                    chiTietValues.put("id_don_hang", donHangId);
                    chiTietValues.put("id_san_pham", sp.getId());
                    chiTietValues.put("so_luong",sp.getQuantity());
                    chiTietValues.put("gia_tien",sp.getPrice()*sp.getQuantity() );
                    db.insert("ChiTietDonHang", null, chiTietValues);
                }
            }
        }

        db.close();
        // Kiểm tra xem việc thêm đơn hàng có thành công hay không
        return result != -1;
    }
    public NhanVien getNhanVienByUsernameAndPassword(String username, String password) {
        NhanVien nhanVien = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM NhanVien WHERE username = ? AND pass = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            nhanVien = new NhanVien();
            // Lấy thông tin nhân viên từ dòng dữ liệu trả về
            nhanVien.setId(cursor.getInt(cursor.getColumnIndex("id")));
            nhanVien.setTenNV(cursor.getString(cursor.getColumnIndex("ten")));
            nhanVien.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            nhanVien.setSDT(cursor.getString(cursor.getColumnIndex("sdt")));
            nhanVien.setDiachi(cursor.getString(cursor.getColumnIndex("dia_chi")));
            nhanVien.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            nhanVien.setPassword(cursor.getString(cursor.getColumnIndex("pass")));
            nhanVien.setChucvu(cursor.getString(cursor.getColumnIndex("chuc_vu")));
        }

        cursor.close();
        db.close();
        return nhanVien;
    }

    public boolean checkQuantity(ArrayList<DonHangChiTiet> donHangChiTiets) {
        boolean isValid = true;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        for (DonHangChiTiet sp : donHangChiTiets) {
            int productId = sp.getIdSP();
            int quantityInList = sp.getSoLuong();

            // Truy vấn để lấy số lượng của sản phẩm từ bảng SanPham
            Cursor cursor = db.rawQuery("SELECT so_luong FROM SanPham WHERE id = ?", new String[]{String.valueOf(productId)});

            if (cursor.moveToFirst()) {
                int quantityInDatabase = cursor.getInt(cursor.getColumnIndex("so_luong"));
                if (quantityInList > quantityInDatabase) {
                    isValid = false; // Nếu số lượng trong danh sách lớn hơn số lượng trong bảng, đánh dấu là không hợp lệ
                    break;
                }
            }

            cursor.close();
        }

        db.close();

        return isValid;
    }
}
