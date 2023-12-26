package com.example.tobeytravelplanner.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tobeytravelplanner.MainActivity;
import com.example.tobeytravelplanner.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class conversationsAdapter extends RecyclerView.Adapter<conversationsAdapter.conversationHolder> {

    ArrayList<String> conversationIds;
    AppCompatActivity context;

    public conversationsAdapter(ArrayList<String> conversationIds, AppCompatActivity context) {
        this.conversationIds = conversationIds;
        this.context = context;
    }

    @NonNull
    @Override
    public conversationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_view, parent, false);
        return new conversationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull conversationHolder holder, int position) {
        DateFormat simple = new SimpleDateFormat("dd/MM/yyyy");
        Date result = new Date(Long.valueOf(conversationIds.get(position)));
        holder.conversationText.setText(simple.format(result));
        holder.conversationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("session", conversationIds.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return conversationIds.size();
    }

    public class conversationHolder extends RecyclerView.ViewHolder {

        TextView conversationText;

        public conversationHolder(@NonNull View itemView) {
            super(itemView);
            conversationText = itemView.findViewById(R.id.conversationText);
        }
    }
}
