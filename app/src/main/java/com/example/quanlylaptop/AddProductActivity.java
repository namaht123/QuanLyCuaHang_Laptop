package com.example.quanlylaptop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quanlylaptop.DbHelper.DatabaseHelper;
import com.example.quanlylaptop.Model.Product;

public class AddProductActivity extends AppCompatActivity {

    private EditText etProductName, etProductCode, etProductPrice;
    private ImageView ivProductImage;
    private Button btnSelectImage, btnAddProduct;
    private Uri imageUri;
    private DatabaseHelper databaseHelper;

    private static final int PICK_IMAGE_REQUEST = 1; // Mã yêu cầu cho việc chọn hình ảnh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        // Ánh xạ các thành phần giao diện
        etProductName = findViewById(R.id.etProductName);
        etProductCode = findViewById(R.id.etProductCode);
        etProductPrice = findViewById(R.id.etProductPrice);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);

        databaseHelper = new DatabaseHelper(this);

        // Sự kiện cho nút chọn hình ảnh
        btnSelectImage.setOnClickListener(v -> openImageChooser());

        // Sự kiện cho nút thêm sản phẩm
        btnAddProduct.setOnClickListener(v -> addProduct());
    }

    // Mở trình chọn hình ảnh
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivProductImage.setImageURI(imageUri);
        }
    }

    // Thêm sản phẩm vào cơ sở dữ liệu
    private void addProduct() {
        String name = etProductName.getText().toString().trim();
        String code = etProductCode.getText().toString().trim();
        String priceString = etProductPrice.getText().toString().trim();

        if (name.isEmpty() || code.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceString);
        String imageUrl = imageUri != null ? imageUri.toString() : ""; // Lưu đường dẫn URI của hình ảnh

        Product product = new Product(name, code, price, imageUrl);
        long id = databaseHelper.addProduct(product);

        if (id != -1) {
            Toast.makeText(this, "Sản phẩm đã được thêm", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình danh sách sản phẩm
        } else {
            Toast.makeText(this, "Lỗi khi thêm sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }
}
