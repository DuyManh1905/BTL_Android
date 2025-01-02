package com.duymanh.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class TestImageActivity extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        imageView = findViewById(R.id.imageView);

        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/testimagesearchjob.firebasestorage.app/o/avt_user.png?alt=media";

        // Hiển thị ảnh với Glide
        Glide.with(this)
                .load(imageUrl)
                .into(imageView);
    }

}