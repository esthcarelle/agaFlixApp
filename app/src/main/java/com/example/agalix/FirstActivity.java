package com.example.agalix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomer.fadingtextview.FadingTextView;

import java.util.concurrent.TimeUnit;

public class FirstActivity extends AppCompatActivity {


    private TextView txt;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ImageView image = (ImageView)findViewById(R.id.img);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        image.startAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));
            }
        });

        TextView textview = (TextView)findViewById(R.id.txt);
        Animation textAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animtwo);
        textview.startAnimation(textAnim);

        textAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation textAnim) {
            }

            @Override
            public void onAnimationRepeat(Animation textAnim) {
            }

            @Override
            public void onAnimationEnd(Animation textAnim) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));
            }
        });
    }
}

