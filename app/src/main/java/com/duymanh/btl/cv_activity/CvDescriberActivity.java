package com.duymanh.btl.cv_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Profile;

public class CvDescriberActivity extends AppCompatActivity {

    private EditText etDescribe;
    private Button btnSubmit;

    private String mota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_describer);

        getSupportActionBar().setTitle("Mô tả về bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mota = (String)getIntent().getStringExtra("mota");

        initView();
        if(mota!=null){
            etDescribe.setText(mota);
        }

        btnSubmit.setOnClickListener(v -> {
            String mota = etDescribe.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("mota",mota);
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
        etDescribe = findViewById(R.id.etDescribe);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
}