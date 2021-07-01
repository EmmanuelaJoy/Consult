package com.moringaschool.consult.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.EditText;

import com.moringaschool.consult.R;
import com.moringaschool.consult.ui.Model.NotificationReceiver;

import static com.moringaschool.consult.ui.Model.BaseApplication.CHANNEL_1_ID;
import static com.moringaschool.consult.ui.Model.BaseApplication.CHANNEL_2_ID;

public class PagerActivty extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText mTitle,mMessage;
//    private MediaSessionCompat mediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

//        mediaSession = new MediaSessionCompat(this, "tag");
        notificationManagerCompat = NotificationManagerCompat.from(this);
        mTitle = findViewById(R.id.alert_title);
        mMessage = findViewById(R.id.alert_message);
    }

    public void sendOnChannel1(View view){
        String title = mTitle.getText().toString();
        String message = mMessage.getText().toString();

        if(title.equals("")){
            mTitle.setError("Field cannot be empty");
        } else if(message.equals("")){
            mMessage.setError("Field cannot be empty");
        } else {
            Intent activityIntent = new Intent(this, Dashboard.class);
            activityIntent.putExtra("urgentAlertTitle", title);
            activityIntent.putExtra("urgentAlertMessage", message);
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    0, activityIntent, 0);

            Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.send);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_announcement)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(artwork)
                    .addAction(R.drawable.ic_tag_faces, "ATTENDING", null)
                    .addAction(R.drawable.ic_tag_faces, "CAN'T ATTEND", null)
                    .addAction(R.drawable.ic_tag_faces, "ON MY WAY", null)
                    .addAction(R.drawable.ic_tag_faces, "I'LL BE LATE", null)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0, 1))
                    .setSubText("Sub text")
                    .setColor(Color.RED)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManagerCompat.notify(1, notification);
        }
    }

    public void sendOnChannel2(View view){
        String title = mTitle.getText().toString();
        String message = mMessage.getText().toString();

        if(title.equals("")){
            mTitle.setError("Field cannot be empty");
        } else if(message.equals("")){
            mMessage.setError("Field cannot be empty");
        } else {
            Intent activityIntent = new Intent(this, Dashboard.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activityIntent.putExtra("alertTitle", title);
            activityIntent.putExtra("alertMessage", message);
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    0, activityIntent, 0);

            Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.scan2);

            Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
            broadcastIntent.putExtra("toastMessage", title);
            PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                    0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                    .setSmallIcon(R.drawable.ic_chat)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(artwork)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setColor(Color.BLUE)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .addAction(R.drawable.ic_chat, "RECEIVED", actionIntent)
                    .build();

            notificationManagerCompat.notify(2, notification);
        }
    }
}