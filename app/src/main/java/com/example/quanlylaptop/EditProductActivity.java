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

public class EditProductActivity extends AppCompatActivity {

    private EditText etProductName, etProductCode, etProductPrice;
    private ImageView ivProductImage;
    private Button btnSelectImage, btnUpdateProduct;
    private Uri imageUri;
    private DatabaseHelper databaseHelper;
    private Product product;

    private static final int PICK_IMAGE_REQUEST = 1; // Mã yêu cầu cho việc chọn hình ảnh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        // Ánh xạ các thành phần giao diện
        etProductName = findViewById(R.id.etProductName);
        etProductCode = findViewById(R.id.etProductCode);
        etProductPrice = findViewById(R.id.etProductPrice);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);

        databaseHelper = new DatabaseHelper(this);

        // Nhận thông tin sản phẩm từ Intent
        Intent intent = getIntent();
        int productId = intent.getIntExtra("productId", -1);
        product = databaseHelper.getProductById(productId);

        if (product != null) {
            // Điền thông tin sản phẩm vào các trường nhập liệu
            etProductName.setText(product.getName());
            etProductCode.setText(product.getCode());
            etProductPrice.setText(String.valueOf(product.getPrice()));
            // Nếu có hình ảnh, hiển thị
            if (!product.getImageUrl().isEmpty()) {
                ivProductImage.setImageURI(Uri.parse(product.getImageUrl()));
            }
        }

        // Sự kiện cho nút chọn hình ảnh
        btnSelectImage.setOnClickListener(v -> openImageChooser());

        // Sự kiện cho nút sửa sản phẩm
        btnUpdateProduct.setOnClickListener(v -> updateProduct());
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

    // Cập nhật sản phẩm vào cơ sở dữ liệu
    private void updateProduct() {
        String name = etProductName.getText().toString().trim();
        String code = etProductCode.getText().toString().trim();
        String priceString = etProductPrice.getText().toString().trim();

        if (name.isEmpty() || code.isEmpty() || priceString.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceString);
        String imageUrl = imageUri != null ? imageUri.toString() : product.getImageUrl(); // Nếu không thay đổi hình ảnh, giữ lại URL cũ

        product.setName(name);
        product.setCode(code);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        boolean isUpdated = databaseHelper.updateProduct(product);

        if (isUpdated) {
            Toast.makeText(this, "Sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show();
            finish(); // Quay lại màn hình danh sách sản phẩm
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }
}
