package com.sistonic.messenger.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sistonic.messenger.R;
import com.sistonic.messenger.SMS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private List<SMS> mSmsList;

    private final InboxAdapterOnItemClickListener mClickHandler;

    public interface InboxAdapterOnItemClickListener {
        void onItemClickListener(String phoneNumber);
    }

    public InboxAdapter(InboxAdapterOnItemClickListener clickHandler, List<SMS> list) {
        mClickHandler = clickHandler;
        mSmsList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mName;
        TextView mMessage;
        TextView mTime;

        public ViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tv_sender_name);
            mMessage = itemView.findViewById(R.id.tv_message);
            mTime = itemView.findViewById(R.id.tv_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            SMS sms = mSmsList.get(adapterPosition);
            mClickHandler.onItemClickListener(sms.getmSenderPhoneNumber());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SMS sms = mSmsList.get(position);

        String name = sms.getmName();
        if (name == null) {
            name = sms.getmSenderPhoneNumber();
        }

        holder.mName.setText(name);
        holder.mMessage.setText(sms.getmMessage());
        holder.mTime.setText(setDate(sms.getmDate()));
    }

    @Override
    public int getItemCount() {
        if (mSmsList == null)
            return 0;
        return mSmsList.size();
    }

    public void setData(List<SMS> list) {
        mSmsList = list;
        notifyDataSetChanged();
    }

    private static String setDate(long timeMills) {
        Date date = new Date(timeMills);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        return dateFormat.format(date);
    }
}
