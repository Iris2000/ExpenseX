package com.example.listview;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    DatabaseHelper db;
    EditText mTextUsername;
    EditText mTextEmail;
    EditText mTextPwd;
    EditText mTextConfirmPwd;
    Button mBtnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DatabaseHelper(this);
        mTextUsername = findViewById(R.id.usernameEditText);
        mTextEmail = findViewById(R.id.emailEditText);
        mTextPwd = findViewById(R.id.passwordEditText);
        mTextConfirmPwd = findViewById(R.id.confirmEditText);
        mBtnSignUp = findViewById(R.id.signUpBtn);
    }

    public void OnSignUpClicked(View view) {
        String username = mTextUsername.getText().toString();
        String email = mTextEmail.getText().toString();
        String pwd = mTextPwd.getText().toString();
        String confirmPwd = mTextConfirmPwd.getText().toString();
        if (username.equals("") || email.equals("") || pwd.equals("") || confirmPwd.equals("")) {
            Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
        }
        else {
            if (pwd.length() >= 8) {
                if (pwd.equals(confirmPwd)) {
                    Boolean checkMail = db.checkMail(email);
                    Boolean checkUsername = db.checkUsername(username);
                    if (checkMail) {
                        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            Toast.makeText(getApplicationContext(), "Incorrect email format", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!checkUsername) {
                                Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Boolean insertUserTable = db.insertUserTable(username, email, pwd);
                                Boolean insertUserCatTable = db.insertUserCatTable(username);
                                if (insertUserTable && insertUserCatTable) {
                                    Toast.makeText(getApplication(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Password must be 8 or more character", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBackBtnClicked(View view) {
        finish();
    }
}
