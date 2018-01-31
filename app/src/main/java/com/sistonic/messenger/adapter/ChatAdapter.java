package com.sistonic.messenger.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sistonic.messenger.R;
import com.sistonic.messenger.SMS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<SMS> mSmsList;

    private final ChatAdapterOnItemClickListener mClickHandler;

    public interface ChatAdapterOnItemClickListener {
        void onItemClickListener(SMS sms);
    }

    public ChatAdapter(ChatAdapterOnItemClickListener clickHandler, List<SMS> list) {
        mClickHandler = clickHandler;
        mSmsList = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mMessage;
        TextView mTime;
        ImageView mPlayMessage;

        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            mMessage = itemView.findViewById(R.id.tv_message);
            mTime = itemView.findViewById(R.id.tv_time);
            mPlayMessage = itemView.findViewById(R.id.iv_play_message);
            mCardView = itemView.findViewById(R.id.card_view);

            mPlayMessage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            SMS sms = mSmsList.get(adapterPosition);
            mClickHandler.onItemClickListener(sms);
        }
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
        SMS sms = mSmsList.get(position);
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