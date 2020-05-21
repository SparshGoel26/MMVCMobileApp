package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dept;

    private Button signupButton;
    private TextView choose_Image;
    private ImageView profile_pic;

    private EditText username, pass,confirmPassword,phoneNumber, address, employee_ID, email_ID;
    //defining AwesomeValidation object
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupButton = (Button)findViewById(R.id.btn_sign_up);

        username = (EditText)findViewById(R.id.user_name);
        employee_ID = (EditText)findViewById(R.id.employee_id);
        email_ID = (EditText)findViewById(R.id.email_id);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        pass = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);
        address = (EditText)findViewById(R.id.address);
        dept = (Spinner) findViewById(R.id.department);
        dept.setOnItemSelectedListener(this);
        profile_pic = findViewById(R.id.profile_pic);
        choose_Image = findViewById(R.id.choose_image);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name = username.getText().toString();
                String employee_id = employee_ID.getText().toString().trim();
                String email = email_ID.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                String user_Pass = pass.getText().toString();
                String user_confirmpass =confirmPassword.getText().toString();
                String addres = address.getText().toString().trim();
                String dept_name = String.valueOf(dept.getSelectedItem());
                String type = "signup";
                SignUp_DB worker = new SignUp_DB(SignUp.this);
                worker.execute(type, user_name, employee_id, email, phone, user_Pass,user_confirmpass,addres,dept_name);

            }
        });

        choose_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==
                            PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else
                    //permission already granted
                    { pickImageFromGallery(); }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });
    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granteed
                    pickImageFromGallery();
                }
                else {
                    // permission was denied
                    Toast.makeText(this, "Permission denied....!", Toast.LENGTH_SHORT).show();
                }
            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            profile_pic.setImageURI(data.getData());
            System.out.println(data.getData());
        }

    }

    public void inputValidations()
    {
        String nameOfUser = username.getText().toString();
        String IdOfUser = employee_ID.getText().toString();
        String emailOfUser = email_ID.getText().toString();
        String passwordOfUser = pass.getText().toString();
        String addressOfUser = address.getText().toString();
        String departmentOfUser = dept.getSelectedItem().toString();

        if(nameOfUser.equals("") ||
                IdOfUser.equals("") ||
                emailOfUser.equals("") ||
                passwordOfUser.equals("") ||
                addressOfUser.equals("")||
                departmentOfUser.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please fill the vacant fields..!", Toast.LENGTH_LONG).show();
        }
        else if(!emailOfUser.matches(String.valueOf(Patterns.EMAIL_ADDRESS)))
        {
            Toast.makeText(getApplicationContext(), "Please enter the valid E-mail address", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Welcome, "+nameOfUser, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUp.this, MainActivity.class);
            startActivity(intent);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
//        Toast.makeText(getApplicationContext(),clgDepartments[position] , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}