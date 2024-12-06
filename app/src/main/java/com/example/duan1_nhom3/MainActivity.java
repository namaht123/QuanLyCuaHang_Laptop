package com.example.duan1_nhom3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.duan1_nhom3.dao.KhachHangDao;
import com.example.duan1_nhom3.dao.NhanVienDao;
import com.example.duan1_nhom3.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private KhachHangDao khachHangDao;
    private NhanVienDao nhanVienDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        Intent intent = getIntent();
        String user = intent.getStringExtra("username");
        String pass = intent.getStringExtra("password");

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        ImageView circularImageView = binding.navView.getHeaderView(0).findViewById(R.id.imageView);

        khachHangDao=new KhachHangDao(this);
        nhanVienDao=new NhanVienDao(this);
        boolean check= khachHangDao.kiemTraTonTai(user,pass);
        boolean check1= nhanVienDao.kiemTraTonTai(user,pass);

        if (check==true) {
            navigationView.getMenu().findItem(R.id.nav_nhanvien).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_doanhthu).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_qlsanpham).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_donhang).setVisible(false);

        }else if(user.equals("admin")&&pass.equals("admin")) {
            navigationView.getMenu().findItem(R.id.nav_donhangcuaban).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_nhanvien).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_giohang).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_doanhthu).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_qlsanpham).setVisible(true);
        }else if(check1==true) {
            navigationView.getMenu().findItem(R.id.nav_donhangcuaban).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_nhanvien).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_doanhthu).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_giohang).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_qlsanpham).setVisible(true);
        }



        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.embe);

        Bitmap circularBitmap = getCircularBitmap(originalBitmap);

        circularImageView.setImageBitmap(circularBitmap);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_doanhthu, R.id.nav_top, R.id.nav_dangxuat,R.id.nav_doimk, R.id.nav_nhanvien, R.id.nav_donhang, R.id.nav_qlsanpham,R.id.nav_sanpham,R.id.nav_donhangcuaban)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    private Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int radius = Math.min(width, height) / 2;

        // Tạo một bitmap trống để chứa ảnh tròn
        Bitmap circularBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        // Tạo một canvas để vẽ ảnh tròn
        android.graphics.Canvas canvas = new android.graphics.Canvas(circularBitmap);
        android.graphics.Path path = new android.graphics.Path();
        path.addCircle(width / 2, height / 2, radius, android.graphics.Path.Direction.CW);
        canvas.clipPath(path);

        // Vẽ ảnh gốc lên canvas
        canvas.drawBitmap(bitmap, 0, 0, null);

        return circularBitmap;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}