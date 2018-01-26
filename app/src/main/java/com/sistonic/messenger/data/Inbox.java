package com.sistonic.messenger.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.sistonic.messenger.Sms;

import java.util.ArrayList;
import java.util.List;

public class Inbox {

    public static List<Sms> populateInbox(Context context) {

        ContentResolver contentResolver = context.getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);

        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int timeMills = smsInboxCursor.getColumnIndex("date");

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return null;

        List<Sms> list = new ArrayList<>();

        do {
            String senderPhone = smsInboxCursor.getString(indexAddress);
            String messageBody = smsInboxCursor.getString(indexBody);
//            String messageDate = setDate(smsInboxCursor.getColumnIndex("date"));

            Sms sms = new Sms(senderPhone, messageBody, "00-00");
            list.add(sms);

        } while (smsInboxCursor.moveToNext());

        List<Sms> newList = new ArrayList<>();

        Log.d("messageApp", "List size " + list.size());

        try {
            for (int i = 0; i < list.size(); i++) {

                Sms sms1 = list.get(i);

                Log.d("messageApp", "value of i " + i + ", sms1 " + sms1.getmSenderPhoneNumber());

                if (i == 0) {
                    newList.add(sms1);
                } else {

                    int count = 0;

                    for (int j = 0; j < newList.size(); j++) {

                        Sms sms2 = newList.get(j);

                        Log.d("messageApp", "value of j " + j + ", sms2 " + sms2.getmSenderPhoneNumber());

                        if (!sms1.getmSenderPhoneNumber().equals(sms2.getmSenderPhoneNumber())) {
                            count++;
                        }
                    }

                    if (count == newList.size()) {
                        newList.add(sms1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("messageApp", "Error");
        }

        Log.d("messageApp", "new List Size " + newList.size());

        return newList;
    }
}
