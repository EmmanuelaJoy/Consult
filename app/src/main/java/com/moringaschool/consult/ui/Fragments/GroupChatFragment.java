package com.moringaschool.consult.ui.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moringaschool.consult.R;

import java.util.ArrayList;

public class GroupChatFragment extends AppCompatDialogFragment {

    Button sendBtn;
    EditText msgEt, senderEt;
    ListView listView;
    ArrayAdapter msgAdapter;
    DatabaseReference myRef;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        View view = inflater.inflate(R.layout.activity_main, container, false);


        myRef = database.getReference("groupchat");


        sendBtn = view.findViewById(R.id.sendBtn);
        senderEt = view.findViewById(R.id.senderEt);
        msgEt = view.findViewById(R.id.msgEt);
        listView = view.findViewById(R.id.listview);


        ArrayList<String> msgList = new ArrayList<String>();

        msgAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.listitem, msgList);

        listView.setAdapter(msgAdapter);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sender = senderEt.getText().toString();
                String msg = msgEt.getText().toString();


                if (msg.length() > 0 && sender.length() > 0) {
                    // msgAdapter.add(sender+">"+msg);


                    myRef.push().setValue(sender + ">" + msg);


                    msgEt.setText("");
                }


            }
        });


        loadMsg();

        return view;
    }




    private void loadMsg()
    {
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                msgAdapter.add(dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
