package home.services.timeBacaho;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import home.services.timeBacaho.Activity.MainActivity;

public class NotificationHelper {
    static final String CHANNEL_ID = "technopoints_id";
    public static void displayNotification(Context context, String title, String body) {

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        PendingIntent acceptPendingIntent = PendingIntent.getActivity(context, 101, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        PendingIntent declinePendingIntent = PendingIntent.getActivity(context, 102, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(),R.drawable.whitelogocircle);
        Bitmap picture = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_add_shopping_cart_gray);


        @SuppressLint({"WrongConstant", "NewApi", "LocalSuppress"}) Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)


                .setLargeIcon(largeIcon)

                .setContentTitle(title)
                .setContentText(body)

                .setStyle(new NotificationCompat.BigPictureStyle()

                      )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1, notification);
    }
}
