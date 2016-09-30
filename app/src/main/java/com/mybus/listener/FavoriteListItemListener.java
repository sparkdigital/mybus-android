package com.mybus.listener;

/**
 * Created by Lucas De Lio on 6/10/2016.
 */
public interface FavoriteListItemListener {
    void onFavoriteItemEdit(int position);
    void onFavoriteItemDelete(int position);
    void onFavoriteClicked(int position);
}