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

import java.util.ArrayList;
import java.util.List;

public class CvExperienceActivity extends AppCompatActivity {


    private LinearLayout experienceContainer;
    private Button btnAddExperience, btnSubmit;

    private List<Experiences> experiencesList = new ArrayList<>(); // Danh sách chứa các đối tượng Education

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_experience);

        getSupportActionBar().setTitle("Kinh nghiệm việc làm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo các view
        experienceContainer = findViewById(R.id.experienceContainer);
        btnAddExperience = findViewById(R.id.btnAddExperience);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy danh sách Educations truyền vào (nếu có)
        List<Experiences> initialExperiences = (List<Experiences>) getIntent().getSerializableExtra("experiences");
        if (initialExperiences != null) {
            experiencesList.addAll(initialExperiences);
            for (Experiences experiences : experiencesList) {
                addExperienceBlock(experiences); // Hiển thị từng block
            }
        }

        // Xử lý nút thêm Education
        btnAddExperience.setOnClickListener(v -> addExperienceBlock(null));

        btnSubmit.setOnClickListener(v -> saveExperienceList());

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
    private void addExperienceBlock(Experiences experiences) {
        // Tạo container cho một khối Education
        LinearLayout educationBlock = new LinearLayout(this);
        educationBlock.setOrientation(LinearLayout.VERTICAL);
        educationBlock.setPadding(16, 16, 16, 16);
        educationBlock.setBackgroundResource(R.drawable.rounded_border);

        // Đặt margin top cho
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (10 * getResources().getDisplayMetrics().density); // 10dp
        educationBlock.setLayoutParams(params);


        // Thêm label và EditText cho "Năm bắt đầu"
        educationBlock.addView(createLabel("Năm bắt đầu"));
        EditText etStartYear = createEditText("Năm bắt đầu", experiences != null ? experiences.getStartAt() : "");
        etStartYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etStartYear);

        // EditText cho "Năm kết thúc"
        educationBlock.addView(createLabel("Năm kết thúc"));
        EditText etEndYear = createEditText("Năm kết thúc", experiences != null ? experiences.getEndAt() : "");
        etEndYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etEndYear);

        // EditText cho "Tên cong ty"
        educationBlock.addView(createLabel("Công ty"));
        EditText etCompanyName = createEditText("Công ty", experiences != null ? experiences.getCompany() : "");
        educationBlock.addView(etCompanyName);

        // EditText cho "vi tri"
        educationBlock.addView(createLabel("Vị trí"));
        EditText etPosittioName = createEditText("Vị trí", experiences != null ? experiences.getPosition() : "");
        educationBlock.addView(etPosittioName);

        //mo ta
        educationBlock.addView(createLabel("Mô tả"));
        EditText etDescription = createEditText("Mô tả", experiences != null ? experiences.getDescription() : "");
        etDescription.setMinLines(4);
        educationBlock.addView(etDescription);

        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> experienceContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        experienceContainer.addView(educationBlock);
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


    private void saveExperienceList() {
        // Xóa danh sách hiện tại
        experiencesList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < experienceContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) experienceContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etStartYear = null, etEndYear = null, etCompanyName = null, etPosittioName = null, etDescription = null;
            // Duyệt qua tất cả các child views của educationBlock
            for (int j = 0; j < educationBlock.getChildCount(); j++) {
                View child = educationBlock.getChildAt(j);
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    // Gán EditText vào đúng biến dựa vào hint của nó
                    if (editText.getHint().toString().equals("Năm bắt đầu")) {
                        etStartYear = editText;
                    } else if (editText.getHint().toString().equals("Năm kết thúc")) {
                        etEndYear = editText;
                    } else if (editText.getHint().toString().equals("Công ty")) {
                        etCompanyName = editText;
                    } else if (editText.getHint().toString().equals("Vị trí")) {
                        etPosittioName = editText;
                    } else if (editText.getHint().toString().equals("Mô tả")) {
                        etDescription = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Experiences experiences = new Experiences(
                    etPosittioName != null ? etPosittioName.getText().toString() : "",
                    etCompanyName != null ? etCompanyName.getText().toString() : "",

                    etStartYear != null ? etStartYear.getText().toString() : "",
                    etEndYear != null ? etEndYear.getText().toString() : "",
                    etDescription != null ? etDescription.getText().toString() : ""
            );
            experiencesList.add(experiences);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("experience", new ArrayList<>(experiencesList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}