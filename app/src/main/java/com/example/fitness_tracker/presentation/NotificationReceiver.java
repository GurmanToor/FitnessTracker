package com.example.fitness_tracker.presentation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.fitness_tracker.R;

public class NotificationReceiver extends BroadcastReceiver {
    String NOTIFICATION_ID;
    CharSequence name = "Channel_1";
    String description = "Weight logging notification" ;
    NotificationManager notificationManager;
    Intent redirectIntent;
    PendingIntent pendingIntent;


    @Override
    public void onReceive(Context context, Intent intent) {


        //initialize the notification manager
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //get the values passed through the intent
        NOTIFICATION_ID = intent.getStringExtra("id");

        //there are two request that can be requested: create a notification or delete a notification
        if(NOTIFICATION_ID != null) { // handle creating a notification

            //open the application when users click on the notification
            redirectIntent = new Intent(context, LoginActivity.class);

            //if the user is already in the application redirect them to the entry point of the application
            redirectIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            pendingIntent = PendingIntent.getActivity(context, 100, redirectIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //create the notification channel
                NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

                //set the description
                channel.setDescription(description);
                // Register the channel with the system
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                notificationManager.createNotificationChannel(channel);
            }

            //design the notification
            NotificationCompat.Builder builder= new NotificationCompat.Builder(context, NOTIFICATION_ID);
            builder.setSmallIcon(R.drawable.scale)
                    .setContentTitle("Log your weight!")
                    .setContentText("It's time to get on that scale")
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);


            //notify
            notificationManager.notify(100, builder.build());
        }
        else{//handle deleting a notification

            //get the id of the channel for the notification
            NOTIFICATION_ID = intent.getStringExtra("delete_id");

            // delete the notification
            deleteNotification(NOTIFICATION_ID);
        }


    }

    /**
     * Deletes a notification
     * @param id the id for the notification channel that needs to be deleted
     */
    public  void deleteNotification(String id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.deleteNotificationChannel(id);
        }
    }


}
