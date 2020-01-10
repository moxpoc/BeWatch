package com.starlayt.moxpoc.slwatch.Tools;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.starlayt.moxpoc.slwatch.ModelAPI.Message;
import com.starlayt.moxpoc.slwatch.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

    private List<Message> messageList;

    public static final int SENDER = 0;
    public static final int RECEIVER = 1;

    public MessageAdapter(Context context, List messages){
        messageList = messages;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;

        public ViewHolder(LinearLayout v){
            super(v);
            mTextView = v.findViewById(R.id.chatText);
        }
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(viewType == 1){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_server, parent, false);
            ViewHolder vh = new ViewHolder((LinearLayout) v);
            return vh;
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_device, parent, false);
            ViewHolder vh = new ViewHolder((LinearLayout) v);
            return vh;
        }
    }

    public void remove(int position){
        messageList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, messageList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position){
        ViewHolder vh =(ViewHolder)viewHolder;
        vh.mTextView.setText(messageList.get(position).getMessage());
        vh.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
    }

    @Override
    public int getItemCount(){
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position){
        Message message = messageList.get(position);
        if(message.getSenderName().equals("Device")){
            return SENDER;
        } else{
            return RECEIVER;
        }
    }
}
