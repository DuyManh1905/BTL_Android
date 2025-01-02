package com.duymanh.btl.cv_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.duymanh.btl.R;
import com.duymanh.btl.model.Educations;
import com.duymanh.btl.model.Hobbies;
import com.duymanh.btl.model.Introducer;

import java.util.ArrayList;
import java.util.List;

public class CvIntroducerActivity extends AppCompatActivity {

    private LinearLayout introducerContainer;
    private Button btnAddIntroducer, btnSubmit;

    private List<Introducer> introducerList = new ArrayList<>(); // Danh sách chứa các đối tượng Education

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_introducer);

        getSupportActionBar().setTitle("Người giới thiệu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo các view
        introducerContainer = findViewById(R.id.introducerContainer);
        btnAddIntroducer = findViewById(R.id.btnAddIntroducer);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy danh sách Educations truyền vào (nếu có)
        List<Introducer> initialIntroducers = (List<Introducer>) getIntent().getSerializableExtra("introducers");
        if (initialIntroducers != null) {
            introducerList.addAll(initialIntroducers);
            for (Introducer introducer : introducerList) {
                addIntroducerBlock(introducer); // Hiển thị từng block
            }
        }

        // Xử lý nút thêm Education
        btnAddIntroducer.setOnClickListener(v -> addIntroducerBlock(null));

        btnSubmit.setOnClickListener(v -> saveIntroducerList());

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

    private void addIntroducerBlock(Introducer introducer) {
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
        educationBlock.addView(createLabel("Thông tin người giới thiệu"));
        EditText etNameIntroducer = createEditText("Thông tin người giới thiệu", introducer != null ? introducer.getInformation() : "");
        etNameIntroducer.setMinLines(4);
        educationBlock.addView(etNameIntroducer);


        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> introducerContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        introducerContainer.addView(educationBlock);
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

    private void saveIntroducerList() {
        // Xóa danh sách hiện tại
        introducerList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < introducerContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) introducerContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etNameIntroducer = null;
            // Duyệt qua tất cả các child views của educationBlock
            for (int j = 0; j < educationBlock.getChildCount(); j++) {
                View child = educationBlock.getChildAt(j);
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    // Gán EditText vào đúng biến dựa vào hint của nó
                    if (editText.getHint().toString().equals("Thông tin người giới thiệu")) {
                        etNameIntroducer = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Introducer introducer = new Introducer(
                    etNameIntroducer != null ? etNameIntroducer.getText().toString() : ""
            );
            introducerList.add(introducer);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("introducer", new ArrayList<>(introducerList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}