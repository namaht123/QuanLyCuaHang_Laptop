package com.example.quanlylaptop.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlylaptop.Model.Product;
import com.example.quanlylaptop.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private List<Product> originalProductList; // Danh sách gốc để tìm kiếm
    private final OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener onProductClickListener) {
        this.productList = productList;
        this.originalProductList = new ArrayList<>(productList); // Sao lưu danh sách gốc
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // Hiển thị tên, mã và giá sản phẩm
        holder.productName.setText(product.getName());
        holder.productCode.setText(product.getCode());

        // Định dạng giá sản phẩm
        String formattedPrice = NumberFormat.getNumberInstance(Locale.getDefault()).format(product.getPrice()) + " VND";
        holder.productPrice.setText(formattedPrice);

        // Kiểm tra URL ảnh và tải bằng Picasso
        String imageUrl = product.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_product)
                    .error(R.drawable.ic_error)
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.ic_product);
        }

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(v -> onProductClickListener.onProductClick(product));
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    // Phương thức cập nhật dữ liệu cho adapter
    public void updateData(List<Product> newProductList) {
        this.productList = newProductList;
        this.originalProductList = new ArrayList<>(newProductList); // Cập nhật danh sách gốc
        notifyDataSetChanged();
    }

    // Phương thức tìm kiếm
    public void filter(String query) {
        if (query == null || query.trim().isEmpty()) {
            // Nếu không có từ khóa, khôi phục danh sách gốc
            productList = new ArrayList<>(originalProductList);
        } else {
            List<Product> filteredList = new ArrayList<>();
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());

            for (Product product : originalProductList) {
                // Kiểm tra nếu tên hoặc mã sản phẩm chứa từ khóa
                if (product.getName().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        product.getCode().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    filteredList.add(product);
                }
            }

            productList = filteredList;
        }
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, productCode, productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productCode = itemView.findViewById(R.id.productCode);
            productPrice = itemView.findViewById(R.id.productPrice);
        }
    }
}
