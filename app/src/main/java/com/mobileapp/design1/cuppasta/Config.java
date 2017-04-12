package com.mobileapp.design1.cuppasta;

/**
 * Created by Juwan on 4/2/2017.
 */

public class Config {
    //URL to our login.php file
    public static final String LOGIN_URL = "http://173.170.13.161/login.php"; //change once AWS is up

    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "CUS_EMAIL";
    public static final String KEY_PASSWORD = "CUS_PASS";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "CUS_EMAIL";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String NAME = "NAME";

    public static final String PASTA = "PASTA";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String PANINI = "PANINI";

    public static final String SAUCE = "SAUCE";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String DESSERT = "DESSERT";

    public static final String SERVE = "SERVE";

    public static final String PHONE = "1234567890";

    public static final String STREET = "STREET";

    public static final String ZIP = "12345";

    public static final String CITY = "CITY";

    public static final String STATE = "STATE";

    public static final String INVNUM = "0";

    public static final String PAYMENT = "SERVE";
}