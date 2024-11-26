package com.example.quanlylaptop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlylaptop.Adapter.ProductAdapter;
import com.example.quanlylaptop.DbHelper.DatabaseHelper;
import com.example.quanlylaptop.Model.Product;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private RecyclerView rvProductList;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Khởi tạo các view
        rvProductList = findViewById(R.id.rvProductList);
        Button btnAddProduct = findViewById(R.id.btnAddProduct);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Lấy danh sách sản phẩm từ cơ sở dữ liệu SQLite
        productList = databaseHelper.getAllProducts();

        // Khởi tạo adapter và thiết lập sự kiện click cho sản phẩm
        productAdapter = new ProductAdapter(productList, product -> {
            // Chuyển đến màn hình chi tiết sản phẩm
            Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
            intent.putExtra("productId", product.getId());  // Truyền ID sản phẩm qua Intent
            startActivity(intent);
        });

        // Thiết lập RecyclerView
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setAdapter(productAdapter);

        // Xử lý sự kiện click nút "Thêm sản phẩm"
        btnAddProduct.setOnClickListener(v -> {
            // Chuyển đến màn hình thêm sản phẩm mới
            Intent intent = new Intent(ProductListActivity.this, AddProductActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật danh sách sản phẩm khi quay lại màn hình
        productList.clear();
        productList.addAll(databaseHelper.getAllProducts());
        productAdapter.notifyDataSetChanged();
    }
}
