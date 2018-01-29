package com.sistonic.messenger.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.sistonic.messenger.SMS;

import java.util.ArrayList;
import java.util.List;

public class Inbox {

    public static final String COLUMN_PERSON = "person";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_DATE = "date";

    private static Cursor contentSmsInbox(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        return smsInboxCursor;
    }

    public static List<SMS> populateInbox(Context context) {

        Cursor cursor = contentSmsInbox(context);

        int indexBody = cursor.getColumnIndex(COLUMN_BODY);
        int indexAddress = cursor.getColumnIndex(COLUMN_ADDRESS);
        int timeMills = cursor.getColumnIndex(COLUMN_DATE);

        if (indexBody < 0 || !cursor.moveToFirst()) return null;

        List<SMS> list = new ArrayList<>();

        do {
            String senderPhone = cursor.getString(indexAddress);
            String messageBody = cursor.getString(indexBody);
//            String messageDate = setDate(smsInboxCursor.getColumnIndex("date"));

            SMS sms = new SMS(senderPhone, messageBody, "00-00");
            list.add(sms);

        } while (cursor.moveToNext());

        List<SMS> newList = new ArrayList<>();

        try {
            for (int i = 0; i < list.size(); i++) {
                SMS sms1 = list.get(i);

                if (i == 0) {
                    newList.add(sms1);
                } else {
                    int count = 0;
                    for (int j = 0; j < newList.size(); j++) {
                        SMS sms2 = newList.get(j);
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
        }

        return newList;
    }

    public static List<SMS> filterNumber(Context context, String phoneNumber) {

        Cursor cursor = contentSmsInbox(context);

        int indexBody = cursor.getColumnIndex("body");
        int indexAddress = cursor.getColumnIndex("address");
        int timeMills = cursor.getColumnIndex("date");

        String person = cursor.getString(cursor.getColumnIndexOrThrow("person"));

        if (indexBody < 0 || !cursor.moveToFirst()) return null;

        List<SMS> list = new ArrayList<>();

        do {
            String senderPhone = cursor.getString(indexAddress);
            String messageBody = cursor.getString(indexBody);
//            String messageDate = setDate(smsInboxCursor.getColumnIndex("date"));

            SMS sms = new SMS(senderPhone, messageBody, "00-00");
            list.add(sms);

        } while (cursor.moveToNext());

        List<SMS> newList = new ArrayList<>();

        Log.d("messageApp", "List size " + list.size());

        try {
            for (int i = 0; i < list.size(); i++) {

                SMS sms = list.get(i);

//                Log.d("messageApp", "value of i " + i + ", sms1 " + sms1.getmSenderPhoneNumber());

                if (sms.getmSenderPhoneNumber().equals(phoneNumber)) {
                    newList.add(sms);
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
