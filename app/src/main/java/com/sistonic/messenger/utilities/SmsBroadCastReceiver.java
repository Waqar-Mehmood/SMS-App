package com.sistonic.messenger.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;

import com.sistonic.messenger.MainActivity;
import com.sistonic.messenger.R;
import com.sistonic.messenger.ChatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsBroadCastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    String mSmsBody;
    String mSenderPhoneNumber;
    long timeMills;
    private TextToSpeech textToSpeech;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            SmsMessage smsMessage;

            for (Object sm : sms) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = intentExtras.getString("format");
                    smsMessage = SmsMessage.createFromPdu((byte[]) sm, format);
                } else {
                    smsMessage = SmsMessage.createFromPdu((byte[]) sm);
                }

                mSmsBody = smsMessage.getMessageBody();
                mSenderPhoneNumber = smsMessage.getOriginatingAddress();
                timeMills = smsMessage.getTimestampMillis();

                Date date = new Date(timeMills);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                String dateText = format.format(date);

                smsMessageStr = mSenderPhoneNumber + "\n" +
                        mSmsBody + "\t" + dateText;
            }

            MainActivity inst = MainActivity.Instance();

            NotificationCompat.Builder notification_Bldr = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_sms_black_24dp)
                    .setLargeIcon(largeIcon(context))
                    .setContentTitle(mSenderPhoneNumber)
                    .setContentText(mSmsBody)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentIntent(pendContentIntent(context, mSenderPhoneNumber))
                    .setAutoCancel(true);
            notification_Bldr.build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                notification_Bldr.setPriority(Notification.PRIORITY_HIGH);
            }
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            mNotificationManager.notify(1234, notification_Bldr.build());


            if (inst != null) {
                inst.updateList(smsMessageStr);
            }
        }
    }

    //for showing the pending notifications
    private static PendingIntent pendContentIntent(Context context, String senderPhoneNumber) {
        Intent startActivityIntent = new Intent(context, ChatActivity.class);
        startActivityIntent.putExtra("PhoneNumber", senderPhoneNumber);
        return PendingIntent.getActivity(
                context,
                1122,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // largeIcon is used for person image with notification message
    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
        return largeIcon;
    }
}