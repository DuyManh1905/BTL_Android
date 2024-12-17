package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duymanh.btl.adapter.RecycleViewCompanyAdapter;
import com.duymanh.btl.adapter.RecycleViewJobAdapter;
import com.duymanh.btl.api.ApiResponseCompany;
import com.duymanh.btl.api.ApiResponseJobFitCompany;
import com.duymanh.btl.api.ApiResponseJobFitUser;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Company;
import com.duymanh.btl.model.Job;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CompanyActivity extends AppCompatActivity implements RecycleViewJobAdapter.ItemListener {

    private TextView tvDescription, tvAddress, tvCompanyName, slNhanVien, tvLinkCompany;
    private RecyclerView recyclerView;
    private RecycleViewJobAdapter adapter;

    private ImageView mapCompany;

    private Company company;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        Intent intent = getIntent();
        company = (Company) intent.getSerializableExtra("item");
        initView();

        adapter = new RecycleViewJobAdapter(this);

        getAllJobByCompany();

        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        tvDescription.setText(company.getDesciption());
        slNhanVien.setText(company.getScale()+"+ nhân viên");
        tvAddress.setText(company.getAddress());
        tvCompanyName.setText(company.getName());
        tvLinkCompany.setText(company.getLinkWebsite());

        mapCompany.setOnClickListener(v -> {
            Intent intent1 = new Intent(CompanyActivity.this, MapActivity.class);
            startActivity(intent1);
        });
    }

    private void initView() {
        tvLinkCompany = findViewById(R.id.tvLinkCompany);
        slNhanVien = findViewById(R.id.slNhanVien);
        recyclerView = findViewById(R.id.recycleViewJobInCompany);
        tvDescription = findViewById(R.id.tvDescription);
        tvAddress = findViewById(R.id.tvAddress);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        mapCompany = findViewById(R.id.mapCompany);
    }
    public void getAllJobByCompany(){
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ApiResponseJobFitCompany> call = apiService.getAllJobByCompany(company.getId());
        call.enqueue(new Callback<ApiResponseJobFitCompany>() {
            @Override
            public void onResponse(Call<ApiResponseJobFitCompany> call, Response<ApiResponseJobFitCompany> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Job> companies = response.body().getData().getContents();
                    // Cập nhật dữ liệu vào RecyclerView
                    adapter.setList(companies); // Giả sử bạn đã định nghĩa phương thức setList trong adapter
                } else {
                    Log.e("API_CALL", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponseJobFitCompany> call, Throwable t) {
                // Xử lý lỗi
                Log.e("API_CALL", "onFailure: ", t);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Job job = adapter.getSchedule(position);
        Intent intent = new Intent(this, ApplyActivity.class);
        intent.putExtra("item",job);
        startActivity(intent);
    }
}