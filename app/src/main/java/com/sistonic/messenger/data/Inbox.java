package com.sistonic.messenger.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.sistonic.messenger.SMS;

import java.util.ArrayList;
import java.util.List;

public class Inbox {

    public static final String COLUMN_PERSON = "person";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_DATE = "date";

    public static List<SMS> populateInbox(Context context) {

        // Create Inbox box URI
        Uri inboxURI = Uri.parse("content://sms/inbox");

        // List required columns
        String[] reqCols = new String[]{COLUMN_ID, COLUMN_ADDRESS, COLUMN_BODY, COLUMN_DATE};

        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = context.getContentResolver()
                .query(inboxURI, reqCols, null, null, null);

        if (cursor.getCount() < 1 || !cursor.moveToFirst()) return null;

        List<SMS> list = new ArrayList<>();

        do {
            String senderPhone = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
            String messageBody = cursor.getString(cursor.getColumnIndex(COLUMN_BODY));
            long messageDate = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));

            SMS sms = new SMS(senderPhone, messageBody, messageDate);
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

        cursor.close();
        return newList;
    }

    public static List<SMS> filterNumber(Context context, String phoneNumber) {

        // Create Inbox box URI
        Uri inboxURI = Uri.parse("content://sms/inbox");

        // List required columns
        String[] reqCols = new String[]{COLUMN_ID, COLUMN_ADDRESS, COLUMN_BODY, COLUMN_DATE};

        String selection = COLUMN_ADDRESS + "=?";
        String[] selectionArgs = {phoneNumber};

        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor cursor = context.getContentResolver()
                .query(inboxURI, reqCols, selection, selectionArgs, null);

        if (cursor.getCount() < 1 || !cursor.moveToFirst()) return null;

        List<SMS> list = new ArrayList<>();

        do {
            String senderPhone = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
            String messageBody = cursor.getString(cursor.getColumnIndex(COLUMN_BODY));
            long messageDate = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));

            SMS sms = new SMS(senderPhone, messageBody, messageDate);
            list.add(sms);

        } while (cursor.moveToNext());

        cursor.close();
        return list;
    }
}