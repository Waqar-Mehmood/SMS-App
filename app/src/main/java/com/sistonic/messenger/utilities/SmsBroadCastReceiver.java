package com.sistonic.messenger.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.sistonic.messenger.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsBroadCastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

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

                String smsBody = smsMessage.getMessageBody();
                String address = smsMessage.getOriginatingAddress();
                long timeMills = smsMessage.getTimestampMillis();

                Date date = new Date(timeMills);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                String dateText = format.format(date);

                smsMessageStr = address + "\n" +
                        smsBody + "\t" + dateText;
            }

            Toast.makeText(context, smsMessageStr, Toast.LENGTH_LONG).show();
            MainActivity inst = MainActivity.Instance();

            if (inst != null) {
                inst.updateList(smsMessageStr);
            }
        }
    }
}