package com.sistonic.messenger;

public class SMS {

    private String mName;
    private String mMessage;
    private long mDate;
    private String mSenderPhoneNumber;

    public SMS(String name, String mSenderPhoneNumber, String mMessage, long mDate) {
        this.mName = name;
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

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmDate(long mDate) {
        this.mDate = mDate;
    }
}
