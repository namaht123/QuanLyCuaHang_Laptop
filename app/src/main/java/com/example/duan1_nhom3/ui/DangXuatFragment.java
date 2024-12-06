package com.example.duan1_nhom3.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.duan1_nhom3.DangNhap;
import com.example.duan1_nhom3.R;


public class DangXuatFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        clearCredentials();
        Intent intent=new Intent(getContext(), DangNhap.class);
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_dang_xuat, container, false);
    }
    private void clearCredentials() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", requireContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.remove("remember");
        editor.apply();
    }
}