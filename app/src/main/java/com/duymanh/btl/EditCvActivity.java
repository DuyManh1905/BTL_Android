package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EditCvActivity extends AppCompatActivity {
    private LinearLayout educationContainer, projectContainer;
    private Button btnAddEducation, btnAddProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cv);

        // Khởi tạo các view
        educationContainer = findViewById(R.id.educationContainer);
        projectContainer = findViewById(R.id.projectContainer);
        btnAddEducation = findViewById(R.id.btnAddEducation);
        btnAddProject = findViewById(R.id.btnAddProject);

        // Xử lý nút thêm Education
        btnAddEducation.setOnClickListener(v -> addEducationBlock());

        // Xử lý nút thêm Project
        btnAddProject.setOnClickListener(v -> addProjectBlock());
    }

    private void addEducationBlock() {
        // Tạo container cho một khối Education
        LinearLayout educationBlock = new LinearLayout(this);
        educationBlock.setOrientation(LinearLayout.VERTICAL);
        educationBlock.setPadding(16, 16, 16, 16);

        // EditText cho "Năm bắt đầu"
        EditText etStartYear = new EditText(this);
        etStartYear.setHint("Năm bắt đầu");
        etStartYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etStartYear);

        // EditText cho "Năm kết thúc"
        EditText etEndYear = new EditText(this);
        etEndYear.setHint("Năm kết thúc");
        etEndYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etEndYear);

        // EditText cho "Tên trường"
        EditText etSchoolName = new EditText(this);
        etSchoolName.setHint("Tên trường");
        educationBlock.addView(etSchoolName);

        // Thêm educationBlock vào educationContainer
        educationContainer.addView(educationBlock);
    }

    private void addProjectBlock() {
        // Tạo container cho một khối Project
        LinearLayout projectBlock = new LinearLayout(this);
        projectBlock.setOrientation(LinearLayout.VERTICAL);
        projectBlock.setPadding(16, 16, 16, 16);

        // EditText cho "Công nghệ"
        EditText etTechnology = new EditText(this);
        etTechnology.setHint("Công nghệ");
        projectBlock.addView(etTechnology);

        // EditText cho "Tên dự án"
        EditText etName = new EditText(this);
        etName.setHint("Tên dự án");
        projectBlock.addView(etName);

        // EditText cho "Số thành viên"
        EditText etTeamSize = new EditText(this);
        etTeamSize.setHint("Số thành viên");
        etTeamSize.setInputType(InputType.TYPE_CLASS_NUMBER);
        projectBlock.addView(etTeamSize);

        // Thêm projectBlock vào projectContainer
        projectContainer.addView(projectBlock);
    }
}