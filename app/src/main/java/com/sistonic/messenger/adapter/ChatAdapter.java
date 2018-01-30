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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private List<SMS> mSmsList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mMessage;
        TextView mTime;

        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            mMessage = itemView.findViewById(R.id.tv_message);
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
                .inflate(R.layout.chat_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SMS sms = mSmsList.get(position);

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

        String time = dateFormat.format(date) + " " + timeFormat.format(date);

        return time;
    }
}