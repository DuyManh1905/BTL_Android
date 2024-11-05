package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.model.Job;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ConfirmApplyActivity extends AppCompatActivity {

    private ApplicationFormDTO applicationFormDTO;
    private Button btnApply;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_apply);

        Intent intent = getIntent();
        applicationFormDTO = (ApplicationFormDTO) intent.getSerializableExtra("applicationFormDTO");

        System.out.println(applicationFormDTO.getUser().getId()+" xxxxxxx");
        btnApply = findViewById(R.id.btnApply);

        btnApply.setOnClickListener(v -> {
            createApplication(applicationFormDTO);
        });
    }
    private void createApplication(ApplicationFormDTO applicationFormDTO) {

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class);
        apiService.createApplicationForm(applicationFormDTO).enqueue(new Callback<ResponseDTO<ApplicationFormDTO>>() {
            @Override
            public void onResponse(Call<ResponseDTO<ApplicationFormDTO>> call, Response<ResponseDTO<ApplicationFormDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(ConfirmApplyActivity.this, SuccessActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        String errorBody = response.errorBody().string();  // lấy thông tin chi tiết lỗi
                        Log.e("API_ERROR", "Error: " + response.message() + ", Error Body: " + errorBody);
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Error: " + response.message() + ", but failed to read error body");
                    }
                    Toast.makeText(ConfirmApplyActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<ApplicationFormDTO>> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc các lỗi khác
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(ConfirmApplyActivity.this, "Error occurred while creating application", Toast.LENGTH_SHORT).show();
            }
        });
    }
}