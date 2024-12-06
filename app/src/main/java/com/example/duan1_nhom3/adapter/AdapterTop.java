package com.example.duan1_nhom3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.model.Top;

import java.util.ArrayList;

public class AdapterTop extends RecyclerView.Adapter<AdapterTop.ViewHolder> {
    private Context context;
    private ArrayList<Top> topList;

    public AdapterTop(Context context, ArrayList<Top> topList) {
        this.context = context;
        this.topList = topList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Top top = topList.get(position);
        holder.textViewProductName.setText("Tên: "+top.getTenSanPham());
        holder.textViewQuantitySold.setText("Số lượng: "+String.valueOf(top.getSoLuongBan()));
        holder.textViewBrand.setText("Thương hiệu: "+String.valueOf(top.getGia()));
        holder.textViewProductPrice.setText("Giá: "+top.getGia());
    }

    @Override
    public int getItemCount() {
        return topList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductName;
        TextView textViewQuantitySold, textViewBrand, textViewProductPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewQuantitySold = itemView.findViewById(R.id.textViewQuantitySold);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
        }
    }
    // Cập nhật dữ liệu mới từ Fragment
    public void updateData(ArrayList<Top> newData) {
        if (newData != null) {
            this.topList.clear();
            this.topList.addAll(newData);
            notifyDataSetChanged();
        }
    }
}
