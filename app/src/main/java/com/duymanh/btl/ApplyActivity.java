package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.dto.JobDTO;
import com.duymanh.btl.dto.UserDTO;
import com.duymanh.btl.model.Job;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApplyActivity extends AppCompatActivity {

    private TextView tvDescription, tvCompany, tvJobName;
    private Button btnApply;
    private Job job;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        Intent intent = getIntent();
        job = (Job) intent.getSerializableExtra("item");

        initView();

        tvDescription.setText(job.getDesciption());
        tvCompany.setText(job.getCompany().getName());
        tvJobName.setText(job.getTitle());

        String requirment = "yeu cau: \n";
        if(job.getJobRequirement()!=null){
            requirment+= job.getJobRequirement().getSkillRequire();
            requirment+= "\n";
            requirment+= job.getJobRequirement().getExperience()+" nam kinh nghiem";
        }


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplicationFormDTO applicationFormDTO = new ApplicationFormDTO();
                JobDTO jobDTO = new JobDTO();
                jobDTO.setId(job.getId());
                applicationFormDTO.setJob(jobDTO);
                UserDTO userDTO = new UserDTO();
                SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
                String userId = prefs.getString("user_id", null);
                userDTO.setId(Integer.parseInt(userId));
                applicationFormDTO.setUser(userDTO);

                Intent intent = new Intent(ApplyActivity.this, ConfirmApplyActivity.class);
                intent.putExtra("applicationFormDTO",applicationFormDTO);
                startActivity(intent);

//                 Gọi API tạo application form
//                createApplication(applicationFormDTO);
            }
        });
    }

    private void initView() {
        tvJobName = findViewById(R.id.tvJobName);
        tvDescription = findViewById(R.id.tvDescription);
        tvCompany = findViewById(R.id.tvCompanyName);
        btnApply = findViewById(R.id.btnApply);
        checkApply();
    }

    public void checkApply() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        Integer userIdnum = Integer.parseInt(userId);
        Integer jobId = job.getId();

        System.out.println("userId la: "+userIdnum+", "+"jobs id la: "+jobId);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class);

        apiService.checkApplicationFormExists(userIdnum, jobId).enqueue(new Callback<ResponseDTO<String>>() {
            @Override
            public void onResponse(Call<ResponseDTO<String>> call, Response<ResponseDTO<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String result = response.body().getData();
                    if ("1".equals(result)) {
                        btnApply.setText("Bạn đã ứng tuyển");
                        Drawable drawable = getResources().getDrawable(R.drawable.normal_button_gray);
                        btnApply.setTextColor(getColor(R.color.green));
                        btnApply.setBackground(drawable);
                        btnApply.setEnabled(false);
                    } else {
                        btnApply.setText("Ứng tuyển ngay");
                        Drawable drawable = getResources().getDrawable(R.drawable.normal_button_green);
                        btnApply.setBackground(drawable);
                    }
                } else {
                    // Log chi tiết hơn lỗi
                    System.out.println("Không gọi được API. Response code: " + response.code());
                    System.out.println("Error body: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<String>> call, Throwable t) {
                System.out.println("API bi loi");
                // Xử lý lỗi
//                Toast.makeText(JobDetailActivity.this, "Failed to check application: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createApplication(ApplicationFormDTO applicationFormDTO) {

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class);
        apiService.createApplicationForm(applicationFormDTO).enqueue(new Callback<ResponseDTO<ApplicationFormDTO>>() {
            @Override
            public void onResponse(Call<ResponseDTO<ApplicationFormDTO>> call, Response<ResponseDTO<ApplicationFormDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Intent intent = new Intent(ApplyActivity.this, SuccessActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        String errorBody = response.errorBody().string();  // lấy thông tin chi tiết lỗi
                        Log.e("API_ERROR", "Error: " + response.message() + ", Error Body: " + errorBody);
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Error: " + response.message() + ", but failed to read error body");
                    }
                    Toast.makeText(ApplyActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<ApplicationFormDTO>> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc các lỗi khác
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(ApplyActivity.this, "Error occurred while creating application", Toast.LENGTH_SHORT).show();
            }
        });
    }

}