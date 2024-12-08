package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.duymanh.btl.adapter.RecycleViewJobAdapter;
import com.duymanh.btl.api.ApiResponseJobFitUser;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Job;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity implements RecycleViewJobAdapter.ItemListener{

    private SearchView searchView;
    private List<Job> jobs;

    private RecycleViewJobAdapter adapter;

    private Spinner spKhuVuc, spMucLuong, spKinhNghiem;
    private LinearLayout filter;
    private TextView soLuongJob;

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        recyclerView = findViewById(R.id.recycleView);
        adapter = new RecycleViewJobAdapter(this);
        getAllJobs();
        soLuongJob.setText(jobs.size()+"");
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    private void initView() {
        searchView = findViewById(R.id.search);
        spKhuVuc = findViewById(R.id.spKhuVuc);
        spMucLuong = findViewById(R.id.spMucLuong);
        spKinhNghiem = findViewById(R.id.spKinhNghiem);
        soLuongJob = findViewById(R.id.tvSoLuongJob);
        filter = findViewById(R.id.filter);

        String[] dskhuvuc = getResources().getStringArray(R.array.khuVuc);
        String[] dsLuong = getResources().getStringArray(R.array.luong);
        String[] dskinhNghiem = getResources().getStringArray(R.array.kinhNghiem);

        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<>(this, R.layout.item_spinner, dskhuvuc);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(this, R.layout.item_spinner, dsLuong);
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<>(this, R.layout.item_spinner, dskinhNghiem);

        spinnerAdapter1.setDropDownViewResource(R.layout.item_spinner);
        spinnerAdapter2.setDropDownViewResource(R.layout.item_spinner);
        spinnerAdapter3.setDropDownViewResource(R.layout.item_spinner);

        spKhuVuc.setAdapter(spinnerAdapter1);
        spMucLuong.setAdapter(spinnerAdapter2);
        spKinhNghiem.setAdapter(spinnerAdapter3);
        jobs = new ArrayList<>();

        filter.setOnClickListener(v -> {
            getAllJobs();
            adapter.notifyDataSetChanged();
        });
    }
    public void getAllJobs() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences prefs = getSharedPreferences("app_prefs",MODE_PRIVATE);

        //viet tai day
        // Lấy thông tin từ SearchView và Spinner
        String keyword = searchView.getQuery().toString(); // Lấy giá trị từ SearchView
        String area = spKhuVuc.getSelectedItem().toString(); // Lấy giá trị từ Spinner Khu Vực
        String salary = spMucLuong.getSelectedItem().toString(); // Lấy giá trị từ Spinner Mức Lương
        String experience = spKinhNghiem.getSelectedItem().toString(); // Lấy giá trị từ Spinner Kinh Nghiệm

        Log.d("duymanhxx", "keyword: "+keyword+" ,area: "+area+" ,salary: "+salary+" ,experience: "+experience);
        String minExperiance = "0";

        if(keyword.isEmpty()){
            keyword = "";
        }
        if(area.equals("Tất cả")){
            area="";
        }
        if(salary.equals("Tất cả")){
            salary="0";
        }
        else{
            salary = salary.split("\\s+")[1];
        }

        if(experience.equals("Tất cả")){
            experience="0";
        }
        else if(experience.equals("Sắp đi làm")){
            experience="0";
        }
        else{
            experience = experience.split("\\s+")[1];
        }

        Log.d("duymanhxx", "keyword: "+keyword+" ,area: "+area+" ,salary: "+salary+" ,experience: "+experience);
        Call<ApiResponseJobFitUser> call = apiService.searchAll(keyword,area,experience,"");
        call.enqueue(new Callback<ApiResponseJobFitUser>() {
            @Override
            public void onResponse(Call<ApiResponseJobFitUser> call, Response<ApiResponseJobFitUser> response) {
                if (response.isSuccessful() && response.body() != null) {
                    jobs = response.body().getData();
                    // Cập nhật dữ liệu vào RecyclerView
                    adapter.setList(jobs); // Giả sử bạn đã định nghĩa phương thức setList trong adapter

                    // Cập nhật số lượng công việc
                    soLuongJob.setText(jobs.size() + "");
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

    @Override
    public void onItemClick(View view, int position) {
        Job job = adapter.getSchedule(position);
        Intent intent = new Intent(this, ApplyActivity.class);
        intent.putExtra("item",job);
        startActivity(intent);
    }

}