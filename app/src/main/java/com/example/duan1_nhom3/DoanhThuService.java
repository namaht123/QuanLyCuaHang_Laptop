package com.example.duan1_nhom3;

import android.content.Context;

import com.example.duan1_nhom3.dao.TopDAO;

public class DoanhThuService {
    private final TopDAO doanhThuDAO;

    public DoanhThuService(Context context) {
        doanhThuDAO = new TopDAO(context);
    }

    public int getDoanhThu(String tuNgay, String denNgay) {
        // Gọi phương thức getDoanhThu từ lớp DAO và trả về kết quả
        return doanhThuDAO.getDoanhThu(tuNgay, denNgay);
    }
}
