package edu.ucsd.cse110.lab5_nearby;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FakedMessageListener extends MessageListener{

    private final MessageListener messageListener;
    private final ScheduledExecutorService excutor;

    public interface MessageHandler
    {
        public void onFound(Message message);
        public void onLost(Message message);
    }

    private List<String> messages_list =  new ArrayList<>();

    public void broadCastMessage(String message){

        this.messages_list.add(message);
    }

    public FakedMessageListener(MessageHandler messageHandler){
        int frequency = 1;
        this.messageListener =new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                super.onFound(message);
                messageHandler.onFound(message);
            }

            @Override
            public void onLost(@NonNull Message message) {
                super.onLost(message);
                messageHandler.onLost(message);
            }
        };

        this.excutor = Executors.newSingleThreadScheduledExecutor();

        excutor.scheduleAtFixedRate(()->{
            while(!this.messages_list.isEmpty()) {
                String messageStr = this.messages_list.remove(0);
                Message message = new Message(messageStr.getBytes(StandardCharsets.UTF_8));
                this.messageListener.onFound(message);
                this.messageListener.onLost(message);
            }

        }, 0, frequency, TimeUnit.SECONDS);
    };

}


