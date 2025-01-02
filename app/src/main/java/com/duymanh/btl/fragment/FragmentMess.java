package com.duymanh.btl.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.duymanh.btl.AddCvActivity;
import com.duymanh.btl.CvActivity;
import com.duymanh.btl.R;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Cv;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentMess extends Fragment {

    private ImageView imgCv;
    private Button btnTaoCv;

    private TextView ngayTao;

    private Cv cv;

    private ApiService apiService;
    private LinearLayout layoutNoneCv, layoutCv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mess,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class); // Khởi tạo apiService

        imgCv = view.findViewById(R.id.imgCv);
        btnTaoCv = view.findViewById(R.id.btnTaoCv);
        ngayTao = view.findViewById(R.id.ngayTao);
        layoutNoneCv = view.findViewById(R.id.layoutNoneCv);
        layoutCv = view.findViewById(R.id.layoutCv);
        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        fetchCvData(Integer.parseInt(userId));


        imgCv.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CvActivity.class);
            startActivity(intent);
        });

        btnTaoCv.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddCvActivity.class);
            startActivity(intent);
        });
    }

    private void fetchCvData(int userId) {
        Log.d("CV", "onResponse: aaaaaaaaaaaaaaaa");
        apiService.getCvByUserId(userId).enqueue(new Callback<ResponseDTO<Cv>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Cv>> call, Response<ResponseDTO<Cv>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cv = response.body().getData();
                    Log.d("CV", "onResponse: "+cv.getName());
                    if(cv==null){
                        layoutNoneCv.setVisibility(View.VISIBLE);
                        layoutCv.setVisibility(View.GONE);
                    }
                    else {
                        ngayTao.setText("Ngày tạo: "+cv.getCreateAt());
                        layoutNoneCv.setVisibility(View.GONE);
                        layoutCv.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    handleNoCv();
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO<Cv>> call, Throwable t) {
                // Xử lý khi có lỗi
                Log.d("CV", "onResponse: bbbbbbbbbbbbbbbbb");
                layoutNoneCv.setVisibility(View.VISIBLE);
                layoutCv.setVisibility(View.GONE);
            }
        });
    }
    private void handleNoCv() {
        layoutNoneCv.setVisibility(View.VISIBLE);
        layoutCv.setVisibility(View.GONE);
    }
}
