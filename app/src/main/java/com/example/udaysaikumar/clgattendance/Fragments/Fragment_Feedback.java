package com.example.udaysaikumar.clgattendance.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Feedback extends Fragment {
   private CardView appShare,email,whatsapp;
private TextInputLayout mySuggestionLayout,myBugLayout;
private TextInputEditText suggestionText, bugText;
private String mySuggesion,myBug;
  private   RatingBar ratingBar;
  private MaterialButton materialButton;
  private boolean aBoolean=false;
  private RetroGet retroGet;
  private  View v;
  private ProgressBar myProgressBar;
  private String TAG="Fragment_Feedback_Log";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_fragment__feedback, container, false);
       // appShare=v.findViewById(R.id.appshare);
       // email=v.findViewById(R.id.bugreport);
      //  whatsapp=v.findViewById(R.id.bugwhatsapp);
        suggestionText =v.findViewById(R.id.mySuggestion);
        //bugText =v.findViewById(R.id.myBugs);
        materialButton=v.findViewById(R.id.submit);
        ratingBar =v.findViewById(R.id.myRating);
        myProgressBar=v.findViewById(R.id.myProgressBar);
mySuggestionLayout=v.findViewById(R.id.mySuggestionLayout);
//myBugLayout=v.findViewById(R.id.myBugsLayout);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (suggestionText.getText().toString().isEmpty()) {
                        mySuggestionLayout.setError("write suggestionText");
                        aBoolean=false;
                    }else
                    {
                        mySuggesion= suggestionText.getText().toString();
                        aBoolean=true;

                    }
                }
                catch (Exception e)
                {
                    mySuggestionLayout.setError("write suggestionText");
                }
                if(aBoolean) {
                    errorDisable();
                  showProgress();

                    postFeedback(mySuggesion, ratingBar.getRating());
                }
            }
        });

//        appShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent sharing=new Intent(Intent.ACTION_SEND);
//                sharing.setType("text/plain");
//                sharing.putExtra(Intent.EXTRA_SUBJECT,"Share");
//                sharing.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.redants.siteParent");
//                startActivity(Intent.createChooser(sharing,"Share"));
//            }
//        });
//        whatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=919494345589&text=BUGREPORT");
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                }
//                catch (Exception e){
//
//                }
//            }
//        });
//        email.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url=null;
//                try {
//
//                    Intent email = new Intent(Intent.ACTION_SEND);
//                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"uforusk@gmail.com"});
//                    email.putExtra(Intent.EXTRA_SUBJECT, "BUG_REPORT");
//                    email.putExtra(Intent.EXTRA_TEXT, "");
//                    email.setType("message/rfc822");
//                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
//                }
//                catch (Exception e){
//                    Log.d(TAG,e.toString());
//
//                }
//            }
//        });
        return v;
    }

   public void postFeedback(final String suggetion, final float rating)
{
    String keySuggestion="Suggestion";
    String keyBug="Bug";
    String myrating="Rating";
    String body = "{\""+keySuggestion+"\":\""+suggetion+"\",\""+myrating+"\":\""+rating+"\"}";
  //  Log.d(TAG,body);
    retroGet= RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
    Call<String> retroCall=retroGet.sendFeedback("application/json",body,getResources().getString(R.string.feedbackCollection),getResources().getString(R.string.APIKEY));
    retroCall.enqueue(new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
          //  Log.d(TAG,response.body());
hideProgress();
try {
    suggestionText.getText().clear();
    //bugText.getText().clear();
    ratingBar.setRating(0);
    errorDisable();


}catch (Exception e)
{

}
            Toast.makeText(v.getContext(),"posted successfully",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
hideProgress();
            Toast.makeText(v.getContext(),"failure",Toast.LENGTH_SHORT).show();

        }
    });
}
public void errorDisable()
{
    mySuggestionLayout.setError(null);
   // myBugLayout.setError(null);
    mySuggestionLayout.setErrorEnabled(false);
    //myBugLayout.setErrorEnabled(false);
}
    public  void showProgress()
    {
        myProgressBar.setVisibility(View.VISIBLE);
        materialButton.setVisibility(View.INVISIBLE);
    }
    public void hideProgress()
    {
        myProgressBar.setVisibility(View.INVISIBLE);
        materialButton.setVisibility(View.VISIBLE);
    }

}
