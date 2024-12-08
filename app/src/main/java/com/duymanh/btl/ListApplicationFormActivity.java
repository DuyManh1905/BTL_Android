package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.duymanh.btl.adapter.RecycleViewApplycationAdapter;
import com.duymanh.btl.api.ApiResponseApplycationForm;
import com.duymanh.btl.api.ApiResponseJobFitUser;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.ApplicationForm;
import com.duymanh.btl.model.Company;
import com.duymanh.btl.model.Job;
import com.duymanh.btl.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListApplicationFormActivity extends AppCompatActivity implements RecycleViewApplycationAdapter.ItemListener, View.OnClickListener {

    private RecycleViewApplycationAdapter adapter;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_application_form);

        recyclerView = findViewById(R.id.recycleViewApply);
        initView();

        adapter = new RecycleViewApplycationAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        getAllApplicationForm();
    }

    private void getAllApplicationForm() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<ApiResponseApplycationForm> call = apiService.getAllApplycationForm(Integer.parseInt(userId));
        call.enqueue(new Callback<ApiResponseApplycationForm>() {
            @Override
            public void onResponse(Call<ApiResponseApplycationForm> call, Response<ApiResponseApplycationForm> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ApplicationForm> jobs = response.body().getData().getContents();
                    // Cập nhật dữ liệu vào RecyclerView
                    adapter.setList(jobs); // Giả sử bạn đã định nghĩa phương thức setList trong adapter
                } else {
                    Log.e("API_CALL", "Response not successful: " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ApiResponseApplycationForm> call, Throwable t) {
                // Xử lý lỗi
                Log.e("API_CALL", "onFailure: ", t);
            }
        });
    }


    private void initView() {
    }

    @Override
    public void onClick(View v) {
        //xu ly cac nut bam cac thanh phan trong item
    }

    @Override
    public void onItemClick(View view, int position) {
        ApplicationForm applicationForm = adapter.getSchedule(position);
        Job job = applicationForm.getJob(); // Truy cập Job trực tiếp từ ApplicationForm

        if (job == null) {
            Log.e("Error", "Job is null");
            return;
        }

        Intent intent = new Intent(this, ApplyActivity.class);
        intent.putExtra("item", job);
        startActivity(intent);

    }

    @Override
    public void onViewCompanyClick(View view, int position) {
        ApplicationForm applicationForm = adapter.getSchedule(position);
        Company company = applicationForm.getJob().getCompany();

        if (company != null) {
            // Mở Activity chi tiết về công ty
            Intent intent = new Intent(this, CompanyActivity.class);
            intent.putExtra("item", company);  // Truyền thông tin công ty
            startActivity(intent);
        }
    }

    @Override
    public void onViewCVClick(View view, int position) {
        ApplicationForm applicationForm = adapter.getSchedule(position);
        Job job = applicationForm.getJob();

        if (job != null) {
            // Mở Activity chi tiết CV
            Toast.makeText(this,"CV",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAllApplicationForm();
    }
}