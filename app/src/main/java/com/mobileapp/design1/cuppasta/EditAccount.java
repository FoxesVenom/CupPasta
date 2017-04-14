package com.mobileapp.design1.cuppasta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditAccount extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextFname;
    private EditText editTextLname;
    private EditText editTextPassword;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private String CUS_EMAIL, CUS_FNAME;
    private String email;
    private String name;
    private String result;

    private Button buttonRegister;

    private static final String Edit_URL = "http://173.170.13.161/editaccount.php"; //change once AWS is up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        editTextFname = (EditText) findViewById(R.id.new_FNAME);
        editTextLname = (EditText) findViewById(R.id.new_LNAME);
        editTextEmail = (EditText) findViewById(R.id.new_EMAIL);
        editTextPassword = (EditText) findViewById(R.id.new_PASS);
        editTextPhone = (EditText) findViewById(R.id.new_PHONE);

        buttonRegister = (Button) findViewById(R.id.Reg_Button);

        buttonRegister.setOnClickListener(EditAccount.this);
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
        String CUS_FNAME = editTextFname.getText().toString().trim();
        String CUS_LNAME = editTextLname.getText().toString().trim();
        String CUS_PHONE = editTextPhone.getText().toString().trim();
        String CUS_PASS = editTextPassword.getText().toString().trim();
        String CUS_EMAIL = editTextEmail.getText().toString().trim().toLowerCase();
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String old = preferences.getString(Config.EMAIL_SHARED_PREF, "");
       // Toast.makeText(getApplicationContext(), old, Toast.LENGTH_LONG).show();

        register(CUS_FNAME,CUS_LNAME,CUS_PHONE,CUS_PASS,CUS_EMAIL, old);
    }

    private void register(String CUS_FNAME, String CUS_LNAME, String CUS_PHONE, String CUS_PASS, String CUS_EMAIL, String old) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditAccount.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(result.trim().equalsIgnoreCase("successfully edited account")){
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                    Intent ordering = new Intent(EditAccount.this, HomeScreen.class);
                    startActivity(ordering);
                }
                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("CUS_FNAME",params[0]);
                data.put("CUS_LNAME",params[1]);
                data.put("CUS_EMAIL",params[2]);
                data.put("CUS_PASS",params[3]);
                data.put("CUS_PHONE",params[4]);
                data.put("OLD",params[5]);

                result = ruc.sendPostRequest(Edit_URL,data);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    result = jsonObject.getString("success");
                    if(result.trim().equalsIgnoreCase("successfully edited account")) {
                        email = jsonObject.getString("email");
                        name = jsonObject.getString("name");
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();


                        //Adding values to editor
                        editor.putString(Config.EMAIL_SHARED_PREF, email);
                        editor.putString(Config.NAME, name);
                        editor.commit();
                        return result;
                    }
                    else{
                        /*email = jsonObject.getString("email");
                        name = jsonObject.getString("name");
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(Config.EMAIL_SHARED_PREF, email);
                        editor.putString(Config.NAME, name);*/
                        //Toast.makeText(getApplicationContext(), "pl", Toast.LENGTH_LONG).show();
                        return result;}

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(CUS_FNAME,CUS_LNAME,CUS_EMAIL,CUS_PASS,CUS_PHONE, old);
    }
}