package com.example.cinemaSearcher.internal;

import com.example.cinemaSearcher.model.realm.FilmRealm;
import com.example.cinemaSearcher.model.realm.GenresRealm;
import com.example.cinemaSearcher.model.respons.Data;
import com.example.cinemaSearcher.model.respons.Film;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.RealmList;
import io.realm.RealmResults;

public class Converter {

    protected List<FilmRealm> films = null;
    protected RealmList<GenresRealm> genres = null;
    protected Set<GenresRealm> genresFilm = null;

    public List<FilmRealm> convertToFilmsList(Data data) {
        films = new ArrayList<>();
        for (Film item : data.getFilms()) {
            FilmRealm filmRealm = new FilmRealm();
            filmRealm.setId(item.getId());
            filmRealm.setLocalizedName(item.getLocalizedName());
            filmRealm.setName(item.getName());
            filmRealm.setYear(item.getYear());
            filmRealm.setRating(item.getRating());
            filmRealm.setImageUrl(item.getImageUrl());
            filmRealm.setDescription(item.getDescription());
            filmRealm.setGenres(convertToGenresRealm(item));
            films.add(filmRealm);
        }
        System.out.println();
        return films;
    }


    public RealmList<GenresRealm> convertToGenresRealm(Film film) {
        genres = new RealmList<>();
        if (film.getGenres().size() > 0) {
            for (String item : film.getGenres()) {
                if (item.length() > 2) {
                    GenresRealm genresRealm = new GenresRealm();
                    genresRealm.setNameGenres(item);
                    genres.add(genresRealm);
                }
            }
        }
        return genres;
    }

    public Set<GenresRealm> getAllGenresFromFilm(RealmResults<FilmRealm> film) {
        genresFilm = new HashSet<>();
        for (FilmRealm item : film) {
            genresFilm.addAll(item.getGenres());
        }
        return genresFilm;
    }
}
