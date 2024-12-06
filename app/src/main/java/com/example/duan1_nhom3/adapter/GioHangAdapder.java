package com.example.duan1_nhom3.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.dao.GioHangChiTietDao;
import com.example.duan1_nhom3.dao.GioHangDao;
import com.example.duan1_nhom3.dao.SanPhamDao;
import com.example.duan1_nhom3.model.GioHang;
import com.example.duan1_nhom3.model.GioHangChiTiet;

import java.util.ArrayList;

public class GioHangAdapder extends RecyclerView.Adapter<GioHangAdapder.ViewHolder> {
    private Context context;

    private ArrayList<GioHang> list;
    private GioHangDao gioHangDao;
    private GioHangChiTietDao gioHangChiTietDao;
    public ArrayList<GioHangChiTiet> list1;
    private SanPhamDao sanPhamDao;



    public GioHangAdapder(Context context, ArrayList<GioHangChiTiet> list1) {
        this.context = context;
        this.list1 = list1;

        sanPhamDao= new SanPhamDao(context);
        gioHangChiTietDao = new GioHangChiTietDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GioHangChiTiet gioHangChiTiet= list1.get(position);
        holder.txtTen.setText(gioHangChiTiet.getTenSP());
        holder.txtSoLuong.setText(""+list1.get(position).getSoLuong());
        holder.btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slSP= sanPhamDao.getSoLuongSanPham(gioHangChiTiet.getId_San_Pham());
                int sl = Integer.parseInt(holder.txtSoLuong.getText().toString()) +1;
               if (sl<slSP){
                   holder.txtSoLuong.setText(""+sl);
                   gioHangChiTiet.setSoLuong(Integer.parseInt(holder.txtSoLuong.getText().toString()));
                   gioHangChiTietDao.updateSoLuong(gioHangChiTiet);
               }else if (sl==slSP){
                   holder.txtSoLuong.setText(""+slSP);
                   gioHangChiTiet.setSoLuong(slSP);
                   gioHangChiTietDao.updateSoLuong(gioHangChiTiet);

               }else {
                   holder.txtSoLuong.setText(""+slSP);

                   gioHangChiTiet.setSoLuong(slSP);
                   gioHangChiTietDao.updateSoLuong(gioHangChiTiet);
                   Toast.makeText(holder.itemView.getContext(), "vui lòng không nhập quá số lượng sản phaam", Toast.LENGTH_SHORT).show();
               }

            }

        });
        holder.btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(holder.txtSoLuong.getText().toString()) -1;

                if (sl<1){
                    holder.txtSoLuong.setText("1");
                    Toast.makeText(holder.itemView.getContext(), "vui lòng không nhập số lượng bằng 0", Toast.LENGTH_SHORT).show();
                }else {
                    holder.txtSoLuong.setText(""+sl);
                    gioHangChiTiet.setSoLuong(Integer.parseInt(holder.txtSoLuong.getText().toString()));
                    gioHangChiTietDao.updateSoLuong(gioHangChiTiet);

                }


            }
        });

        holder.txtGia.setText(String.valueOf(gioHangChiTiet.getGiaTien()));
        holder.btnHuy.setOnClickListener(v -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Xác nhận hủy");
            alertDialogBuilder.setMessage("Bạn có chắc chắn muốn hủy ?");
            alertDialogBuilder.setPositiveButton("Có", (dialog, which) -> {

                gioHangChiTietDao.deleteSanPham(gioHangChiTiet.getId_San_Pham());

                list1.remove(position);
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


    }

    @Override
    public int getItemCount() {
       return list1.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton btnTang,btnGiam,btnHuy;
        TextView txtTen, txtMau, txtSize, txtSoLuong, txtGia,txtHang,txtID,txtTong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnGiam=itemView.findViewById(R.id.btnGiam);
            btnTang=itemView.findViewById(R.id.btnTang);
            txtTen = itemView.findViewById(R.id.lvTen);

            txtSoLuong = itemView.findViewById(R.id.lvSoLuong);
            txtGia = itemView.findViewById(R.id.lvGia);

            btnHuy=itemView.findViewById(R.id.btnHuyGH);
        }
    }
    }
