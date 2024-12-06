package com.example.duan1_nhom3.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom3.R;
import com.example.duan1_nhom3.dao.GioHangChiTietDao;
import com.example.duan1_nhom3.dao.GioHangDao;
import com.example.duan1_nhom3.dao.SanPhamDao;
import com.example.duan1_nhom3.model.GioHangChiTiet;
import com.example.duan1_nhom3.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class AdapterSP extends RecyclerView.Adapter<AdapterSP.ViewHolder> implements Filterable {

    private final Context context;
    private final GioHangChiTietDao gioHangChiTietDao;
    public ArrayList<SanPham> list;
    public ArrayList<GioHangChiTiet> list1;

    private final ArrayList<SanPham> fullList; // Danh sách đầy đủ, không bị lọc
    private final SanPhamDao sanPhamDao;
    private GioHangDao gioHangDao;
    int idGioHang;

    public AdapterSP(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        this.fullList = new ArrayList<>(list); // Sao chép danh sách để dùng khi cần lọc lại
        sanPhamDao = new SanPhamDao(context);
        gioHangChiTietDao = new GioHangChiTietDao(context);

    }

    public void filterList(ArrayList<SanPham> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlysp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        holder.textViewName.setText("Tên: " + sanPham.getName());
        holder.textViewQuantity.setText("Số lượng: " + sanPham.getQuantity());
        holder.textViewPrice.setText("Giá: " + sanPham.getPrice());
        holder.textViewColor.setText("Màu: " + sanPham.getColor());
        holder.textViewSize.setText("Size: " + sanPham.getSize());
        holder.textViewBrand.setText("Thương hiệu: " + sanPham.getBrand());
        holder.textViewStatus.setText("Trạng thái: " + (sanPham.getQuantity() > 0 ? "Còn hàng" : "Hết hàng"));

        holder.imageThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String password = sharedPreferences.getString("password", "");
                gioHangDao = new GioHangDao(context.getApplicationContext());
                idGioHang = gioHangDao.layIdGioHangTuUsernameVaMatKhau(username, password);

                list1 = gioHangChiTietDao.getList(idGioHang);

                boolean existsInCart = false;
                for (GioHangChiTiet gioHangChiTiet : list1) {
                    if (gioHangChiTiet.getId_San_Pham() == sanPham.getId()) {
                        existsInCart = true;
                        break;
                    }
                }

                if (!existsInCart) {
                    gioHangChiTietDao.add(sanPham.getId(), idGioHang);
                    Toast.makeText(holder.itemView.getContext(), "Đã thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Sản phẩm đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

@Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<SanPham> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(fullList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (SanPham item : fullList) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                list.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

    }

    private void addToCart(SanPham sanPham) {

        // Thêm sản phẩm vào giỏ hàng
        // Giả sử số lượng mặc định là 1

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewColor, textViewSize, textViewBrand, textViewPrice, textViewQuantity, textViewStatus;

        ImageView imageThemSP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewColor = itemView.findViewById(R.id.textViewColor);
            textViewSize = itemView.findViewById(R.id.textViewSize);
            textViewBrand = itemView.findViewById(R.id.textViewBrand);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            imageThemSP = itemView.findViewById(R.id.imggiohang);
        }
    }
}
