package com.mybus.listener;

/**
 * Created by ldimitroff on 03/06/16.
 */
public interface CompoundSearchBoxListener {

    /**
     * Click on the From TextView
     */
    void onFromClick(String address);

    /**
     * Click on the To TextView
     */
    void onToClick(String address);

    /**
     * Click on the Drawer Toggle
     */
    void onBackArrowClick();

    /**
     * Click on the Flip Search
     */
    void onFlipSearchClick();

    /**
     * Click on the Search Icon
     */
    void onSearchButtonClick();
}
