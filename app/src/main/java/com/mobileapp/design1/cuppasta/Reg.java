package com.mobileapp.design1.cuppasta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class Reg extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPhone;

    private Button buttonRegister;

    private static final String REGISTER_URL = "http://173.170.13.161/register.php"; //change once AWS is up


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        editTextFname = (EditText) findViewById(R.id.new_FNAME);
        editTextLname = (EditText) findViewById(R.id.new_LNAME);
        editTextEmail = (EditText) findViewById(R.id.new_EMAIL);
        editTextPassword = (EditText) findViewById(R.id.new_PASS);
        editTextPhone = (EditText) findViewById(R.id.new_PHONE);

        buttonRegister = (Button) findViewById(R.id.Reg_Button);

        buttonRegister.setOnClickListener(Reg.this);
    }
    public void gotoHome(View view)
    {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void gotoReg(View view)
    {
        Intent reg = new Intent(this, Reg.class);
        startActivity(reg);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String CUS_FNAME = editTextFname.getText().toString().trim().toLowerCase();
        String CUS_LNAME = editTextLname.getText().toString().trim().toLowerCase();
        String CUS_PHONE = editTextPhone.getText().toString().trim().toLowerCase();
        String CUS_PASS = editTextPassword.getText().toString().trim().toLowerCase();
        String CUS_EMAIL = editTextEmail.getText().toString().trim().toLowerCase();

        register(CUS_FNAME,CUS_LNAME,CUS_PHONE,CUS_PASS,CUS_EMAIL);
    }

    private void register(String CUS_FNAME, String CUS_LNAME, String CUS_PHONE, String CUS_PASS, String CUS_EMAIL) {
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Reg.this, "Please Wait",null, true, true);
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
                data.put("CUS_FNAME",params[0]);
                data.put("CUS_LNAME",params[1]);
                data.put("CUS_EMAIL",params[2]);
                data.put("CUS_PASS",params[3]);
                data.put("CUS_PHONE",params[4]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(CUS_FNAME,CUS_LNAME,CUS_EMAIL,CUS_PASS,CUS_PHONE);
    }
}


