package com.example.duan1_nhom3.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.adapter.SanPhamAdapter;
import com.example.duan1_nhom3.dao.SanPhamDao;
import com.example.duan1_nhom3.model.SanPham;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class QuanLySanPhamFragment extends Fragment {
    private ArrayList<SanPham> list = new ArrayList<>();
    private SanPhamDao sanPhamDao;
    private SanPhamAdapter sanPhamAdapter;
    private RecyclerView rycSanPham;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_san_pham, container, false);

        // Khởi tạo các thành phần
        fab = view.findViewById(R.id.fab);
        rycSanPham = view.findViewById(R.id.rycSanPham);
        sanPhamDao = new SanPhamDao(getContext());
        list = sanPhamDao.getAllProductsWithDetails();
        sanPhamAdapter = new SanPhamAdapter(getContext(), list);

        // Thiết lập RecyclerView
        rycSanPham.setAdapter(sanPhamAdapter);
        rycSanPham.setLayoutManager(new LinearLayoutManager(getContext()));

        // Thiết lập sự kiện click cho nút FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog();
            }
        });

        return view;
    }

    private void showAddProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thêm sản phẩm mới");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_sanpham, null);
        builder.setView(view);

        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextQuantity = view.findViewById(R.id.editTextQuantity);
        final EditText editTextPrice = view.findViewById(R.id.editTextPrice);
        final EditText editTextBrand = view.findViewById(R.id.editTextBrand);
        final EditText editTextSize = view.findViewById(R.id.editTextSize);
        final EditText editTextColor = view.findViewById(R.id.editTextColor);
        final EditText editTextStatus = view.findViewById(R.id.editTextStatus); // Khai báo EditText cho trạng thái


        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy thông tin sản phẩm từ các EditText
                String name = editTextName.getText().toString();
                String quantityText = editTextQuantity.getText().toString();
                String priceText = editTextPrice.getText().toString();
                String size = editTextSize.getText().toString();
                String color = editTextColor.getText().toString();
                String brand = editTextBrand.getText().toString();
                String statusText = editTextStatus.getText().toString();

                // Kiểm tra xem các trường dữ liệu có đầy đủ không
                if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || size.isEmpty() || color.isEmpty() || brand.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

// Chuyển đổi số lượng và giá thành kiểu số nguyên
                int quantity, price, status;
                try {
                    quantity = Integer.parseInt(quantityText);
                    price = Integer.parseInt(priceText);
                    status = Integer.parseInt(statusText);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Vui lòng nhập số nguyên hợp lệ cho số lượng, giá và trạng thái", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra số lượng và giá có lớn hơn 0 không
                if (quantity < 0 || price <= 0) {
                    Toast.makeText(getContext(), "Số lượng và giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng sản phẩm mới
                SanPham newProduct = new SanPham(name, quantity, price, size, color, brand, status);

                // Thêm sản phẩm mới vào cơ sở dữ liệu
                sanPhamDao.addProduct(newProduct);

                // Cập nhật danh sách sản phẩm trên RecyclerView
                list.add(newProduct);
                sanPhamAdapter.notifyDataSetChanged();
                if (quantity > 0) {
                    editTextStatus.setText("Còn hàng");
                } else {
                    editTextStatus.setText("Hết hàng");
                }


                // Hiển thị thông báo thành công
                Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
