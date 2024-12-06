package com.example.duan1_nhom3.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.dao.NhanVienDao;
import com.example.duan1_nhom3.model.NhanVien;

import java.util.ArrayList;

public class NhanVienAdapter  extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<NhanVien> list;
    private NhanVienDao nhanVienDao;

    public NhanVienAdapter(Context context, ArrayList<NhanVien> list, NhanVienDao nhanVienDao) {
        this.context = context;
        this.list = list;
        this.nhanVienDao = nhanVienDao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlnv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText(String.valueOf(list.get(position).getId()));
        holder.ten.setText(list.get(position).getTenNV());
        holder.email.setText(list.get(position).getEmail());
        holder.diachi.setText(list.get(position).getDiachi());
        holder.sdt.setText(list.get(position).getSDT());
        holder.chucvu.setText(list.get(position).getChucvu());
        holder.user.setText(list.get(position).getUsername());
        holder.pass.setText(list.get(position).getPassword());
        holder.trangthai.setText(list.get(position).getTrangthai());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateDiaLog(list.get(holder.getAdapterPosition()));
            }
        });
        holder.doi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getTrangthai().equalsIgnoreCase("Đang làm")){
                    String ttmoi="Đã nghỉ";
                    boolean checktt= nhanVienDao.DoiTrangThai(list.get(position).getId(),ttmoi);
                    list.clear();
                    list=nhanVienDao.getDS();
                    notifyDataSetChanged();
                }else{
                    String ttmoi="Đang làm";
                    boolean checktt= nhanVienDao.DoiTrangThai(list.get(position).getId(),ttmoi);
                    list.clear();
                    list=nhanVienDao.getDS();
                    notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    TextView id,ten,email,diachi,sdt,chucvu,user,pass,trangthai;
    Button doi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.txtID);
            ten=itemView.findViewById(R.id.txtTen);
            email=itemView.findViewById(R.id.txtEmail);
            diachi=itemView.findViewById(R.id.txtDiachi);
            sdt=itemView.findViewById(R.id.txtSDT);
            chucvu=itemView.findViewById(R.id.txtChucVu);
            user=itemView.findViewById(R.id.txtUser);
            pass=itemView.findViewById(R.id.txtPass);
            trangthai=itemView.findViewById(R.id.txtTrangThai);
            doi=itemView.findViewById(R.id.btnTrangThai);

        }
    }
    public void UpdateDiaLog(NhanVien nhanVien){
        AlertDialog.Builder builder1=new AlertDialog.Builder(context);
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view1=inflater.inflate(R.layout.item_update_nv,null);
        builder1.setView(view1);
        Dialog Dialog1=builder1.create();
        Dialog1.show();
        Button Thoat=view1.findViewById(R.id.btnThoatup);
        Button Them=view1.findViewById(R.id.btnUPNV);

        EditText Ten=view1.findViewById(R.id.edtTenup);
        EditText email=view1.findViewById(R.id.edtEmailup);
        EditText diachi=view1.findViewById(R.id.edtDiaChiup);
        EditText sdt=view1.findViewById(R.id.edtSDTup);
        EditText chucvu=view1.findViewById(R.id.edtChucVuup);
        EditText user=view1.findViewById(R.id.edtUserup);
        EditText pass=view1.findViewById(R.id.edtPassup);

        Ten.setText(nhanVien.getTenNV().toString());
        email.setText(nhanVien.getEmail().toString());
        diachi.setText(nhanVien.getDiachi().toString());
        sdt.setText(nhanVien.getSDT().toString());
        chucvu.setText(nhanVien.getChucvu().toString());
        user.setText(nhanVien.getUsername().toString());
        pass.setText(nhanVien.getPassword().toString());




        Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TENUP=Ten.getText().toString();
                String EMAILUP=email.getText().toString();
                String DIACHIUP=diachi.getText().toString();
                String SDTUP=sdt.getText().toString();
                String CHUCVUUP=chucvu.getText().toString();
                String USERUP=user.getText().toString();
                String PASSUP=pass.getText().toString();
                String TRANGTHAIUP="Đang làm";

                if(TENUP.length()==0||EMAILUP.length()==0||DIACHIUP.length()==0||SDTUP.length()==0||CHUCVUUP.length()==0||USERUP.length()==0||PASSUP.length()==0){
                    Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else{
                    NhanVien nhanVien1=new NhanVien(nhanVien.getId(),TENUP,EMAILUP,SDTUP,DIACHIUP,USERUP,PASSUP,CHUCVUUP,TRANGTHAIUP);
                    boolean check= nhanVienDao.updateNV(nhanVien1);
                    if(check){
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list=nhanVienDao.getDS();
                        notifyDataSetChanged();

                        Dialog1.dismiss();
                    }else{
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog1.dismiss();
            }
        });

    }
}
