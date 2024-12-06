package com.example.duan1_nhom3.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.dao.SanPhamDao;
import com.example.duan1_nhom3.model.SanPham;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SanPham> list;
    private SanPhamDao sanPhamDao;

    public SanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        sanPhamDao = new SanPhamDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPham sanPham = list.get(position);
        holder.textViewName.setText("Tên: " + sanPham.getName());
        holder.textViewColor.setText("Màu: " + sanPham.getColor());
        holder.textViewSize.setText("Size: " + sanPham.getSize());
        holder.textViewBrand.setText("Thương hiệu: " + sanPham.getBrand());
        holder.textViewPrice.setText("Giá: " + sanPham.getPrice());
        holder.textViewQuantity.setText("Số lượng: " + sanPham.getQuantity());
        holder.textViewStatus.setText("Trạng thái: " + (sanPham.getQuantity() > 0 ? "Còn hàng" : "Hết hàng"));

        // Xóa sản phẩm
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(position, sanPham);
            }
        });
    }

    private void showUpdateDialog(final int position, final SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cập nhật sản phẩm");

        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_sanpham, null);
        builder.setView(view);

        final TextView editTextName = view.findViewById(R.id.editTextName);
        final TextView editTextColor = view.findViewById(R.id.editTextColor);
        final TextView editTextSize = view.findViewById(R.id.editTextSize);
        final TextView editTextBrand = view.findViewById(R.id.editTextBrand);
        final TextView editTextPrice = view.findViewById(R.id.editTextPrice);
        final TextView editTextQuantity = view.findViewById(R.id.editTextQuantity);

        editTextName.setText(sanPham.getName());
        editTextQuantity.setText(String.valueOf(sanPham.getQuantity()));
        editTextPrice.setText(String.valueOf(sanPham.getPrice()));
        editTextBrand.setText(sanPham.getBrand());
        editTextSize.setText(sanPham.getSize());
        editTextColor.setText(sanPham.getColor());

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newName = editTextName.getText().toString();
                String newColor = editTextColor.getText().toString();
                String newSize = editTextSize.getText().toString();
                String newBrand = editTextBrand.getText().toString();
                String priceText = editTextPrice.getText().toString();
                String quantityText = editTextQuantity.getText().toString();

                if (newName.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || newSize.isEmpty() || newColor.isEmpty() || newBrand.isEmpty()) {
                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                    return;
                }

                int newQuantity = Integer.parseInt(quantityText);
                int newPrice = Integer.parseInt(priceText);

                if (newQuantity < 0 || newPrice <= 0) {
                    Toast.makeText(context, "Số lượng và giá phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                SanPham updatedSanPham = new SanPham(sanPham.getId(), newName, newQuantity, newPrice, newColor, newSize, newBrand, sanPham.getStatus());
                list.set(position, updatedSanPham);
                sanPhamDao.updateProduct(updatedSanPham);

                Toast.makeText(context, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
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



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewColor, textViewSize, textViewBrand, textViewPrice, textViewQuantity, textViewStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewSize = itemView.findViewById(R.id.textViewSize);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
        }
    }
}
