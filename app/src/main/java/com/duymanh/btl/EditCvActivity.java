package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditCvActivity extends AppCompatActivity {
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

    private ApiService apiService;


    private Profile profile,profileBack;
    private String mota,motaBack;
    private List<Educations> educations;
    private List<Experiences> experiences;
    private List<Skills> skills;
    private List<Project> projects;
    private List<Certificates> certificates;
    private List<Awards> awards;
    private List<Hobbies> hobbies;
    private List<Introducer> introducers;
    private Cv cv;

    private Button xemTruoc;

    private LinearLayout thongTinLienHe, moTaChung, hocVan, kinhNghiemLamViec, kyNang, duAn, chungChi, danhHieuVaGiaiThuong,soThich,nguoiGioiThieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cv);

        getSupportActionBar().setTitle("Chỉnh sửa CV");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);

        fetchCvData(Integer.parseInt(userId));

        xemTruoc = findViewById(R.id.xemTruoc);

        thongTinLienHe = findViewById(R.id.thongTinLienHe);
        thongTinLienHe.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvProfileActivity.class);
            if(profile!=null){
                intent.putExtra("profile",profile);
            }
            startActivityForResult(intent, REQUEST_PROFILE);
        });

        moTaChung = findViewById(R.id.moTaChung);
        moTaChung.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvDescriberActivity.class);
            if(mota!=null){
                intent.putExtra("mota",mota);
            }
            startActivityForResult(intent, REQUEST_DESCRIBER);
        });

        hocVan = findViewById(R.id.hocVan);
        hocVan.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvEducationActivity.class);
            if(educations!=null){
                intent.putExtra("educations",new ArrayList<>(educations));
            }
            startActivityForResult(intent, REQUEST_EDUCATION);
        });

        kinhNghiemLamViec = findViewById(R.id.kinhNghiemLamViec);
        kinhNghiemLamViec.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvExperienceActivity.class);
            if(experiences!=null){
                intent.putExtra("experiences",new ArrayList<>(experiences));
            }
            startActivityForResult(intent, REQUEST_EXPERIENCE);
        });

        kyNang = findViewById(R.id.kyNang);
        kyNang.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvSkillActivity.class);
            if(skills!=null){
                intent.putExtra("skills",new ArrayList<>(skills));
            }
            startActivityForResult(intent, REQUEST_SKILL);
        });

        duAn = findViewById(R.id.duAn);
        duAn.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvProjectActivity.class);
            if(projects!=null){
                intent.putExtra("projects",new ArrayList<>(projects));
            }
            startActivityForResult(intent, REQUEST_PROJECT);
        });

        chungChi = findViewById(R.id.chungChi);
        chungChi.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvCertificateActivity.class);
            if(certificates!=null){
                intent.putExtra("certificates",new ArrayList<>(certificates));
            }
            startActivityForResult(intent, REQUEST_CERTIFICATE);
        });

        danhHieuVaGiaiThuong = findViewById(R.id.danhHieuVaGiaiThuong);
        danhHieuVaGiaiThuong.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvAwardActivity.class);
            if(awards!=null){
                intent.putExtra("awards",new ArrayList<>(awards));
            }
            startActivityForResult(intent, REQUEST_AWARD);
        });

        soThich = findViewById(R.id.soThich);
        soThich.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvHobbyActivity.class);
            if(hobbies!=null){
                intent.putExtra("hobbies",new ArrayList<>(hobbies));
            }
            startActivityForResult(intent, REQUEST_HOBBY);
        });

        nguoiGioiThieu = findViewById(R.id.nguoiGioiThieu);
        nguoiGioiThieu.setOnClickListener(v -> {
            Intent intent = new Intent(EditCvActivity.this, CvIntroducerActivity.class);
            if(introducers!=null){
                intent.putExtra("introducers",new ArrayList<>(introducers));
            }
            startActivityForResult(intent, REQUEST_INTRODUCER);
        });

        xemTruoc.setOnClickListener(v -> {
            Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
            apiService = retrofit.create(ApiService.class);
            Cv cvupdate = new Cv();
            cvupdate.setId(cv.getId());
            if(profileBack!=null){
                cvupdate.setProfile(profileBack);
            }
            if(motaBack == null || motaBack.length()==0) {
                cvupdate.setDescription(cv.getDescription());
            }
            else{
                cvupdate.setDescription(motaBack);
            }
            apiService.updateCv(cvupdate).enqueue(new Callback<ResponseDTO<Cv>>() {
                @Override
                public void onResponse(Call<ResponseDTO<Cv>> call, Response<ResponseDTO<Cv>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(EditCvActivity.this, "sua Cv thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditCvActivity.this, CvActivity.class);
                        startActivity(intent);
                    } else {
                        try {
                            String errorBody = response.errorBody().string();  // lấy thông tin chi tiết lỗi
                            Log.e("API_ERROR", "Error: " + response.message() + ", Error Body: " + errorBody);
                        } catch (IOException e) {
                            Log.e("API_ERROR", "Error: " + response.message() + ", but failed to read error body");
                        }
                        Toast.makeText(EditCvActivity.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseDTO<Cv>> call, Throwable t) {
                    // Xử lý lỗi kết nối hoặc các lỗi khác
                    Log.e("API_FAILURE", "Error: " + t.getMessage());
                    Toast.makeText(EditCvActivity.this, "Error occurred while creating application", Toast.LENGTH_SHORT).show();
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

    private void fetchCvData(int userId) {
        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class);
        apiService.getCvByUserId(userId).enqueue(new Callback<ResponseDTO<Cv>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Cv>> call, Response<ResponseDTO<Cv>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cv = response.body().getData();

                    Log.d("TESTCV", cv.toString());

                    if(cv!=null){
                        if(cv.getProfile()!=null){
                            profile = cv.getProfile();
                        }
                        if(cv.getDescription()!=null){
                            mota = cv.getDescription();
                        }
                        if(cv.getEducations()!=null){
                            educations = cv.getEducations();
                        }
                        if(cv.getExperiences()!=null){
                            experiences = cv.getExperiences();
                        }
                        if(cv.getSkills()!=null){
//                            Log.d("CVSKILL", "onResponse: "+cv.getSkills().get(0).getName());
                            skills = cv.getSkills();
                        }
                        if(cv.getProjects()!=null){
                            projects = cv.getProjects();
                        }
                        if(cv.getCertificates()!=null){
                            certificates = cv.getCertificates();
                        }
                        if(cv.getAwards()!=null){
                            awards = cv.getAwards();
                        }
                        if(cv.getHobbies()!=null){
                            hobbies = cv.getHobbies();
                        }
                        if(cv.getIntroducers()!=null){
                            introducers = cv.getIntroducers();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            if(requestCode == REQUEST_PROFILE){
                profileBack = (Profile) data.getSerializableExtra("profile");
                if(profile!=null){
                    profileBack.setId(profile.getId());
                }
            }
            else if(requestCode == REQUEST_DESCRIBER){
                motaBack = data.getStringExtra("mota");
            }
//            else if(requestCode == REQUEST_EDUCATION){
//                educations = (List<Educations>) data.getSerializableExtra("education");
//                if(educations!=null){
//                    cv.setEducations(educations);
//                    Log.d("PROFILEDM2", cv.getEducations().get(0).getSchool());
//                }
//            }
//            else if(requestCode == REQUEST_EXPERIENCE){
//                experiences = (List<Experiences>) data.getSerializableExtra("experience");
//                if(experiences!=null){
//                    cv.setExperiences(experiences);
//                    Log.d("PROFILEDM2", cv.getExperiences().get(0).getCompany());
//                }
//            }
//            else if(requestCode == REQUEST_SKILL){
//                skills = (List<Skills>) data.getSerializableExtra("skill");
//                if(skills!=null){
//                    cv.setSkills(skills);
//                    Log.d("PROFILEDM2", cv.getSkills().get(0).getName());
//                }
//            }
//            else if(requestCode == REQUEST_PROJECT){
//                projects = (List<Project>) data.getSerializableExtra("project");
//                if(projects!=null){
//                    cv.setProjects(projects);
//                    Log.d("PROFILEDM2", cv.getProjects().get(0).getName());
//                }
//            }
//            else if(requestCode == REQUEST_CERTIFICATE){
//                certificates = (List<Certificates>) data.getSerializableExtra("certificate");
//                if(certificates!=null){
//                    cv.setCertificates(certificates);
//                    Log.d("PROFILEDM2", cv.getCertificates().get(0).getName());
//                }
//            }
//            else if(requestCode == REQUEST_AWARD){
//                awards = (List<Awards>) data.getSerializableExtra("award");
//                if(awards!=null){
//                    cv.setAwards(awards);
//                    Log.d("PROFILEDM2", cv.getAwards().get(0).getName());
//                }
//            }
//            else if(requestCode == REQUEST_HOBBY){
//                hobbies = (List<Hobbies>) data.getSerializableExtra("hobby");
//                if(hobbies!=null){
//                    cv.setHobbies(hobbies);
//                    Log.d("PROFILEDM2", cv.getHobbies().get(0).getName());
//                }
//            }
//            else if(requestCode == REQUEST_INTRODUCER){
//                introducers = (List<Introducer>) data.getSerializableExtra("introducer");
//                if(introducers!=null){
//                    cv.setIntroducers(introducers);
//                    Log.d("PROFILEDM2", cv.getIntroducers().get(0).getInformation());
//                }
//            }
        }

    }

}