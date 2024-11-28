package com.example.quanlylaptop;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.quanlylaptop.Fragment.OrderFragment;
import com.example.quanlylaptop.Fragment.ProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Hiển thị ProductFragment mặc định
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new ProductFragment())
                    .commit();
        }

        // Tạo HashMap ánh xạ itemId tới các Fragment tương ứng
        HashMap<Integer, Fragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.menu_products, new ProductFragment());
        fragmentMap.put(R.id.menu_orders, new OrderFragment());

        // Xử lý sự kiện chọn menu
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = fragmentMap.get(item.getItemId());

            // Thay thế fragment nếu có
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}
