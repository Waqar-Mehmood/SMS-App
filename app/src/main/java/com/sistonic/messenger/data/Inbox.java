package com.sistonic.messenger.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.sistonic.messenger.SMS;

import java.util.ArrayList;
import java.util.List;

public class Inbox {

    public static final String COLUMN_PERSON = "person";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_DATE = "date";

    /*
    *
    * Populate Inbox
    *
    * */

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

            String name = getContactName(context, senderPhone);

            SMS sms = new SMS(name, senderPhone, messageBody, messageDate);

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

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return newList;
    }

    /*
    *
    * Filter Number
    *
    * */

    public static List<SMS> filterNumber(Context context, String phoneNumber) {

        // Create Inbox box URI
        Uri inboxURI = Uri.parse("content://sms/inbox");
        // Create Sent box URI
        Uri sentURI = Uri.parse("content://sms/sent");

        // List required columns
        String[] reqCols = new String[]{COLUMN_ADDRESS, COLUMN_BODY, COLUMN_DATE};

        String selection = COLUMN_ADDRESS + "=?";
        String[] selectionArgs = {phoneNumber};
        String sortOrder = COLUMN_DATE + " ASC";

        // Fetch Inbox SMS Message from Built-in Content Provider
        Cursor inboxCursor = context.getContentResolver()
                .query(inboxURI, reqCols, selection, selectionArgs, sortOrder);

        // Fetch Sent SMS Message from Built-in Content Provider
        Cursor sentCursor = context.getContentResolver()
                .query(sentURI, reqCols, selection, selectionArgs, sortOrder);

        List<SMS> list = new ArrayList<>();

        if (inboxCursor.getCount() < 1 || !inboxCursor.moveToFirst()) {

        } else {
            do {
                String senderPhone = inboxCursor.getString(inboxCursor.getColumnIndex(COLUMN_ADDRESS));
                String messageBody = inboxCursor.getString(inboxCursor.getColumnIndex(COLUMN_BODY));
                long messageDate = inboxCursor.getLong(inboxCursor.getColumnIndex(COLUMN_DATE));

                String name = getContactName(context, senderPhone);
                SMS sms = new SMS(name, senderPhone, messageBody, messageDate);
                list.add(sms);

            } while (inboxCursor.moveToNext());
        }

        if ((sentCursor.getCount() < 1 || !sentCursor.moveToFirst())) {

        } else {
            do {
                String senderPhone = sentCursor.getString(sentCursor.getColumnIndex(COLUMN_ADDRESS));
                String messageBody = sentCursor.getString(sentCursor.getColumnIndex(COLUMN_BODY));
                long messageDate = sentCursor.getLong(sentCursor.getColumnIndex(COLUMN_DATE));

                String name = getContactName(context, senderPhone);
                SMS sms = new SMS(name, senderPhone, messageBody, messageDate);
                list.add(sms);

            } while (sentCursor.moveToNext());
        }

        if (inboxCursor != null && !inboxCursor.isClosed()) {
            inboxCursor.close();
        }
        if (sentCursor != null && !sentCursor.isClosed()) {
            sentCursor.close();
        }

        //---------
        // Compare List items
        //----------

//        List<SMS> newList = new ArrayList<>();

//        for (Iterator<SMS> iterator = list.iterator(); iterator.hasNext(); ) {
//            long value = iterator.next().getmDate();
//
//            for (Iterator<SMS> i = list.iterator(); iterator.hasNext(); ) {
//                long v = iterator.next().getmDate();
//
//                if (value < v) {
//                    list.remove(i.next());
//                    list.add(i.next());
//                }
//            }
//        }


//        for (int i = 0; i < list.size(); i++) {
//            SMS sms = list.get(i);
//
//            for (int j = 0; j < list.size(); j++) {
//                SMS s = list.get(i);
//
//                Date date1 = new Date(sms.getmDate());
//                Date date2 = new Date(s.getmDate());
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
//                String d1 = dateFormat.format(date1);
//                String d2 = dateFormat.format(date2);
//
//                try {
//                    date1 = dateFormat.parse(d1);
//                    date2 = dateFormat.parse(d2);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                if (date1.compareTo(date2) < 0) {
//                    list.remove(s);
//                    list.add(s);
//
//                    Log.d("SMS-APP", date1 + ", " + date2);
//
//                } else {
//
//                }
//            }
//        }

        //----------

        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    /*
    *
    *  Get Contact Name
    *
    * */

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();

        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber));

        Cursor cursor = cr.query(uri,
                new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        if (cursor == null) {
            return null;
        }

        String contactName = null;
        if (cursor.moveToFirst()) {
            contactName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
}