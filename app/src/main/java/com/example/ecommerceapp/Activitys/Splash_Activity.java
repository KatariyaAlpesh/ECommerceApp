package com.example.ecommerceapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.ecommerceapp.R;

public class Splash_Activity extends AppCompatActivity
{

    boolean IsLoggedIn;
    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        preferences = getSharedPreferences("MyPreferences" , MODE_PRIVATE);
        editor = preferences.edit();

        if (preferences.contains("IsLoggedIn"))
        {
            IsLoggedIn = preferences.getBoolean("IsLoggedIn" , false);
        }

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                if (IsLoggedIn)
                {
//                    Toast.makeText(Splash_Screen.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                    Intent Inext;
                    Inext = new Intent(Splash_Activity.this , MainActivity.class);
                    startActivity(Inext);
                    finish();
                }
                else
                {
                    Intent Inext;
                    Inext = new Intent(Splash_Activity.this , Login_Activity.class);
                    startActivity(Inext);
                    finish();
                }
            }
        } , 3000);

    }
}