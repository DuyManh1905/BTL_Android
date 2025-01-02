package com.duymanh.btl.cv_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Educations;

import java.util.ArrayList;
import java.util.List;

public class CvEducationActivity extends AppCompatActivity {

    private LinearLayout educationContainer;
    private Button btnAddEducation, btnSubmit;

    private List<Educations> educationList = new ArrayList<>(); // Danh sách chứa các đối tượng Education

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_education);

        getSupportActionBar().setTitle("Học vấn của bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo các view
        educationContainer = findViewById(R.id.educationContainer);
        btnAddEducation = findViewById(R.id.btnAddEducation);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy danh sách Educations truyền vào (nếu có)
        List<Educations> initialEducations = (List<Educations>) getIntent().getSerializableExtra("educations");
        if (initialEducations != null) {
            educationList.addAll(initialEducations);
            for (Educations education : educationList) {
                addEducationBlock(education); // Hiển thị từng block
            }
        }
        btnAddEducation.setOnClickListener(v -> addEducationBlock(null));

        btnSubmit.setOnClickListener(v -> saveEducationList());

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

    private void addEducationBlock(Educations education) {
        // Tạo container cho một khối Education
        LinearLayout educationBlock = new LinearLayout(this);
        educationBlock.setOrientation(LinearLayout.VERTICAL);
        educationBlock.setPadding(16, 16, 16, 16);
        educationBlock.setBackgroundResource(R.drawable.rounded_border);

        // Đặt margin top cho educationBlock
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = (int) (10 * getResources().getDisplayMetrics().density); // 10dp
        educationBlock.setLayoutParams(params);

        // Thêm label và EditText cho "Năm bắt đầu"
        educationBlock.addView(createLabel("Năm bắt đầu"));
        EditText etStartYear = createEditText("Năm bắt đầu", education != null ? education.getStartAt() : "");
        etStartYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etStartYear);

        // Thêm label và EditText cho "Năm kết thúc"
        educationBlock.addView(createLabel("Năm kết thúc"));
        EditText etEndYear = createEditText("Năm kết thúc", education != null ? education.getEndAt() : "");
        etEndYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etEndYear);

        // Thêm label và EditText cho "Tên trường"
        educationBlock.addView(createLabel("Tên trường"));
        EditText etSchoolName = createEditText("Tên trường", education != null ? education.getSchool() : "");
        educationBlock.addView(etSchoolName);

        // Thêm label và EditText cho "Ngành"
        educationBlock.addView(createLabel("Ngành"));
        EditText etMajorName = createEditText("Ngành", education != null ? education.getMajor() : "");
        educationBlock.addView(etMajorName);

        // Thêm label và EditText cho "Mô tả"
        educationBlock.addView(createLabel("Mô tả"));
        EditText etDescription = createEditText("Mô tả", education != null ? education.getDescription() : "");
        educationBlock.addView(etDescription);

        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> educationContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        educationContainer.addView(educationBlock);
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



    private void saveEducationList() {
        // Xóa danh sách hiện tại
        educationList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < educationContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) educationContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etStartYear = null, etEndYear = null, etSchoolName = null, etMajorName = null, etDescription = null;
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
                    } else if (editText.getHint().toString().equals("Tên trường")) {
                        etSchoolName = editText;
                    } else if (editText.getHint().toString().equals("Ngành")) {
                        etMajorName = editText;
                    } else if (editText.getHint().toString().equals("Mô tả")) {
                        etDescription = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Educations education = new Educations(
                    etMajorName != null ? etMajorName.getText().toString() : "",
                    etSchoolName != null ? etSchoolName.getText().toString() : "",
                    etStartYear != null ? etStartYear.getText().toString() : "",
                    etEndYear != null ? etEndYear.getText().toString() : "",
                    etDescription != null ? etDescription.getText().toString() : ""
            );
            educationList.add(education);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("education",new ArrayList<>(educationList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}