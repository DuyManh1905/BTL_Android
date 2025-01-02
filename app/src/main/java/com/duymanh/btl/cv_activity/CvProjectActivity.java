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
import com.duymanh.btl.model.Project;

import java.util.ArrayList;
import java.util.List;

public class CvProjectActivity extends AppCompatActivity {


    private LinearLayout projectContainer;
    private Button btnAddProject, btnSubmit;

    private List<Project> projectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv_project);

        getSupportActionBar().setTitle("Dự án của bạn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Khởi tạo các view
        projectContainer = findViewById(R.id.projectContainer);
        btnAddProject = findViewById(R.id.btnAddProject);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Lấy danh sách Educations truyền vào (nếu có)
        List<Project> initialProjects = (List<Project>) getIntent().getSerializableExtra("projects");
        if (initialProjects != null) {
            projectList.addAll(initialProjects);
            for (Project project : projectList) {
                addProjectBlock(project); // Hiển thị từng block
            }
        }

        // Xử lý nút thêm Education
        btnAddProject.setOnClickListener(v -> addProjectBlock(null));

        btnSubmit.setOnClickListener(v -> saveProjectList());

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

    private void addProjectBlock(Project project) {
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

        // EditText cho "Năm bắt đầu"
        educationBlock.addView(createLabel("Năm bắt đầu"));
        EditText etStartYear = createEditText("Năm bắt đầu", project != null ? project.getStartAt() : "");
        etStartYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etStartYear);

        // EditText cho "Năm kết thúc"
        educationBlock.addView(createLabel("Năm kết thúc"));
        EditText etEndYear  = createEditText("Năm kết thúc", project != null ? project.getStartAt() : "");
        etEndYear.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etEndYear);

        // EditText cho "Ten du an"
        educationBlock.addView(createLabel("Tên dự án"));
        EditText etProjectName  = createEditText("Tên dự án", project != null ? project.getName() : "");
        educationBlock.addView(etProjectName);

        // EditText cho "so luong thanh vien"
        educationBlock.addView(createLabel("Số lượng thành viên"));
        EditText etTeamSize  = createEditText("Số lượng thành viên", project != null ? project.getTeamSize() : "");
        etTeamSize.setInputType(InputType.TYPE_CLASS_NUMBER);
        educationBlock.addView(etTeamSize);

        // EditText cho "khach hang"
        educationBlock.addView(createLabel("Khách hàng"));
        EditText etCustommr  = createEditText("Khách hàng", project != null ? project.getCustomer() : "");
        educationBlock.addView(etCustommr);

        // EditText cho "vi tri"
        educationBlock.addView(createLabel("Vị trí"));
        EditText etPosittioName  = createEditText("Vị trí", project != null ? project.getPosition() : "");
        educationBlock.addView(etPosittioName);

        //cong nghe
        educationBlock.addView(createLabel("Công nghệ sử dụng"));
        EditText etTechnology  = createEditText("Công nghệ sử dụng", project != null ? project.getTechnologies() : "");
        educationBlock.addView(etTechnology);

        //mo ta
        educationBlock.addView(createLabel("Mô tả"));
        EditText etDescription  = createEditText("Mô tả", project != null ? project.getDescription() : "");
        educationBlock.addView(etDescription);



        // Tạo nút xóa block
        Button btnDelete = new Button(this);
        btnDelete.setText("Xóa");
        btnDelete.setOnClickListener(v -> projectContainer.removeView(educationBlock)); // Xóa block khi nhấn
        btnDelete.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark)); // Chọn màu đỏ
        educationBlock.addView(btnDelete);

        // Thêm educationBlock vào educationContainer
        projectContainer.addView(educationBlock);
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

    private void saveProjectList() {
        // Xóa danh sách hiện tại
        projectList.clear();

        // Lấy thông tin từ tất cả các block trong educationContainer
        for (int i = 0; i < projectContainer.getChildCount(); i++) {
            LinearLayout educationBlock = (LinearLayout) projectContainer.getChildAt(i);
            // Khai báo các EditText
            EditText etStartYear = null, etEndYear = null, etProjectName = null,
                    etTeamSize = null, etCustommr = null,etPosittioName = null, etTechnology=null, etDescription=null;
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
                    } else if (editText.getHint().toString().equals("Tên dự án")) {
                        etProjectName = editText;
                    } else if (editText.getHint().toString().equals("Số lượng thành viên")) {
                        etTeamSize = editText;
                    } else if (editText.getHint().toString().equals("Khách hàng")) {
                        etCustommr = editText;
                    } else if (editText.getHint().toString().equals("Vị trí")) {
                        etPosittioName = editText;
                    } else if (editText.getHint().toString().equals("Công nghệ sử dụng")) {
                        etTechnology = editText;
                    } else if (editText.getHint().toString().equals("Mô tả")) {
                        etDescription = editText;
                    }
                }
            }

            // Tạo đối tượng Education và thêm vào danh sách
            Project project = new Project(
                    etProjectName != null ? etProjectName.getText().toString() : "",
                    etCustommr != null ? etCustommr.getText().toString() : "",
                    etTeamSize != null ? etTeamSize.getText().toString() : "",
                    etPosittioName != null ? etPosittioName.getText().toString() : "",
                    etTechnology != null ? etTechnology.getText().toString() : "",
                    etStartYear != null ? etStartYear.getText().toString() : "",
                    etEndYear != null ? etEndYear.getText().toString() : "",
                    etDescription != null ? etDescription.getText().toString() : ""
            );
            projectList.add(project);
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("project", new ArrayList<>(projectList));
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}