package edu.ucsd.cse110.lab5_nearby;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

//import com.google.android.gms.nearby.messages.Message;



public class MainActivity extends AppCompatActivity implements FakedMessageListener.MessageHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //################ create it here #####################################
        this.messageListener = new FakedMessageListener(this);
    };

    //#########################use it here ##################################
    private static final String TAG = "Lab5-Nearby";

    private FakedMessageListener messageListener;

    // broadCast Message
    public void onMyTestButtonClicked(View view) {

        this.messageListener.broadCastMessage("This is from derek!");
    }

    // found bluetooth message:
    public void onFound(com.google.android.gms.nearby.messages.Message message){

        //####### your code here:
        Log.d(TAG, "Found message: " + new String(message.getContent()));
    }

    // lost bluetooth
    public void onLost(com.google.android.gms.nearby.messages.Message message){

        //####### your code here:
        Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
    }
    //##############################################################################

}