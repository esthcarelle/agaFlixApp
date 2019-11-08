package com.example.agalix;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button upload = (Button)findViewById(R.id.btn_upload);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        upload.startAnimation(animation);

        Button viewFiles = (Button)findViewById(R.id.btn_viewFiles);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        viewFiles.startAnimation(anim);
    }
}
