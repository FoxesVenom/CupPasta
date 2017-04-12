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

import java.util.HashMap;

public class Validate extends AppCompatActivity implements View.OnClickListener{

    private Boolean pass2 = false;
    private String Email, Inv, Name, Street, City, State, Zip,Pasta, Sauce, Panini, Dessert, Party, Total;
    private TextView address, summary, invoice, total;


    private Button Val;
    private static final String CONFIRM_URL = "http://173.170.13.161/updateinv.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);

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
            Validate();
        }
    }

    private void Validate() {
        CatInfo("2", Inv);
    }

    private void CatInfo(String pass, String inv) {
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
                if (s.trim().equalsIgnoreCase("Catering info updated!")) {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                    Intent ordering = new Intent(Validate.this, Validate.class);
                    startActivity(ordering);
                }
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
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
                return  result;
            }
        }

        subCat ru = new subCat();
        ru.execute(pass,inv);
    }
}