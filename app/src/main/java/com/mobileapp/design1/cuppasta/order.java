package com.mobileapp.design1.cuppasta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


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
