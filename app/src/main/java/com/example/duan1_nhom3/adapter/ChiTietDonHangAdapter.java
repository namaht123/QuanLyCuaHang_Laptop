package com.example.duan1_nhom3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.model.DonHangChiTiet;

import java.util.ArrayList;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DonHangChiTiet> listCT;

    public ChiTietDonHangAdapter(Context context, ArrayList<DonHangChiTiet> listCT) {
        this.context = context;
        this.listCT = listCT;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qldh_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangChiTiet donHangChiTiet = listCT.get(position);
        holder.txtTen.setText(donHangChiTiet.getTenSP());
        holder.txtMau.setText(donHangChiTiet.getMau());
        holder.txtSize.setText(donHangChiTiet.getKichThuoc());
        holder.txtSoLuong.setText(String.valueOf(donHangChiTiet.getSoLuong()));
        holder.txtGia.setText(String.valueOf(donHangChiTiet.getGia()));
    }

    @Override
    public int getItemCount() {
        return listCT.size();
    }

    public void updateData(ArrayList<DonHangChiTiet> newList) {
        listCT.clear();
        listCT.addAll(newList);
        notifyDataSetChanged();
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
