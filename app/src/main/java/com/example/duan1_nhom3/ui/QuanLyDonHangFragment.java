package com.example.duan1_nhom3.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.adapter.DonHangAdapter;
import com.example.duan1_nhom3.dao.DonHangDao;
import com.example.duan1_nhom3.model.DonHang;

import java.util.ArrayList;

public class QuanLyDonHangFragment extends Fragment {
    RecyclerView rcv;
    DonHangAdapter adapter;
    DonHangDao dao;
    private ArrayList<DonHang> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_don_hang, container, false);
        rcv=view.findViewById(R.id.rcvQlDH);
        dao = new DonHangDao(getContext());
        list = dao.getList();
        adapter=new DonHangAdapter(getContext(),list);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}