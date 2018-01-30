package com.sistonic.messenger;

public class SMS {

    private String mMessage;
    private long mDate;
    private String mSenderPhoneNumber;

    public SMS(String mSenderPhoneNumber, String mMessage, long mDate) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mSenderPhoneNumber = mSenderPhoneNumber;
    }

    public String getmMessage() {
        return mMessage;
    }

    public long getmDate() {
        return mDate;
    }

    public String getmSenderPhoneNumber() {
        return mSenderPhoneNumber;
    }
}
