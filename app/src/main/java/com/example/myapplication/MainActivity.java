package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText emp_id, passOfUser;
    Button signinPage, signupPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emp_id = (EditText)findViewById(R.id.employee_id);
        passOfUser = (EditText)findViewById(R.id.password);
        signinPage = (Button) findViewById(R.id.btn_sign_in);
        signupPage = (Button) findViewById(R.id.btn_sign_up);
        signupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
            }
        });
        signinPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonClick(view);
            }
        });
    }


     public void onButtonClick(View view) {
         String user_ID = emp_id.getText().toString();
         String user_Pass = passOfUser.getText().toString();
         String type = "login";
         SignIn_DB worker = new SignIn_DB(this);
         worker.execute(type, user_ID, user_Pass);
     }
}