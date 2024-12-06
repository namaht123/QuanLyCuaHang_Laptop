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
import com.example.duan1_nhom3.model.NhanVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DonHang> list;
    private DonHangDao donHangDao;
    private DonHangChiTietDao donHangChiTietDao;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public DonHangAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
        donHangChiTietDao = new DonHangChiTietDao(context);
        donHangDao = new DonHangDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_qldonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHang donHang = list.get(position);
        holder.txtMaDonHang.setText(String.valueOf(donHang.getId()));
        holder.txtTenKhachHang.setText(donHang.getTenKH());
        holder.txtTenNhanVien.setText(donHang.getTenNV());
        String ngayMuaFormatted = dateFormat.format(donHang.getNgayMua());
        holder.txtNgayMua.setText(ngayMuaFormatted);
        holder.txtTongTien.setText(String.valueOf(donHang.getTongTien()));

        if (donHang.getTrangThai() == 1) {
            holder.txtTrangThai.setText("Đơn hàng đã được xác nhận");
            holder.txtTrangThai.setTextColor(Color.parseColor("#5F65ED"));
            holder.linearLayout.setVisibility(View.GONE);
            holder.linearLayout1.setVisibility(View.VISIBLE);
            holder.btnGui.setOnClickListener(v1 -> {
                AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(context);
                alertDialogBuilder1.setTitle("Gửi đơn hàng");
                alertDialogBuilder1.setMessage("Đơn hàng sẽ được gửi?");
                alertDialogBuilder1.setPositiveButton("Có", (dialog, which) -> {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    String password = sharedPreferences.getString("password", "");
                    NhanVien nhanVien = donHangDao.getNhanVienByUsernameAndPassword(username, password);
                    donHangDao.updateNameAndStatus(donHang.getId(), nhanVien.getId(), 2);
                    list.get(position).setTenNV(nhanVien.getTenNV());
                    list.get(position).setTrangThai(2);
                    notifyDataSetChanged();

                });
                alertDialogBuilder1.setNegativeButton("Không", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog alertDialog1 = alertDialogBuilder1.create();
                alertDialog1.show();
            });
        } else if (donHang.getTrangThai() == 2) {
            holder.txtTrangThai.setText("Đơn hàng đang được vận chuyển");
            holder.txtTrangThai.setTextColor(Color.parseColor("#F42936"));
            holder.linearLayout.setVisibility(View.GONE);
            holder.linearLayout1.setVisibility(View.GONE);

        } else if (donHang.getTrangThai()==3) {
            holder.txtTrangThai.setText("Thành công");
            holder.txtTrangThai.setTextColor(Color.parseColor("#5F65ED"));
            holder.linearLayout.setVisibility(View.GONE);
            holder.linearLayout1.setVisibility(View.GONE);
        } else {
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.linearLayout1.setVisibility(View.GONE);
            holder.txtTrangThai.setText("Chờ xác nhận");
            holder.txtTrangThai.setTextColor(Color.parseColor("#F42936"));
            holder.btnHuy.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Xác nhận hủy");
                alertDialogBuilder.setMessage("Bạn có chắc chắn muốn hủy ?");
                alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {
                    donHangChiTietDao.deleteByDonHangId(donHang.getId());
                    donHangDao.delete(donHang.getId());
                    list.remove(position);
                    notifyDataSetChanged();

                    Toast.makeText(holder.itemView.getContext(), "Hủy thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                alertDialogBuilder.setNegativeButton("Không", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });
            holder.btnXacNhan.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Xác nhận đơn hàng");
                alertDialogBuilder.setMessage("Đơn hàng sẽ được xác nhận?");
                alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                    String username = sharedPreferences.getString("username", "");
                    String password = sharedPreferences.getString("password", "");
                    NhanVien nhanVien = donHangDao.getNhanVienByUsernameAndPassword(username, password);
                    donHangDao.updateNameAndStatus(donHang.getId(), nhanVien.getId(), 1);
                    list.get(position).setTenNV(nhanVien.getTenNV());
                    list.get(position).setTrangThai(1);
                    ArrayList<DonHangChiTiet> listCT = donHangChiTietDao.getList(donHang.getId());
                    boolean check=donHangDao.checkQuantity(listCT);
                    if (check==true){
                        holder.linearLayout1.setVisibility(View.VISIBLE);
                        holder.linearLayout.setVisibility(View.GONE);
                        donHangChiTietDao.updateProductQuantities(listCT);
                        notifyDataSetChanged();
                        Toast.makeText(holder.itemView.getContext(), "Xác nhận thành công đơn hàng", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Số lượng hàng trong kho không đủ", Toast.LENGTH_SHORT).show();
                        holder.linearLayout1.setVisibility(View.GONE);
                        holder.linearLayout.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }

                });
                alertDialogBuilder.setNegativeButton("Không", (dialogInterface, which) -> {
                    dialogInterface.dismiss();
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });
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
        TextView txtMaDonHang, txtTenKhachHang, txtTenNhanVien, txtNgayMua, txtTongTien, txtTrangThai;
        LinearLayout linearLayout,linearLayout1;
        Button btnXacNhan, btnHuy,btnGui;
        RecyclerView rcvSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.txtMaDonHang);
            txtTenKhachHang = itemView.findViewById(R.id.txtQLDHTenKhachHang);
            txtTenNhanVien = itemView.findViewById(R.id.txtQLDHTenNV);
            txtNgayMua = itemView.findViewById(R.id.txtNgayThue);
            txtTongTien = itemView.findViewById(R.id.txtTongTien);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            btnHuy = itemView.findViewById(R.id.btnHuy);
            btnGui = itemView.findViewById(R.id.btnGui1);
            btnXacNhan = itemView.findViewById(R.id.btnXacNhan);
            linearLayout = itemView.findViewById(R.id.lilButon);
            linearLayout1 = itemView.findViewById(R.id.lilButon1);
            rcvSanPham = itemView.findViewById(R.id.rcvQLDHSanPham);
        }
    }

}
