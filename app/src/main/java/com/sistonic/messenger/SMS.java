package com.sistonic.messenger;

public class Sms {

    private String mMessage;
    private String mDate;
    private String mSenderPhoneNumber;

    public Sms(String senderPhoneNumber, String mMessage, String mDate) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mSenderPhoneNumber = senderPhoneNumber;
    }

    public String getmMessage() {
        return mMessage;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSenderPhoneNumber() {
        return mSenderPhoneNumber;
    }
}
