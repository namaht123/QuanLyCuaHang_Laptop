package com.example.duan1_nhom3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.model.SanPham;

import java.util.ArrayList;

public class ChiTeitAddSPDonHang extends RecyclerView.Adapter<ChiTeitAddSPDonHang.ViewHolder> {

    private Context context;
    private ArrayList<SanPham> list;

    public ChiTeitAddSPDonHang(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qldh_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        holder.txtTen.setText(sanPham.getName());
        holder.txtMau.setText(sanPham.getColor());
        holder.txtSize.setText(sanPham.getSize());
        holder.txtSoLuong.setText(String.valueOf(sanPham.getQuantity()));
        holder.txtGia.setText(String.valueOf(sanPham.getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtMau, txtSize, txtSoLuong, txtGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtMau = itemView.findViewById(R.id.txtMau);
            txtSize = itemView.findViewById(R.id.txtSize);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            txtGia = itemView.findViewById(R.id.txtGiaTien);
        }
    }
}
