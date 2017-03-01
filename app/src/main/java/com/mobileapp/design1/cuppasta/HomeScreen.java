package com.mobileapp.design1.cuppasta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

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
}
