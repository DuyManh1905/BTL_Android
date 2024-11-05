package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private ApiService apiService;

    private EditText userName, password;
    private Button btnLogin;

    private TextView txtSigup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class);


        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        txtSigup = findViewById(R.id.txtSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("manh","da vao ham login");
                String mUsername = userName.getText().toString();
                String mPassword = password.getText().toString();

                login(mUsername, mPassword);

            }
        });

        txtSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String xusername, String xpassword) {
        apiService.login(xusername, xpassword).enqueue(new Callback<ResponseDTO<String>>() {
            @Override
            public void onResponse(Call<ResponseDTO<String>> call, Response<ResponseDTO<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.i("manh", "dong 75");
                    if (response.body().getCode() == 200) {
                        String token = response.body().getData();
                        // Lưu token vào SharedPreferences
                        getSharedPreferences("app_prefs", MODE_PRIVATE)
                                .edit()
                                .putString("jwt_token", token)
                                .apply();

                        saveUserInfoFromToken(token);

                        // Chuyển đến Activity Home
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Thong tin dang nhap khong chinh xac", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO<String>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserInfoFromToken(String token) {
        // Giải mã JWT token
        JWT jwt = new JWT(token);

        // Lấy thông tin người dùng từ token
        String userId = jwt.getClaim("id").asString();
        String username = jwt.getSubject();  // subject (sub) là username trong ví dụ trên

        // Lưu thông tin vào SharedPreferences
        getSharedPreferences("app_prefs", MODE_PRIVATE)
                .edit()
                .putString("user_id", userId)
                .putString("username", username)
                .apply();
    }
}