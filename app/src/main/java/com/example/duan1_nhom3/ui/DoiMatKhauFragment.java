package com.example.duan1_nhom3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1_nhom3.DangNhap;
import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.dao.KhachHangDao;
import com.example.duan1_nhom3.dao.NhanVienDao;


public class DoiMatKhauFragment extends Fragment {
    KhachHangDao khachHangDao;
    NhanVienDao nhanVienDao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edtUse=view.findViewById(R.id.edtUser);
        EditText edtPas=view.findViewById(R.id.edtPass);
        EditText newPas=view.findViewById(R.id.edtnewpass);
        EditText renewPas=view.findViewById(R.id.edtrenewPass);
        Button Save=view.findViewById(R.id.btnSAVE);
        Button Thoat=view.findViewById(R.id.btnThoat);

        khachHangDao=new KhachHangDao(getContext());
        nhanVienDao=new NhanVienDao(getContext());


        Save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String use=edtUse.getText().toString();
                String Pas=edtPas.getText().toString();
                String newpa=newPas.getText().toString();
                String renew=renewPas.getText().toString();
                boolean checktkkh=khachHangDao.kiemTraTonTai(use,Pas);
                boolean checktknv= nhanVienDao.DoiMK(use,Pas);

                if(use.equals("")||Pas.equals("")||newpa.equals("")||renew.equals("")){
                    Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
                }else if(checktkkh==true){
                    if(!newpa.equals(renew)){
                        Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean check=khachHangDao.DoiMK(use,newpa);
                        if(check) {
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), DangNhap.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), "Đổi mk thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else if(checktknv==true){
                    if(!newpa.equals(renew)){
                        Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean check=nhanVienDao.DoiMK(use,newpa);
                        if(check) {
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), DangNhap.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getContext(), "Đổi mk thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }



            }
        });
        Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhap.class);
                startActivity(intent);
            }
        });

    }
}