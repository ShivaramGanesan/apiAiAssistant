package com.example.sanjaye.assistant;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SANJAYE on 08-12-2017.
 */

public class Adapt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ChatMessage> mList;
    private static final int MY_MESSAGE = 0;
    private static final int REPLY_MESSAGE = 1;


    public class ViewHolder1 extends RecyclerView.ViewHolder{
        TextView m1,t1;
        public ViewHolder1(View itemView) {
            super(itemView);
            m1 = (TextView)itemView.findViewById(R.id.mymessagesent);
            t1 = (TextView)itemView.findViewById(R.id.texttimesent);

        }
    }
    public  class  ViewHolder2 extends  RecyclerView.ViewHolder{
        TextView m2,t2;
        public ViewHolder2(View itemView) {
            super(itemView);
            m2 = (TextView)itemView.findViewById(R.id.replymessage);
            t2 = (TextView)itemView.findViewById(R.id.timetextrecieved);
        }
    }

    public Adapt(List<ChatMessage> mList) {
        this.mList = mList;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mList.get(position);
        if(chatMessage.isMine == true)
            return  MY_MESSAGE;
        else
            return REPLY_MESSAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType==MY_MESSAGE){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
            return new ViewHolder1(v);
        }
        else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_message, parent, false);
            return  new ViewHolder2(v);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage chatMessage = mList.get(position);
        switch (holder.getItemViewType()){
            case 0:
                ViewHolder1 viewHolder1 = (ViewHolder1)holder;
                ((ViewHolder1) holder).m1.setText(chatMessage.getContent());
                ((ViewHolder1) holder).t1.setText(chatMessage.getTime());
                break;
            case 1:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                ((ViewHolder2) holder).m2.setText(chatMessage.getContent());
                ((ViewHolder2) holder).t2.setText(chatMessage.getTime());
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
