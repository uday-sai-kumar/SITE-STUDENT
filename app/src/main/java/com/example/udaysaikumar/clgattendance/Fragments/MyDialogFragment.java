package com.example.udaysaikumar.clgattendance.Fragments;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.res.ResourcesCompat;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.udaysaikumar.clgattendance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import static android.content.Context.MODE_PRIVATE;


public class MyDialogFragment extends DialogFragment{
    TableLayout tableAttendance;
    String UNAME;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dialog, container, false);
        tableAttendance=v.findViewById(R.id.mydialogtable);
        SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MyLogin", MODE_PRIVATE);
        UNAME=sharedPreferences.getString("username","").toUpperCase();
        Log.d("jsonperiod",UNAME);
        String json= getArguments() != null ? getArguments().getString("dialog") : null;
        if(json!=null && !json.equals("[  ]") ){
        try {
            Log.d("jsonperiods",json);

            Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
            JSONArray jsonArray = new JSONArray(json);
            Log.d("jsonsize", String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.d("jsonperiod",jsonObject.toString());
                Iterator<String> list = jsonObject.keys();
                Log.d("jsonperiod",list.toString());
                String period=null;
                while (list.hasNext()) {
                    Log.d("jsonperiod","wow");
                   // Log.d("jsonperiod",list.next());
                    String k=list.next();
                    if (k.startsWith("period")) {
                        period=k;
                        break;
                    }
                }
                Log.d("jsonperiod",period);
                JSONObject jsonObject1 = jsonObject.getJSONObject(period);
                Iterator<String> iterator=jsonObject1.keys();
              //  LinkedList<String> linkedList=new LinkedList<>();
                boolean checking=false;
                while (iterator.hasNext())
                {
                    if(iterator.next().equals(UNAME))
                    {
                        checking=true;
                        break;
                    }
                }
                TableRow tableRow = new TableRow(v.getContext());
                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView t = new TextView(v.getContext());
                //     Log.d("jsonperiod",period);
                t.setTypeface(typeface);
                t.setPadding(2,2,2,2);
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                t.setGravity(Gravity.CENTER);
                tableRow.addView(t);
                ImageView imageView=new ImageView(v.getContext());
                imageView.setPadding(2,2,2,2);
                if(checking)
                {

                    t.setText(period);
                    imageView.setImageResource(R.drawable.absent);

                }
                else{
                    t.setText(period);
                    imageView.setImageResource(R.drawable.present);

                }
                tableRow.addView(imageView);
                tableAttendance.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


            }
           /* System.out.println("hihihihi"+jsonObject1.toString());
            Iterator<String> iterator=jsonObject1.keys();


            while (iterator.hasNext()) {
                String key=iterator.next();

                                TableRow tableRow = new TableRow(v.getContext());
                                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                TextView t = new TextView(v.getContext());
                                t.setText(key);
                                t.setTypeface(typeface);
                                t.setPadding(2,2,2,2);
                                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                               // t.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                                t.setGravity(Gravity.CENTER);
                                tableRow.addView(t);
                                ImageView imageView=new ImageView(v.getContext());
                                imageView.setPadding(2,2,2,2);
                                        if(jsonObject1.get(key).toString().equals("P"))
                                        {
                                            imageView.setImageResource(R.drawable.present);
                                            }
                                            else imageView.setImageResource(R.drawable.absent);
                                tableRow.addView(imageView);
                                tableAttendance.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }*/

        } catch (JSONException e) {
            Log.d("jsonperiod",e.toString());
            Log.d("jsonperiod","hellofolkes");
            TextView t=v.findViewById(R.id.mytext);
            t.setPadding(2,2,2,2);
            t.setText(getResources().getText(R.string.sorry));
            t.setTextColor(getResources().getColor(R.color.bad));
            Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
            t.setTypeface(typeface);
            t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            t.setGravity(Gravity.CENTER);

        }catch (Exception e)
        {
            Log.d("jsonperiod",e.toString());
        }}
        else {
            Log.d("jsonperiod","hellofolkes");
            TextView t=v.findViewById(R.id.mytext);
            t.setPadding(2,2,2,2);
            t.setText(getResources().getText(R.string.sorry));
            t.setTextColor(getResources().getColor(R.color.bad));
            Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
            t.setTypeface(typeface);
            t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            t.setGravity(Gravity.CENTER);
        }
        return v;
    }


}
