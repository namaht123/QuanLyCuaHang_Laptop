package com.example.duan1_nhom3.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.dao.DonHangChiTietDao;
import com.example.duan1_nhom3.dao.DonHangDao;
import com.example.duan1_nhom3.model.DonHang;
import com.example.duan1_nhom3.model.DonHangChiTiet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonHangCuaBanAdapter extends RecyclerView.Adapter<DonHangCuaBanAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DonHang> list;
    private DonHangDao donHangDao;
    private DonHangChiTietDao donHangChiTietDao;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public DonHangCuaBanAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
        donHangChiTietDao = new DonHangChiTietDao(context);
        donHangDao = new DonHangDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_donhangcuaban, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        String ngayMuaFormatted = dateFormat.format(donHang.getNgayMua());
        holder.txtNgayMua.setText(ngayMuaFormatted);
        holder.txtTongTien.setText(String.valueOf(donHang.getTongTien()));

        if (donHang.getTrangThai() == 1) {
            holder.txtTrangThai.setText("Đơn hàng đã được xác nhận");
            holder.txtTrangThai.setTextColor(Color.parseColor("#5F65ED"));
            holder.linearLayout.setVisibility(View.GONE);

        } else if (donHang.getTrangThai() == 2) {
            holder.txtTrangThai.setText("Đơn hàng đang được vận chuyển");
            holder.txtTrangThai.setTextColor(Color.parseColor("#F42936"));
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.btnXacNhan.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Nhận được hàng");
                alertDialogBuilder.setMessage("Bạn đã nhận được hàng chưa?");
                alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    String password = sharedPreferences.getString("password", "");
                    donHangDao.updateNameAndStatus(donHang.getId(),donHang.getIdNV(), 3);
                    list.get(position).setTrangThai(3);
                    ArrayList<DonHangChiTiet> listCT = donHangChiTietDao.getList(donHang.getId());
                    notifyDataSetChanged();
                    holder.linearLayout.setVisibility(View.GONE);

                    Toast.makeText(holder.itemView.getContext(), "Cảm ơn bạn đã mua hàng", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            });alertDialogBuilder.setNegativeButton("Không", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });

        } else if (donHang.getTrangThai() == 3) {
            holder.txtTrangThai.setText("Thành công");
            holder.txtTrangThai.setTextColor(Color.parseColor("#5F65ED"));
            holder.linearLayout.setVisibility(View.GONE);
        } else {
            holder.linearLayout.setVisibility(View.GONE);
            holder.txtTrangThai.setText("Chờ xác nhận");
            holder.txtTrangThai.setTextColor(Color.parseColor("#F42936"));
        }
        ArrayList<DonHangChiTiet> listCT = donHangChiTietDao.getList(donHang.getId());
        ChiTietDonHangAdapter chiTietDonHangAdapter = new ChiTietDonHangAdapter(context, listCT);
        holder.rcvSanPham.setAdapter(chiTietDonHangAdapter);
        holder.rcvSanPham.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNgayMua, txtTongTien, txtTrangThai;
        LinearLayout linearLayout;
        Button btnXacNhan;
        RecyclerView rcvSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNgayMua = itemView.findViewById(R.id.txtNgayThue1);
            txtTongTien = itemView.findViewById(R.id.txtTongTien1);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai1);
            btnXacNhan = itemView.findViewById(R.id.btnNhanHang);
            linearLayout = itemView.findViewById(R.id.lilButon1);
            rcvSanPham = itemView.findViewById(R.id.rcvQLDHSanPhamCuaBan);
        }
    }

}
