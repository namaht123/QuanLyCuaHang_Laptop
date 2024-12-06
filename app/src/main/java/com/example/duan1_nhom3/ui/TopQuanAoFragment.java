package com.example.duan1_nhom3.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.adapter.AdapterTop;
import com.example.duan1_nhom3.dao.TopDAO;
import com.example.duan1_nhom3.model.Top;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class TopQuanAoFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterTop adapterTop;
    private ArrayList<Top> topList;
    private TopDAO topDAO;
    private Button btnSelectEndDate, btnSelectStartDate, btntop;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
    private int mYear, mMonth, mDay;
    private EditText edDenNgay, edTuNgay;


    private Calendar calendar;

    public TopQuanAoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo danh sách và Adapter
        topList = new ArrayList<>();
        adapterTop = new AdapterTop(getContext(), topList);
        // Khởi tạo DAO
        topDAO = new TopDAO(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_quan_ao, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterTop);
        btnSelectStartDate = view.findViewById(R.id.btnSelectStartDate);
        btnSelectEndDate = view.findViewById(R.id.btnSelectEndDate);
        btntop = view.findViewById(R.id.btntop);
        edTuNgay = view.findViewById(R.id.edTuNgay);
        edDenNgay = view.findViewById(R.id.edDenNgay);

        calendar = Calendar.getInstance();

        btnSelectStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mDateTuNgay);
            }
        });

        btnSelectEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mDateDenNgay);
            }
        });

        btntop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startDate = edTuNgay.getText().toString();
                String endDate = edDenNgay.getText().toString();

                if (startDate.isEmpty() || endDate.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng chọn ngày bắt đầu và ngày kết thúc", Toast.LENGTH_SHORT).show();
                } else {
                    loadTopSellingProducts(startDate, endDate);
                }
            }
        });

        return view;
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

    private void loadTopSellingProducts(String startDate, String endDate) {
        // Xóa dữ liệu cũ
        topList.clear();
        // Thêm dữ liệu mới từ DAO
        topList.addAll(topDAO.getTopSellingProducts(startDate, endDate));
        // Thông báo cho Adapter là dữ liệu đã thay đổi
        adapterTop.notifyDataSetChanged();
    }

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
