package com.example.duan1_nhom3.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.adapter.AdapterSP;
import com.example.duan1_nhom3.adapter.SlideShowPagerAdapter;
import com.example.duan1_nhom3.dao.SanPhamDao;
import com.example.duan1_nhom3.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SanPhamFragment extends Fragment {

    private ArrayList<SanPham> list = new ArrayList<>();
    private SanPhamDao sanPhamDao;
    private AdapterSP adapterSP;
    private RecyclerView rycSP;
    private EditText editTextMinPrice, editTextMaxPrice;
    private FloatingActionButton btnApplyFilter;
    private ViewPager mViewPager;
    private SlideShowPagerAdapter mAdapter;
    private ArrayList<Integer> mImageIds;

    private Handler mHandler;
    private final int SLIDE_DELAY = 3000;
    private Runnable mSlideRunnable;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);


        mViewPager = view.findViewById(R.id.viewPager);

        // Thêm các ảnh sản phẩm vào mImageIds
        mImageIds = new ArrayList<>();
        mImageIds.add(R.drawable.banner3);
        mImageIds.add(R.drawable.banner4);
        mImageIds.add(R.drawable.banner5);

        // Khởi tạo và thiết lập adapter cho ViewPager
        mAdapter = new SlideShowPagerAdapter(requireContext(), mImageIds);
        mViewPager.setAdapter(mAdapter);

        // Khởi tạo Handler
        mHandler = new Handler();

        // Khởi tạo Runnable cho slide show
        mSlideRunnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = mViewPager.getCurrentItem();
                int totalItems = mAdapter.getCount();
                int nextItem = (currentItem + 1) % totalItems;
                mViewPager.setCurrentItem(nextItem);
                mHandler.postDelayed(this, SLIDE_DELAY);
            }
        };

        // Bắt đầu slide show
        mHandler.postDelayed(mSlideRunnable, SLIDE_DELAY);

        // Khởi tạo các thành phần
        rycSP = view.findViewById(R.id.rycsp);
        editTextMinPrice = view.findViewById(R.id.edit_text_min_price);
        editTextMaxPrice = view.findViewById(R.id.edit_text_max_price);
        btnApplyFilter = view.findViewById(R.id.btn_apply_filter);
        sanPhamDao = new SanPhamDao(requireContext());
        list = sanPhamDao.getAllProductsWithDetails();
        adapterSP = new AdapterSP(requireContext(), list);

        // Thiết lập RecyclerView
        rycSP.setAdapter(adapterSP);
        rycSP.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Thiết lập sự kiện áp dụng bộ lọc
        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyFilter();
            }
        });

        // Thiết lập sự kiện thay đổi trong EditText
        editTextMinPrice.addTextChangedListener(textWatcher);
        editTextMaxPrice.addTextChangedListener(textWatcher);

        return view;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            applyFilter();
        }
    };

    private void applyFilter() {
        double minPrice = 0;
        double maxPrice = Double.MAX_VALUE;
        if (!editTextMinPrice.getText().toString().isEmpty()) {
            minPrice = Double.parseDouble(editTextMinPrice.getText().toString());
        }
        if (!editTextMaxPrice.getText().toString().isEmpty()) {
            maxPrice = Double.parseDouble(editTextMaxPrice.getText().toString());
        }
        ArrayList<SanPham> filteredList = new ArrayList<>();
        for (SanPham sanPham : list) {
            double price = sanPham.getPrice();
            if (price >= minPrice && price <= maxPrice) {
                filteredList.add(sanPham);
            }
        }
        // Cập nhật RecyclerView với danh sách đã lọc
        adapterSP.filterList(filteredList);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Đặt lại giá trị của EditText thành rỗng
        editTextMinPrice.setText("");
        editTextMaxPrice.setText("");
        // Áp dụng bộ lọc mặc định
        applyFilter();
        mHandler.removeCallbacks(mSlideRunnable);

    }



}
