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

import com.duymanh.btl.adapter.RecycleViewCompanyAdapter;
import com.duymanh.btl.api.ApiResponseApplycationForm;
import com.duymanh.btl.api.ApiResponseCompany;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.ApplicationForm;
import com.duymanh.btl.model.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListFollowCompanyActivity extends AppCompatActivity implements RecycleViewCompanyAdapter.ItemListener, View.OnClickListener {


    private RecycleViewCompanyAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_follow_company);

        getSupportActionBar().setTitle("Các công ty đã follow");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycleViewFollow);
        initView();

        adapter = new RecycleViewCompanyAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        getAllCompanyFollow();
    }

    private void getAllCompanyFollow() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<ResponseDTO<List<Company>>> call = apiService.getAllFollowCompanies(Integer.parseInt(userId));
        call.enqueue(new Callback<ResponseDTO<List<Company>>>() {
            @Override
            public void onResponse(Call<ResponseDTO<List<Company>>> call, Response<ResponseDTO<List<Company>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Company> companies = response.body().getData();
                    // Cập nhật dữ liệu vào RecyclerView
                    adapter.setList(companies); // Giả sử bạn đã định nghĩa phương thức setList trong adapter
                } else {
                    Log.e("API_CALL", "Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO<List<Company>>> call, Throwable t) {
                // Xử lý lỗi
                Log.e("API_CALL", "onFailure: ", t);
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
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Company company = adapter.getSchedule(position);
        if (company == null) {
            Log.e("Error", "Job is null");
            return;
        }
        Intent intent = new Intent(this, CompanyActivity.class);
        intent.putExtra("item", company);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllCompanyFollow();
    }
}