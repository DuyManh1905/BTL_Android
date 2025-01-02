package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duymanh.btl.adapter.RecycleViewCompanyAdapter;
import com.duymanh.btl.adapter.RecycleViewJobAdapter;
import com.duymanh.btl.api.ApiResponseCompany;
import com.duymanh.btl.api.ApiResponseJobFitCompany;
import com.duymanh.btl.api.ApiResponseJobFitUser;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Company;
import com.duymanh.btl.model.Job;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CompanyActivity extends AppCompatActivity implements RecycleViewJobAdapter.ItemListener {

    private TextView tvDescription, tvAddress, tvCompanyName, slNhanVien, tvLinkCompany;
    private RecyclerView recyclerView;
    private RecycleViewJobAdapter adapter;

    private ImageView mapCompany;
    private TextView tvFollow, tvNumberFollow;

    private Company company;

    private ShapeableImageView avt_company;

    private ImageView imageBackground;

    private boolean isFollow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        getSupportActionBar().setTitle("Thông tin công ty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        company = (Company) intent.getSerializableExtra("item");
        initView();

        adapter = new RecycleViewJobAdapter(this);

        getAllJobByCompany();

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

        tvDescription.setText(company.getDesciption());
        slNhanVien.setText(company.getScale());
        tvAddress.setText(company.getAddress());
        tvCompanyName.setText(company.getName());
        tvLinkCompany.setText(company.getLinkWebsite());

        mapCompany.setOnClickListener(v -> {
            Intent intent1 = new Intent(CompanyActivity.this, MapActivity.class);
            startActivity(intent1);
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
        tvLinkCompany = findViewById(R.id.tvLinkCompany);
        slNhanVien = findViewById(R.id.slNhanVien);
        recyclerView = findViewById(R.id.recycleViewJobInCompany);
        tvDescription = findViewById(R.id.tvDescription);
        tvAddress = findViewById(R.id.tvAddress);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        mapCompany = findViewById(R.id.mapCompany);
        tvFollow = findViewById(R.id.tvFollow);
        tvNumberFollow = findViewById(R.id.tvNumberFollow);

        avt_company = findViewById(R.id.avt_company);
        String imageUrl = company.getAvataURL();
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.company2) // Hình ảnh mặc định khi tải
                .error(R.drawable.company2)       // Hình ảnh khi có lỗi
                .into(avt_company);

        imageBackground = findViewById(R.id.imageBackground);
        String imageCoverUrl = company.getImageCoverURL();
        Glide.with(this)
                .load(imageCoverUrl)
                .placeholder(R.drawable.company_cover) // Hình ảnh mặc định khi tải
                .error(R.drawable.company_cover)       // Hình ảnh khi có lỗi
                .into(imageBackground);

        checkFollowStatus();
        fetchNumberFollow();
    }

    private void fetchNumberFollow() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getNumberFollow(company.getId()).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int numberFollow = response.body();
                    tvNumberFollow.setText(numberFollow+" người theo dõi        ");
                } else {
                    Log.e("API_ERROR", "Response error: ");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("API_ERROR", "Request failed:");
            }
        });
    }

    public void getAllJobByCompany() {
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
        intent.putExtra("item", job);
        startActivity(intent);
    }

    private void checkFollowStatus() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<Boolean> call = apiService.checkFollow(Integer.parseInt(userId), company.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    isFollow = response.body();
                    updateFollowIcon();

                    tvFollow.setOnClickListener(v -> {
                        if(isFollow){
                            unFollowCompany();
                        }
                        else{
                            followCompany();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

        private void unFollowCompany() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<Void> call = apiService.unFollowCompany(Integer.parseInt(userId), company.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isFollow = false;
                    updateFollowIcon();
                    Toast.makeText(CompanyActivity.this, "Đã hủy theo dõi công ty!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CompanyActivity.this, "Không thể hủy follow", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Failed to remove saved job", t);
                Toast.makeText(CompanyActivity.this, "Lỗi khi unfollow!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void followCompany() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);
        SharedPreferences prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<Void> call = apiService.followCompany(Integer.parseInt(userId), company.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    isFollow = true;
                    updateFollowIcon();
                    Toast.makeText(CompanyActivity.this, "Đã follow công ty!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CompanyActivity.this, "Không thể lưu công việc!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Failed to save job", t);
                Toast.makeText(CompanyActivity.this, "Lỗi khi lưu công việc!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateFollowIcon() {
        if (isFollow) {
            tvFollow.setText("x Hủy theo dõi công ty");
            tvFollow.setTextColor(getResources().getColor(R.color.red));
            tvFollow.setBackgroundResource(R.drawable.rounded_border_red);
        } else {
            tvFollow.setText("+ Theo dõi công ty");
            tvFollow.setBackgroundResource(R.color.green);
        }
    }
}