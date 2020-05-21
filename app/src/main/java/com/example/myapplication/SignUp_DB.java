package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SignUp_DB extends AsyncTask<String, Void, String> {
    AlertDialog alertDialog;
    Context context;

    SignUp_DB(Context ctx) { context = ctx; }

    @Override
    protected String doInBackground(String... params) {

        String type = params[0];
        String login_URL = "http://mmvc.000webhostapp.com/AndroSignup.php";
        if(type.equals("signup"))
        {
            try
            {
                String user_name = params[1];
                String employee_id = params[2];
                String email = params[3];
                String phone = params[4];
                String user_Pass = params[5];
                String user_confirmpass = params[6];
                String addres = params[7];
                String dept_name = params[8];

                URL url = new URL(login_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter( outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8")+"="+URLEncoder.encode(user_name, "UTF-8")
                        +"&"+URLEncoder.encode("employee_id", "UTF-8")+"="+URLEncoder.encode(employee_id, "UTF-8")
                        +"&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")
                        +"&"+URLEncoder.encode("phone_number", "UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")
                        +"&"+URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(user_Pass, "UTF-8")
                        +"&"+URLEncoder.encode("confirm_password", "UTF-8")+"="+URLEncoder.encode(user_confirmpass, "UTF-8")
                        +"&"+URLEncoder.encode("address", "UTF-8")+"="+URLEncoder.encode(addres, "UTF-8")
                        +"&"+URLEncoder.encode("department", "UTF-8")+"="+URLEncoder.encode(dept_name, "UTF-8");

                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Sign Up Status");
    }

    @Override
    protected void onPostExecute(String resul) {
        alertDialog.setTitle(resul);
        alertDialog.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

}
