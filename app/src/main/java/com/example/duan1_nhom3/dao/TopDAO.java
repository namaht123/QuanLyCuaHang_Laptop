package com.example.duan1_nhom3.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_nhom3.DBHelper.db;
import com.example.duan1_nhom3.model.Top;

import java.util.ArrayList;
import java.util.List;

public class TopDAO {
    private final SQLiteDatabase db;
    private final SQLiteOpenHelper dbHelper;


    public TopDAO(Context context) {
        dbHelper = new db(context);
        db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Top> getTopSellingProducts(String startDate, String endDate) {
        ArrayList<Top> topList = new ArrayList<>();

        String query = "SELECT  sp.ten AS ten_san_pham, " +
                "SUM(ct.so_luong) AS so_luong_ban, " +
                "sp.gia_tien AS gia, " +
                "tt.ten AS thuong_hieu " +
                "FROM SanPham sp " +
                "JOIN ChiTietDonHang ct ON sp.id = ct.id_san_pham " +
                "JOIN DonHang dh ON ct.id_don_hang = dh.id " +
                "JOIN ThuocTinhSanPham tsp ON sp.id = tsp.id_san_pham " +
                "JOIN ThuocTinh tt ON tsp.id_thuoc_tinh = tt.id " +
                "WHERE dh.trang_thai = 3 " +
                "AND dh.ngay_mua BETWEEN ? AND ? " +
                "GROUP BY sp.ten, sp.gia_tien, tt.ten " +
                "ORDER BY so_luong_ban DESC " +
                "LIMIT 10";


        if (db != null) {
            Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});
            if (cursor.moveToFirst()) {
                do {
                    String tenSanPham = cursor.getString(cursor.getColumnIndex("ten_san_pham"));
                    int soLuongBan = cursor.getInt(cursor.getColumnIndex("so_luong_ban"));
                    int gia = cursor.getInt(cursor.getColumnIndex("gia"));
                    String thuongHieu = cursor.getString(cursor.getColumnIndex("thuong_hieu"));


                    // Kiểm tra xem sản phẩm đã tồn tại trong danh sách chưa
                    boolean isProductExist = false;
                    for (Top top : topList) {
                        if (top.getTenSanPham().equals(tenSanPham)) {
                            // Nếu sản phẩm đã tồn tại, cập nhật số lượng bán
                            top.setSoLuongBan(top.getSoLuongBan());
                            isProductExist = true;
                            break;
                        }
                    }

                    // Nếu sản phẩm chưa tồn tại trong danh sách, thêm mới vào danh sách
                    if (!isProductExist) {
                        Top top = new Top(tenSanPham, soLuongBan, gia, thuongHieu);
                        topList.add(top);
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }


        return topList;
    }


    public int getDoanhThu(String tuNgay, String denNgay) {
        int doanhThu = 0;

        // Chuỗi truy vấn SQL để tính tổng doanh thu từ bảng ChiTietDonHang trong khoảng thời gian từ ngày đến ngày
        String sqlDoanhThu = "SELECT SUM(ctd.so_luong * sp.gia_tien) AS doanhThu " +
                "FROM ChiTietDonHang ctd " +
                "JOIN DonHang dh ON ctd.id_don_hang = dh.id " +
                "JOIN SanPham sp ON ctd.id_san_pham = sp.id " +
                "WHERE dh.ngay_mua BETWEEN ? AND ? " +
                "AND dh.trang_thai = 3"; // Thêm điều kiện trạng thái đơn hàng là 1


        // Danh sách để lưu kết quả truy vấn
        List<Integer> list = new ArrayList<>();

        // Thực hiện truy vấn SQL
        Cursor cursor = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});

        // Duyệt qua kết quả truy vấn và lưu vào danh sách
        while (cursor.moveToNext()) {
            try {
                // Lấy giá trị doanh thu từ cột "doanhThu" trong kết quả truy vấn
                int doanhThuValue = cursor.getInt(cursor.getColumnIndex("doanhThu"));
                list.add(doanhThuValue);
            } catch (Exception e) {
                // Xử lý ngoại lệ nếu có
                e.printStackTrace();
            }
        }

        // Kiểm tra nếu danh sách không rỗng thì lấy giá trị đầu tiên
        if (!list.isEmpty()) {
            doanhThu = list.get(0);
        }

        return doanhThu;
    }


}
