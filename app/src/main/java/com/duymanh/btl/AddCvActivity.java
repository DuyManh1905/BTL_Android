package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.cv_activity.CvAwardActivity;
import com.duymanh.btl.cv_activity.CvCertificateActivity;
import com.duymanh.btl.cv_activity.CvDescriberActivity;
import com.duymanh.btl.cv_activity.CvEducationActivity;
import com.duymanh.btl.cv_activity.CvExperienceActivity;
import com.duymanh.btl.cv_activity.CvHobbyActivity;
import com.duymanh.btl.cv_activity.CvIntroducerActivity;
import com.duymanh.btl.cv_activity.CvProfileActivity;
import com.duymanh.btl.cv_activity.CvProjectActivity;
import com.duymanh.btl.cv_activity.CvSkillActivity;
import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.model.Awards;
import com.duymanh.btl.model.Certificates;
import com.duymanh.btl.model.Cv;
import com.duymanh.btl.model.Educations;
import com.duymanh.btl.model.Experiences;
import com.duymanh.btl.model.Hobbies;
import com.duymanh.btl.model.Introducer;
import com.duymanh.btl.model.Profile;
import com.duymanh.btl.model.Project;
import com.duymanh.btl.model.Skills;
import com.duymanh.btl.model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddCvActivity extends AppCompatActivity {
    private static final int REQUEST_PROFILE = 1;
    private static final int REQUEST_DESCRIBER = 2;
    private static final int REQUEST_EDUCATION = 3;
    private static final int REQUEST_EXPERIENCE = 4;
    private static final int REQUEST_SKILL = 5;
    private static final int REQUEST_PROJECT = 6;
    private static final int REQUEST_CERTIFICATE = 7;
    private static final int REQUEST_AWARD = 8;
    private static final int REQUEST_HOBBY = 9;
    private static final int REQUEST_INTRODUCER = 10;

    private LinearLayout thongTinLienHe, moTaChung, hocVan, kinhNghiemLamViec, kyNang, duAn, chungChi, danhHieuVaGiaiThuong,soThich,nguoiGioiThieu;
    private Button show,luuCV;

    private ApiService apiService;


    private Profile profile;
    private String mota;
    private List<Educations> educations;
    private List<Experiences> experiences;
    private List<Skills> skills;
    private List<Project> projects;
    private List<Certificates> certificates;
    private List<Awards> awards;
    private List<Hobbies> hobbies;
    private List<Introducer> introducers;
    private Cv cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cv);


        getSupportActionBar().setTitle("Tạo CV");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        show = findViewById(R.id.show);
        luuCV = findViewById(R.id.luuCV);

        cv = new Cv();

        thongTinLienHe = findViewById(R.id.thongTinLienHe);
        thongTinLienHe.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvProfileActivity.class);
            startActivityForResult(intent, REQUEST_PROFILE);
        });

        moTaChung = findViewById(R.id.moTaChung);
        moTaChung.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvDescriberActivity.class);
            startActivityForResult(intent, REQUEST_DESCRIBER);
        });

        hocVan = findViewById(R.id.hocVan);
        hocVan.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvEducationActivity.class);
            startActivityForResult(intent, REQUEST_EDUCATION);
        });

        kinhNghiemLamViec = findViewById(R.id.kinhNghiemLamViec);
        kinhNghiemLamViec.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvExperienceActivity.class);
            startActivityForResult(intent, REQUEST_EXPERIENCE);
        });

        kyNang = findViewById(R.id.kyNang);
        kyNang.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvSkillActivity.class);
            startActivityForResult(intent, REQUEST_SKILL);
        });

        duAn = findViewById(R.id.duAn);
        duAn.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvProjectActivity.class);
            startActivityForResult(intent, REQUEST_PROJECT);
        });

        chungChi = findViewById(R.id.chungChi);
        chungChi.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvCertificateActivity.class);
            startActivityForResult(intent, REQUEST_CERTIFICATE);
        });

        danhHieuVaGiaiThuong = findViewById(R.id.danhHieuVaGiaiThuong);
        danhHieuVaGiaiThuong.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvAwardActivity.class);
            startActivityForResult(intent, REQUEST_AWARD);
        });

        soThich = findViewById(R.id.soThich);
        soThich.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvHobbyActivity.class);
            startActivityForResult(intent, REQUEST_HOBBY);
        });

        nguoiGioiThieu = findViewById(R.id.nguoiGioiThieu);
        nguoiGioiThieu.setOnClickListener(v -> {
            Intent intent = new Intent(AddCvActivity.this, CvIntroducerActivity.class);
            startActivityForResult(intent, REQUEST_INTRODUCER);
        });

        show.setOnClickListener(v -> {
            if (cv.getProfile() != null) {
                Log.d("PROFILEDM", cv.getProfile().getName());
                if(cv.getDescription()!=null){
                    Log.d("PROFILEDM", cv.getDescription());
                }
            } else {
                Log.d("PROFILEDM", "Profile is not set yet.");
            }

            if(cv.getEducations()!=null){
                Log.d("PROFILEDM", cv.getEducations().get(0).getSchool());
            }
            if(cv.getExperiences()!=null){
                Log.d("PROFILEDM", cv.getExperiences().get(0).getCompany());
            }
            if(cv.getSkills()!=null){
                Log.d("PROFILEDM", cv.getSkills().get(0).getName());
            }
            if(cv.getProjects()!=null){
                Log.d("PROFILEDM", cv.getProjects().get(0).getName());
            }
            if(cv.getCertificates()!=null){
                Log.d("PROFILEDM", cv.getCertificates().get(0).getName());
            }
            if(cv.getAwards()!=null){
                Log.d("PROFILEDM", cv.getAwards().get(0).getName());
            }
            if(cv.getHobbies()!=null){
                Log.d("PROFILEDM", cv.getHobbies().get(0).getName());
            }
            if(cv.getIntroducers()!=null){
                Log.d("PROFILEDM", cv.getIntroducers().get(0).getInformation());
            }
            Log.d("PROFILEDM", cv.toString());
            if(cv.getEducations()!=null){
                Log.d("PROFILEDM", cv.getEducations().toString());
            }
        });

        luuCV.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            String userId = prefs.getString("user_id", null);
            User user = new User();
            user.setId(Integer.parseInt(userId));

            cv.setUser(user);
            cv.setIsPublic(1);
            Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
            apiService = retrofit.create(ApiService.class);
            apiService.createCv(cv).enqueue(new Callback<ResponseDTO<Cv>>() {
                @Override
                public void onResponse(Call<ResponseDTO<Cv>> call, Response<ResponseDTO<Cv>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(AddCvActivity.this, "Tạo cv thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddCvActivity.this, CvActivity.class);
                        startActivity(intent);
                    } else {
                        try {
                            String errorBody = response.errorBody().string();  // lấy thông tin chi tiết lỗi
                            Log.e("API_ERROR", "Error: " + response.message() + ", Error Body: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API_ERROR", "Error: " + response.message() + ", but failed to read error body");
                        }
                        Toast.makeText(AddCvActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseDTO<Cv>> call, Throwable t) {
                    // Xử lý lỗi kết nối hoặc các lỗi khác
                    Log.e("API_FAILURE", "Error: " + t.getMessage());
                    Toast.makeText(AddCvActivity.this, "Error occurred while creating application", Toast.LENGTH_SHORT).show();
                }
            });
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if(requestCode == REQUEST_PROFILE){
                profile = (Profile) data.getSerializableExtra("profile");
                if(profile!=null){
                    cv.setProfile(profile);
                    Log.d("PROFILEDM2", cv.getProfile().getName());
                }
            }
            else if(requestCode == REQUEST_DESCRIBER){
                mota = data.getStringExtra("mota");
                if(mota!=null){
                    cv.setDescription(mota);
                    Log.d("PROFILEDM2", cv.getProfile().getName());
                }
            }
            else if(requestCode == REQUEST_EDUCATION){
                educations = (List<Educations>) data.getSerializableExtra("education");
                if(educations!=null){
                    cv.setEducations(educations);
                    Log.d("PROFILEDM2", cv.getEducations().get(0).getSchool());
                }
            }
            else if(requestCode == REQUEST_EXPERIENCE){
                experiences = (List<Experiences>) data.getSerializableExtra("experience");
                if(experiences!=null){
                    cv.setExperiences(experiences);
                    Log.d("PROFILEDM2", cv.getExperiences().get(0).getCompany());
                }
            }
            else if(requestCode == REQUEST_SKILL){
                skills = (List<Skills>) data.getSerializableExtra("skill");
                if(skills!=null){
                    cv.setSkills(skills);
                    Log.d("PROFILEDM2", cv.getSkills().get(0).getName());
                }
            }
            else if(requestCode == REQUEST_PROJECT){
                projects = (List<Project>) data.getSerializableExtra("project");
                if(projects!=null){
                    cv.setProjects(projects);
                    Log.d("PROFILEDM2", cv.getProjects().get(0).getName());
                }
            }
            else if(requestCode == REQUEST_CERTIFICATE){
                certificates = (List<Certificates>) data.getSerializableExtra("certificate");
                if(certificates!=null){
                    cv.setCertificates(certificates);
                    Log.d("PROFILEDM2", cv.getCertificates().get(0).getName());
                }
            }
            else if(requestCode == REQUEST_AWARD){
                awards = (List<Awards>) data.getSerializableExtra("award");
                if(awards!=null){
                    cv.setAwards(awards);
                    Log.d("PROFILEDM2", cv.getAwards().get(0).getName());
                }
            }
            else if(requestCode == REQUEST_HOBBY){
                hobbies = (List<Hobbies>) data.getSerializableExtra("hobby");
                if(hobbies!=null){
                    cv.setHobbies(hobbies);
                    Log.d("PROFILEDM2", cv.getHobbies().get(0).getName());
                }
            }
            else if(requestCode == REQUEST_INTRODUCER){
                introducers = (List<Introducer>) data.getSerializableExtra("introducer");
                if(introducers!=null){
                    cv.setIntroducers(introducers);
                    Log.d("PROFILEDM2", cv.getIntroducers().get(0).getInformation());
                }
            }
        }

    }

}