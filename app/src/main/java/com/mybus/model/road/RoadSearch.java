package com.mybus.model.road;

public class RoadSearch {
    private String mIdLine = null;
    private String mIdLine2 = null;
    private String mDirection = null;
    private String mDirection2 = null;
    private String mStop1 = null;
    private String mStop2 = null;
    private String mStop1L2 = null;
    private String mStop2L2 = null;

    public RoadSearch() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public RoadSearch(String mIdLine, String mDirection, String mStop1, String mStop2) {
        this.mIdLine = mIdLine;
        this.mDirection = mDirection;
        this.mStop1 = mStop1;
        this.mStop2 = mStop2;
    }

    public RoadSearch(String mIdLine, String mIdLine2, String mDirection, String mDirection2,
                      String mStop1, String mStop2, String mStop1L2, String mStop2L2) {
        this.mIdLine = mIdLine;
        this.mIdLine2 = mIdLine2;
        this.mDirection = mDirection;
        this.mDirection2 = mDirection2;
        this.mStop1 = mStop1;
        this.mStop2 = mStop2;
        this.mStop1L2 = mStop1L2;
        this.mStop2L2 = mStop2L2;
    }

    public String getmIdLine() {
        return mIdLine;
    }

    public void setmIdLine(String mIdLine) {
        this.mIdLine = mIdLine;
    }

    public String getmIdLine2() {
        return mIdLine2;
    }

    public void setmIdLine2(String mIdLine2) {
        this.mIdLine2 = mIdLine2;
    }

    public String getmDirection() {
        return mDirection;
    }

    public void setmDirection(String mDirection) {
        this.mDirection = mDirection;
    }

    public String getmDirection2() {
        return mDirection2;
    }

    public void setmDirection2(String mDirection2) {
        this.mDirection2 = mDirection2;
    }

    public String getmStop1() {
        return mStop1;
    }

    public void setmStop1(String mStop1) {
        this.mStop1 = mStop1;
    }

    public String getmStop2() {
        return mStop2;
    }

    public void setmStop2(String mStop2) {
        this.mStop2 = mStop2;
    }

    public String getmStop1L2() {
        return mStop1L2;
    }

    public void setmStop1L2(String mStop1L2) {
        this.mStop1L2 = mStop1L2;
    }

    public String getmStop2L2() {
        return mStop2L2;
    }

    public void setmStop2L2(String mStop2L2) {
        this.mStop2L2 = mStop2L2;
    }
}
