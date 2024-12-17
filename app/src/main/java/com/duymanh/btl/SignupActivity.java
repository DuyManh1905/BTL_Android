package com.duymanh.btl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.duymanh.btl.R;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.User;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    private EditText edUserName, edEmail, edPassword, edRePassword, edName;
    private Button btnSignup;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edName = findViewById(R.id.edName);
        edUserName = findViewById(R.id.edUserName);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edRePassword = findViewById(R.id.edRePassword);
        btnSignup = findViewById(R.id.btnSignup);

        // Khởi tạo Retrofit và ApiService
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUserName.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String rePassword = edRePassword.getText().toString();
                String name = edName.getText().toString();

                // Kiểm tra các trường
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gọi phương thức đăng ký
                registerUser(email, password, username,name);
            }
        });
    }

    private void registerUser(String email, String password, String username, String name) {
        // Gọi API để đăng ký
        Call<ResponseDTO<User>> call = apiService.register(email, password, name, username);
        call.enqueue(new Callback<ResponseDTO<User>>() {
            @Override
            public void onResponse(Call<ResponseDTO<User>> call, Response<ResponseDTO<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    // Xử lý sau khi đăng ký thành công (ví dụ: chuyển hướng đến màn hình đăng nhập)
                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                } else {
                    // Xử lý lỗi nếu có
                    Toast.makeText(SignupActivity.this, "Registration Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<User>> call, Throwable t) {
                // Xử lý lỗi khi không kết nối được tới API
                Toast.makeText(SignupActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("SignupActivity", "Error: " + t.getMessage());
            }
        });
    }
}
