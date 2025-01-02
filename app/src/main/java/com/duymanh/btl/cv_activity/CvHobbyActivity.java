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
import com.duymanh.btl.model.Educations;
import com.duymanh.btl.model.Hobbies;

import java.util.ArrayList;
import java.util.List;

public class CvHobbyActivity extends AppCompatActivity {

    private LinearLayout hobbyContainer;
    private Button btnAddHobby, btnSubmit;

    private List<Hobbies> hobbiesList = new ArrayList<>(); // Danh sách chứa các đối tượng Education

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_hobby);

        getSupportActionBar().setTitle("Sở thích của bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo các view
        hobbyContainer = findViewById(R.id.hobbyContainer);
        btnAddHobby = findViewById(R.id.btnAddHobby);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy danh sách Educations truyền vào (nếu có)
        List<Hobbies> initialHobbies = (List<Hobbies>) getIntent().getSerializableExtra("hobbies");
        if (initialHobbies != null) {
            hobbiesList.addAll(initialHobbies);
            for (Hobbies hobbies : hobbiesList) {
                addHobbyBlock(hobbies); // Hiển thị từng block
            }
        }

        // Xử lý nút thêm Education
        btnAddHobby.setOnClickListener(v -> addHobbyBlock(null));

        btnSubmit.setOnClickListener(v -> saveHobbyList());

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

    private void addHobbyBlock(Hobbies hobbies) {
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
        educationBlock.addView(createLabel("Mô tả"));
        EditText etSchoolName = createEditText("Mô tả", hobbies != null ? hobbies.getName() : "");
        educationBlock.addView(etSchoolName);


        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> hobbyContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        hobbyContainer.addView(educationBlock);
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
    private void saveHobbyList() {
        // Xóa danh sách hiện tại
        hobbiesList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < hobbyContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) hobbyContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etNameHobby = null;
            // Duyệt qua tất cả các child views của educationBlock
            for (int j = 0; j < educationBlock.getChildCount(); j++) {
                View child = educationBlock.getChildAt(j);
                if (child instanceof EditText) {
                    EditText editText = (EditText) child;
                    // Gán EditText vào đúng biến dựa vào hint của nó
                    if (editText.getHint().toString().equals("Mô tả")) {
                        etNameHobby = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Hobbies hobbies = new Hobbies(
                    etNameHobby != null ? etNameHobby.getText().toString() : ""
            );
            hobbiesList.add(hobbies);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("hobby", new ArrayList<>(hobbiesList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}