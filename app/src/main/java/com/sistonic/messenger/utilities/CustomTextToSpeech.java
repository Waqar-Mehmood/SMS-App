package com.sistonic.messenger.utilities;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class CustomTextToSpeech implements TextToSpeech.OnInitListener {

    private int result = 0;
    private Context mContext;
    private TextToSpeech tts;

    private String mMessage;

    public CustomTextToSpeech(Context context) {
        mContext = context;

        tts = new TextToSpeech(context, this);
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;

        stopPlaying();
        speakOut();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            result = tts.setLanguage(Locale.ENGLISH);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {

            } else {
                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed");
        }
    }

    private void speakOut() {
        if (result != tts.setLanguage(Locale.ENGLISH)) {

        } else {
            tts.speak(mMessage, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    //stoping tts
    public void stopPlaying() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
        }
    }

    //shutdown tts when activity destroy
    public void shutdown() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
