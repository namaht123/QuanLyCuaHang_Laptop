package com.example.duan1_nhom3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_nhom3.dao.KhachHangDao;
import com.example.duan1_nhom3.dao.NhanVienDao;

public class DangNhap extends AppCompatActivity {
    private KhachHangDao khachHangDao;
    private NhanVienDao nhanVienDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        EditText edtUser=findViewById(R.id.edtUser);
        EditText edtPass=findViewById(R.id.edtPass);
        Button btnLogin=findViewById(R.id.btnLogin);
        CheckBox checkboxRemember = findViewById(R.id.checkbox_remember);
        TextView txtSignUp=findViewById(R.id.txtSignUp);
        khachHangDao =new KhachHangDao(this);
        nhanVienDao =new NhanVienDao(this);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=edtUser.getText().toString();
                String pass=edtPass.getText().toString();
                boolean shouldRemember = checkboxRemember.isChecked();
                boolean check= khachHangDao.kiemTraTonTai(user,pass);
                boolean check1= nhanVienDao.kiemTraTonTai(user,pass);

                if(check==true&&shouldRemember==true||check1==true&&shouldRemember==true){
                    SharedPreferences preferences1 = getSharedPreferences("UserInfo", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("username", user);
                    editor.putString("password", pass);
                    editor.putBoolean("remember", shouldRemember);
                    editor.apply();
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    intent.putExtra("username", user);
                    intent.putExtra("password", pass);
                    startActivity(intent);

                }else if(check==true||check1==true){
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    intent.putExtra("username", user);
                    intent.putExtra("password", pass);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString("username", null);
        String savedPassword = sharedPreferences.getString("password", null);
        boolean shouldRemember1 = sharedPreferences.getBoolean("remember", false);

        if (savedUsername != null && savedPassword != null && shouldRemember1) {
            edtUser.setText(savedUsername);
            edtPass.setText(savedPassword);
            checkboxRemember.setChecked(true);
        }
    }
}