package edu.ucsd.cse110.lab5_nearby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Lab5-Nearby";
    private FakedMessageListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MessageListener realListener =new MessageListener() {

            @Override
            public void onFound(@NonNull Message message) {
                super.onFound(message);

                Log.d(TAG, "Found message: " + new String(message.getContent()));
            }

            @Override
            public void onLost(@NonNull Message message) {
                super.onLost(message);

                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };

        //Fake receiving a message every three seconds:
        this.messageListener = new FakedMessageListener(realListener, 3, "Hello world!");
    };

    @Override
    protected void onStart(){
        super.onStart();
        Nearby.getMessagesClient(this).subscribe(messageListener);
    }


    @Override
    protected void onStop(){
        super.onStop();
        Nearby.getMessagesClient(this).unsubscribe(messageListener);
    }
}