package com.mybus.listener;

/**
 * Created by Lucas De Lio on 6/14/2016.
 */
public interface FavoriteAddOrEditListener {

    void onAddNewFavorite(String favoriteName);

    void onChangeFavoriteName(String favoriteName);
}
