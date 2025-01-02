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
import com.duymanh.btl.model.Certificates;
import com.duymanh.btl.model.Educations;
import com.duymanh.btl.model.Skills;

import java.util.ArrayList;
import java.util.List;

public class CvCertificateActivity extends AppCompatActivity {


    private LinearLayout certificateContainer;
    private Button btnAddCertificate, btnSubmit;

    private List<Certificates> certificatesList = new ArrayList<>(); // Danh sách chứa các đối tượng Education

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_certificate);


        getSupportActionBar().setTitle("Chững chỉ của bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Khởi tạo các view
        certificateContainer = findViewById(R.id.certificateContainer);
        btnAddCertificate = findViewById(R.id.btnAddCertificate);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy danh sách Educations truyền vào (nếu có)
        List<Certificates> initialCertificates = (List<Certificates>) getIntent().getSerializableExtra("certificates");
        if (initialCertificates != null) {
            certificatesList.addAll(initialCertificates);
            for (Certificates certificates : certificatesList) {
                addCertificateBlock(certificates); // Hiển thị từng block
            }
        }

        // Xử lý nút thêm Education
        btnAddCertificate.setOnClickListener(v -> addCertificateBlock(null));

        btnSubmit.setOnClickListener(v -> saveCertificateList());

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

    private void addCertificateBlock(Certificates certificates) {
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
        educationBlock.addView(createLabel("Tên chứng chỉ"));
        EditText etNameCertificate = createEditText("Tên chứng chỉ", certificates != null ? certificates.getName() : "");
        educationBlock.addView(etNameCertificate);

        //mo ta
        educationBlock.addView(createLabel("Thời gian"));
        EditText etTime = createEditText("Thời gian", certificates != null ? certificates.getTime() : "");
        etTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etTime);

        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> certificateContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        certificateContainer.addView(educationBlock);
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

    private void saveCertificateList() {
        // Xóa danh sách hiện tại
        certificatesList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < certificateContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) certificateContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etNameCertificate = null, etTime = null;
            // Duyệt qua tất cả các child views của educationBlock
            for (int j = 0; j < educationBlock.getChildCount(); j++) {
                View child = educationBlock.getChildAt(j);
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    // Gán EditText vào đúng biến dựa vào hint của nó
                    if (editText.getHint().toString().equals("Tên chứng chỉ")) {
                        etNameCertificate = editText;
                    } else if (editText.getHint().toString().equals("Thời gian")) {
                        etTime = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Certificates certificates = new Certificates(
                    etNameCertificate != null ? etNameCertificate.getText().toString() : "",
                    etTime != null ? etTime.getText().toString() : ""
            );
            certificatesList.add(certificates);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("certificate", new ArrayList<>(certificatesList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}