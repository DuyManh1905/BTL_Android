package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.dto.JobDTO;
import com.duymanh.btl.dto.UserDTO;
import com.duymanh.btl.model.Job;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApplyActivity extends AppCompatActivity {

    private TextView tvDiaDiemLamViec, tvThoiGiamLamViec, tvYeuCau, tvQuyenLoi;
    private TextView tvDescription, tvCompany, tvJobName, tvDiaChi, tvKinhNghiem, tvMucLuong, tvKinhnghiem2, tvHinhThuc, tvSoLuongTuyen, tvGioiTinh, tvCapBac, tvHanNop;
    private Button btnApply;
    private ShapeableImageView imgAvtCompany;
    private ImageView imgAvtCoverCompany;

    private ImageView ic_savedJob;
    private Job job;

    private boolean isSaved = false;


    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        getSupportActionBar().setTitle("Thông tin công việc");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        job = (Job) intent.getSerializableExtra("item");
        initView();

        Log.d("Debug", "Thời gian làm việc: " + job.getTime());

        tvDescription.setText(job.getDesciption());
        tvCompany.setText(job.getCompany().getName());
        tvJobName.setText(job.getTitle());
        tvDiaChi.setText(job.getJobRequirement().getArea());
        tvMucLuong.setText(job.getSalary());
        tvHinhThuc.setText(job.getForm());

        if (job.getNumberRecruitment() != 0) {
            tvSoLuongTuyen.setText(job.getNumberRecruitment() + "");
        } else {
            tvSoLuongTuyen.setText("Không giới hạn");
        }


        if (job.getJobRequirement() != null && !job.getJobRequirement().getExperience().equals("0")) {
            tvKinhNghiem.setText("> " + job.getJobRequirement().getExperience() + " năm");
            tvKinhnghiem2.setText("> " + job.getJobRequirement().getExperience() + " năm");
        } else {
            tvKinhNghiem.setText("khong y/c");
            tvKinhnghiem2.setText("khong y/c");
        }

        tvGioiTinh.setText(job.getJobRequirement().getSex());
        tvCapBac.setText(job.getRanking());
        tvHanNop.setText(dateLongtoShort(job.getEndAt()));
        tvDiaDiemLamViec.setText(job.getWorkLocation());
        tvThoiGiamLamViec.setText(job.getTime());

        tvQuyenLoi.setText(job.getInterest());

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
                intent.putExtra("applicationFormDTO", applicationFormDTO);
                startActivity(intent);

//                 Gọi API tạo application form
//                createApplication(applicationFormDTO);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Trở về activity trước đó
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initView() {
        tvQuyenLoi = findViewById(R.id.tvQuyenLoi);
        tvThoiGiamLamViec = findViewById(R.id.tvThoiGiamLamViec);
        tvDiaDiemLamViec = findViewById(R.id.tvDiaDiemLamViec);
        tvHanNop = findViewById(R.id.tvHanNop);
        tvCapBac = findViewById(R.id.tvCapBac);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvSoLuongTuyen = findViewById(R.id.tvSoLuongTuyen);
        tvHinhThuc = findViewById(R.id.tvHinhThuc);
        tvMucLuong = findViewById(R.id.tvMucLuong);
        tvDiaChi = findViewById(R.id.tvDiachi);
        tvKinhNghiem = findViewById(R.id.tvKinhnghiem);
        tvKinhnghiem2 = findViewById(R.id.tvKinhnghiem2);
        tvJobName = findViewById(R.id.tvJobName);
        tvDescription = findViewById(R.id.tvDescription);
        tvCompany = findViewById(R.id.tvCompanyName);
        btnApply = findViewById(R.id.btnApply);
        ic_savedJob = findViewById(R.id.ic_savedJob);

        imgAvtCoverCompany = findViewById(R.id.imgAvtCoverCompany);
        imgAvtCompany = findViewById(R.id.imgAvtCompany);

        String imageUrl = job.getCompany().getAvataURL();
        String imageCoverUrl = job.getCompany().getImageCoverURL();

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.company2) // Hình ảnh mặc định khi tải
                .error(R.drawable.company2)       // Hình ảnh khi có lỗi
                .into(imgAvtCompany);

        Glide.with(this)
                .load(imageCoverUrl)
                .placeholder(R.drawable.company_cover) // Hình ảnh mặc định khi tải
                .error(R.drawable.company_cover)       // Hình ảnh khi có lỗi
                .into(imgAvtCoverCompany);

        checkApply();
        checkJobSavedStatus();
    }

    public void checkApply() {
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        Integer userIdnum = Integer.parseInt(userId);
        Integer jobId = job.getId();

        System.out.println("userId la: " + userIdnum + ", " + "jobs id la: " + jobId);

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

    private String dateLongtoShort(String inputDate) {
        // Định dạng ban đầu của chuỗi
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // Định dạng mong muốn
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = "22/12/2024";

        try {
            // Chuyển đổi chuỗi sang Date
            Date date = inputFormat.parse(inputDate);
            // Định dạng lại Date thành chuỗi mong muốn
            formattedDate = outputFormat.format(date);
            // Hiển thị kết quả
            System.out.println("Ngày đã chuyển đổi: " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    private void checkJobSavedStatus() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<Boolean> call = apiService.checkUserSaveJob(Integer.parseInt(userId), job.getId());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    isSaved = response.body();
                    updateSavedJobIcon();

                    // Set OnClickListener for the icon
                    ic_savedJob.setOnClickListener(v -> {
                        if (isSaved) {
                            removeSaveJob();
                        } else {
                            saveJob();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("API_ERROR", "Failed to check job saved status", t);
                ic_savedJob.setImageResource(R.drawable.ic_bookmark_border);
            }
        });
    }


    private void removeSaveJob() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<Void> call = apiService.removeSaveJob(Integer.parseInt(userId), job.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isSaved = false;
                    updateSavedJobIcon();
                    Toast.makeText(ApplyActivity.this, "Đã xóa công việc khỏi danh sách lưu!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ApplyActivity.this, "Không thể xóa công việc!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Failed to remove saved job", t);
                Toast.makeText(ApplyActivity.this, "Lỗi khi xóa công việc!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void saveJob() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<Void> call = apiService.saveJob(Integer.parseInt(userId), job.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isSaved = true;
                    updateSavedJobIcon();
                    Toast.makeText(ApplyActivity.this, "Đã lưu công việc!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ApplyActivity.this, "Không thể lưu công việc!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Failed to save job", t);
                Toast.makeText(ApplyActivity.this, "Lỗi khi lưu công việc!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSavedJobIcon() {
        if (isSaved) {
            ic_savedJob.setImageResource(R.drawable.ic_bookmark);
        } else {
            ic_savedJob.setImageResource(R.drawable.ic_bookmark_border);
        }
    }


}