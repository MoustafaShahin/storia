package io.github.froger.mahmoud.ui.activity;

/**
 * Created by Moustafa on 12/17/2017.
 */


import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import io.github.froger.instamaterial.R;
import io.github.froger.mahmoud.ui.singletonPattern.UserDetails;

public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    UserDetails x ;


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(Chat.this,Users.class);
        finish();
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        x.create();
        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://storia-ae8bf.firebaseio.com/messages/" + x.username + "_" + x.chatWith);
        reference2 = new Firebase("https://storia-ae8bf.firebaseio.com/messages/" + x.chatWith + "_" + x.username);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", x.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(userName.equals(x.username)){
                    addMessageBox("You:-\n" + message, 1);
                }
                else{
                    addMessageBox(x.chatWith + ":-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            // el discord wa2f 3ndy

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);
        textView.setTextSize(17);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextColor(R.color.black);
        textView.setGravity(Gravity.RIGHT);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        lp.setMargins(0, 5, 0, 10);
        lp.gravity = Gravity.RIGHT;

        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
            textView.setGravity(Gravity.RIGHT);
            textView.setPadding(10,10,60,10);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
            textView.setGravity(Gravity.LEFT);
            textView.setPadding(60,10,10,10);
        }

        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}