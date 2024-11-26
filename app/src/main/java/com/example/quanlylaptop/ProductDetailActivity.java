package com.example.quanlylaptop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlylaptop.DbHelper.DatabaseHelper;
import com.example.quanlylaptop.Model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productName, productCode, productPrice;
    private Button btnDelete, btnEdit;
    private int productId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productName = findViewById(R.id.productName);
        productCode = findViewById(R.id.productCode);
        productPrice = findViewById(R.id.productPrice);
        btnDelete = findViewById(R.id.btnDeleteProduct);
        btnEdit = findViewById(R.id.btnEditProduct);

        databaseHelper = new DatabaseHelper(this);

        // Lấy thông tin sản phẩm từ intent
        productId = getIntent().getIntExtra("productId", -1);
        Product product = databaseHelper.getProductById(productId);

        if (product != null) {
            productName.setText(product.getName());
            productCode.setText(product.getCode());
            productPrice.setText(String.valueOf(product.getPrice()));
        }

        btnDelete.setOnClickListener(v -> {
            databaseHelper.deleteProduct(productId);
            Toast.makeText(this, "Sản phẩm đã bị xóa", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình danh sách sản phẩm
        });

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ProductDetailActivity.this, EditProductActivity.class);
            intent.putExtra("productId", productId);
            startActivity(intent);
        });
    }

}