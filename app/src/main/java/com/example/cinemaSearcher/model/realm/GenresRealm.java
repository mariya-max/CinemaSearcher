package com.example.cinemaSearcher.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GenresRealm extends RealmObject {

    @PrimaryKey
    private String nameGenres;

    public String getNameGenres() {
        return nameGenres;
    }

    public void setNameGenres(String nameGenres) {
        this.nameGenres = nameGenres;
    }
}
