package com.example.duan1_nhom3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class ManHinhChao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchao);
        Handler handler = new Handler();
        ImageView cm=findViewById(R.id.chaomung);
        // Áp dụng animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
        cm.startAnimation(animation);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ManHinhChao.this, DangNhap.class);
                startActivity(intent);
            }
        }, 3500);
    }
}