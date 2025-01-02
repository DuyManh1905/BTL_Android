package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.duymanh.btl.adapter.RecycleViewCompanyAdapter;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Company;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchCompanyActivity extends AppCompatActivity implements RecycleViewCompanyAdapter.ItemListener{

    private SearchView searchView;
    private List<Company> companies;
    private TextView soLuongCompany;

    private RecycleViewCompanyAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_company);

        getSupportActionBar().setTitle("Tìm kiếm công ty");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

        recyclerView = findViewById(R.id.recycleView);
        adapter = new RecycleViewCompanyAdapter();
        getAllCompany("");
        soLuongCompany.setText(companies.size()+"");
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

    }

    private void initView() {
        searchView = findViewById(R.id.search);
        soLuongCompany = findViewById(R.id.tvSoLuongCompany);
        companies = new ArrayList<>();
//        searchView.setOnSearchClickListener(v -> {
//            getAllCompany();
//            adapter.notifyDataSetChanged();
//        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getAllCompany(newText);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void getAllCompany(String title) {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

//        String title = searchView.getQuery().toString();
        Call<List<Company>> call = apiService.searchAll(title);
        call.enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(Call<List<Company>> call, Response<List<Company>> response) {
                if(response.isSuccessful() && response.body() != null){
                    companies = response.body();
                    adapter.setList(companies);
                    soLuongCompany.setText(companies.size()+"");
                }
                else{
                    Log.e("API_CALL", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Company>> call, Throwable t) {
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

    @Override
    public void onItemClick(View view, int position) {
        Company company = adapter.getSchedule(position);
        Intent intent = new Intent(this, CompanyActivity.class);
        intent.putExtra("item",company);
        startActivity(intent);
    }
}