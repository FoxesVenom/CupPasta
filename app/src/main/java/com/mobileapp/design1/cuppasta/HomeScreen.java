package com.mobileapp.design1.cuppasta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.SharedPreferences;


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
}
