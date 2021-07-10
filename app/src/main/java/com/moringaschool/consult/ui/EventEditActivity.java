package com.moringaschool.consult.ui;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moringaschool.consult.R;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;


public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameET;
    private TextView eventDateTV, eventTimeTV;
    private LocalTime time;
    FirebaseDatabase firebaseDatabase;




    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();

        eventDateTV.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTV.setText("Time: " + CalendarUtils.formattedTime(time));

        String name = eventNameET.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Event");
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateTV = findViewById(R.id.eventDateTV);
        eventTimeTV = findViewById(R.id.eventTimeTV);
    }

    public void saveEventAction(View view) {
        String name = eventNameET.getText().toString();

        Event newEvent = new Event(name, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);

        finish();
        if (TextUtils.isEmpty(name))  {
            // if the text fields are empty
            // then show the below message.
            Toast.makeText(EventEditActivity.this, "Please add event", Toast.LENGTH_SHORT).show();
        } else {
            // else call the method to add
            // data to our database.
            addDatatoFirebase(name, CalendarUtils.selectedDate, time);

        }


    }

    private void addDatatoFirebase(String name, LocalDate selectedDate, LocalTime time) {
        Event newEvent = new Event(name, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);

        newEvent.setEventName(name);
        newEvent.setEventDate(selectedDate);
        newEvent.setEventTime(time);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(newEvent);

                // after adding this data we are showing toast message.
                Toast.makeText(EventEditActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(EventEditActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }



}