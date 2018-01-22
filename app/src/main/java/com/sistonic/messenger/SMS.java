package com.sistonic.messenger;

public class Sms {

    private String mMessage;
    private String mDate;
    private String mSentBy;

    public Sms(String mMessage, String mDate, String mSentBy) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mSentBy = mSentBy;
    }

    public String getmMessage() {
        return mMessage;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSentBy() {
        return mSentBy;
    }
}
