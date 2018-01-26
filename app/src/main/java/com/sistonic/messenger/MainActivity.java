package com.sistonic.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sistonic.messenger.data.Inbox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static MainActivity inst;

    private SmsAdapter mAdapter;

    private RecyclerView mMessagesRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final int LOADER_ID = 0;

    public static MainActivity Instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab_new_sms);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity.this, SendSMSActivity.class);
                startActivity(intent);
            }
        });

        mMessagesRecyclerView = findViewById(R.id.rv_messages);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMessagesRecyclerView.setLayoutManager(mLayoutManager);

        mMessagesRecyclerView.setHasFixedSize(true);

        mAdapter = new SmsAdapter(this, new ArrayList<Sms>());
        mMessagesRecyclerView.setAdapter(mAdapter);

        SmsInbox();
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    public void SmsInbox() {
        mAdapter.setData(null);
        mAdapter.setData(Inbox.populateInbox(this));
    }

    private String setDate(int timeMills) {
        Date date = new Date(timeMills);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        return format.format(date);
    }

    public void updateList(final String smsMessage) {
//        arrayAdapter.insert(smsMessage, 0);
//        arrayAdapter.notifyDataSetChanged();
    }


//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//        try {
//            String[] smsMessages = SmsMessageList.get(position).split("\n");
//            String address = smsMessages[0];
//            String smsMessage = "";
//
//            for (int j = 0; j <= smsMessages.length; j++) {
//                smsMessage += smsMessages;
//            }
//
//            String smsMessageStr = address + "\n";
//            smsMessageStr += smsMessage;
//            Toast.makeText(this, smsMessageStr, Toast.LENGTH_LONG).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}