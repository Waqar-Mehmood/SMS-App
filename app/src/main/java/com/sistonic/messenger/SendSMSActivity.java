package com.sistonic.messenger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSActivity extends AppCompatActivity {

    private Button sendSmsButton;
    private EditText phoneNumber;
    private EditText smsMessageEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        sendSmsButton = findViewById(R.id.btnSendSMS);
        phoneNumber = findViewById(R.id.editTextPhoneNum);
        smsMessageEt = findViewById(R.id.editTextSMS);

        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });
    }

    private void sendSms() {
        String toPhoneNum = phoneNumber.getText().toString();
        String smsMessage = smsMessageEt.getText().toString();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(toPhoneNum, null, smsMessage, null, null);

            Toast.makeText(this, "SMS Sent", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}