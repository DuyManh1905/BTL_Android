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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.auth0.android.jwt.JWT;
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

    private TextView txtName, txtUsername, txtEmail, txtBirthdate, txtRole;
    private Button btnUpdate,btnLogOut;
    private ApiService apiService;

    private boolean isEdit = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);

        txtName = view.findViewById(R.id.txtName);
        txtUsername = view.findViewById(R.id.txtUsername);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtBirthdate = view.findViewById(R.id.txtBirthdate);
        txtRole = view.findViewById(R.id.txtRole);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        btnLogOut = view.findViewById(R.id.btnLogOut);


        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class); // Khởi tạo apiService

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

        btnUpdate.setText("Update");
        txtName.setFocusable(false);
        txtUsername.setFocusable(false);
        txtEmail.setFocusable(false);
        txtBirthdate.setFocusable(false);
        txtRole.setFocusable(false);

        // Khi nhấn nút Update

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit == false){
                    isEdit = true;
                    btnUpdate.setText("Save");

                    txtName.setFocusableInTouchMode(true);
                    txtUsername.setFocusableInTouchMode(true);
                    txtEmail.setFocusableInTouchMode(true);
                    txtBirthdate.setFocusableInTouchMode(true);
                    txtRole.setFocusableInTouchMode(true);

                    txtName.setFocusable(true);
                    txtUsername.setFocusable(true);
                    txtEmail.setFocusable(true);
                    txtBirthdate.setFocusable(true);
                    txtRole.setFocusable(true);

                }

                else{
                    String name = txtName.getText().toString();
                    String username = txtUsername.getText().toString();
                    String email = txtEmail.getText().toString();
                    String birthdate = txtBirthdate.getText().toString();
                    String role = txtRole.getText().toString();

                    //saveDB
                    btnUpdate.setText("Update");
                    txtName.setFocusable(false);
                    txtUsername.setFocusable(false);
                    txtEmail.setFocusable(false);
                    txtBirthdate.setFocusable(false);
                    txtRole.setFocusable(false);
                    isEdit = false;
                    Toast.makeText(getActivity(), "Da luu thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    private void fetchUserData(int userId) {
        apiService.getUserDashboard(userId).enqueue(new Callback<ResponseDTO<User>>() {
            @Override
            public void onResponse(Call<ResponseDTO<User>> call, Response<ResponseDTO<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body().getData();
                    txtName.setText(user.getName());
                    txtUsername.setText(user.getUsername());
                    txtEmail.setText(user.getEmail());
                    txtBirthdate.setText(user.getBirthdate());
                    txtRole.setText(user.getRoles().get(0).getName());  // Nếu chỉ có 1 role
                }
            }

            @Override
            public void onFailure(Call<ResponseDTO<User>> call, Throwable t) {
                // Xử lý khi có lỗi
            }
        });

    }
}
