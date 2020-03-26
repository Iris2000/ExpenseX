package com.example.listview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextEmail;
    EditText mTextPwd;
    Button mBtnSignIn;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        db = new DatabaseHelper(this);
        mTextEmail = findViewById(R.id.emailSignIn);
        mTextPwd = findViewById(R.id.passwordSignIn);
        mBtnSignIn = findViewById(R.id.signInBtn);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        if (sp.getBoolean("logged", true)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void OnSignUpClicked(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void OnSignInClicked(View view) {
        String email = mTextEmail.getText().toString();
        String pwd = mTextPwd.getText().toString();
        Boolean checkMail = db.checkMail(email);
        Boolean checkAcc = db.checkAcc(email, pwd);
        if (email.equals("") || pwd.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else {
            if (checkMail) {
                Toast.makeText(getApplicationContext(), "Email doesn't exists", Toast.LENGTH_SHORT).show();
            }
            else {
                if (!checkAcc) {
                    Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show();
                    String username = db.getUsername(email);
                    Log.d("Get username: ", username);
                    Log.d("Get email: ", email);
                    sp.edit().putBoolean("Logged", true).apply();
                    sp.edit().putString("username", username).apply();
                    sp.edit().putString("email", email).apply();

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}
