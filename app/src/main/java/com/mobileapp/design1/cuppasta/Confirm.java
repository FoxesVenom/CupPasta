package com.mobileapp.design1.cuppasta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Confirm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        String pasta = intent.getStringExtra("Pasta");
        String sauce = intent.getStringExtra("Sauce");
        String panini = intent.getStringExtra("Panini");
        String dessert = intent.getStringExtra("Dessert");
        int num = intent.getIntExtra("Serve", 0);

        String all = new String("Pasta: " + pasta + "\nSauce: " + sauce + "\nPanini: " + panini + "\nDessert: " + dessert);
        TextView TextOrder = (TextView) findViewById(R.id.TextOrder);
        TextOrder.setText(all);
        TextView Serve = (TextView) findViewById(R.id.Serve);
        Serve.setText("You will be serving: " + num);
    }
}
