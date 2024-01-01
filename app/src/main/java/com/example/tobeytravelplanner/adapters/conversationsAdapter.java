package com.example.tobeytravelplanner.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tobeytravelplanner.MainActivity;
import com.example.tobeytravelplanner.R;
import com.example.tobeytravelplanner.functionalClasses.FirebaseHandler;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class conversationsAdapter extends RecyclerView.Adapter<conversationsAdapter.conversationHolder> {

    ArrayList<String> conversationIds, locations, departures;
    FirebaseHandler firebaseHandler = new FirebaseHandler();
    AppCompatActivity context;

    public conversationsAdapter(ArrayList<String> conversationIds, ArrayList<String> locations, ArrayList<String> departures, AppCompatActivity context) {
        this.conversationIds = conversationIds;
        this.locations = locations;
        this.departures = departures;
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
        holder.createdText.setText(simple.format(result));
        String[]place = new String[1];
        place[0]=locations.get(position);
        String[]placeholder = new String[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Picasso.with(context)
                        .load(placeholder[0])
                        .into(holder.image);
            }
        };
        holder.image.setImageResource(0);
        firebaseHandler.putImage(place, runnable, placeholder);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("session", conversationIds.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
        if (locations.get(position) == null) {
            holder.conversationText.setText("No hotels found");
            return;
        }
        if (locations.get(position).isEmpty())
            holder.conversationText.setText("Unfinished conversation");
        else
            holder.conversationText.setText(locations.get(position) + " trip on " + departures.get(position));
    }

    @Override
    public int getItemCount() {
        return conversationIds.size();
    }

    public void sortAll() {
        ArrayList<String> concat = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            String add = conversationIds.get(i);
            concat.add(conversationIds.get(i) + "`" + locations.get(i) + "`" + departures.get(i));
        }
        Collections.sort(concat, Collections.reverseOrder());
        conversationIds.clear();
        locations.clear();
        departures.clear();
        for (int i = 0; i < concat.size(); i++) {
            String[] splitted = concat.get(i).split("`");
            conversationIds.add(splitted[0]);
            if (splitted.length > 1) {
                if (splitted[1].equals("null"))
                    locations.add(null);
                else
                    locations.add(splitted[1]);
                if (splitted[2].equals("null"))
                    departures.add(null);
                else
                    departures.add(splitted[2]);
            } else {
                locations.add("");
                departures.add("");
            }
        }
    }

    public class conversationHolder extends RecyclerView.ViewHolder {

        TextView conversationText, createdText;
        LinearLayout root;
        ImageView image;

        public conversationHolder(@NonNull View itemView) {
            super(itemView);
            conversationText = itemView.findViewById(R.id.conversationText);
            createdText = itemView.findViewById(R.id.createdText);
            root = itemView.findViewById(R.id.llroot);
            image = itemView.findViewById(R.id.conversationImage);
        }
    }
}
