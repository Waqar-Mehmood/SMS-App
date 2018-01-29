package com.sistonic.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sistonic.messenger.adapter.ChatAdapter;
import com.sistonic.messenger.data.Inbox;

import java.util.ArrayList;

public class SendSMSActivity extends AppCompatActivity {

    private Button sendSmsButton;
    private EditText phoneNumber;
    private EditText smsMessageEt;

    private ChatAdapter mAdapter;

    private RecyclerView mMessagesRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        sendSmsButton = findViewById(R.id.btn_send_sms);
        phoneNumber = findViewById(R.id.et_phone_number);
        smsMessageEt = findViewById(R.id.et_sms);

        mMessagesRecyclerView = findViewById(R.id.rv_messages_1);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMessagesRecyclerView.setLayoutManager(mLayoutManager);

        mMessagesRecyclerView.setHasFixedSize(true);

        mAdapter = new ChatAdapter(this, new ArrayList<SMS>());
        mMessagesRecyclerView.setAdapter(mAdapter);

        sendSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("PhoneNumber")) {
                String phoneNumber = intent.getStringExtra("PhoneNumber");

                chatMessages(phoneNumber);
            }
        }
    }

    private void chatMessages(String phoneNumber) {
        mAdapter.setData(null);
        mAdapter.setData(Inbox.filterNumber(this, phoneNumber));
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