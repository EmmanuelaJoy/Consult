package com.moringaschool.consult.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.moringaschool.consult.R;

import static com.moringaschool.consult.ui.Model.BaseApplication.CHANNEL_1_ID;
import static com.moringaschool.consult.ui.Model.BaseApplication.CHANNEL_2_ID;

public class PagerActivty extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText mTitle,mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        mTitle = findViewById(R.id.alert_title);
        mMessage = findViewById(R.id.alert_message);
    }

    public void sendOnChannel1(View view){
        String title = mTitle.getText().toString();
        String message = mMessage.getText().toString();

        Intent activityIntent = new Intent(this, Dashboard.class);
        activityIntent.putExtra("urgentAlertTitle", title);
        activityIntent.putExtra("urgentAlertMessage", message);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_announcement)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setContentIntent(contentIntent)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    public void sendOnChannel2(View view){
        String title = mTitle.getText().toString();
        String message = mMessage.getText().toString();

        Intent activityIntent = new Intent(this, Dashboard.class);
        activityIntent.putExtra("alertTitle", title);
        activityIntent.putExtra("alertMessage", message);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_chat)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(contentIntent)
                .build();

        notificationManagerCompat.notify(2, notification);
    }
}