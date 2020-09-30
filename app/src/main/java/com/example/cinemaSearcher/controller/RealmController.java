package com.example.cinemaSearcher.controller;

import android.content.Context;

import com.example.cinemaSearcher.model.realm.FilmRealm;
import com.example.cinemaSearcher.model.realm.GenresRealm;

import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmController {

    private Realm realm;

    public RealmController(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void removeAll() {
        realm.beginTransaction();
        RealmResults<FilmRealm> films = realm.where(FilmRealm.class).findAllSorted("localizedName");
        films.clear();
        realm.commitTransaction();
    }

    public void saveAll(List<FilmRealm> films) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(films);
        realm.commitTransaction();
    }

    public RealmResults<FilmRealm> getAllFilms() {
        return realm.where(FilmRealm.class).findAllSorted("localizedName");
    }

    public void save(Set<GenresRealm> genresRealms) {
        realm.beginTransaction();
        realm.copyToRealm(genresRealms);
        realm.commitTransaction();
    }

    public RealmResults<GenresRealm> getFilmsGenres() {
        return realm.where(GenresRealm.class).findAllSorted("nameGenres");
    }

    public RealmResults<FilmRealm> getFilmsByGenres(String genres) {
        return realm.where(FilmRealm.class).equalTo("genres.nameGenres", genres).findAllSorted("localizedName");
    }

    public void closeRealm() {
        realm.close();
    }
}
