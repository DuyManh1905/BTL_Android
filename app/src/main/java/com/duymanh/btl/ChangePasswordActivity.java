package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button btnHuy;
    private Button btnLuu;

    private EditText edtMKCu, edtMKmoi, edtMKmoiNhapLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btnHuy = findViewById(R.id.btnHuyDK);
        btnLuu = findViewById(R.id.btnLuu);
        edtMKCu = findViewById(R.id.edtMatKhauHienTai);
        edtMKmoi = findViewById(R.id.edtMatKhauMoi);
        edtMKmoiNhapLai = findViewById(R.id.edtNhapLaiMatKhauMoi);


        btnHuy.setOnClickListener(v -> {
            finish();
        });

        btnLuu.setOnClickListener(v -> {
            String mk = edtMKCu.getText().toString();
            String mkm = edtMKmoi.getText().toString();
            String mkmnl = edtMKmoiNhapLai.getText().toString();

            if(!mk.equals("123456")){
                Toast.makeText(this,"Mật khẩu hiện tại không đúng!!",Toast.LENGTH_SHORT).show();
            }
            else if(!mkm.equals(mkmnl)){
                Toast.makeText(this,"Mật khẩu nhập lại chưa trùng khớp!!",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                // Dùng Handler để trì hoãn việc quay lại Activity trước 2 giây
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish(); // Kết thúc Activity hiện tại
                    }
                }, 1500); // 2000ms tương đương với 2 giây
            }
        });
    }
}