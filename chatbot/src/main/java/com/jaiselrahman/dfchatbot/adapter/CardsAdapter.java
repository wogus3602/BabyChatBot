package com.jaiselrahman.dfchatbot.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.jaiselrahman.dfchatbot.R;
import com.jaiselrahman.dfchatbot.app.AppController;
import com.jaiselrahman.dfchatbot.model.Cards;

import java.util.ArrayList;

/**
 * Created by jaisel on 12/1/18.
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private ArrayList<Cards> cards;
    private Context context;

    public CardsAdapter(Context context, ArrayList<Cards> cards) {
        this.context = context;
        this.cards = cards;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Cards card = cards.get(position);
        holder.title.setText(card.getTitle());
        if (card.getSubtitle() != null) {
            holder.subtitle.setVisibility(View.VISIBLE);
            holder.subtitle.setText(card.getSubtitle());
        }
        holder.image.setImageUrl(card.getImgUrl(), AppController.getInstance().getImageLoader());
        holder.button.setText(card.getbuttons().getText());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(card.getbuttons().getPostback()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, subtitle;
        private NetworkImageView image;
        private Button button;

        ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            subtitle = v.findViewById(R.id.subtitle);
            image = v.findViewById(R.id.image);
            button = v.findViewById(R.id.button);
        }
    }
}
