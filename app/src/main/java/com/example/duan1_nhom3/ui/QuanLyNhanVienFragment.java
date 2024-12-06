package com.example.duan1_nhom3.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.adapter.NhanVienAdapter;
import com.example.duan1_nhom3.dao.NhanVienDao;
import com.example.duan1_nhom3.model.NhanVien;

import java.util.ArrayList;


public class QuanLyNhanVienFragment extends Fragment {
    ArrayList<NhanVien> list;
    RecyclerView rcNV;
    NhanVienDao nhanVienDao;
    NhanVienAdapter nhanVienAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_quan_ly_nhan_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcNV=view.findViewById(R.id.rcvQlNV);
        ImageView them=view.findViewById(R.id.imgThemNV);

        nhanVienDao=new NhanVienDao(getContext());
        list=nhanVienDao.getDS();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rcNV.setLayoutManager(linearLayoutManager);
        nhanVienAdapter=new NhanVienAdapter(getContext(),list,nhanVienDao);
        rcNV.setAdapter(nhanVienAdapter);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTNV();
            }
        });
    }
    public void loadnv(){
        list=nhanVienDao.getDS();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        rcNV.setLayoutManager(linearLayoutManager);
        nhanVienAdapter=new NhanVienAdapter(getContext(),list,nhanVienDao);
        rcNV.setAdapter(nhanVienAdapter);
    }
    public void DialogTNV(){
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        View view=getLayoutInflater().inflate(R.layout.item_add_nv,null);
        builder.setView(view);
        Dialog dialog=builder.create();
        dialog.show();
        EditText Ten=view.findViewById(R.id.edtTen);
        EditText Email=view.findViewById(R.id.edtEmail);
        EditText DiaChi=view.findViewById(R.id.edtDiaChi);
        EditText SDT=view.findViewById(R.id.edtSDT);
        EditText ChucVu=view.findViewById(R.id.edtChucVu);
        EditText User=view.findViewById(R.id.edtUser);
        EditText Pass=view.findViewById(R.id.edtPass);
        Button them=view.findViewById(R.id.btnADDNV);
        Button thoat=view.findViewById(R.id.btnThoat);
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tennv=Ten.getText().toString();
                String emailnv=Email.getText().toString();
                String diachi=DiaChi.getText().toString();
                String sdt=SDT.getText().toString();
                String cv=ChucVu.getText().toString();
                String use=User.getText().toString();
                String pass=Pass.getText().toString();
                String trangthai="Đang làm";
                if(tennv.length()==0||emailnv.length()==0||diachi.length()==0||sdt.length()==0||cv.length()==0||use.length()==0||pass.length()==0){
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }else{
                    NhanVien nhanVien=new NhanVien(1,tennv,emailnv,sdt,diachi,use,pass,cv,trangthai);
                    boolean check= nhanVienDao.themNV(nhanVien);
                    if(check){
                        Toast.makeText(getContext(), "Thêm thành viên thành công", Toast.LENGTH_SHORT).show();
                        loadnv();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm thành viên thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}