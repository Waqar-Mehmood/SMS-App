package com.sistonic.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sistonic.messenger.adapter.InboxAdapter;
import com.sistonic.messenger.adapter.InboxAdapter.InboxAdapterOnItemClickListener;
import com.sistonic.messenger.data.Inbox;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InboxAdapterOnItemClickListener {

    public static MainActivity inst;

    private InboxAdapter mAdapter;

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
                Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, "NewMessage");
                startActivity(intent);
            }
        });

        mMessagesRecyclerView = findViewById(R.id.rv_inbox_messages);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMessagesRecyclerView.setLayoutManager(mLayoutManager);

        mMessagesRecyclerView.setHasFixedSize(true);

        mAdapter = new InboxAdapter(this, new ArrayList<SMS>());
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

    public void updateList(final String smsMessage) {
//        arrayAdapter.insert(smsMessage, 0);
//        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClickListener(String phoneNumber) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("PhoneNumber", phoneNumber);
        startActivity(intent);
    }
}