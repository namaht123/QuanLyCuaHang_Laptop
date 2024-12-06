package com.example.duan1_nhom3.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.duan1_nhom3.DoanhThuService;
import com.example.duan1_nhom3.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DoanhThuFragment extends Fragment {

    private EditText edTuNgay, edDenNgay;
    private Button btnDoanhThu;
    private TextView tvDoanhThu;
    private ImageView btnTuNgay, btnDenNgay;
    private DoanhThuService doanhThuService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private int mYear, mMonth, mDay;

    public DoanhThuFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_doanh_thu, container, false);

        edTuNgay = root.findViewById(R.id.edTuNgay);
        edDenNgay = root.findViewById(R.id.edDenNgay);
        btnDoanhThu = root.findViewById(R.id.btnDoanhThu);
        tvDoanhThu = root.findViewById(R.id.tvDoanhThu);
        btnTuNgay = root.findViewById(R.id.btnTuNgay);
        btnDenNgay = root.findViewById(R.id.btnDenNgay);

        doanhThuService = new DoanhThuService(requireContext());

        // Xử lý sự kiện khi nhấn nút "Doanh Thu"
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày bắt đầu và kết thúc từ EditText
                String tuNgay = edTuNgay.getText().toString();
                String denNgay = edDenNgay.getText().toString();

                // Kiểm tra nếu tuNgay hoặc denNgay rỗng thì không tính doanh thu
                if (!tuNgay.isEmpty() && !denNgay.isEmpty()) {
                    int doanhThu = doanhThuService.getDoanhThu(tuNgay, denNgay);
                    tvDoanhThu.setText("Doanh Thu: " + doanhThu + " VNĐ");
                } else {
                    // Hiển thị thông báo lỗi nếu người dùng chưa nhập đủ thông tin
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện khi nhấn vào ảnh chọn ngày bắt đầu
        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mDateTuNgay);
            }
        });

        // Xử lý sự kiện khi nhấn vào ảnh chọn ngày kết thúc
        btnDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mDateDenNgay);
            }
        });

        return root;
    }

    private void showDatePickerDialog(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), listener, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            String selectedDate = sdf.format(c.getTime());
            edTuNgay.setText(selectedDate);

            // Kiểm tra xem ngày bắt đầu có lớn hơn ngày kết thúc không
            if (!edDenNgay.getText().toString().isEmpty() && compareDates(selectedDate, edDenNgay.getText().toString()) > 0) {
                // Ngày bắt đầu lớn hơn ngày kết thúc, hiển thị thông báo lỗi
                Toast.makeText(getContext(), "Ngày bắt đầu không thể lớn hơn ngày kết thúc", Toast.LENGTH_SHORT).show();
                // Xóa nội dung đã chọn và yêu cầu người dùng chọn lại
                edTuNgay.setText("");
            }
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            String selectedDate = sdf.format(c.getTime());
            edDenNgay.setText(selectedDate);

            // Kiểm tra xem ngày kết thúc có nhỏ hơn ngày bắt đầu không
            if (!edTuNgay.getText().toString().isEmpty() && compareDates(edTuNgay.getText().toString(), selectedDate) > 0) {
                // Ngày kết thúc nhỏ hơn ngày bắt đầu, hiển thị thông báo lỗi
                Toast.makeText(getContext(), "Ngày kết thúc không thể nhỏ hơn ngày bắt đầu", Toast.LENGTH_SHORT).show();
                // Xóa nội dung đã chọn và yêu cầu người dùng chọn lại
                edDenNgay.setText("");
            }
        }
    };


    // Hàm so sánh ngày mà không xem xét thời gian
//    private int compareDates(String date1, String date2) {
//        try {
//            // Chuyển đổi ngày từ chuỗi văn bản sang đối tượng Calendar
//            Calendar cal1 = Calendar.getInstance();
//            cal1.setTime(sdf.parse(date1));
//
//            Calendar cal2 = Calendar.getInstance();
//            cal2.setTime(sdf.parse(date2));
//
//            // So sánh trực tiếp hai đối tượng Calendar
//            return cal1.compareTo(cal2);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
    // Hàm so sánh ngày mà không xem xét thời gian
    private int compareDates(String date1, String date2) {
        try {
            // Chuyển đổi ngày từ chuỗi văn bản sang đối tượng Calendar
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(date1));

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(sdf.parse(date2));

            // So sánh trực tiếp hai đối tượng Calendar
            return cal1.compareTo(cal2);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }




}

