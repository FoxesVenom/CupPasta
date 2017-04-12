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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class Validate extends AppCompatActivity implements View.OnClickListener{

    private EditText Address;
    private EditText City;
    private EditText Zip;
    private EditText State;
    private EditText Phone;
    private Spinner saucespinner;
    private Spinner paninispinner;
    private Spinner dessertspinner;
    private RadioGroup radioGroup;
    private EditText num;
    private EditText email;
    private Boolean pass2 = false;

    private Button Val;
    private static final String CONFIRM_URL = "http://173.170.13.161/updateinv.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String storedPreference = preferences.getString(Config.EMAIL_SHARED_PREF, "");

        City = (EditText) findViewById(R.id.City);
        Zip = (EditText) findViewById(R.id.ZipCode);
        State = (EditText) findViewById(R.id.State);
        Phone = (EditText) findViewById(R.id.num);
        Address = (EditText) findViewById(R.id.Address);



        Val = (Button) findViewById(R.id.Valid);

        Val.setOnClickListener(Validate.this);
    }
    public void gotoHome(View view)
    {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

    public void gotoReg(View view)
    {
        Intent ordering = new Intent(this, order.class);
        startActivity(ordering);
    }

    @Override
    public void onClick(View v) {
        if(v == Val){
            sendCat();
        }
    }

    private void sendCat() {
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String storedPreference = preferences.getString(Config.INVNUM, "");

        pass2 = true;
        String address = Address.getText().toString();
        String city = City.getText().toString();
        String state = State.getText().toString();
        String zip = Zip.getText().toString();
        String phone = Phone.getText().toString();
        String inv = storedPreference;


        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = preferences.edit();

        //Adding values to editor
        editor.putString(Config.STREET, address);
        editor.putString(Config.CITY, city);
        editor.putString(Config.STATE, state);
        editor.putString(Config.ZIP, zip);
        editor.putString(Config.PHONE, phone);

        //Saving values to editor
        editor.commit();
        String pass = Boolean.toString(pass2);
        CatInfo(pass,address,city,state, zip, phone, inv);
    }

    private void CatInfo(String pass, String address, String city, String state, String zip, String phone, String inv) {
        class subCat extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Validate.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                Intent ordering = new Intent(Validate.this, Validate.class);
                startActivity(ordering);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Pass",params[0]);
                data.put("Address",params[1]);
                data.put("City",params[2]);
                data.put("State",params[3]);
                data.put("Zip",params[4]);
                data.put("Phone",params[5]);
                data.put("inv",params[6]);

                String result = ruc.sendPostRequest(CONFIRM_URL,data);
                pass2 = false;
                return  result;
            }
        }

        subCat ru = new subCat();
        ru.execute(pass, address, city,state, zip, phone, inv);
    }
}