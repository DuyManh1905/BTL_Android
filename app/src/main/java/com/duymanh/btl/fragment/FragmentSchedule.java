package com.duymanh.btl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.duymanh.btl.ApplyActivity;
import com.duymanh.btl.CompanyActivity;
import com.duymanh.btl.R;
import com.duymanh.btl.SearchCompanyActivity;
import com.duymanh.btl.adapter.RecycleViewCompanyAdapter;

import com.duymanh.btl.api.ApiResponseCompany;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Company;
import com.duymanh.btl.model.Job;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentSchedule extends Fragment implements RecycleViewCompanyAdapter.ItemListener,View.OnClickListener{

    private RecycleViewCompanyAdapter adapter;
    private RecyclerView recyclerView;
    private Spinner spCategory;

    private TextView tvXemTatCa;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule,container,false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);

        initView(view);

        adapter = new RecycleViewCompanyAdapter();

        getAllCompanies();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);

    }


    private void initView(View view) {
        tvXemTatCa = view.findViewById(R.id.tvXemTatCa);
        tvXemTatCa.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchCompanyActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        Company company = adapter.getSchedule(position);
        Intent intent = new Intent(getActivity(), CompanyActivity.class);
        intent.putExtra("item",company);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllCompanies();
    }


    //Xu ly khi chon ngay thang
    @Override
    public void onClick(View v) {
//        if(v==eFrom){
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    String date="";
//                    if(month>8){
//                        date = dayOfMonth+"/"+(month+1)+"/"+year;
//                    }
//                    else{
//                        date = dayOfMonth+"/0"+(month+1)+"/"+year;
//                    }
//                    eFrom.setText(date);
//                }
//            },year,month,day);
//            dialog.show();
//        }
//        if(v==eTo){
//            final Calendar c = Calendar.getInstance();
//            int year = c.get(Calendar.YEAR);
//            int month = c.get(Calendar.MONTH);
//            int day = c.get(Calendar.DAY_OF_MONTH);
//            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                    String date="";
//                    if(month>8){
//                        date = dayOfMonth+"/"+(month+1)+"/"+year;
//                    }
//                    else{
//                        date = dayOfMonth+"/0"+(month+1)+"/"+year;
//                    }
//                    eTo.setText(date);
//                }
//            },year,month,day);
//            dialog.show();
//        }
//        if(v==btSearch){
//            String from = eFrom.getText().toString();
//            String to = eTo.getText().toString();
//            if(!from.isEmpty() && !to.isEmpty()){
//                List<Schedule> list = db.searchByDateFromTo(from,to);
//                adapter.setList(list);
//                tvCount.setText("Have :"+count(list)+" task");
//
//            }
//        }
    }
    public void getAllCompanies() {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ApiResponseCompany> call = apiService.getAllCompany();
        call.enqueue(new Callback<ApiResponseCompany>() {
            @Override
            public void onResponse(Call<ApiResponseCompany> call, Response<ApiResponseCompany> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Company> companies = response.body().getData().getContents();
                    // Cập nhật dữ liệu vào RecyclerView
                    adapter.setList(companies); // Giả sử bạn đã định nghĩa phương thức setList trong adapter
                } else {
                    Log.e("API_CALL", "Response not successful: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponseCompany> call, Throwable t) {
                // Xử lý lỗi
                Log.e("API_CALL", "onFailure: ", t);
            }
        });
    }
}
