package com.sistonic.messenger;

public class SMS {

    private String mMessage;
    private String mDate;
    private String mSenderPhoneNumber;

    public SMS(String mSenderPhoneNumber, String mMessage, String mDate) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mSenderPhoneNumber = mSenderPhoneNumber;
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
