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
import com.squareup.picasso.Picasso; // Import Picasso

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(List<Product> productList, OnProductClickListener onProductClickListener) {
        this.productList = productList;
        this.onProductClickListener = onProductClickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item layout for each product
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productCode.setText(product.getCode());
        holder.productPrice.setText(String.valueOf(product.getPrice()));

        // Kiểm tra nếu URL hình ảnh không rỗng và hợp lệ trước khi tải
        String imageUrl = product.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Tải hình ảnh bằng Picasso nếu URL hợp lệ
            Picasso.get().load(imageUrl).into(holder.productImage);
        } else {
            // Nếu không có URL hình ảnh, sử dụng hình ảnh mặc định
            holder.productImage.setImageResource(R.drawable.ic_product);  // Thay "default_image" bằng hình ảnh mặc định của bạn
        }

        holder.itemView.setOnClickListener(v -> onProductClickListener.onProductClick(product));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
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
