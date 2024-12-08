package com.duymanh.btl.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.auth0.android.jwt.JWT;
import com.duymanh.btl.ChangePasswordActivity;
import com.duymanh.btl.JobSavedActivity;
import com.duymanh.btl.ListApplicationFormActivity;
import com.duymanh.btl.LoginActivity;
import com.duymanh.btl.R;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentUser extends Fragment {

    private LinearLayout btnLogOut;
    private ApiService apiService;

    private LinearLayout changePassword;


    private LinearLayout viecLamDaUngTuyen, viecLamDaLuu, viecLamPhuHop, congTyDangTheoDoi;
    private TextView numberviecLamDaUngTuyen, numberviecLamDaLuu, numberviecLamPhuHop, numberCongTyDangTheoDoi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);

        btnLogOut = view.findViewById(R.id.btnLogout);
        changePassword = view.findViewById(R.id.changePassword);

        viecLamDaUngTuyen = view.findViewById(R.id.viecLamDaUngTuyen);
        viecLamDaLuu = view.findViewById(R.id.viecLamDaLuu);
        viecLamPhuHop = view.findViewById(R.id.viecLamPhuHop);
        congTyDangTheoDoi = view.findViewById(R.id.congTyDangTheoDoi);

        numberviecLamDaUngTuyen = view.findViewById(R.id.numberViecLamDaUngTuyen);
        numberviecLamDaLuu = view.findViewById(R.id.numberViecLamDaLuu);
        numberviecLamPhuHop = view.findViewById(R.id.numberViecLamPhuHop);
        numberCongTyDangTheoDoi = view.findViewById(R.id.numberCongTyDangTheoDoi);


        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class); // Khởi tạo apiService

        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        if (userId != null) {
            int parsedUserId = Integer.parseInt(userId);
            fetchApplicationFormCount(parsedUserId); // Gọi phương thức fetchApplicationFormCount
            fetchSavedJobsCount(parsedUserId);
        }

//        numberviecLamDaLuu.setText("2");
        numberviecLamPhuHop.setText("30");
        numberCongTyDangTheoDoi.setText("4");

        viecLamDaUngTuyen.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListApplicationFormActivity.class);
            startActivity(intent);
        });

        viecLamDaLuu.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), JobSavedActivity.class);
            startActivity(intent);
        });

        viecLamPhuHop.setOnClickListener(v -> {
            Toast.makeText(getContext(),"numberviecLamPhuHop",Toast.LENGTH_SHORT).show();
        });

        congTyDangTheoDoi.setOnClickListener(v -> {
            Toast.makeText(getContext(),"numberCongTyDangTheoDoi",Toast.LENGTH_SHORT).show();
        });

        changePassword.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });





        btnLogOut.setOnClickListener(v -> {
            // Lấy SharedPreferences từ Activity
            SharedPreferences preferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();  // Xóa tất cả dữ liệu
            editor.apply();  // Áp dụng thay đổi

            // Chuyển về LoginActivity sau khi đăng xuất
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Đảm bảo người dùng không quay lại Activity trước đó
            startActivity(intent);
            requireActivity().finish();  // Đóng Activity chứa Fragment hiện tại
        });
        if (userId != null) {
            fetchUserData(Integer.parseInt(userId));
        }

        // Khi nhấn nút Update
        return view;
    }
    private void fetchUserData(int userId) {
        apiService.getUserDashboard(userId).enqueue(new Callback<ResponseDTO<User>>() {
            @Override
            public void onResponse(Call<ResponseDTO<User>> call, Response<ResponseDTO<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<User>> call, Throwable t) {
                // Xử lý khi có lỗi
            }
        });

    }

    private void fetchApplicationFormCount(int userId) {
        apiService.getApplicationFormCountByUser(userId).enqueue(new Callback<ResponseDTO<Integer>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Integer>> call, Response<ResponseDTO<Integer>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy số lượng từ response và cập nhật TextView
                    int count = response.body().getData();
                    numberviecLamDaUngTuyen.setText(String.valueOf(count));
                } else {
                    Toast.makeText(getContext(), "Không lấy được số lượng việc làm đã ứng tuyển.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<Integer>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Error fetching application form count", t);
            }
        });
    }
    private void fetchSavedJobsCount(int userId) {
        apiService.getSavedJobsCount(userId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    int savedJobsCount = response.body();
                    numberviecLamDaLuu.setText(String.valueOf(savedJobsCount));
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

}
