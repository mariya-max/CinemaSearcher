package com.example.cinemaSearcher.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemaSearcher.R;
import com.example.cinemaSearcher.controller.RealmController;
import com.example.cinemaSearcher.model.realm.FilmRealm;
import com.example.cinemaSearcher.model.realm.GenresRealm;
import com.example.cinemaSearcher.ui.adapters.FilmAdapter;
import com.example.cinemaSearcher.ui.adapters.MainAdapter;

import io.realm.RealmResults;

public class MainFragment extends Fragment {

    TextView textView;
    RecyclerView recyclerView;
    RecyclerView recycler;
    RealmResults<GenresRealm> genresRealms;
    RealmResults<FilmRealm> filmRealms;
    RealmController controller;
    String genres;
    View view;
    MainAdapter adapter;
    FilmAdapter filmAdapter;

    private int someStateValue;
    private final String SOME_VALUE_KEY = "someValueToSave";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SOME_VALUE_KEY, someStateValue);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            someStateValue = savedInstanceState.getInt(SOME_VALUE_KEY);
        }
        init(inflater, container);
        refreshData();
        initRecyclerView();
        return view;
    }

    public void refreshFilmRealms() {
        filmRealms = controller.getFilmsByGenres(genres);
        filmAdapter.updateList(filmRealms);
    }

    public void refreshData() {
        genresRealms = controller.getFilmsGenres();
        filmRealms = controller.getAllFilms();
    }

    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        controller = new RealmController(getActivity());
        textView = view.findViewById(R.id.title);

        recyclerView = view.findViewById(R.id.rv);
        recycler = view.findViewById(R.id.recycler);
    }

    private void initRecyclerView() {
        LinearLayoutManager llm = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(llm);

        adapter = new MainAdapter(genresRealms, getActivity());
        adapter.setOnItemClickListener(new MainAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GenresRealm genresRealm = genresRealms.get(position);
                genres = genresRealm.getNameGenres();
                refreshFilmRealms();
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        GridLayoutManager glm = new GridLayoutManager(this.getActivity(), 2);
        recycler.setLayoutManager(glm);

        filmAdapter = new FilmAdapter(filmRealms, getActivity());
        recycler.setAdapter(filmAdapter);
        filmAdapter.setOnItemClickListener(new FilmAdapter.ClickListeners() {
            @Override
            public void onItemClick(View view, int position) {

                Integer idFilm = filmRealms.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", idFilm);

                Fragment fragmentFilm = new FilmFragment(getActivity(), filmRealms);
                fragmentFilm.setArguments(bundle);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, fragmentFilm);
                transaction.addToBackStack(null);
                transaction.commit();
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controller.closeRealm();
    }
}