package com.sistonic.messenger.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sistonic.messenger.R;
import com.sistonic.messenger.SMS;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<SMS> mSmsList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mSenderPhoneNumber;
        TextView mMessage;
        TextView mTime;

        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            mSenderPhoneNumber = itemView.findViewById(R.id.tv_sender_phone_number);
            mMessage = itemView.findViewById(R.id.tv_message);
            mMessage.setMaxLines(10);
            mTime = itemView.findViewById(R.id.tv_time);

            mCardView = itemView.findViewById(R.id.card_view);
        }
    }

    public ChatAdapter(Context context, List<SMS> list) {
        mContext = context;
        mSmsList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messages_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SMS sms = mSmsList.get(position);

        holder.mSenderPhoneNumber.setText(sms.getmSenderPhoneNumber());
        holder.mMessage.setText(sms.getmMessage());
        holder.mTime.setText(sms.getmDate());
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
}
