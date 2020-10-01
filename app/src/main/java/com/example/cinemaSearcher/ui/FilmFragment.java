package com.example.cinemaSearcher.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cinemaSearcher.R;
import com.example.cinemaSearcher.model.realm.FilmRealm;

import io.realm.RealmResults;

public class FilmFragment extends Fragment {

    private ImageView imageView;
    private TextView locName;
    private TextView name;
    private TextView year;
    private TextView rating;
    private TextView description;
    private Context context;
    private View view;
    private Toolbar toolbar;
    private RealmResults<FilmRealm> filmRealms;

    public FilmFragment(Context context, RealmResults<FilmRealm> filmRealms) {
        this.context = context;
        this.filmRealms = filmRealms;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        init(inflater, container);
        fillView();
        return view;
    }

    private void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_film, container, false);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        imageView = view.findViewById(R.id.imageСover);
        locName = view.findViewById(R.id.locName);
        name = view.findViewById(R.id.name);
        year = view.findViewById(R.id.year);
        rating = view.findViewById(R.id.rating);
        description = view.findViewById(R.id.description);
    }

    private void fillView() {
        Bundle bundle = this.getArguments();
        assert bundle != null;
        Integer integer = bundle.getInt("id");

        for (FilmRealm film : filmRealms) {
            if (integer.equals(film.getId())) {
                Glide.with(context.getApplicationContext())
                        .load(film.getImageUrl())
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.ic_cloud_off_white_foreground)
                        .into(imageView);
                locName.setText(film.getLocalizedName());
                name.setText(film.getName());
                year.setText(film.getYear());
                rating.setText(film.getRating());
                description.setText(film.getDescription());
                toolbar.setTitle(film.getLocalizedName());
            }
        }
    }
}