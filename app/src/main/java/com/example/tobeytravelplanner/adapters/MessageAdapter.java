package com.example.tobeytravelplanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tobeytravelplanner.AllHotelsScreen;
import com.example.tobeytravelplanner.R;
import com.example.tobeytravelplanner.objects.Message;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    ArrayList<Message>arr;
    Context context;

    public MessageAdapter(ArrayList<Message> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_view, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        System.out.println(arr.get(position).getAuthor()+": "+arr.get(position).getMessageContent());
        if (arr.get(position).getAuthor().equals("user")){
            params.gravity = Gravity.END;
            holder.textView.setBackground(context.getResources().getDrawable(R.drawable.user_message));
        }
        else {
            params.gravity = Gravity.START;
            holder.textView.setBackground(context.getResources().getDrawable(R.drawable.model_message));
        }
        holder.root.setLayoutParams(params);
        holder.textView.setText(arr.get(position).getMessageContent());
        if (arr.get(position).getItenaryId().isEmpty()){
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        else {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AllHotelsScreen.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public class MessageHolder extends RecyclerView.ViewHolder{

        LinearLayout root;
        TextView textView;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.messageRoot);
            textView = itemView.findViewById(R.id.msgContent);
        }
    }
}
