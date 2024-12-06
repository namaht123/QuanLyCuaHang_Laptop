package com.example.duan1_nhom3.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.adapter.DonHangCuaBanAdapter;
import com.example.duan1_nhom3.dao.DonHangDao;
import com.example.duan1_nhom3.model.DonHang;

import java.util.ArrayList;


public class DonHangCuaBanFragment extends Fragment {

    RecyclerView rcv;
    DonHangCuaBanAdapter adapter;
    DonHangDao dao;
    private ArrayList<DonHang> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_don_hang_cua_ban, container, false);
        dao = new DonHangDao(getContext());
        rcv=view.findViewById(R.id.rcvdonhangcuaban);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        list = dao.getListDonHangByLogin(username,password);
        adapter=new DonHangCuaBanAdapter(getContext(),list);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}