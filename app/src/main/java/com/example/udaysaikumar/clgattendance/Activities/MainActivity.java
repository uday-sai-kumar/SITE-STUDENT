package com.example.udaysaikumar.clgattendance.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.udaysaikumar.clgattendance.Login.PhoneData;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RetroGet retroGet;
    TextInputEditText phone;
    String phoneNo;
    List<PhoneData> list;
    Button login;
    String API_KEY;
    ProgressBar progressBar;
    String COLLECTION="PHONENUMBERS";
    boolean aBoolean=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Objects.requireNonNull(getSupportActionBar()).hide();
        API_KEY=getResources().getString(R.string.APIKEY);
        phone=findViewById(R.id.pNo);
        progressBar=findViewById(R.id.progressBar);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                try {


                    if (phone.getText().toString().equals("") || phone.getText().length() <= 9) {
                        phone.setError("enter valid number");
                        aBoolean = false;
                        // Toast.makeText(MainActivity.this,"please enter valid number",Toast.LENGTH_SHORT).show();
                    } else {
                        aBoolean = true;
                    }
                }catch (Exception e)
                {
                    aBoolean=false;
                    phone.setError("enter valid number");
                }
                if(aBoolean){
showProgress();
                   //enableView(false);
                    phoneNo = phone.getText().toString();

                    //final String p="{\"parentmobile\":{$eq:\""+phoneNo+"\"}}";
                   final String p="{\"studentmobile\":{$eq:"+Long.parseLong(phoneNo)+"}}";
                    retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                    Call<List<PhoneData>> dataCall = retroGet.getPhoneData(COLLECTION,API_KEY,p);
                    dataCall.enqueue(new Callback<List<PhoneData>>() {
                        @Override
                        public void onResponse(@NonNull Call<List<PhoneData>> call, @NonNull Response<List<PhoneData>> response) {
                            if (response.body()!= null ) {
                                list = response.body();
                                try {
                                    System.out.println("ourresponse" + list.get(0).parentmobile);
                                    System.out.println("ourresponse" + list.get(0).studentmobile);
                                }catch (Exception e)
                                {
                                    Log.d("ourresponse",e.getMessage());
                                }
                                try{
                                if (!list.isEmpty()) {
                                    if (Long.parseLong(phoneNo)==list.get(0).getStudentmobile() || Long.parseLong(phoneNo)==list.get(0).getParentmobile()) {
                                        hideProgress();
                                        SharedPreferences s = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = s.edit();
                                        editor.putString("phone", phoneNo);
                                        editor.putString("username",list.get(0).getRegno());
                                        editor.apply();
                                        //progressBar.setVisibility(View.INVISIBLE);
                                        Intent i = new Intent(MainActivity.this, OTPActivity.class);
                                         startActivity(i);
                                        finish();
                                    }
                                    else {
                                       // System.out.println("ourresponse 1"+list);

                                       fun();

                                    }
                                }
                                else {
                                    System.out.println("ourresponse 2"+list);

                                    fun();
                                }

                            }
                            catch (Exception e){
                            hideProgress();
                            }

                            }

                         //  enableView(true);

                        }
                        @Override
                        public void onFailure(@NonNull Call<List<PhoneData>> call, @NonNull Throwable t) {
                          hideProgress();
                            Toast.makeText(MainActivity.this, "Failure, please connect to active network", Toast.LENGTH_LONG).show();

                        }

                    });

                }
            }
        });


    }
    void fun(){
       hideProgress();
        Toast.makeText(MainActivity.this, "number not updated in database try with parent number", Toast.LENGTH_LONG).show();
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
}
