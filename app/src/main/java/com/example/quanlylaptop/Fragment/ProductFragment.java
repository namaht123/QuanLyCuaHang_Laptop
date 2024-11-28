package com.example.quanlylaptop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlylaptop.Adapter.ProductAdapter;
import com.example.quanlylaptop.AddProductActivity;
import com.example.quanlylaptop.DbHelper.DatabaseHelper;
import com.example.quanlylaptop.Model.Product;
import com.example.quanlylaptop.ProductDetailActivity;
import com.example.quanlylaptop.R;

import java.util.List;

public class ProductFragment extends Fragment {
    private RecyclerView rvProductList;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper databaseHelper;
    private EditText etSearchProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        // Khởi tạo các view
        rvProductList = view.findViewById(R.id.rvProductList);
        etSearchProduct = view.findViewById(R.id.etSearchProduct);
        ImageButton btnSearchProduct = view.findViewById(R.id.btnSearchProduct);  // Thay Button thành ImageButton
        ImageButton btnAddProduct = view.findViewById(R.id.btnAddProduct);        // Thay Button thành ImageButton

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu SQLite
        productList = databaseHelper.getAllProducts();

        // Khởi tạo adapter và thiết lập sự kiện click cho sản phẩm
        productAdapter = new ProductAdapter(productList, product -> {
            // Chuyển đến màn hình chi tiết sản phẩm
            Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
            intent.putExtra("productId", product.getId()); // Truyền ID sản phẩm qua Intent
            startActivity(intent);
        });

        // Thiết lập RecyclerView
        rvProductList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvProductList.setAdapter(productAdapter);

        // Xử lý sự kiện tìm kiếm
        btnSearchProduct.setOnClickListener(v -> {
            String query = etSearchProduct.getText().toString().trim();
            if (!TextUtils.isEmpty(query)) {
                // Lọc danh sách dựa trên từ khóa tìm kiếm
                productAdapter.filter(query);
            } else {
                // Hiển thị toàn bộ danh sách nếu ô tìm kiếm trống
                productAdapter.filter("");
            }
        });

        // Xử lý sự kiện click nút "Thêm sản phẩm"
        btnAddProduct.setOnClickListener(v -> {
            // Chuyển đến màn hình thêm sản phẩm mới
            Intent intent = new Intent(getActivity(), AddProductActivity.class);
            startActivity(intent);
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cập nhật danh sách sản phẩm khi quay lại màn hình
        productList.clear();
        productList.addAll(databaseHelper.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }
}
