package com.duymanh.btl.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.auth0.android.jwt.JWT;
import com.bumptech.glide.Glide;
import com.duymanh.btl.ChangePasswordActivity;
import com.duymanh.btl.ConfirmApplyActivity;
import com.duymanh.btl.JobSavedActivity;
import com.duymanh.btl.ListApplicationFormActivity;
import com.duymanh.btl.ListFollowCompanyActivity;
import com.duymanh.btl.LoginActivity;
import com.duymanh.btl.R;
import com.duymanh.btl.SuccessActivity;
import com.duymanh.btl.TestImageActivity;
import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.dto.JobSuggestionDTO;
import com.duymanh.btl.model.Cv;
import com.duymanh.btl.model.User;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FragmentUser extends Fragment {

    private LinearLayout btnLogOut;
    private ApiService apiService;

    private User user;
    private Cv cv;

    private LinearLayout changePassword;

    private LinearLayout viecLamDaUngTuyen, viecLamDaLuu, viecLamPhuHop, congTyDangTheoDoi;
    private TextView numberviecLamDaUngTuyen, numberviecLamDaLuu, numberviecLamPhuHop, numberCongTyDangTheoDoi, nameUser, email;

    private boolean isEditingKinhNghiem = false;
    private TextView tvKinhNghiem, btnSuaKinhNghiem;

    private CircleImageView avt_user;
    private Spinner spinnerKinhNghiem;

    private boolean isEditingCongViecMongMuon = false;
    private TextView congViecMongMuon, btnSuaCongViecMongMuon;
    private Spinner spinnerCongViecMongMuon;

    private boolean isEditingDiaDiemMongMuon = false;
    private TextView diaDiemLamViecMongMuon, btnSuaDiaDiemMongMuon;
    private Spinner spinnerDiaDiemMongMuon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);



        initView(view);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class); // Khởi tạo apiService

        SharedPreferences prefs = getActivity().getSharedPreferences("app_prefs", getContext().MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        if (userId != null) {
            int parsedUserId = Integer.parseInt(userId);
            fetchUserData(Integer.parseInt(userId));
            fetchApplicationFormCount(parsedUserId); // Gọi phương thức fetchApplicationFormCount
            fetchSavedJobsCount(parsedUserId);
            fetchCvData(parsedUserId);
            fetchFollowCompanyCount(parsedUserId);
        }

        //sua kinh nghiem

        spinnerKinhNghiem.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.kinh_nghiem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKinhNghiem.setAdapter(adapter);
        btnSuaKinhNghiem.setOnClickListener(v -> {
            if (isEditingKinhNghiem) {
                // Người dùng nhấn nút "Lưu"
                String selectedValue = spinnerKinhNghiem.getSelectedItem().toString();
                tvKinhNghiem.setText(selectedValue);
                tvKinhNghiem.setTextColor(getResources().getColor(R.color.green));

                // Ẩn Spinner và đổi nút thành "Sửa"
                spinnerKinhNghiem.setVisibility(View.GONE);
                btnSuaKinhNghiem.setText("Sửa");

                // Gọi API để cập nhật dữ liệu (thay thế bằng logic API của bạn)
            } else {
                // Người dùng nhấn nút "Sửa"
                spinnerKinhNghiem.setVisibility(View.VISIBLE);
                tvKinhNghiem.setText("");
                btnSuaKinhNghiem.setText("Lưu");
            }

            isEditingKinhNghiem = !isEditingKinhNghiem;
        });


        //sua cong viec mong muon
        spinnerCongViecMongMuon.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.cong_viec_mong_muon, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCongViecMongMuon.setAdapter(adapter2);
        btnSuaCongViecMongMuon.setOnClickListener(v -> {
            if (isEditingCongViecMongMuon) {
                if(cv==null){
                    Toast.makeText(getContext(),"Bạn chưa có CV, hãy tạo trước!",Toast.LENGTH_SHORT).show();
                    congViecMongMuon.setText("Chưa cập nhật");
                    spinnerCongViecMongMuon.setVisibility(View.GONE);
                    btnSuaCongViecMongMuon.setText("Sửa");
                }
                else{
                    // Người dùng nhấn nút "Lưu"
                    String selectedValue = spinnerCongViecMongMuon.getSelectedItem().toString();
                    congViecMongMuon.setText(selectedValue);
                    congViecMongMuon.setTextColor(getResources().getColor(R.color.green));

                    // Ẩn Spinner và đổi nút thành "Sửa"
                    spinnerCongViecMongMuon.setVisibility(View.GONE);
                    btnSuaCongViecMongMuon.setText("Sửa");

                    // Gọi API để cập nhật dữ liệu (thay thế bằng logic API của bạn)
                    updateCongViecToServer(selectedValue);
                }
            } else {
                // Người dùng nhấn nút "Sửa"
                spinnerCongViecMongMuon.setVisibility(View.VISIBLE);
                congViecMongMuon.setText("");
                btnSuaCongViecMongMuon.setText("Lưu");
            }

            isEditingCongViecMongMuon = !isEditingCongViecMongMuon;
        });

        //sua dia diem mong muon
        spinnerDiaDiemMongMuon.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.dia_diem_lam_viec, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiaDiemMongMuon.setAdapter(adapter3);
        btnSuaDiaDiemMongMuon.setOnClickListener(v -> {
            if (isEditingDiaDiemMongMuon) {
                if(cv==null){
                    Toast.makeText(getContext(),"Bạn chưa có CV, hãy tạo trước!",Toast.LENGTH_SHORT).show();
                    diaDiemLamViecMongMuon.setText("Chưa cập nhật");
                    spinnerDiaDiemMongMuon.setVisibility(View.GONE);
                    btnSuaDiaDiemMongMuon.setText("Sửa");
                }
                else{
                    // Người dùng nhấn nút "Lưu"
                    String selectedValue = spinnerDiaDiemMongMuon.getSelectedItem().toString();
                    diaDiemLamViecMongMuon.setText(selectedValue);
                    diaDiemLamViecMongMuon.setTextColor(getResources().getColor(R.color.green));

                    // Ẩn Spinner và đổi nút thành "Sửa"
                    spinnerDiaDiemMongMuon.setVisibility(View.GONE);
                    btnSuaDiaDiemMongMuon.setText("Sửa");

                    // Gọi API để cập nhật dữ liệu (thay thế bằng logic API của bạn)
                    updateDiaDiemToServer(selectedValue);
                }
            } else {
                // Người dùng nhấn nút "Sửa"
                spinnerDiaDiemMongMuon.setVisibility(View.VISIBLE);
                diaDiemLamViecMongMuon.setText("");
                btnSuaDiaDiemMongMuon.setText("Lưu");
            }

            isEditingDiaDiemMongMuon = !isEditingDiaDiemMongMuon;
        });






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
//            Toast.makeText(getContext(),"numberviecLamPhuHop",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), TestImageActivity.class);
            startActivity(intent);
        });


        congTyDangTheoDoi.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ListFollowCompanyActivity.class);
            startActivity(intent);
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

        // Khi nhấn nút Update
        return view;
    }

    private void fetchFollowCompanyCount(int userId) {
        apiService.getNumberFollowCompanyByUser(userId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy số lượng từ response và cập nhật TextView
                    int count = response.body();
                    numberCongTyDangTheoDoi.setText(String.valueOf(count));
                } else {
                    Toast.makeText(getContext(), "Không lấy được số lượng việc làm đã ứng tuyển.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi khi gọi API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API_ERROR", "Error fetching application form count", t);
            }
        });
    }

    private void initView(View view) {
        btnLogOut = view.findViewById(R.id.btnLogout);
        changePassword = view.findViewById(R.id.changePassword);
        tvKinhNghiem = view.findViewById(R.id.KinhNghiemUser);
        btnSuaKinhNghiem = view.findViewById(R.id.btnSuaKinhNghiem);
        spinnerKinhNghiem = view.findViewById(R.id.spinnerKinhNghiem);
        diaDiemLamViecMongMuon = view.findViewById(R.id.diaDiemLamViecMongMuon);
        btnSuaDiaDiemMongMuon = view.findViewById(R.id.btnSuaDiaDiemMongMuon);
        spinnerDiaDiemMongMuon = view.findViewById(R.id.spinnerDiaDiemMongMuon);
        congViecMongMuon = view.findViewById(R.id.congViecMongMuon);
        btnSuaCongViecMongMuon = view.findViewById(R.id.btnSuaCongViecMongMuon);
        spinnerCongViecMongMuon = view.findViewById(R.id.spinnerCongViecMongMuon);
        viecLamDaUngTuyen = view.findViewById(R.id.viecLamDaUngTuyen);
        viecLamDaLuu = view.findViewById(R.id.viecLamDaLuu);
        viecLamPhuHop = view.findViewById(R.id.viecLamPhuHop);
        congTyDangTheoDoi = view.findViewById(R.id.congTyDangTheoDoi);

        numberviecLamDaUngTuyen = view.findViewById(R.id.numberViecLamDaUngTuyen);
        numberviecLamDaLuu = view.findViewById(R.id.numberViecLamDaLuu);
        numberviecLamPhuHop = view.findViewById(R.id.numberViecLamPhuHop);
        numberCongTyDangTheoDoi = view.findViewById(R.id.numberCongTyDangTheoDoi);

        nameUser = view.findViewById(R.id.nameUser);
        email = view.findViewById(R.id.email);

        avt_user = view.findViewById(R.id.avt_user);



    }

    private void fetchUserData(int userId) {
        apiService.getUserDashboard(userId).enqueue(new Callback<ResponseDTO<User>>() {
            @Override
            public void onResponse(Call<ResponseDTO<User>> call, Response<ResponseDTO<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user = response.body().getData();
                    nameUser.setText(user.getName());
                    email.setText(user.getEmail());
                    email.setTextColor(getResources().getColor(R.color.green));

                    String imageUrl = user.getAvataURL();
                    // Hiển thị ảnh với Glide
                    Glide.with(getActivity())
                            .load(imageUrl)
                            .placeholder(R.drawable.avt_user)
                            .error(R.drawable.avt_user)
                            .into(avt_user);
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO<User>> call, Throwable t) {
                // Xử lý khi có lỗi
            }
        });

    }

    private void fetchCvData(int userId) {
        apiService.getCvByUserId(userId).enqueue(new Callback<ResponseDTO<Cv>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Cv>> call, Response<ResponseDTO<Cv>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cv = response.body().getData();
                    if(cv!=null){
                        if(cv.getDesiredWorkLocation()!=null){
                            diaDiemLamViecMongMuon.setTextColor(getResources().getColor(R.color.green));
                            diaDiemLamViecMongMuon.setText(cv.getDesiredWorkLocation());
                        }
                        if(cv.getDesiredJob()!=null){
                            congViecMongMuon.setTextColor(getResources().getColor(R.color.green));
                            congViecMongMuon.setText(cv.getDesiredJob());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO<Cv>> call, Throwable t) {
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
    private void updateDiaDiemToServer(String value) {

        JobSuggestionDTO dto = new JobSuggestionDTO();
        dto.setId(cv.getId());
        dto.setDesiredJob(cv.getDesiredJob());
        dto.setDesiredWorkLocation(value);

        Log.d("JobSuggest", "cvId: "+cv.getId()+" cv mong muon: "+cv.getDesiredJob());
        apiService.updateSuggestionCv(dto).enqueue(new Callback<ResponseDTO<JobSuggestionDTO>>() {
            @Override
            public void onResponse(Call<ResponseDTO<JobSuggestionDTO>> call, Response<ResponseDTO<JobSuggestionDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(),"Thay đổi thông tin thành công",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();  // lấy thông tin chi tiết lỗi
                        Log.e("API_ERROR", "Error: " + response.message() + ", Error Body: " + errorBody);
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Error: " + response.message() + ", but failed to read error body");
                    }
                    Toast.makeText(getContext(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO<JobSuggestionDTO>> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc các lỗi khác
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error occurred while creating application", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateCongViecToServer(String value) {

        JobSuggestionDTO dto = new JobSuggestionDTO();
        dto.setId(cv.getId());
        dto.setDesiredJob(value);
        dto.setDesiredWorkLocation(cv.getDesiredWorkLocation());

        Log.d("JobSuggest", "cvId: "+cv.getId()+" cv mong muon: "+cv.getDesiredJob());
        apiService.updateSuggestionCv(dto).enqueue(new Callback<ResponseDTO<JobSuggestionDTO>>() {
            @Override
            public void onResponse(Call<ResponseDTO<JobSuggestionDTO>> call, Response<ResponseDTO<JobSuggestionDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(),"Thay đổi thông tin thành công",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        String errorBody = response.errorBody().string();  // lấy thông tin chi tiết lỗi
                        Log.e("API_ERROR", "Error: " + response.message() + ", Error Body: " + errorBody);
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Error: " + response.message() + ", but failed to read error body");
                    }
                    Toast.makeText(getContext(), "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseDTO<JobSuggestionDTO>> call, Throwable t) {
                // Xử lý lỗi kết nối hoặc các lỗi khác
                Log.e("API_FAILURE", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Error occurred while creating application", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
