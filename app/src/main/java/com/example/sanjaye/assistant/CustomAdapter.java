package com.example.sanjaye.assistant;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SANJAYE on 07-12-2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    public List<ChatMessage> chatMessageList;
    private static final int MY_MESSAGE = 0;
    private static final int REPLY_MESSAGE = 1;


    public class  MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView contentMessage,contentTime;

        public MyViewHolder(View view){
            super(view);

            contentMessage = (TextView)view.findViewById(R.id.mymessagesent);
            contentTime = (TextView)view.findViewById(R.id.texttimesent);
        }

    }
    public  class HisViewHolder extends RecyclerView.ViewHolder{
        public TextView hisText,hisTime;

        public HisViewHolder(View view){
            super(view);
            hisText = (TextView)view.findViewById(R.id.replymessage);
            hisTime = (TextView)view.findViewById(R.id.timetextrecieved);
        }
    }

    public CustomAdapter(List<ChatMessage> chatMessageList){
        this.chatMessageList = chatMessageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(viewType == MY_MESSAGE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
        }
        else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_message, parent, false);
        }
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        holder.contentMessage.setText(chatMessage.getContent());
        holder.contentTime.setText(chatMessage.getTime());

    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = chatMessageList.get(position);
        if (chatMessage.isMine == true)
            return MY_MESSAGE;
        else
            return REPLY_MESSAGE;
    }
}
