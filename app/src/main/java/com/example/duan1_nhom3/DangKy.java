package com.example.duan1_nhom3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_nhom3.dao.KhachHangDao;

public class DangKy extends AppCompatActivity {
KhachHangDao khachHangDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        khachHangDao=new KhachHangDao(this);
        EditText ten=findViewById(R.id.edtTen);
        EditText diachi=findViewById(R.id.edtDiaChi);
        EditText sdt=findViewById(R.id.edtSDT);
        EditText email=findViewById(R.id.edtEmail);
        EditText name=findViewById(R.id.edtUsename);
        EditText pass=findViewById(R.id.edtPass);
        EditText repass=findViewById(R.id.edtRepass);
        Button signup=findViewById(R.id.btnSignUp);
        Button thoat=findViewById(R.id.btnThoatup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKh=ten.getText().toString();
                String dc=diachi.getText().toString();
                String sdtKH=sdt.getText().toString();
                String emailKH=email.getText().toString();
                String tendk=name.getText().toString();
                String passhk=pass.getText().toString();
                String repasshk=repass.getText().toString();
                if(!passhk.equals(repasshk)){
                    Toast.makeText(DangKy.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check=khachHangDao.Register(tenKh,emailKH,dc,sdtKH,tendk,passhk);
                    if(check){
                        Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangKy.this, DangNhap.class);
                        startActivity(intent);
                    }
                }
            }
        });
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            }
        });
    }
}