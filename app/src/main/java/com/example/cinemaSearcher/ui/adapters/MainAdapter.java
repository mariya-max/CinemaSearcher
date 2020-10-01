package com.example.cinemaSearcher.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaSearcher.R;
import com.example.cinemaSearcher.model.realm.GenresRealm;

import io.realm.RealmResults;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private static ClickListener clickListener;
    private int selectedPos = RecyclerView.NO_POSITION;
    private RealmResults<GenresRealm> genresRealms;
    protected Context context;

    public MainAdapter(RealmResults<GenresRealm> genresRealms, Context context) {
        this.genresRealms = genresRealms;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genres_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GenresRealm data = genresRealms.get(position);
        holder.textView.setText(data.getNameGenres());
        holder.itemView.setSelected(selectedPos == position);

    }

    @Override
    public int getItemCount() {
        if (genresRealms == null) {
            return 0;
        }
        return genresRealms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = itemView.findViewById(R.id.genresTextView);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(view, getAdapterPosition());
            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        MainAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View view, int position);
    }
}
