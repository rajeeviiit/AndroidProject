package com.example.ravi.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText U_Fname;
    private EditText U_Lname;
    private EditText U_email;
    private EditText U_password;
    private Button buttonRegister;
    private TextView linkLogin;

    private static final String REGISTER_URL = "http://api.whitepanda.in/api/writers/register";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        U_Fname = (EditText) findViewById(R.id.u_Fname);
        U_Lname = (EditText) findViewById(R.id.u_Lname);
        U_password = (EditText) findViewById(R.id.u_password);
        U_email = (EditText) findViewById(R.id.u_email);
        buttonRegister = (Button) findViewById(R.id.btn_register);
        linkLogin = (TextView) findViewById(R.id.link_login);

        buttonRegister.setOnClickListener(this);
        linkLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.btn_register:
                RegisterUser();
                break;

            case R.id.link_login:
                GobackSignIn();
                break;

        }


    }

    private void GobackSignIn(){

        Intent backsignin = new Intent(RegisterActivity.this,login.class);
        startActivity(backsignin);

    }
    private void RegisterUser() {
        String fname = U_Fname.getText().toString().trim().toLowerCase();
        String lname = U_Lname.getText().toString().trim().toLowerCase();
        String password = U_password.getText().toString().trim().toLowerCase();
        String email = U_email.getText().toString().trim().toLowerCase();

        register(fname,lname,email,password);
    }

    private void register(String fname, String lname, String email, String password) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("first_name",params[0]);
                data.put("last_name",params[1]);
                data.put("email",params[2]);
                data.put("password",params[3]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(fname,lname,email,password);
    }

    }


