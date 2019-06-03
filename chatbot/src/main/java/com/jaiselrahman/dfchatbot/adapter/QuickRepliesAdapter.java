package com.jaiselrahman.dfchatbot.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.util.ValueIterator;
import android.util.Log;
import android.view.ViewGroup;
import android.view.View;

import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jaiselrahman.dfchatbot.ChatBotActivity;
import com.jaiselrahman.dfchatbot.R;
import com.jaiselrahman.dfchatbot.model.Quick;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.Locale;

public class QuickRepliesAdapter  extends RecyclerView.Adapter<QuickRepliesAdapter.ViewHolder> {
    private ArrayList<Quick> quicks;
    private Context context;

    private String string;
    public ChatBotActivity chatBotActivity=new ChatBotActivity();

    public QuickRepliesAdapter(Context context, ArrayList<Quick> quicks) {
        this.context = context;
        this.quicks = quicks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(context).inflate(R.layout.quickreplies_item, parent, false);
        return new ViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Quick quick = quicks.get(position);

        holder.title1.setText(quick.getTitle1());
        holder.title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatBotActivity.getsuggestion(quick.getTitle1());
            }
        });

        holder.title2.setText(quick.getTitle2());
        holder.title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatBotActivity.getsuggestion(quick.getTitle2());
            }
        });
        Log.d("12312321312321",""+quick.getCount());

        holder.title3.setVisibility(View.GONE);
        holder.title4.setVisibility(View.GONE);
        if(quick.getCount()==3 ||quick.getCount()==4 ) {
            holder.title3.setVisibility(View.VISIBLE);
            holder.title3.setText(quick.getTitle3());
            holder.title3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatBotActivity.getsuggestion(quick.getTitle3());
                }
            });
        }

        if(quick.getCount() ==4){
            holder.title4.setVisibility(View.VISIBLE);
            holder.title4.setText(quick.getTitle4());
            holder.title4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatBotActivity.getsuggestion(quick.getTitle4());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return quicks.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private Button title1;
        private Button title2;
        private Button title3;
        private Button title4;
        ViewHolder(View v) {
            super(v);
                title1 = v.findViewById(R.id.title1);
                title2 = v.findViewById(R.id.title2);
                title3 = v.findViewById(R.id.title3);
                title4 = v.findViewById(R.id.title4);

        }

    }
}
