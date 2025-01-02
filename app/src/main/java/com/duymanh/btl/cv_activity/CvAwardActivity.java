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
import com.duymanh.btl.model.Awards;
import com.duymanh.btl.model.Certificates;
import com.duymanh.btl.model.Educations;

import java.util.ArrayList;
import java.util.List;

public class CvAwardActivity extends AppCompatActivity {


    private LinearLayout awardContainer;
    private Button btnAddAward, btnSubmit;

    private List<Awards> awardsList = new ArrayList<>(); // Danh sách chứa các đối tượng Education

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_award);

        getSupportActionBar().setTitle("Các giải thưởng của bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo các view
        awardContainer = findViewById(R.id.awardContainer);
        btnAddAward = findViewById(R.id.btnAddAward);
        btnSubmit = findViewById(R.id.btnSubmit);

        List<Awards> initialAwards = (List<Awards>) getIntent().getSerializableExtra("awards");
        if (initialAwards != null) {
            awardsList.addAll(initialAwards);
            for (Awards awards : awardsList) {
                addAwardBlock(awards); // Hiển thị từng block
            }
        }

        // Xử lý nút thêm Education
        btnAddAward.setOnClickListener(v -> addAwardBlock(null));

        btnSubmit.setOnClickListener(v -> saveAwardList());

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

    private void addAwardBlock(Awards awards) {
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
        educationBlock.addView(createLabel("Tên danh hiệu"));
        EditText etNameCertificate = createEditText("Tên danh hiệu", awards != null ? awards.getName() : "");
        educationBlock.addView(etNameCertificate);

        //mo ta
        educationBlock.addView(createLabel("Thời gian"));
        EditText etTime = createEditText("Thời gian", awards != null ? awards.getTime() : "");
        etTime.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etTime);

        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> awardContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        awardContainer.addView(educationBlock);
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


    private void saveAwardList() {
        // Xóa danh sách hiện tại
        awardsList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < awardContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) awardContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etNameCertificate = null, etTime = null;
            // Duyệt qua tất cả các child views của educationBlock
            for (int j = 0; j < educationBlock.getChildCount(); j++) {
                View child = educationBlock.getChildAt(j);
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    // Gán EditText vào đúng biến dựa vào hint của nó
                    if (editText.getHint().toString().equals("Tên danh hiệu")) {
                        etNameCertificate = editText;
                    } else if (editText.getHint().toString().equals("Thời gian")) {
                        etTime = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Awards awards = new Awards(
                    etNameCertificate != null ? etNameCertificate.getText().toString() : "",
                    etTime != null ? etTime.getText().toString() : ""
            );
            awardsList.add(awards);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("award", new ArrayList<>(awardsList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}