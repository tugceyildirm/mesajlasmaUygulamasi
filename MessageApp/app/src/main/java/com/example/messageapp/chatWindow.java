package com.example.messageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatWindow extends AppCompatActivity {


    String reciverimg,reciverUid,reciverName,SenderUID;
    CircleImageView profile;
    TextView recivernNName;
    CardView sendbtn;
    EditText textmsg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static  String senderImg;
    public static  String  reciverIImg;
    String senderRoom, reciverRoom;
    RecyclerView messageAdpter;
    ArrayList<msgModelclass> messagesArrayList;
    messagersAdpter mmessagesAdpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        database=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        messageAdpter=findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdpter.setLayoutManager(linearLayoutManager);
        mmessagesAdpter= new messagersAdpter(chatWindow.this,messagesArrayList);
        messageAdpter.setAdapter(mmessagesAdpter);
        messagesArrayList= new ArrayList<>();


        reciverName=getIntent().getStringExtra("nameee");
        reciverUid=getIntent().getStringExtra("uid");
        reciverimg=getIntent().getStringExtra("reciverImg");



        sendbtn=findViewById(R.id.sendbtnn);
        textmsg=findViewById(R.id.textmsg);

        profile=findViewById(R.id.profilechat);
        recivernNName=findViewById(R.id.recivername);

        Picasso.get().load(reciverimg).into(profile);
        recivernNName.setText(""+reciverName);

        SenderUID= firebaseAuth.getUid();

        senderRoom= SenderUID+ reciverUid;
        reciverRoom= reciverUid+SenderUID;

        DatabaseReference reference= database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    msgModelclass messages =dataSnapshot.getValue(msgModelclass.class);
                    messagesArrayList.add(messages);

                }
                mmessagesAdpter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                senderImg = snapshot.child("profilepic").getValue().toString();
                reciverIImg=reciverimg;



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message= textmsg.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(chatWindow.this,"Mesajı yazınız.",Toast.LENGTH_SHORT).show();
                }

                textmsg.setText("");
                Date date = new Date();
                msgModelclass messagess =new msgModelclass(message,SenderUID,date.getTime());
                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom).child("messages").push()
                        .setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats").child(reciverRoom).child("messages")
                                        .push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });


            }
        });






    }
}