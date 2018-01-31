package com.sistonic.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sistonic.messenger.adapter.ChatAdapter;
import com.sistonic.messenger.data.Inbox;
import com.sistonic.messenger.utilities.CustomTextToSpeech;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.ChatAdapterOnItemClickListener {

    private RelativeLayout mRelativeLayout;
    private LinearLayout mLinearLayout;
    private Button sendSmsButton;
    private EditText phoneNumber;
    private EditText smsMessageEt;
    private TextView mContactName;
    private TextView mContactPhoneNumber;

    private ChatAdapter mAdapter;

    private RecyclerView mMessagesRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private CustomTextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textToSpeech = new CustomTextToSpeech(this);

        mRelativeLayout = findViewById(R.id.rl_et_phone_number);
        mLinearLayout = findViewById(R.id.ll_contact_info);

        sendSmsButton = findViewById(R.id.btn_send_sms);
        phoneNumber = findViewById(R.id.et_phone_number);
        smsMessageEt = findViewById(R.id.et_sms);

        mContactName = findViewById(R.id.tv_contact_name);
        mContactPhoneNumber = findViewById(R.id.tv_contact_phone_number);

        mMessagesRecyclerView = findViewById(R.id.rv_chat_messages);
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
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                mRelativeLayout.setVisibility(View.VISIBLE);
                mLinearLayout.setVisibility(View.GONE);
            } else if (intent.hasExtra("PhoneNumber")) {
                mRelativeLayout.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);

                String phoneNumber = intent.getStringExtra("PhoneNumber");
                String name = Inbox.getContactName(this, phoneNumber);

                mContactPhoneNumber.setText(phoneNumber);

                if (TextUtils.isEmpty(name)) {
                    mContactName.setVisibility(View.GONE);
                } else {
                    mContactName.setVisibility(View.VISIBLE);
                    mContactName.setText(name);
                }

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClickListener(SMS sms) {
        textToSpeech.setmMessage(sms.getmMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        textToSpeech.shutdown();
    }
}