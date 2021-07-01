package com.moringaschool.consult.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.moringaschool.consult.R;
import com.moringaschool.consult.ui.Model.NotificationReceiver;

import java.util.HashMap;
import java.util.List;

import static com.moringaschool.consult.ui.Model.BaseApplication.CHANNEL_1_ID;
import static com.moringaschool.consult.ui.Model.BaseApplication.CHANNEL_2_ID;

public class PagerActivty extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText mTitle, mMessage;
    //    private MediaSessionCompat mediaSession;
    Button upload;
    ImageView alertImage;
    public static final int CAMERA_CODE = 200;
    public static final int GALLERY_CODE = 100;
    Uri imageUri = null;

    FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

//        mediaSession = new MediaSessionCompat(this, "tag");
        notificationManagerCompat = NotificationManagerCompat.from(this);
        mTitle = findViewById(R.id.alert_title);
        mMessage = findViewById(R.id.alert_message);
        alertImage = findViewById(R.id.alert_image);
        upload = findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    public void sendOnChannel1(View view) {
        String title = mTitle.getText().toString();
        String message = mMessage.getText().toString();

        if (title.equals("")) {
            mTitle.setError("Field cannot be empty");
        } else if (message.equals("")) {
            mMessage.setError("Field cannot be empty");
        } else {
            Intent activityIntent = new Intent(this, Dashboard.class);
            activityIntent.putExtra("urgentAlertTitle", title);
            activityIntent.putExtra("urgentAlertMessage", message);
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    0, activityIntent, 0);

            Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.drawable.ic_alert);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_announcement)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(artwork)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle())
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

    public void sendOnChannel2(View view) {
        String title = mTitle.getText().toString();
        String message = mMessage.getText().toString();

        if (title.equals("")) {
            mTitle.setError("Field cannot be empty");
        } else if (message.equals("")) {
            mMessage.setError("Field cannot be empty");
        } else {
            Intent activityIntent = new Intent(this, Dashboard.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activityIntent.putExtra("alertTitle", title);
            activityIntent.putExtra("alertMessage", message);
            PendingIntent contentIntent = PendingIntent.getActivity(this,
                    0, activityIntent, 0);

            Bitmap artwork = BitmapFactory.decodeResource(getResources(), R.id.alert_image);

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

    private void changeImage() {

        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PagerActivty.this);
        builder.setTitle("Choose an Option");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {


                if (i == 0) {

                    openCamera();

                }

                if (i == 1) {

                    openGallery();
                }
            }
        });

        builder.create().show();


    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); // all kinds of images
        startActivityForResult(intent, GALLERY_CODE);
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        values.put(MediaStore.Images.Media.TITLE, "Temp Desc");
        imageUri = PagerActivty.this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_CODE);


    }


    private void Permissions() {


        Dexter.withContext(PagerActivty.this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }

        }).check();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {

            imageUri = data.getData();
            alertImage.setVisibility(View.VISIBLE);
            alertImage.setImageURI(imageUri);

            String filepath = "Photos/" + "userprofile_";

            StorageReference reference = FirebaseStorage.getInstance().getReference(filepath);
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String imageURL = uri.toString();

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("imageURL", imageURL);


                        }
                    });


                }
            });


        }


        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {

            Uri uri = imageUri;
            alertImage.setVisibility(View.VISIBLE);
            alertImage.setImageURI(uri);

            String filepath = "Photos/" + "userprofile_" ;

            StorageReference reference = FirebaseStorage.getInstance().getReference(filepath);
            reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                    task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String imageURL = uri.toString();


                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("imageURL", imageURL);



                        }
                    });


                }
            });

        }
    }
}