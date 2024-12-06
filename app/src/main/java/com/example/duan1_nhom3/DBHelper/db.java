package com.example.duan1_nhom3.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class db extends SQLiteOpenHelper {
    public static final String DB_NAME = "CHQA";


    public static final int DB_VERSION = 15;



    public db(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Thuộc tính
        String createTableThuocTinh = "CREATE TABLE ThuocTinh (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL)";
        db.execSQL(createTableThuocTinh);

        // Chèn dữ liệu vào bảng Thuộc tính
        db.execSQL("INSERT INTO ThuocTinh (ten) VALUES ('Màu sắc'), ('Hãng'), ('Kích thước')");

        // Tạo bảng Sản phẩm
        String createTableSanPham = "CREATE TABLE SanPham (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL, " +
                "so_luong INTEGER," +
                "gia_tien INTEGER," +
                "trang_thai INTEGER NOT NULL DEFAULT 0)"; // Thêm cột trạng thái
        db.execSQL(createTableSanPham);

        // Chèn dữ liệu vào bảng Sản phẩm
        db.execSQL("INSERT INTO SanPham (ten, so_luong, gia_tien, trang_thai) VALUES ('Áo sơ mi', 20, 250000, 1), ('Quần jean', 10, 250000, 0)");

        //Tạo bảng thuộc tính sản phẩm
        String createTableThuocTinhSanPham = "CREATE TABLE ThuocTinhSanPham (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_thuoc_tinh INTEGER REFERENCES ThuocTinh(id)," +
                "id_san_pham INTEGER REFERENCES SanPham(id)," +
                "gia_tri_thuoc_tinh TEXT NOT NULL )";
        db.execSQL(createTableThuocTinhSanPham);

        // Chèn dữ liệu vào bảng Thuộc tính sản phẩm
        db.execSQL("INSERT INTO ThuocTinhSanPham (id_thuoc_tinh,id_san_pham,gia_tri_thuoc_tinh) VALUES (1,1,'Be'), (1,2,'Hồng'), (2,1,'Adidas'),(2,2,'Adidas'),(3,1,'L'),(3,2,'XL')");

        // Tạo bảng Giỏ hàng
        String createTableGioHang = "CREATE TABLE GioHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_khach_hang INTEGER REFERENCES KhachHang(id), " +
                "FOREIGN KEY (id_khach_hang) REFERENCES KhachHang(id))";
        db.execSQL(createTableGioHang);

        // Chèn dữ liệu vào bảng Giỏ hàng
        db.execSQL("INSERT INTO GioHang (id_khach_hang) VALUES (1)");

        // Tạo bảng Giỏ hàng chi tiết
        String createTableGioHangChiTiet = "CREATE TABLE GioHangChiTiet (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_gio_hang INTEGER REFERENCES GioHang(id), " +
                "id_san_pham INTEGER REFERENCES SanPham(id), " +
                "so_luong INTEGER, " +
                "FOREIGN KEY (id_gio_hang ) REFERENCES GioHang(id), " +
                "FOREIGN KEY (id_san_pham) REFERENCES SanPham(id))";
        db.execSQL(createTableGioHangChiTiet);


        db.execSQL("INSERT INTO GioHangChiTiet (id_gio_hang, id_san_pham, so_luong) VALUES (1, 1, 2),(1,2,2)");


        // Tạo bảng Khách hàng
        String createTableKhachHang = "CREATE TABLE KhachHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL, " +
                "email TEXT, " +
                "sdt TEXT, " +
                "dia_chi TEXT, " +
                "username TEXT NOT NULL, " +
                "pass TEXT NOT NULL)";
        db.execSQL(createTableKhachHang);

        // Chèn dữ liệu vào bảng Khách hàng
        db.execSQL("INSERT INTO KhachHang (ten, email, sdt, dia_chi, username, pass) VALUES ('Nguyễn Văn A', 'nguyenvana@example.com', '0123456789', 'Hà Nội', 'nguyenvana', '123456')");

        // Tạo bảng Nhân viên
        String createTableNhanVien = "CREATE TABLE NhanVien (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT NOT NULL, " +
                "email TEXT, " +
                "sdt TEXT, " +
                "dia_chi TEXT, " +
                "username TEXT NOT NULL, " +
                "pass TEXT NOT NULL, " +
                "chuc_vu TEXT NOT NULL," +
                "trangthai TEXT NOT NULL)";
        db.execSQL(createTableNhanVien);

        // Chèn dữ liệu vào bảng Nhân viên
        db.execSQL("INSERT INTO NhanVien (ten, email, sdt, dia_chi, username, pass, chuc_vu,trangthai) VALUES ('Nguyễn Thị B', 'vinhtd@gmail.com', '0987654321', 'Hồ Chí Minh', 'nguyenthidb', '123456', 'nhavien','Đang làm'), ('Trần Đức Vinh', 'vinh04@gmail.com', '0987654321', 'Hồ Chí Minh', 'admin', 'admin', 'admin','Đang làm')");

        // Tạo bảng Đơn hàng
        String createTableDonHang = "CREATE TABLE DonHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_khach_hang INTEGER REFERENCES KhachHang(id), " +
                "id_nhan_vien INTEGER REFERENCES NhanVien(id), " +
                "ngay_mua DATE NOT NULL, " +
                "trang_thai INTEGER NOT NULL, " +
                "tong_tien INTEGER NOT NULL, " +
                "FOREIGN KEY (id_khach_hang) REFERENCES KhachHang(id), " +
                "FOREIGN KEY (id_nhan_vien) REFERENCES NhanVien(id))";
        db.execSQL(createTableDonHang);

        // Chèn dữ liệu vào bảng Đơn hàng
        db.execSQL("INSERT INTO DonHang (id_khach_hang, id_nhan_vien, ngay_mua, trang_thai, tong_tien) VALUES (1, 1, '2024/03/14', 1, 750000),(1,'', '2024/03/14', 0, 250000)");

        // Tạo bảng Chi tiết đơn hàng
        String createTableChiTietDonHang = "CREATE TABLE ChiTietDonHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_san_pham INTEGER REFERENCES SanPham(id), " +
                "id_don_hang INTEGER REFERENCES DonHang(id), " +
                "so_luong INTEGER NOT NULL, " +
                "gia_tien INTEGER NOT NULL, " +
                "FOREIGN KEY (id_san_pham) REFERENCES SanPham(id), " +
                "FOREIGN KEY (id_don_hang) REFERENCES DonHang(id))";
        db.execSQL(createTableChiTietDonHang);

        // Chèn dữ liệu vào bảng Chi tiết đơn hàng
        db.execSQL("INSERT INTO ChiTietDonHang (id_san_pham, id_don_hang, so_luong, gia_tien) VALUES (1, 1, 2, 500000), (2, 1, 1, 250000),(1,2,1,250000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i != i1) {
            db.execSQL("drop table if exists ThuocTinh");
            db.execSQL("drop table if exists ThuocTinhSanPham");
            db.execSQL("drop table if exists SanPham");
            db.execSQL("drop table if exists GioHangChiTiet");
            db.execSQL("drop table if exists KhachHang");
            db.execSQL("drop table if exists NhanVien");
            db.execSQL("drop table if exists DonHang");
            db.execSQL("drop table if exists GioHang");
            db.execSQL("drop table if exists ChiTietDonHang");
            onCreate(db);
        }
    }

}
