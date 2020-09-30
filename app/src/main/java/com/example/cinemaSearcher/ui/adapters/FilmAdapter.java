package com.example.cinemaSearcher.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinemaSearcher.R;
import com.example.cinemaSearcher.model.realm.FilmRealm;

import io.realm.RealmResults;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private static ClickListeners clickListeners;

    private RealmResults<FilmRealm> filmRealms;
    Context context;

    public FilmAdapter(RealmResults<FilmRealm> filmRealms, Context context) {
        this.filmRealms = filmRealms;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.films_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context.getApplicationContext())
                .load(filmRealms.get(position).getImageUrl())
                .skipMemoryCache(true)
                .placeholder(R.drawable.ic_cloud_off_white_foreground)
                .into(holder.imageView);
        holder.textView.setText(filmRealms.get(position).getLocalizedName());
    }

    @Override
    public int getItemCount() {
        if (filmRealms == null) {
            return 0;
        }
        return filmRealms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.text_name);
        }

        @Override
        public void onClick(View v) {
            clickListeners.onItemClick(v, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(FilmAdapter.ClickListeners clickListeners) {
        FilmAdapter.clickListeners = clickListeners;
    }

    public interface ClickListeners {
        void onItemClick(View view, int position);
    }

    public void updateList(RealmResults<FilmRealm> filmRealms) {
        this.filmRealms = filmRealms;
        notifyDataSetChanged();
    }
}
