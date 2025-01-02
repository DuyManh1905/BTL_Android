package com.duymanh.btl.cv_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Educations;
import com.duymanh.btl.model.Experiences;
import com.duymanh.btl.model.Skills;

import java.util.ArrayList;
import java.util.List;

public class CvSkillActivity extends AppCompatActivity {


    private LinearLayout skillContainer;
    private Button btnAddSkill, btnSubmit;

    private List<Skills> skillsList = new ArrayList<>(); // Danh sách chứa các đối tượng Education

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_skill);

        getSupportActionBar().setTitle("Các kỹ năng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo các view
        skillContainer = findViewById(R.id.skillContainer);
        btnAddSkill = findViewById(R.id.btnAddSkill);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy danh sách Educations truyền vào (nếu có)
        List<Skills> initialSkills = (List<Skills>) getIntent().getSerializableExtra("skills");
        if (initialSkills != null) {
            skillsList.addAll(initialSkills);
            for (Skills skills : skillsList) {
                addSkillBlock(skills); // Hiển thị từng block
            }
        }

        // Xử lý nút thêm Education
        btnAddSkill.setOnClickListener(v -> addSkillBlock(null));

        btnSubmit.setOnClickListener(v -> saveSkillList());

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

    private void addSkillBlock(Skills skills) {
        // Tạo container cho một khối Skill
        LinearLayout educationBlock = new LinearLayout(this);
        educationBlock.setOrientation(LinearLayout.VERTICAL);
        educationBlock.setPadding(16, 16, 16, 16);
        educationBlock.setBackgroundResource(R.drawable.rounded_border);

        // Đặt margin top cho
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (10 * getResources().getDisplayMetrics().density); // 10dp
        educationBlock.setLayoutParams(params);

        // EditText cho "tên kỹ năng"
        educationBlock.addView(createLabel("Tên kỹ năng"));
        EditText etNameSkill  = createEditText("Tên kỹ năng", skills != null ? skills.getName() : "");
        educationBlock.addView(etNameSkill);


        //mo ta
        educationBlock.addView(createLabel("Mô tả"));
        EditText etDescription  = createEditText("Mô tả", skills != null ? skills.getDescription() : "");
        educationBlock.addView(etDescription);

        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> skillContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        skillContainer.addView(educationBlock);
    }


    private TextView createLabel(String text) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(16); // Kích thước chữ
        label.setTextColor(getResources().getColor(android.R.color.black)); // Màu chữ
        return label;
    }

    private EditText createEditText(String hint, String text) {
        EditText editText = new EditText(this);
        editText.setHint(hint);
        editText.setText(text);
        editText.setBackgroundResource(R.drawable.input_text_bg); // Gán background từ bgr.xml
        editText.setPadding(25, 25, 25, 25); // Padding

        // Thiết lập marginBottom
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, (int) (15 * getResources().getDisplayMetrics().density)); // 15dp
        editText.setLayoutParams(params);
        return editText;
    }

    private void saveSkillList() {
        // Xóa danh sách hiện tại
        skillsList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < skillContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) skillContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etNameSkill = null, etDescription = null;
            // Duyệt qua tất cả các child views của educationBlock
            for (int j = 0; j < educationBlock.getChildCount(); j++) {
                View child = educationBlock.getChildAt(j);
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    // Gán EditText vào đúng biến dựa vào hint của nó
                    if (editText.getHint().toString().equals("Tên kỹ năng")) {
                        etNameSkill = editText;
                    } else if (editText.getHint().toString().equals("Mô tả")) {
                        etDescription = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Skills skills = new Skills(
                    etNameSkill != null ? etNameSkill.getText().toString() : "",
                    etDescription != null ? etDescription.getText().toString() : ""
            );
            skillsList.add(skills);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("skill", new ArrayList<>(skillsList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}