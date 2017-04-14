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
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

import java.util.HashMap;

public class Validate extends AppCompatActivity implements View.OnClickListener{

    private Boolean pass2 = false;
    private String Email, Inv, Name, Street, City, State, Zip,Pasta, Sauce, Panini, Dessert, Party, Total;
    private TextView address, summary, invoice, total;
    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();
    private TextView text;
    private Button btn_date;
    private Button btn_time;
    private String time;


    private Button Val, Can;
    private static final String DELETE = "http://173.170.13.161/deleteorder.php";
    private static final String VALID_URL = "http://173.170.13.161/valid.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        dateTime.set(Calendar.SECOND, 00);
        text = (TextView) findViewById(R.id.txt_TextDateTime);
        btn_date = (Button) findViewById(R.id.btn_datePicker);
        btn_time = (Button) findViewById(R.id.btn_timePicker);

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });

        updateTextLabel();

        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Email = preferences.getString(Config.EMAIL_SHARED_PREF, "");
        Inv = preferences.getString(Config.INVNUM, "");
        Name = preferences.getString(Config.NAME, "");
        Street = preferences.getString(Config.STREET, "");
        City = preferences.getString(Config.CITY, "");
        State = preferences.getString(Config.STATE, "");
        Zip = preferences.getString(Config.ZIP, "");
        Pasta = preferences.getString(Config.PASTA, "");
        Sauce = preferences.getString(Config.SAUCE, "");
        Panini = preferences.getString(Config.PANINI, "");
        Dessert = preferences.getString(Config.DESSERT, "");
        Party = preferences.getString(Config.SERVE, "");
        Total = preferences.getString(Config.TOTAL, "");

        address = (TextView) findViewById(R.id.addr);
        invoice = (TextView) findViewById(R.id.invoice);
        summary = (TextView) findViewById(R.id.summ);
        total = (TextView) findViewById(R.id.total);
        String PushAddr = Street + "\n" + City + ", " + State + "   " + Zip;
        String PushSumm = "Pasta: " + Pasta + "\n" + "Sauce: " + Sauce + "\n" + "Panini: " + Panini + "\n" + "Dessert: " + Dessert + "\n" + "Party Size: " + Party;
        String PushInv = "Your Invoice Number is " + Inv;
        String PushTotal = "Your Total is: $" + Total;
        address.setText(PushAddr);
        summary.setText(PushSumm);
        invoice.setText(PushInv);
        total.setText(PushTotal);

        Val = (Button) findViewById(R.id.Valid);

        Val.setOnClickListener(Validate.this);
        Can = (Button) findViewById(R.id.cancel);

        Can.setOnClickListener(Validate.this);

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
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Email = preferences.getString(Config.EMAIL_SHARED_PREF, "");
        Inv = preferences.getString(Config.INVNUM, "");
        Name = preferences.getString(Config.NAME, "");
        Street = preferences.getString(Config.STREET, "");
        City = preferences.getString(Config.CITY, "");
        State = preferences.getString(Config.STATE, "");
        Zip = preferences.getString(Config.ZIP, "");
        Pasta = preferences.getString(Config.PASTA, "");
        Sauce = preferences.getString(Config.SAUCE, "");
        Panini = preferences.getString(Config.PANINI, "");
        Dessert = preferences.getString(Config.DESSERT, "");
        Party = preferences.getString(Config.SERVE, "");
        Total = preferences.getString(Config.TOTAL, "");
        if(v == Val){
            Validate();
        }else if(v == Can){
            deleteOrder();
        }
    }

    private void Validate() {
        time = text.getText().toString();
        CatInfo("2", Inv, Email, time);
    }

    private void updateDate(){
        new DatePickerDialog(this, d, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(this, t, dateTime.get(Calendar.HOUR_OF_DAY), dateTime.get(Calendar.MINUTE), false).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateTime.set(Calendar.YEAR, year);
            dateTime.set(Calendar.MONTH, monthOfYear);
            dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateTextLabel();
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateTime.set(Calendar.MINUTE, minute);
            dateTime.set(Calendar.SECOND, 00);
            updateTextLabel();
        }
    };

    private void updateTextLabel(){
        text.setText(formatDateTime.format(dateTime.getTime()));
    }

    private void CatInfo(String pass, String inv, String email, String time) {
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
                if (s.trim().equalsIgnoreCase("Email Sent!")) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    SharedPreferences.Editor editor = preferences.edit();

                    //Adding values to editor
                    editor.putString(Config.SERVE, "SERVE");
                    editor.putString(Config.PASTA, "PASTA");
                    editor.putString(Config.PANINI, "PANINI");
                    editor.putString(Config.DESSERT, "DESSERT");
                    editor.putString(Config.SAUCE, "SAUCE");
                    editor.putString(Config.PHONE, "1234567890");
                    editor.putString(Config.STREET, "STREET");
                    editor.putString(Config.ZIP, "12345");
                    editor.putString(Config.CITY, "CITY");
                    editor.putString(Config.STATE, "STATE");
                    editor.putString(Config.INVNUM, "0");
                    editor.putString(Config.PAYMENT, "PAY");


                    //Saving values to editor
                    editor.commit();
                    Intent ordering = new Intent(Validate.this, HomeScreen.class);
                    ordering.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ordering);
                }
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("Pass",params[0]);
                data.put("Inv",params[1]);
                data.put("Email",params[2]);
                data.put("Time",params[3]);

                String result = ruc.sendPostRequest(VALID_URL,data);
                return  result;
            }
        }

        subCat ru = new subCat();
        ru.execute(pass,inv, email, time);
    }

    private void deleteOrder() {
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String storedPreference = preferences.getString(Config.EMAIL_SHARED_PREF, "");

        String CUS_EMAIL = storedPreference;
        String InvAsString = preferences.getString(Config.INVNUM, "");

        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editor = preferences.edit();

        //Adding values to editor
        editor.putString(Config.SERVE, "");
        editor.putString(Config.PASTA, "");
        editor.putString(Config.PANINI, "");
        editor.putString(Config.DESSERT, "");
        editor.putString(Config.SAUCE, "");
        editor.putString(Config.PHONE, "1234567890");
        editor.putString(Config.STREET, "STREET");
        editor.putString(Config.ZIP, "12345");
        editor.putString(Config.CITY, "CITY");
        editor.putString(Config.STATE, "STATE");
        editor.putString(Config.INVNUM, "0");
        editor.putString(Config.PAYMENT, "PAY");


        //Saving values to editor
        editor.commit();

        delete(InvAsString);
    }

    private void delete(String inv) {
        class subDel extends AsyncTask<String, Void, String>{
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

                if(s.trim().equalsIgnoreCase("Unfinished Order Canceled")){
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    Intent ordering = new Intent(Validate.this, HomeScreen.class);
                    ordering.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(ordering);
                }
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(String... params) {



                HashMap<String, String> data = new HashMap<String,String>();
                data.put("inv",params[0]);


                String result = ruc.sendPostRequest(DELETE,data);

                return result;
            }
        }

        subDel ru = new subDel();
        ru.execute(inv);
    }
}