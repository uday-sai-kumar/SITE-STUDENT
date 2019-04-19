package com.example.udaysaikumar.clgattendance.Fragments;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.ObjectKey;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Iterator;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment {


private CircleImageView profile_photo;
private ProgressBar homeProgress,myPhotoProgress;
private TextView appusername,regno;
private TableLayout basic,btech;
private LinearLayout linearProgress;
private StorageReference mStorageRef,childRef,mStorage;
   private String UNAME;
    public final int REQUEST_CODE = 2;
   private View v;
 private String API_KEY;
 private String TAG="Fragment_Home_Log";
 private String PROFILE="PROFILE";
 private TextView phoneNumber,address;
 private String notAvailable;
 private String YEAR="Year";
 private String sems;
 private  RetroGet retroGet;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_fragment__home, container, false);
        profile_photo=v.findViewById(R.id.profile_photo);
        myPhotoProgress=v.findViewById(R.id.myPhotoProgress);
        homeProgress=v.findViewById(R.id.homeprogress);
        linearProgress=v.findViewById(R.id.linearprogress);
        appusername=v.findViewById(R.id.appusername);
        regno=v.findViewById(R.id.appregno);
        basic=v.findViewById(R.id.basic);
        btech=v.findViewById(R.id.btech);
        phoneNumber=v.findViewById(R.id.phoneNumber);
        address=v.findViewById(R.id.address);
API_KEY=getResources().getString(R.string.APIKEY);
        notAvailable=getResources().getString(R.string.notAvailable);
        mStorageRef = FirebaseStorage.getInstance().getReference();
      //  Log.d(TAG,mStorageRef.toString());
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
         UNAME=sharedPreferences.getString("username","");
     //   LoginData data=new LoginData();
      //  showProgress();
      //  showPhotoProgress();
        profile();
        showProfile();


        return v;
    }
    public void showProfile()
    {
        showProgress();
        try {
            basic.removeAllViewsInLayout();
            btech.removeAllViewsInLayout();
        }catch (Exception E)
        {

        }
        String q="{\"regno\":{$eq:\""+UNAME+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataCall = retroGet.getProfile(PROFILE,API_KEY,q);
        dataCall.enqueue(new Callback<String>()

        {
            @Override
            public void onResponse
                    (@NonNull Call < String > call, @NonNull Response < String > response){
                if (response.body() != null) {
                    String json = response.body();
                    try {
                        JSONArray j = new JSONArray(json);
                        JSONObject job = j.getJSONObject(0);
                        JSONObject jj = job.getJSONObject("SI");
                        JSONObject jj1 = job.getJSONObject("BTECH");
                        String myPhone=job.getString("Mobile");
                        String myAddress=job.getString("Address");
                        if(!myPhone.equals(""))
                        {
                            phoneNumber.setText(myPhone);
                        }else phoneNumber.setText(notAvailable);
                        if(!myAddress.equals(""))
                        {
                            address.setText(myAddress);
                        }else address.setText(notAvailable);
                        Iterator<String> it = jj.keys();
                        Iterator<String> it1 = jj1.keys();
                        appusername.setText(job.get("Name").toString());
                        regno.setText(job.get("regno").toString());
                        Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
                        while (it.hasNext()) {
                            String key = it.next();
                            TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0,0,0,1);
                            TableRow tableRow = new TableRow(v.getContext());
                            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            TextView t1 = new TextView(v.getContext());

                            t1.setTextAppearance(v.getContext(),R.style.TextViewGreen);
                            t1.setTypeface(typeface);
                           // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                            t1.setGravity(Gravity.START|Gravity.CENTER_HORIZONTAL);
                            t1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                         //   t1.setTextColor(Color.WHITE);
                            // t1.setBackgroundColor(Color.WHITE);
                            //  t1.setBackgroundResource(R.drawable.table_custom_text);
                            t1.setText(key);
                            tableRow.addView(t1,layoutParams);
                            TextView t2 = new TextView(v.getContext());
                            t2.setTextAppearance(v.getContext(),R.style.TextViewTheme1);
                            t2.setTypeface(typeface);
                           // t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                            // t2.setBackgroundColor(Color.WHITE);
                            // t2.setBackgroundResource(R.drawable.table_custom_text);
                            t2.setText(jj.get(key).toString());
                            t2.setGravity(Gravity.START|Gravity.CENTER_HORIZONTAL);
                            t2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            //t2.setTextAppearance(getContext(),android.R.style.TextAppearance_DeviceDefault_Medium);
                            tableRow.addView(t2,layoutParams);
                            basic.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        }
                        TableRow tableRow1 = new TableRow(v.getContext());
                        tableRow1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        TextView ttt = new TextView(v.getContext());
                        ttt.setTextAppearance(v.getContext(),R.style.TextViewBlue);
                        ttt.setText(YEAR);
                        ttt.setGravity(Gravity.CENTER);
                        ttt.setTypeface(typeface);
                     //   ttt.setSingleLine();
                       // ttt.setMaxLines(1);
                       // ttt.setEllipsize(TextUtils.TruncateAt.END);
                       // ttt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                       // ttt.setBackgroundColor(getResources().getColor(R.color.homeColor));
                        tableRow1.addView(ttt);
                        TextView tt = new TextView(v.getContext());
                        tt.setTextAppearance(v.getContext(),R.style.TextViewBlue);
                        tt.setText(getResources().getText(R.string.sem1));
                        tt.setGravity(Gravity.CENTER);
                        tt.setTypeface(typeface);
                        //tt.setBackgroundColor(getResources().getColor(R.color.homeColor));
                        // tt.setBackgroundColor(Color.parseColor("#ffcdd2"));
                        tableRow1.addView(tt);
                        TextView tt1 = new TextView(v.getContext());
                        tt1.setTextAppearance(v.getContext(),R.style.TextViewBlue);
                        tt1.setText(getResources().getText(R.string.sem2));
                       // tt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                        tt1.setTypeface(typeface);
                       // tt1.setBackgroundColor(getResources().getColor(R.color.homeColor));
                        // tt1.setBackgroundColor(Color.parseColor("#ffcdd2"));
                        tt1.setGravity(Gravity.CENTER);
                        tableRow1.addView(tt1);
                        btech.addView(tableRow1);
                        while (it1.hasNext()) {

                            String key = it1.next();
                            TableRow tableRow = new TableRow(v.getContext());
                            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            TextView t1 = new TextView(v.getContext());
                            t1.setTypeface(typeface);
                            t1.setTextAppearance(v.getContext(),R.style.TextViewGreen);
                            t1.setGravity(Gravity.START|Gravity.CENTER_HORIZONTAL);
                           // t1.setSingleLine();
                            t1.setMaxLines(1);
                           // t1.setEllipsize(TextUtils.TruncateAt.END);
                            t1.setPadding(2,0,0,0);
                           // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                           // t1.setTextColor(Color.WHITE);
                            t1.setText(key);
                           // t1.setBackgroundColor(getResources().getColor(R.color.bagroundText));
                            //t1.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                            tableRow.addView(t1);
                            JSONObject sem = jj1.getJSONObject(key);
                            Iterator<String> semit = sem.keys();
                            while (semit.hasNext()) {

                                sems = semit.next();
                                TextView t2 = new TextView(v.getContext());
                                t2.setTextAppearance(v.getContext(),R.style.TextViewTheme2);
                                t2.setTypeface(typeface);
                                t2.setGravity(Gravity.CENTER);
                               // t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                               // t2.setBackgroundColor(Color.WHITE);
                                // t2.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                                t2.setText(sem.get(sems).toString());
                                tableRow.addView(t2);

                            }
                            btech.addView(tableRow);

                        }
                        FinalPercentage.setFinalPercentage(sems);
                        // linearProgress.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        hideProgress();
                        e.printStackTrace();
                    }

                }
                hideProgress();
            }

            @Override
            public void onFailure (@NonNull Call < String > call, @NonNull Throwable t){
                //hideProgress();
//                connectionInterface= (ConnectionInterface) getActivity();
//                try {
//                    // connectionInterface.reload();
//                }catch (NullPointerException e){
//                    Log.d(TAG,e.getMessage());
//                }
                //if(checkNet()) {
                    hidePhotoProgress();
                    showProfile();

              //  }
                //imageProgress.setVisibility(View.INVISIBLE);

            }

        });

    }
    public void showPhotoProgress()
    {
       myPhotoProgress.setVisibility(View.VISIBLE);
       profile_photo.setVisibility(View.INVISIBLE);
    }
    public void hidePhotoProgress()
    {
        myPhotoProgress.setVisibility(View.INVISIBLE);
        profile_photo.setVisibility(View.VISIBLE);
    }
    public void showProgress()
    {
       homeProgress.setVisibility(View.VISIBLE);
       linearProgress.setVisibility(View.INVISIBLE);
    }
    public void hideProgress()
    {
        homeProgress.setVisibility(View.INVISIBLE);
        linearProgress.setVisibility(View.VISIBLE);
    }

    public void profile()
    {
        showPhotoProgress();
        childRef=mStorageRef.child("Photos/"+UNAME+".JPG");
        Log.d("glideLoad",childRef.toString());
        GlideApp.with(v.getContext()).asBitmap().load(childRef).override(100).apply(new RequestOptions().transform(new RoundedCorners(40))).signature(new ObjectKey(System.currentTimeMillis())).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                //imageProgress.setVisibility(View.INVISIBLE);
                Log.d("glideLoad",e.getMessage());
                profile_photo.setImageDrawable(getResources().getDrawable(R.drawable.mydefault));
                hidePhotoProgress();
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                profile_photo.setImageBitmap(resource);
                hidePhotoProgress();
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).submit();
    }


}
