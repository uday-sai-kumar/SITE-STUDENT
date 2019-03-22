package com.example.udaysaikumar.clgattendance.Others;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import com.example.udaysaikumar.clgattendance.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class DayDecorator implements DayViewDecorator{
    Drawable drawable;
   Context c;
   public String semi;
  private   CalendarDay calendarDay;
    public DayDecorator(Context c,CalendarDay calendarDay,int count) {
        switch (count){
            case -1:
                drawable=ContextCompat.getDrawable(c,R.drawable.mcv_round_cirlce_current);
                break;
            case -2:
                drawable=ContextCompat.getDrawable(c,R.drawable.mcv_round_cirlce_sunday);
                break;
            case -3:
                drawable=ContextCompat.getDrawable(c,R.drawable.mcv_round_cirlce_all);
                 break;
            case 7:
                drawable=ContextCompat.getDrawable(c,R.drawable.mcv_round_cirlce_full);
                 break;
            default:
                drawable=ContextCompat.getDrawable(c,R.drawable.mcv_round_cirlce_semi);
                 break;

        }
        this.c=c;
        this.calendarDay=calendarDay;
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(calendarDay);
    }

    @Override
    public void decorate(DayViewFacade view) {
        //view.setSelectionDrawable(drawable);

      //  view.addSpan(drawable);
        view.setBackgroundDrawable(drawable);
       //view.addSpan(new ForegroundColorSpan(Color.WHITE));
      //  view.addSpan(new DotSpan(5,Color.GREEN));
    }
}
