package com.duymanh.btl.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.ApplyActivity;
import com.duymanh.btl.R;
import com.duymanh.btl.SearchActivity;
import com.duymanh.btl.adapter.RecycleViewJobAdapter;
import com.duymanh.btl.api.ApiResponseJobFitUser;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentHome extends Fragment implements RecycleViewJobAdapter.ItemListener {
    private RecycleViewJobAdapter adapter;
    private RecyclerView recyclerView;
    private TextView nowDate, tvXemTatCa;
    private SearchView searchView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);

        adapter = new RecycleViewJobAdapter(requireContext());

        searchView = view.findViewById(R.id.search);


        initView(view);

        getAllJobs();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

    }

    private void initView(View view) {
        tvXemTatCa = view.findViewById(R.id.tvXemTatCa);
        nowDate = view.findViewById(R.id.nowDate);
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String day = f.format(d);
        nowDate.setText(day);
        searchView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });

        tvXemTatCa.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Job job = adapter.getSchedule(position);
        Intent intent = new Intent(getActivity(), ApplyActivity.class);
        intent.putExtra("item",job);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        System.out.println("iiiiii:da vao resume");
        super.onResume();
        getAllJobs();
    }

    public void getAllJobs() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        Call<ApiResponseJobFitUser> call = apiService.getAllJobsFitUser(Integer.parseInt(userId));
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
}
