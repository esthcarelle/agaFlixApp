package com.example.agalix;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

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
