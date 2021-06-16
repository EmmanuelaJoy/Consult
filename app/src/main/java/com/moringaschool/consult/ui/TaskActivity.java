package com.moringaschool.consult.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.moringaschool.consult.R;

import java.util.Calendar;

public class TaskActivity extends AppCompatActivity {
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_task);
//    }
    public void AddCalendarEvent(View view) {
        Calendar calendarEvent = Calendar.getInstance();
        Intent i = new Intent(Intent.ACTION_EDIT);
        i.setType("vnd.android.cursor.item/event");
        i.putExtra("beginTime", calendarEvent.getTimeInMillis());
        i.putExtra("allDay", true);
        i.putExtra("rule", "FREQ=YEARLY");
        i.putExtra("endTime", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
        i.putExtra("title", "Calendar Event");
        startActivity(i);
    }
    // Define the variable of CalendarView type
    // and TextView type;
    CalendarView calender;
    TextView date_view;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // By ID we can use each component
        // which id is assign in xml file
        // use findViewById() to get the
        // CalendarView and TextView
        calender = findViewById(R.id.calender);
        date_view = findViewById(R.id.date_view);

        // Add Listener in calendar
        // In this Listener have one method
// and in this method we will
// get the value of DAYS, MONTH, YEARS
        calender
                .setOnDateChangeListener(
                        (view, year, month, dayOfMonth) -> {

                            // Store the value of date with
                            // format in String type Variable
                            // Add 1 in month because month
                            // index is start with 0
                            String Date
                                    = dayOfMonth + "-"
                                    + (month + 1) + "-" + year;

                            // set this date in TextView for Display
                            date_view.setText(Date);
                        });
    }
}

