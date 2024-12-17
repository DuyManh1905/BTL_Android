package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.duymanh.btl.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        String username = prefs.getString("username", null);

        if (userId != null && username != null) {
            // Sử dụng thông tin người dùng
            Log.d("User Info", "User ID: " + userId + ", Username: " + username);
        }

        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        // Kiểm tra Intent để mở Fragment tương ứng
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fragment_to_open")) {
            String fragmentToOpen = intent.getStringExtra("fragment_to_open");
            if ("FragmentMess".equals(fragmentToOpen)) {
                viewPager.setCurrentItem(2); // Đặt trang tương ứng với FragmentMess
                navigationView.getMenu().findItem(R.id.mMess).setChecked(true);
            }
        }
        
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0: navigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1: navigationView.getMenu().findItem(R.id.mSchedule).setChecked(true);
                        break;
                    case 2: navigationView.getMenu().findItem(R.id.mMess).setChecked(true);
                        break;
                    case 3: navigationView.getMenu().findItem(R.id.mUser).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.mHome){
                    viewPager.setCurrentItem(0);
                }
                else if(item.getItemId()==R.id.mSchedule){
                    viewPager.setCurrentItem(1);
                }
                else if(item.getItemId()==R.id.mMess){
                    viewPager.setCurrentItem(2);
                }
                else if(item.getItemId()==R.id.mUser){
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            SharedPreferences preferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();  // Xóa tất cả dữ liệu
            editor.apply();  // Áp dụng thay đổi

            // Chuyển về LoginActivity sau khi đăng xuất
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Đảm bảo người dùng không quay lại Activity trước đó
            startActivity(intent);
            finish();  // Đóng Activity hiện tại
            return true;
        }
        return super.onContextItemSelected(item);
    }
}