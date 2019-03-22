package com.example.udaysaikumar.clgattendance.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.udaysaikumar.clgattendance.Login.LoginData;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitOPTServer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    TextInputEditText optText;
    MaterialButton login;
    Chip resent;
    RetroGet retroGet;
    List<LoginData> list;
    ProgressBar progressBar;
    RelativeLayout main;
    int otpNo;
    String API_KEY;
    void enableView(boolean b)
    {
        if(b)
        {
            optText.setEnabled(true);
            login.setEnabled(true);
            resent.setEnabled(true
            );
            progressBar.setVisibility(View.INVISIBLE);

        }
        else
        {
            optText.setEnabled(false);
            login.setEnabled(false);
            resent.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
void getOTP()
{
   // showProgress();
    //enableView(false);
    SharedPreferences s = getSharedPreferences("MyLogin", MODE_PRIVATE);
    Random random=new Random();
     otpNo=random.nextInt(7000)+1000;
    String otp="Your one time password is \n"+otpNo;
    retroGet= RetrofitOPTServer.getRetrofitOTP().create(RetroGet.class);
    String un="sasicollege",pass="SITE2002",from="INSITE",to=s.getString("phone",""),type="1";
    Call<String> retro=retroGet.getOTP(un,pass,from,to,otp,type);
    retro.enqueue(new Callback<String>() {
        @Override
        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//enableView(true);
            hideProgress();
            showChip();
            //  Toast.makeText(view.getContext(), response.body(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
            //enableView(true);
            hideProgress();
            showChip();
            //Toast.makeText(view.getContext(),"Good",Toast.LENGTH_SHORT).show();
        }
    });
}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        API_KEY=getResources().getString(R.string.APIKEY);

       // Objects.requireNonNull(getSupportActionBar()).hide();
        optText = findViewById(R.id.opt);
        login = findViewById(R.id.login);
        resent = findViewById(R.id.resentotp);
        main=findViewById(R.id.main);
        progressBar=findViewById(R.id.progressBar);
        getOTP();
        resent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                //enableView(false);
               getOTP();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
               checkOTP();
                }


        });
    }
    void checkOTP() {
try{
        if (!optText.getText().toString().equals("") || optText.getText().toString().length() > 0) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyLogin", MODE_PRIVATE);
            // int i = sharedPreferences.getInt("otp", 0);
            int k = Integer.parseInt(optText.getText().toString());
            if (k == otpNo) {
                // enableView(false);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("logged", true);
                SharedPreferences sharedPref = getSharedPreferences("MyLogin", MODE_PRIVATE);
                String username = sharedPref.getString("username", "");
                System.out.println("important" + username);
                String q = "{\"regno\":{$eq:\"" + username + "\"}}";
                retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                Call<List<LoginData>> dataCall = retroGet.getPhone("LOGIN", API_KEY, q);
                dataCall.enqueue(new Callback<List<LoginData>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<LoginData>> call, @NonNull Response<List<LoginData>> response) {
                        //  assert response.body() != null;
                        try {
                            if (response.body() != null & !response.body().isEmpty()) {
                                list = response.body();
                               // System.out.println("important" + list);
                                if (list != null && !list.isEmpty()) {
                                   // System.out.println("OTP activity" + list.get(0).getMarks());
                                    editor.putString("marks", list.get(0).getMarks());
                                    editor.putString("profile", list.get(0).getProfile());
                                    editor.putString("attendance", list.get(0).getAttendance());
                                    Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
                                    editor.apply();
                                    hideProgress();
                                    startActivity(intent);
                                    finish();

                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "sorry, no document found", Toast.LENGTH_SHORT).show();
                              //  enableView(false);
                                hideProgress();

                            }
                        } catch (Exception e) {
                                hideProgress();
                        }
                      //  enableView(true);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<LoginData>> call, @NonNull Throwable t) {
                       // enableView(true);
                        hideProgress();
                        Toast.makeText(getApplicationContext(), "please connect to active network", Toast.LENGTH_SHORT).show();
                    }

                });

            } else {
                alertOTP();
            }
        } else {
            alertOTP();
        }
    }
        catch(Exception e)
        {
            hideProgress();
        }
    }
    void alertOTP()
    {
        //enableView(true);
        hideProgress();
        Toast.makeText(getApplicationContext(), "please enter valid OTP", Toast.LENGTH_SHORT).show();

    }
    public void showProgress()
    {
        login.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }
    public void hideProgress()
    {
        login.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
    public void showChip()
    {
       resent.setVisibility(View.VISIBLE);
    }
}
