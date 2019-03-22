package com.example.udaysaikumar.clgattendance.Fragments;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.Others.DayDecorator;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.TimeStampClass;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.Context.MODE_PRIVATE;
/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Attendance extends Fragment {

  private   RetroGet retroGet;
  private   String API_KEY;
  private  String ATTENDANCE_COLLECTION;
  private MaterialCalendarView mCV;
   private String timestamp;
   private View v;
   private String UNAME;
   private TextView myText;
   private int maxX,imageId;
    private  SeekBar seekBar;
   private Float f;
    private String TAG="Fragment_Attendance_Log";
    private Calendar myCalendar;
private  TableLayout tableAttendance;
private NestedScrollView nestedScrollView;
private Map<String,LinkedList<MyAttendance>> myMap=new LinkedHashMap<>();
private List<String> monthList=new LinkedList<>();
    private   LinearLayout linearLayout;
  private   ProgressBar progressBar;
  private static int todayMonth;
  private static int todayYear;
  private static int todayDate;
  private String month;
  private String year;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_fragment__attendance, container, false);
       // Log.d(TAG+"myAttendance","reached 1");
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
seekBar=v.findViewById(R.id.seekBar);
myText=v.findViewById(R.id.myView);
nestedScrollView=v.findViewById(R.id.fragattend);
        linearLayout=v.findViewById(R.id.attendaceLayout);
        progressBar=v.findViewById(R.id.attendanceProgress);
        mCV=v.findViewById(R.id.calenderView);
tableAttendance=v.findViewById(R.id.tableAttendance);
        myCalendar=Calendar.getInstance();
        API_KEY=getResources().getString(R.string.APIKEY);
        ATTENDANCE_COLLECTION=getResources().getString(R.string.attendanceCollection);
        mCV.canScrollVertically(1);
        mCV.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
seekBar.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
});
mCV.setSelectionColor(getResources().getColor(R.color.colorPrimary));
        Point point=new Point();
        try {

            getActivity().getWindowManager().getDefaultDisplay().getSize(point);
        }
        catch (Exception e){}
maxX=point.x;
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        SharedPreferences sharedPreferencess=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        UNAME=sharedPreferencess.getString("username","");
        mCV.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String month=(date.getMonth()+1)+"/"+date.getYear();
               // Log.d(TAG+"myAttendance","reached 10");
                //    Log.d(TAG,myMap.toString());
                if(date.getYear()<todayYear) {
                  //  Log.d(TAG+"Reaching","Reaching");
                    if (monthList.contains(month)) {
                        // Log.d(TAG+"_ContainsMonth",month);
                        tableAttendance.removeAllViewsInLayout();
                        displayTable();
                    } else {
                        tableAttendance.removeAllViewsInLayout();
                        showProgress();
                        // Log.d(TAG+"_ContainsMonth_NOT",month);
                        callAttendance(month);
                    }
                }else if(date.getYear()==todayYear){
                    if(date.getMonth()+1<=todayMonth) {
                        if (monthList.contains(month)) {
                            // Log.d(TAG+"_ContainsMonth",month);
                            tableAttendance.removeAllViewsInLayout();
                            displayTable();
                        } else {
                            tableAttendance.removeAllViewsInLayout();
                          //  showProgress();
                            // Log.d(TAG+"_ContainsMonth_NOT",month);
                            callAttendance(month);
                        }
                    }
                }else{
                    tableAttendance.removeAllViewsInLayout();
                }
               // Log.d(TAG,String.valueOf(date.getDay())+"  "+String.valueOf(date.getMonth())+" "+String.valueOf(date.getYear()));
            }
        });
        loadPercentage();
        loadCalendar();
        return v;
    }


    public void loadCalendar()
    {

        retroGet= TimeStampClass.getTimestamp().create(RetroGet.class);
        Call<String> stringCall=retroGet.getTimeStamp();
        showProgress();
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                String json=response.body();
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    JSONArray jsonArray=jsonObject.getJSONArray("zones");
                    JSONObject jsonObject1=jsonArray.getJSONObject(0);
                    timestamp=jsonObject1.getString("timestamp");
                    Timestamp time=new Timestamp((Long.parseLong(timestamp)-19800)*1000L);
                    Date date=new Date(time.getTime());
                    myCalendar.setTime(date);
                    //  todayDate= myCalendar.get(Calendar.DAY_OF_MONTH);
                    //  Log.d(TAG,String.valueOf(myCalendar.get(Calendar.MONTH))+" "+String.valueOf(myCalendar.get(Calendar.DATE)));
                    mCV.setDateSelected(date,true);

                    mCV.addDecorator(new DayDecorator(v.getContext(),mCV.getSelectedDate(),-1));
                    todayMonth=myCalendar.get(Calendar.MONTH)+1;
                    todayYear=myCalendar.get(Calendar.YEAR);
                    todayDate=myCalendar.get(Calendar.DAY_OF_MONTH);
                    month=String.valueOf(todayMonth);
                     year=String.valueOf(todayYear);
                    String current=month+"/"+year;
                    // Log.d(TAG+"Current",current);
                    callAttendance(month+"/"+year);
                    // Log.d(TAG+"myAttendance","reached 8");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                hideProgress();
              //  if(checkNet()) {
                    loadCalendar();
              //  }
                // Log.d(TAG+"myAttendance","reached 9"+t.toString());
               // ConnectionInterface connectionInterface= (ConnectionInterface) getActivity();
               // connectionInterface.reload();
                // Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();

            }
        });

    }
    public void loadPercentage()
    {
        showProgress();
        String qq="{\"regno\":{$eq:\""+UNAME+"\"}}";
        Log.d(TAG+"myQuery",qq);
        Call<String> dataAttendance = retroGet.getPercentage("PERCENTAGE",API_KEY,qq);
        // Log.d(TAG+"myAttendance","reached 1");
        dataAttendance.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                assert response.body() != null;
                String json=response.body();
                // Log.d(TAG+"myAttendance","reached 2");
                // Log.d(TAG+"myAttendance",response.body());
                try {
                    if (!json.isEmpty()) {
                        JSONArray jsonArray = new JSONArray(json);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        Iterator<String> iterator = jsonObject.keys();
                        String st = null;
                        while (iterator.hasNext()) {
                            st = iterator.next();
                        }
                        String jsonObject1 = jsonObject.get(st).toString();

                        f = Float.parseFloat(jsonObject1);
                        if (f >= 75) {
                            imageId = R.drawable.high_logo;

                        } else if (f > 65) {
                            imageId = R.drawable.medium_logo;

                        } else {
                            imageId = R.drawable.low_logo;

                        }
                        myText.setText(f.toString());
                        seekBar.setProgress(Math.round(f));
                        final ViewTreeObserver viewTreeObserver = seekBar.getViewTreeObserver();

                        if (viewTreeObserver.isAlive()) {
                            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                @Override
                                public void onGlobalLayout() {
                                    int val = (seekBar.getProgress() * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                                    int textViewX = val - (myText.getWidth() / 2);
                                    int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX /*your margin*/;
                                    myText.setX(finalX < 0 ? 0/*your margin*/ : finalX);
                                    myText.setTextSize(25);
                                    seekBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                }
                            });

                        }
                        seekBar.setThumb(getResources().getDrawable(imageId));
                        // Log.d(TAG+"myAttendance","reached 3");
                    }
                }
                    catch (Exception e) {
                    hideProgress();
                    loadPercentage();
                        e.printStackTrace();
                    }
                }


            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                hideProgress();
              //  linearLayout.setVisibility(View.VISIBLE);
               // if(checkNet()) {
                    loadPercentage();
                //}
//                ConnectionInterface connectionInterface= (ConnectionInterface) getActivity();
//                connectionInterface.reload();
            }
        });
    }
    public int position(int ss)
    {
      //  Log.d(TAG+"myAttendance","reached 11");
        int val = (ss * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        int textViewX = val - (myText.getWidth() / 2);
        int finalX = myText.getWidth() + textViewX > maxX ? (maxX - myText.getWidth()) : textViewX;
        return finalX;
    }
    public void callAttendance(final String adate){
        showProgress();
       // Log.d(TAG+"myAttendance","reached 12");
        // String customReg="15A81A05J1";
       final String q= "{\"Rollno\":\""+UNAME+"\",\"MONTHYEAR\":\""+adate+"\"}";
     //   System.out.println("wowhellojson"+q);
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataAttendance = retroGet.getAttendance(ATTENDANCE_COLLECTION,API_KEY,q);
        dataAttendance.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
              //  System.out.println(TAG+response.body());
               // Log.d(TAG+"myAttendance",response.body());
               // Log.d(TAG+"myAttendance","reached 13");
                if(response.body()!=null)
                {
                    try {
                        if (!Objects.requireNonNull(response.body()).isEmpty()) {
                            String json = response.body();
                            System.out.println(TAG + json);
                            monthList.add(adate);
                           // Log.d(TAG+"parse","parsingjson");
                            parseJson(json,adate);
                           // Log.d(TAG+"parse","colors");
                            applyColors(adate);
                          //  Log.d(TAG+"parse","changecolors");
                            changeColors();
                         //   Log.d(TAG+"parse","diplaytable");
                            displayTable();
                        }
                    }catch (Exception e){
                        hideProgress();
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                // Log.d(TAG+"myAttendance","reached 9"+t.toString());
                try {
                    hideProgress();
                    Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
                    //if(checkNet()) {
                       // callAttendance(month + "/" + year);
                  //  }
//                    ConnectionInterface connectionInterface = (ConnectionInterface) getActivity();
//                    connectionInterface.reload();
                }catch (Exception e)
                {
                  hideProgress();
                }
               // Log.d(TAG+"myAttendance",t.toString());
              //  Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
    }
public void hideProgress()
{
    progressBar.setVisibility(View.INVISIBLE);
    linearLayout.setVisibility(View.VISIBLE);
}
public void displayTable()
    {
        hideProgress();
        mCV.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String day= String.valueOf(date.getDay());
                String month=String.valueOf(date.getMonth()+1);
                String year=String.valueOf(date.getYear());
                showTable(day+"/"+month+"/"+year);
            }
        });
    }
    public void changeColors(){
        Set<String> iterator=myMap.keySet();
        Iterator<String> iterator1=iterator.iterator();
        while (iterator1.hasNext())
        {
            String myDate1=iterator1.next();
            DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
            Date mydate;
            try {
                mydate = dateFormat.parse(myDate1);
                mCV.setDateSelected(mydate,true);
                LinkedList<MyAttendance> list=myMap.get(myDate1);
                mCV.addDecorator(new DayDecorator(v.getContext(),mCV.getSelectedDate(),list.size()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void showTable(String date)
    {
        tableAttendance.removeAllViewsInLayout();
        if(myMap.containsKey(date))
        {
            try {
                Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
                LinkedList<MyAttendance> linkedList = myMap.get(date);
                TableRow tr1 = new TableRow(v.getContext());
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, 0, 0, 1);
                TextView t2 = new TextView(v.getContext());
                t2.setTextAppearance(v.getContext(),R.style.TextViewBlue);
                t2.setText("Subject");
                //t2.setSingleLine();
                t2.setMaxLines(1);
                //t2.setEllipsize(TextUtils.TruncateAt.END);
                t2.setPadding(5, 0, 0, 0);
                t2.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
               // t2.setBackgroundColor(Color.WHITE);
                t2.setTypeface(typeface);
                t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tr1.addView(t2, layoutParams);
                TextView t3 = new TextView(v.getContext());
                t3.setTextAppearance(v.getContext(),R.style.TextViewBlue);
                t3.setText("Period");
             //   t3.setSingleLine();
                t3.setMaxLines(1);
             //   t3.setEllipsize(TextUtils.TruncateAt.END);
                t3.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
              //  t3.setTextColor(getResources().getColor(R.color.colorPrimary));
                //t3.setBackgroundColor(Color.WHITE);
                t3.setTypeface(typeface);
              //  t3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tr1.addView(t3, layoutParams);
                tableAttendance.addView(tr1, layoutParams);
                for (int i = 0; i < linkedList.size(); i++) {
                    TableRow tr = new TableRow(v.getContext());
                    layoutParams.setMargins(0, 0, 0, 1);
                    TextView t = new TextView(v.getContext());
                    t.setTextAppearance(v.getContext(),R.style.TextViewGreen);
                    String newString=linkedList.get(i).getSubjectName();
                    t.setText(newString);
                    t.setPadding(5, 0, 0, 0);
                    t.setGravity(Gravity.START);
                   // t.setTextColor(Color.BLACK);
                   // t.setBackgroundColor(Color.WHITE);
                    t.setTypeface(typeface);
                    //t.setSingleLine();
                    t.setMaxLines(1);
                    //t.setEllipsize(TextUtils.TruncateAt.END);
                    //t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    tr.addView(t, layoutParams);
                    TextView t1 = new TextView(v.getContext());
                    t1.setTextAppearance(v.getContext(),R.style.TextViewTheme);
                    t1.setText(linkedList.get(i).getPeriod());
                    t1.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
                 //   t1.setTextColor(Color.BLACK);
                  //  t1.setBackgroundColor(Color.WHITE);
                    t1.setSingleLine();
                   // t1.setMaxLines(1);
                   // t1.setEllipsize(TextUtils.TruncateAt.END);
                    t1.setTypeface(typeface);
                   // t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    tr.addView(t1, layoutParams);
                    tableAttendance.addView(tr, layoutParams);
                }
            }catch (Exception e)
            {
                hideProgress();
                //Log.d(TAG,e.getMessage());
            }
            hideProgress();
            nestedScrollView.fullScroll(View.FOCUS_DOWN);

        }
    }
public void parseJson(String json,String date)
{
    try
    {
        JSONArray jsonArray=new JSONArray(json);
     //   Log.d(TAG+"wow",String.valueOf(jsonArray.length()));
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String myDate=jsonObject.getString("Date");
            String[] myArray=myDate.split("/");
            String myDate1=myArray[1]+"/"+date;
           // Log.d(TAG,myDate1);
if(myMap.containsKey(myDate1)){
   // Log.d(TAG+"_Contains",myDate1);
    String subjectName=jsonObject.getString("SubjectName");
    String period=jsonObject.getString("PeridNo");
    myMap.get(myDate1).add(new MyAttendance(subjectName,period));
   // Log.d(TAG+"_Contains",myMap.toString());

}else{
   // Log.d(TAG+"_Contains",myDate1);
    String subjectName=jsonObject.getString("SubjectName");
    String period=jsonObject.getString("PeridNo");
    LinkedList<MyAttendance> linkedList=new LinkedList<>();
 linkedList.add(new MyAttendance(subjectName,period));
 myMap.put(myDate1,linkedList);
  //  Log.d(TAG+"_Contains",myMap.toString());
}
        }
    }catch (Exception e)
    {
//Log.d(TAG,e.getMessage());
    }
}

public void applyColors(String myDate)
{
    DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
    Date mydate;
    int limit=0;
    try {
        String[] wholeDate=myDate.split("/");
        String date="1/"+myDate;
        mydate = dateFormat.parse(date);
        // mCV.setDateSelected(mydate,true);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(mydate);
        //int todayYear=Integer.valueOf(todayDate[])
        int wholeMonth=Integer.valueOf(wholeDate[0]);
        int wholeYear=Integer.valueOf(wholeDate[1]);
        if(wholeYear<todayYear)
        {
          //  Log.d(TAG+"todayDate","reaching1");
            limit=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        else if(wholeYear>todayYear)
        {
            //Log.d(TAG+"todayDate","reaching2");
            limit=-1;
        }else
        {
            if(wholeMonth<todayMonth){
             //   Log.d(TAG+"todayDate","reaching3");
                limit=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            }else if(wholeMonth==todayMonth){
               // Log.d(TAG+"todayDate","reaching4");
                limit=todayDate;
            }else
            {         //   Log.d(TAG+"todayDate","reaching5");

                limit=-1;
            }
        }
     //   Log.d(TAG+"todayDate",String.valueOf(todayMonth+"-"+todayYear));

       // Log.d(TAG+"todayDate",String.valueOf(limit));

        for(int i=1;i<=limit;i++)
        {

            Date date1;
            Calendar calendar1=Calendar.getInstance();
            date1=dateFormat.parse(String.valueOf(i)+"/"+myDate);
           // Log.d(TAG+"dates",String.valueOf(i)+"/"+myDate);
           // Log.d(TAG+"dates",date1.toString());
            calendar1.setTime(date1);
            mCV.setSelectedDate(calendar1);
           // Log.d(TAG+"sundayCheck",String.valueOf(calendar1.get(Calendar.DAY_OF_WEEK))+" gap "+Calendar.SUNDAY);
            if(calendar1.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
            {
               // Log.d(TAG+"sunday",date1.toString());
                mCV.addDecorator(new DayDecorator(v.getContext(),mCV.getSelectedDate(),-2));
            }
            else
            {
                mCV.addDecorator(new DayDecorator(v.getContext(),mCV.getSelectedDate(),-3));

            }
        }
       // LinkedList<MyAttendance> list=myMap.get(date);
       // mCV.addDecorator(new DayDecorator(v.getContext(),mCV.getSelectedDate(),-3));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
