package com.example.udaysaikumar.clgattendance.Fragments;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import org.json.JSONObject;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSem_11 extends Fragment {
    RetroGet retroGet;
    private String API_KEY;
    private TableLayout tableLayout, headTable;
    private TableLayout tableLayout1;
    private JSONObject jj;
    private List<String> st = new ArrayList<>();
    private TextView subject, internals, externals, total;
    private String TAG="FragmentSem_11_Log";
    private DecimalFormat df;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_fragment_sem_11, container, false);
        tableLayout = v.findViewById(R.id.tables11);
        tableLayout1 = v.findViewById(R.id.tablesfinal);
        subject = v.findViewById(R.id.subject);
        internals = v.findViewById(R.id.internals);
        externals = v.findViewById(R.id.externals);
        total = v.findViewById(R.id.total);
        df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        API_KEY = getResources().getString(R.string.APIKEY);
      /*  String UNAME = LoginStaticData.getRegno();
        if (Integer.parseInt(UNAME.subSequence(0, 2).toString()) >= 15) {

            subject.setText(R.string.subject);
            internals.setText(R.string.internals);
            externals.setText(R.string.externals);
            total.setText(R.string.totalmarks);
        } else {

            subject.setText(R.string.subject);
            internals.setText(R.string.internals1);
            externals.setText(R.string.externals1);

            total.setText(R.string.totalmarks1);

            //tableLayout.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }*/
        String str = getArguments() != null ? getArguments().getString("data") : null;
        String semister=getArguments()!=null?getArguments().getString("semister"):null;
        try{
            if (str.contains("points")) {
                subject.setText(R.string.subject);
                internals.setText(R.string.internals1);
                externals.setText(R.string.externals1);
                total.setText(R.string.totalmarks1);
            }else
            {
                subject.setText(R.string.subject);
                internals.setText(R.string.internals);
                externals.setText(R.string.externals);
                total.setText(R.string.totalmarks);
            }
        }catch(Exception e)
        {

        }
     //   Log.d(TAG,str);
        try {
            JSONObject jj=new JSONObject(str);
            Iterator<String> headers = jj.keys();

            while (headers.hasNext()) {
                String head = headers.next();
                st.add(head);
            }

            JSONObject jj1 = jj.getJSONObject(st.get(0));
            JSONObject jj2 = jj.getJSONObject(st.get(1));
            JSONObject jj3 = jj.getJSONObject(st.get(2));
            JSONObject jj4 = jj.getJSONObject(st.get(3));
            Iterator<String> it = jj1.keys();
            Iterator<String> it1 = jj2.keys();
            Iterator<String> it2 = jj3.keys();
            Iterator<String> it4 = jj4.keys();
            while (it.hasNext()) {
                String key = it.next();
                String key1 = it1.next();
                String key2 = it2.next();
                Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);

                TableRow tr = new TableRow(v.getContext());
                TableLayout.LayoutParams layoutParams=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,1);
                tr.setLayoutParams(layoutParams);

                TextView t = new TextView(v.getContext());
                t.setTextAppearance(v.getContext(),R.style.TextViewGreen);
                t.setText(key);
                t.setTypeface(typeface);
               // t.setTextColor(Color.BLACK);
                t.setGravity(Gravity.START);
                t.setMaxLines(1);
               // t.setSingleLine();
              //  t.setEllipsize(TextUtils.TruncateAt.END);
              //  t.setBackgroundColor(Color.WHITE);
                //t.setBackgroundResource(R.drawable.table_custom_text);
              //  t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tr.addView(t);
                TextView t1 = new TextView(v.getContext());
                t1.setTextAppearance(v.getContext(),R.style.TextViewTheme);
                //  t1.setBackgroundResource(R.drawable.table_custom_text);
                t1.setText(jj1.get(key).toString());
                t1.setTypeface(typeface);
              //  t1.setBackgroundColor(Color.WHITE);
                t1.setGravity(Gravity.CENTER);
               // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tr.addView(t1);
                TextView t2 = new TextView(v.getContext());
                t2.setTextAppearance(v.getContext(),R.style.TextViewTheme);
                t2.setText(jj2.get(key1).toString());
                t2.setGravity(Gravity.CENTER);
              //  t2.setBackgroundColor(Color.WHITE);
                //  t2.setBackgroundResource(R.drawable.table_custom_text);
                t2.setTypeface(typeface);
              //  t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tr.addView(t2);
                TextView t3 = new TextView(v.getContext());
                t3.setTextAppearance(v.getContext(),R.style.TextViewTheme);
                t3.setText(jj3.get(key2).toString());
                t3.setTypeface(typeface);
             //   t3.setBackgroundColor(Color.WHITE);
                t3.setGravity(Gravity.CENTER);
                //  t3.setBackgroundResource(R.drawable.table_custom_text);
                t3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tr.addView(t3);
                tableLayout.addView(tr, layoutParams);
            }
            while (it4.hasNext()) {
                String key = it4.next();
                Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
                TableRow tr = new TableRow(v.getContext());
                TableRow.LayoutParams layoutParams=new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,0,0,1);
                //    tr.setLayoutParams(layoutParams);

                TextView t = new TextView(v.getContext());
                t.setTextAppearance(v.getContext(),R.style.TextViewGreen);
                if(key.trim().equals("%"))
                {
                    t.setText(semister+" "+key);
                }else
                t.setText(key);
                t.setGravity(Gravity.START);
              //  t.setTextColor(Color.BLACK);
              //  t.setBackgroundColor(Color.WHITE);
                // t.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                t.setTypeface(typeface);
               // t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tr.addView(t,layoutParams);
                TextView t1 = new TextView(v.getContext());
                t1.setTextAppearance(v.getContext(),R.style.TextViewTheme);
                //t1.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                String rounding=jj4.get(key).toString();
                Double myDouvle=Double.valueOf(rounding);
                String newValue= df.format(myDouvle);
                t1.setText(newValue);
                t1.setGravity(Gravity.CENTER);
              //  t1.setBackgroundColor(Color.WHITE);
                t1.setTypeface(typeface);
                //t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                tr.addView(t1,layoutParams);
                tableLayout1.addView(tr, layoutParams);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return v;
    }
}