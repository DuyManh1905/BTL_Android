package com.duymanh.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duymanh.btl.dal.SQLiteHelper;
import com.duymanh.btl.model.User;

public class SignupActivity extends AppCompatActivity {

    private EditText userName,email,password,rePassword;
    private Button btSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userName = findViewById(R.id.edUserName);
        password = findViewById(R.id.edPassword);
        email = findViewById(R.id.edEmail);
        rePassword = findViewById(R.id.edRePassword);
        btSignup = findViewById(R.id.btnSignup);


        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                String mUsername = userName.getText().toString();
                String mPassword = password.getText().toString();
                String mEmail = email.getText().toString();
                String mRepassword = rePassword.getText().toString();

                if(!mPassword.equalsIgnoreCase(mRepassword)){
                    Toast.makeText(getApplicationContext(),"Vui long nhap mat khau trung nhau!",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!db.checkUserSigup(mUsername)){
                        Toast.makeText(getApplicationContext(),"Ten tai khoan da ton tai!",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        db.addUser(new User(mUsername,mPassword,mEmail));
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}