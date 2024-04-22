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

public class LoginActivity extends AppCompatActivity {

    private EditText userName,password;
    private Button btnLogin;

    private TextView txtSigup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        txtSigup = findViewById(R.id.txtSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mUsername = userName.getText().toString();
                String mPassword = password.getText().toString();

                User u = new User(mUsername,mPassword);
                SQLiteHelper db = new SQLiteHelper(getApplicationContext());
                if(db.checkUser(u)){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Sai tai khoan hoac mat khau!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        txtSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}