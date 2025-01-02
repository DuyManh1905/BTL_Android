package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.duymanh.btl.api.ApiService;
import com.duymanh.btl.api.ResponseDTO;
import com.duymanh.btl.api.RetrofitClient;
import com.duymanh.btl.model.Awards;
import com.duymanh.btl.model.Certificates;
import com.duymanh.btl.model.Cv;
import com.duymanh.btl.model.Educations;
import com.duymanh.btl.model.Experiences;
import com.duymanh.btl.model.Hobbies;
import com.duymanh.btl.model.Introducer;
import com.duymanh.btl.model.Project;
import com.duymanh.btl.model.Skills;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CvActivity extends AppCompatActivity {

    private TextView nameUser, majorUser,describeUser, diaChiUser, fbUser, sdtUser, emailUser, hocVanUser, skillUser, experienceUser;
    private TextView soLuongNguoiThamGiaDuAn,congNgheSuDungDuAn,thoiGianKetThucDuAn, khachHangDuAn, viTriDuAn,tenDuAn, thoiGanBatDauDuAn;
    private TextView chungChiUser, giaiThuongUser, soThichUser, nguoiGTUser;
    private ApiService apiService;

    private FloatingActionButton fab;

    private Cv cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);

        getSupportActionBar().setTitle("Cv của bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8081");
        apiService = retrofit.create(ApiService.class); // Khởi tạo apiService
        SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
        String userId = prefs.getString("user_id", null);
        if(userId!=null){
            fetchCvData(Integer.parseInt(userId));
        }
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(CvActivity.this, EditCvActivity.class);
            startActivity(intent);
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
        diaChiUser = findViewById(R.id.diaChiUser);
        fbUser = findViewById(R.id.fbUser);
        sdtUser = findViewById(R.id.sdtUser);
        emailUser = findViewById(R.id.emailUser);
        nameUser = findViewById(R.id.nameUser);
        majorUser = findViewById(R.id.majorUser);
        describeUser = findViewById(R.id.describeUser);
        hocVanUser = findViewById(R.id.hocVanUser);
        skillUser = findViewById(R.id.skillUser);
        experienceUser = findViewById(R.id.experienceUser);
        soLuongNguoiThamGiaDuAn = findViewById(R.id.soLuongNguoiThamGiaDuAn);
        congNgheSuDungDuAn = findViewById(R.id.congNgheSuDungDuAn);
        thoiGianKetThucDuAn = findViewById(R.id.thoiGianKetThucDuAn);
        khachHangDuAn = findViewById(R.id.khachHangDuAn);
        viTriDuAn = findViewById(R.id.viTriDuAn);
        tenDuAn = findViewById(R.id.tenDuAn);
        thoiGanBatDauDuAn = findViewById(R.id.thoiGanBatDauDuAn);
        chungChiUser = findViewById(R.id.chungChiUser);
        giaiThuongUser = findViewById(R.id.giaiThuongUser);
        soThichUser = findViewById(R.id.soThichUser);
        nguoiGTUser = findViewById(R.id.nguoiGTUser);
        fab = findViewById(R.id.fab);
    }

    private void fetchCvData(int userId) {
        apiService.getCvByUserId(userId).enqueue(new Callback<ResponseDTO<Cv>>() {
            @Override
            public void onResponse(Call<ResponseDTO<Cv>> call, Response<ResponseDTO<Cv>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cv = response.body().getData();
                    if(cv!=null){
                        majorUser.setText(cv.getDesiredJob());
                        describeUser.setText(cv.getDescription());
                        if(cv.getProfile()!=null){
                            nameUser.setText(cv.getProfile().getName());
                            emailUser.setText(cv.getProfile().getEmail());
                            sdtUser.setText(cv.getProfile().getPhoneNumber());
                            diaChiUser.setText(cv.getProfile().getAddress());
                            fbUser.setText(cv.getProfile().getWebsite());
                        }
                        if(cv.getEducations()!=null && cv.getEducations().size()>0){
                            String hocvan = "";
                            List<Educations> educations = cv.getEducations();
                            for(int i=0;i<educations.size();i++){
                                hocvan+=educations.get(i).getSchool()+"\n";
                                hocvan+=educations.get(i).getMajor()+"\n";
                                hocvan+=educations.get(i).getStartAt()+" - "+educations.get(i).getEndAt()+"\n";
                                hocvan+=educations.get(i).getDescription()+"\n\n";
                            }
                            hocVanUser.setText(hocvan);
                        }
                        if(cv.getSkills()!=null && cv.getSkills().size()>0){
                            String skill = "";
                            List<Skills> skills = cv.getSkills();
                            for(int i=0;i<skills.size();i++){
                                skill+=skills.get(i).getName()+"\n";
                                skill+=skills.get(i).getDescription()+"\n\n";
                            }
                            skillUser.setText(skill);
                        }
                        if(cv.getExperiences()!=null && cv.getExperiences().size()>0){
                            String experiences = "";
                            List<Experiences> list = cv.getExperiences();
                            for(int i=0;i<list.size();i++){
                                experiences+= list.get(i).getStartAt()+" - "+list.get(i).getEndAt()+"\n";
                                experiences+="CÔNG TY: "+list.get(i).getCompany()+"\n";
                                experiences+=list.get(i).getPosition()+"\n";
                                experiences+=list.get(i).getDescription()+"\n\n";
                            }
                            experienceUser.setText(experiences);
                        }
                        if(cv.getProjects()!=null && cv.getProjects().size()>0){
                            Project p = cv.getProjects().get(0);
                            soLuongNguoiThamGiaDuAn.setText(p.getTeamSize());
                            congNgheSuDungDuAn.setText(p.getTechnologies());
                            thoiGianKetThucDuAn.setText(p.getEndAt());
                            khachHangDuAn.setText(p.getCustomer());
                            viTriDuAn.setText(p.getPosition());
                            tenDuAn.setText(p.getName());
                            thoiGanBatDauDuAn.setText(p.getStartAt());
                        }
                        if(cv.getCertificates()!=null && cv.getCertificates().size()>0){
                            String certi = "";
                            List<Certificates> list = cv.getCertificates();
                            for(int i=0;i<list.size();i++){
                                certi+="Thời gian: "+ list.get(i).getTime()+"\n";
                                certi+="Tên bằng: "+list.get(i).getName()+"\n\n";
                            }
                            chungChiUser.setText(certi);
                        }
                        if(cv.getAwards()!=null && cv.getAwards().size()>0){
                            String award = "";
                            List<Awards> list = cv.getAwards();
                            for(int i=0;i<list.size();i++){
                                award+="Thời gian: "+ list.get(i).getTime()+"\n";
                                award+="Danh hiệu(Giải thưởng): "+list.get(i).getName()+"\n\n";
                            }
                            giaiThuongUser.setText(award);
                        }
                        if(cv.getHobbies()!=null && cv.getHobbies().size()>0){
                            String hobbie = "";
                            List<Hobbies> list = cv.getHobbies();
                            for(int i=0;i<list.size();i++){
                                hobbie+="- "+list.get(i).getName()+"\n\n";
                            }
                            soThichUser.setText(hobbie);
                        }
                        if(cv.getIntroducers()!=null && cv.getIntroducers().size()>0){
                            String introducer = "";
                            List<Introducer> list = cv.getIntroducers();
                            for(int i=0;i<list.size();i++){
                                introducer+="- "+list.get(i).getInformation()+"\n\n";
                            }
                            if(!introducer.isEmpty()){
                                nguoiGTUser.setText(introducer);
                            }
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CvActivity.this, MainActivity.class);
        startActivity(intent);
    }
}