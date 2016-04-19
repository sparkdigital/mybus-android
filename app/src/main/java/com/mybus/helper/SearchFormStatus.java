package com.mybus.helper;

/**
 * Singleton class to keep track of what has been entered on the Search Form
 *
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class SearchFormStatus {

    private static SearchFormStatus instance = null;

    private boolean mIsStartFilled = false;
    private boolean mIsDestinationFilled = false;
    private String mStartMarkerId;

    /**
     * Default constructor
     */
    private SearchFormStatus() {
    }

    public static SearchFormStatus getInstance() {
        if (instance == null) {
            instance = new SearchFormStatus();
        }
        return instance;
    }

    public boolean isDestinationFilled() {
        return mIsDestinationFilled;
    }

    public void setDestinationFilled(boolean mIsDestinationFilled) {
        this.mIsDestinationFilled = mIsDestinationFilled;
    }

    public boolean isStartFilled() {
        return mIsStartFilled;
    }

    public void setStartFilled(boolean startFilled) {
        mIsStartFilled = startFilled;
    }

    public boolean canMakeSearch() {
        return this.mIsStartFilled && this.mIsDestinationFilled;
    }

    public void setStartMarkerId(String id) {
        this.mStartMarkerId = id;
    }

    public String getStartMarkerId() {
        return this.mStartMarkerId;
    }

    /**
     * Clears the status of the form to make a new search
     */
    public void clearFormStatus() {
        this.mIsStartFilled = mIsDestinationFilled = false;
        this.mStartMarkerId = null;
    }
}
