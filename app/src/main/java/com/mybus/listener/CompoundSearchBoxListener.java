package com.mybus.listener;

/**
 * Created by ldimitroff on 03/06/16.
 */
public interface CompoundSearchBoxListener {

    /**
     * Click on the From TextView
     */
    void onFromClick();

    /**
     * Click on the To TextView
     */
    void onToClick();

    /**
     * Click on the Drawer Toggle
     */
    void onDrawerToggleClick();

    /**
     * Click on the Flip Search
     */
    void onFlipSearchClick();
}
