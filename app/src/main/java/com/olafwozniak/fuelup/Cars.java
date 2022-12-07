package com.olafwozniak.fuelup;

public class Cars {
    private int iId;
    private String sTitle;
    private String sLicentNumber;
    private String sType;
    private String sActive;

    public int getId() { return iId; }

    public void setId(int mId) { this.iId = mId; }

    public void setTitle(String s) { this.sTitle = s; }

    public String getTitle() { return sTitle; }

    public void setLicentNumber(String s) { this.sLicentNumber = s; }

    public String getLicentNumber() { return sLicentNumber; }

    public void setType(String type) { this.sType = type; }

    public String getType() { return sType; }

    public void setsActive(String active) {this.sActive = active;}

    public String getsActive() {return sActive;}
}

