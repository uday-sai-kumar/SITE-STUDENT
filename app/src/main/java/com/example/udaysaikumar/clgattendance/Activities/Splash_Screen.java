package com.example.udaysaikumar.clgattendance.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.udaysaikumar.clgattendance.R;

import java.util.Objects;

public class Splash_Screen extends AppCompatActivity {
ImageView splash_image;
String SPLASH_URL="http://www.goqwickly.com/imgs/computer-screens/attendance-splash.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash__screen);
      //  Objects.requireNonNull(getSupportActionBar()).hide();
        splash_image=findViewById(R.id.splashimage);
new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
        SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
        if(sharedPreferences.getBoolean("logged",false))
        {
            Intent i=new Intent(getApplicationContext(),BottomBarActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
    }
},2000);
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
                if(sharedPreferences.getBoolean("logged",false))
                {
                    Intent i=new Intent(getApplicationContext(),BottomBarActivity.class);
                    startActivity(i,ActivityOptions.makeSceneTransitionAnimation(Splash_Screen.this).toBundle());
                    finish();
                }
                else {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i,ActivityOptions.makeSceneTransitionAnimation(Splash_Screen.this).toBundle());
                    finish();
                }

            }
        }).start();*/

    }
}
