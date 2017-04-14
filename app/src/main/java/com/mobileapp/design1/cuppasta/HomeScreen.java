package com.mobileapp.design1.cuppasta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;
import java.util.HashMap;



public class HomeScreen extends AppCompatActivity implements View.OnClickListener{
    private int inv;
    private Button buttonDel;
    private static final String DELETE = "http://173.170.13.161/deleteorder.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        buttonDel = (Button) findViewById(R.id.order);

        buttonDel.setOnClickListener(HomeScreen.this);


        }

        //int inv = Integer.parseInt(storedPreference);


    public void order(View view)
    {
        Intent order = new Intent(this, order.class);
        startActivity(order);
    }

    public void about(View view)
    {
        Intent about = new Intent(this, AboutUs.class);
        startActivity(about);
    }

    public void rewards(View view)
    {
        Intent rewards = new Intent(this, Rewards.class);
        startActivity(rewards);
    }

    public void edit(View view)
    {
        Intent edit = new Intent(this, EditAccount.class);
        startActivity(edit);
    }

    private void logout(){
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();

        //Puting the value false for loggedin
        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

        //Putting blank value to email
        editor.putString(Config.EMAIL_SHARED_PREF, "");

        //Saving the sharedpreferences
        editor.commit();

        //Starting login activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String storedPreference = preferences.getString(Config.INVNUM, "");
        Toast.makeText(getApplicationContext(),storedPreference,Toast.LENGTH_LONG).show();
        //inv = Integer.parseInt(storedPreference);
        if(v == buttonDel){
            if (storedPreference != "0") {
                if (storedPreference == ""){
                    Intent ordering = new Intent(HomeScreen.this, order.class);
                    startActivity(ordering);
                }
                else {
                    deleteOrder();
                }
            }
            else {
                Intent ordering = new Intent(HomeScreen.this, order.class);
                startActivity(ordering);
            }

        }
    }

    private void deleteOrder() {
        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String storedPreference = preferences.getString(Config.EMAIL_SHARED_PREF, "");

        String CUS_EMAIL = storedPreference;
        String InvAsString = preferences.getString(Config.INVNUM, "");

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

        delete(InvAsString);
    }

    private void delete(String inv) {
        class subDel extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(HomeScreen.this, "Please Wait",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();

                if(s.trim().equalsIgnoreCase("Old Order Deleted")){
                    Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    Intent ordering = new Intent(HomeScreen.this, order.class);
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
