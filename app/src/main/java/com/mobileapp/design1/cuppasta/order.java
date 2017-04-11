package com.mobileapp.design1.cuppasta;

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


/* LEAVE UNTIL DETERMINED NO LONGER NEEDED FOR REFERENCE
public class order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

//get name of pastaspinner
    public void Confirm(View view) {
        Spinner pastaspinner = (Spinner) findViewById(R.id.pastaspinner);
        Spinner saucespinner = (Spinner) findViewById(R.id.saucespinner);
        Spinner paninispinner = (Spinner) findViewById(R.id.paninispinner);
        Spinner dessertspinner = (Spinner) findViewById(R.id.dessertspinner);
        EditText num = (EditText) findViewById(R.id.num);

        String pastaspinner_data = pastaspinner.getSelectedItem().toString();
        String saucespinner_data = saucespinner.getSelectedItem().toString();
        String paninispinner_data = paninispinner.getSelectedItem().toString();
        String dessertspinner_data = dessertspinner.getSelectedItem().toString();
        int num1 = Integer.valueOf(num.getText().toString());
        Intent intent = new Intent (this, Confirm.class);
        intent.putExtra("Pasta", pastaspinner_data);
        intent.putExtra("Sauce", saucespinner_data);
        intent.putExtra("Panini", paninispinner_data);
        intent.putExtra("Dessert", dessertspinner_data);
        intent.putExtra("Serve", num1);
        startActivity(intent);
    }
}
*/
public class order extends AppCompatActivity implements View.OnClickListener{

    private Spinner pastaspinner;
    private Spinner saucespinner;
    private Spinner paninispinner;
    private Spinner dessertspinner;
    private EditText num;
    private EditText email;

    private Button buttonOrder;

    private static final String ORDER_URL = "http://173.170.13.161/invoice.php"; //change once AWS is up


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (EditText) findViewById(R.id.Email);
        setContentView(R.layout.activity_order);

        pastaspinner = (Spinner) findViewById(R.id.pastaspinner);
        saucespinner = (Spinner) findViewById(R.id.saucespinner);
        paninispinner = (Spinner) findViewById(R.id.paninispinner);
        dessertspinner = (Spinner) findViewById(R.id.dessertspinner);
        num = (EditText) findViewById(R.id.num);



        buttonOrder = (Button) findViewById(R.id.submitOrder);

        buttonOrder.setOnClickListener(order.this);
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
        if(v == buttonOrder){
            submitOrder();
        }
    }

    private void submitOrder() {
        String pastaspinner_data = pastaspinner.getSelectedItem().toString();
        String saucespinner_data = saucespinner.getSelectedItem().toString();
        String paninispinner_data = paninispinner.getSelectedItem().toString();
        String dessertspinner_data = dessertspinner.getSelectedItem().toString();
        String num_data = num.getText().toString();
        String CUS_EMAIL = email.getText().toString().trim().toLowerCase();

        ordering(pastaspinner_data,saucespinner_data,paninispinner_data,dessertspinner_data, num_data, CUS_EMAIL);
    }

    private void ordering(String pastaspinner_data, String saucespinner_data, String paninispinner_data, String dessertspinner_data, String num_data, String CUS_EMAIL) {
        class subOrder extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(order.this, "Please Wait",null, true, true);
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
                data.put("Pasta",params[0]);
                data.put("Sauce",params[1]);
                data.put("Panini",params[2]);
                data.put("Dessert",params[3]);
                data.put("Serving",params[4]);
                data.put("CUS_EMAIL",params[5]);

                String result = ruc.sendPostRequest(ORDER_URL,data);

                return  result;
            }
        }

        subOrder ru = new subOrder();
        ru.execute(pastaspinner_data,saucespinner_data,paninispinner_data,dessertspinner_data, num_data, CUS_EMAIL);
    }
}


