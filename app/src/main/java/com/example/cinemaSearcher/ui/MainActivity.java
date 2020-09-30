package com.example.cinemaSearcher.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.cinemaSearcher.App;
import com.example.cinemaSearcher.R;
import com.example.cinemaSearcher.controller.RealmController;
import com.example.cinemaSearcher.internal.Converter;
import com.example.cinemaSearcher.model.realm.FilmRealm;
import com.example.cinemaSearcher.model.realm.GenresRealm;
import com.example.cinemaSearcher.model.respons.Data;

import java.util.List;
import java.util.Set;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<FilmRealm> filmRealms;
    Set<GenresRealm> genresRealms;
    Fragment fragment;
    Data data;
    RealmController realmController;
    Converter converter;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realmController = new RealmController(this);
        converter = new Converter();
        frameLayout = findViewById(R.id.fragment_container);

        App.getApi().getCinemaList().enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() == null) return;
                data = response.body();

                saveAllBD();
                saveAllGenres();
                init();
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void init() {
        FragmentManager fm = getSupportFragmentManager();
        fragment = (Fragment) fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public void saveAllBD() {
        realmController.removeAll();
        filmRealms = converter.convertToFilmsList(data);
        realmController.saveAll(filmRealms);
    }

    public void saveAllGenres() {
        RealmResults<FilmRealm> list = realmController.getAllFilms();
        genresRealms = converter.getAllGenresFromFilm(list);
        realmController.save(genresRealms);
    }
}