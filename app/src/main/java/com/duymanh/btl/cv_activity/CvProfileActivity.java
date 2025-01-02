package com.duymanh.btl.cv_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Profile;

public class CvProfileActivity extends AppCompatActivity {

    private EditText etFullName, etPhone, etEmail, etAddress, etGender, etDateOfBirth,etWebsite;
    private Button btnSubmit;

    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_profile);

        getSupportActionBar().setTitle("Hồ sơ cá nhân");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        profile = (Profile)getIntent().getSerializableExtra("profile");


        initView();

        if(profile!=null){
            etWebsite.setText(profile.getWebsite());
            etFullName.setText(profile.getName());
            etPhone.setText(profile.getPhoneNumber());
            etEmail.setText(profile.getEmail());
            etGender.setText(profile.getGender());
            etDateOfBirth.setText(profile.getDateBirth());
            etAddress.setText(profile.getAddress());
        }

        btnSubmit.setOnClickListener(v -> {
            String web = etWebsite.getText().toString();
            String name = etFullName.getText().toString();
            String sdt = etPhone.getText().toString();
            String mail = etEmail.getText().toString();
            String gt = etGender.getText().toString();
            String ngaysinh = etDateOfBirth.getText().toString();
            String diaChi = etAddress.getText().toString();
            Profile profile = new Profile(name,gt,ngaysinh,sdt,diaChi,mail,web,"");

            Intent resultIntent = new Intent();
            resultIntent.putExtra("profile",profile);
            setResult(RESULT_OK, resultIntent);
            finish();
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
        etWebsite = findViewById(R.id.etWebsite);
        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etAddress = findViewById(R.id.etAddress);
        etGender = findViewById(R.id.etGender);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}