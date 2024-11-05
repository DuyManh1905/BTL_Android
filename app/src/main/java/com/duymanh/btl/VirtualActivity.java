package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class VirtualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = getSharedPreferences("app_prefs", MODE_PRIVATE)
                .getString("jwt_token", null);

        Log.d("VirtualActivity", "Token: " + token);

        if (token != null) {
            // Người dùng đã đăng nhập, chuyển đến HomeActivity
            startActivity(new Intent(VirtualActivity.this, MainActivity.class));
        } else {
            // Người dùng chưa đăng nhập, chuyển đến LoginActivity
            startActivity(new Intent(VirtualActivity.this, LoginActivity.class));
        }
        finish();  // đóng MainActivity
    }
}