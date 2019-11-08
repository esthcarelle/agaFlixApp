package com.example.agalix;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private TextView signUpPage;
    private FirebaseAuth authentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        signUpPage = (TextView) findViewById(R.id.login_text_view);

        authentication = FirebaseAuth.getInstance();

        Button btn = (Button)findViewById(R.id.login_button);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        btn.startAnimation(animation);

        signUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLoginActivity.this, UserSignupActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if(mail.equals("kts@gmail.com") && pass.equals("123456")){
                    startActivity(new Intent(UserLoginActivity.this, DisplayActivity.class));
                    finish();
                }

                if(email.getText().length()==0){
                    email.setError("This field is required!");
                    email.requestFocus();
                }
                else if(password.getText().length()==0){
                    password.setError("This field is required");
                    password.requestFocus();
                }
                else if(email.getText().length()==0 && password.getText().length()==0){
                    Toast.makeText(UserLoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(email.getText().length()!=0 && password.getText().length()!=0) {

                    authentication.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(UserLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(UserLoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();

                            if (!task.isSuccessful()) {
                                Toast.makeText(UserLoginActivity.this, "Authentication failed! Please try again!", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(UserLoginActivity.this, UserActivity.class));
                                finish();
                            }
                        }
                    });
                }
            }
        });
    }
}
