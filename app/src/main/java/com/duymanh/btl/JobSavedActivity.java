package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.duymanh.btl.adapter.RecycleViewJobAdapter;
import com.duymanh.btl.api.ApiResponseJobFitUser;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Job;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class JobSavedActivity extends AppCompatActivity implements RecycleViewJobAdapter.ItemListener{

    private RecycleViewJobAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_saved);

        getSupportActionBar().setTitle("Công việc đã lưu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new RecycleViewJobAdapter(this);
        recyclerView = findViewById(R.id.recycleSavedJob);
        initView();
        getAllJobsSaved();
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
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

    private void getAllJobsSaved() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<ApiResponseJobFitUser> call = apiService.getAllJobSaved(Integer.parseInt(userId));
        call.enqueue(new Callback<ApiResponseJobFitUser>() {
            @Override
            public void onResponse(Call<ApiResponseJobFitUser> call, Response<ApiResponseJobFitUser> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Job> jobs = response.body().getData();
                    // Cập nhật dữ liệu vào RecyclerView
                    adapter.setList(jobs); // Giả sử bạn đã định nghĩa phương thức setList trong adapter
                } else {
                    Log.e("API_CALL", "Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ApiResponseJobFitUser> call, Throwable t) {
                // Xử lý lỗi
                Log.e("API_CALL", "onFailure: ", t);
            }
        });
    }

    private void initView() {
    }

    @Override
    public void onItemClick(View view, int position) {
        Job job = adapter.getSchedule(position);
        Intent intent = new Intent(this, ApplyActivity.class);
        intent.putExtra("item",job);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        System.out.println("iiiiii:da vao resume");
        super.onResume();
        getAllJobsSaved();
    }
}