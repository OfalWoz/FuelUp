package com.olafwozniak.fuelup;

import android.text.BoringLayout;

import java.util.Date;

public class Fuels {
    private int iId;
    private String sTitle;
    private Float sLiters;
    private String sType;
    private Float sPriceFuel;
    private Float sTotalPrice;
    private Float sMileage;
    private String carID;
    private Date mDate;
    private Boolean bFull;
    private Float fAvergeLiters;
    private Float fAvergeCost;

    public int getIdFuel() {
        return iId;
    }

    public void setIdFuel(int mId) {
        this.iId = mId;
    }

    public void setTitleFuel(String s) { this.sTitle = s; }

    public String getTitleFuel(){ return sTitle; }

    public void setLiters(Float s) {
        this.sLiters = s;
    }

    public Float getLiters() {
        return sLiters;
    }

    public void setTypeFuel(String s) { this.sType = s; }

    public String getTypeFuel() {
        return sType;
    }

    public void setsPriceFuel(Float s) {
        this.sPriceFuel = s;
    }

    public Float getsPriceFuel() {
        return sPriceFuel;
    }

    public void setsTotalPrice(Float s) { this.sTotalPrice = s; }

    public Float getsTotalPrice() { return sTotalPrice; }

    public void setCarID(String i) { this.carID = i; }

    public String  getCarID() {return carID; }

    public void setsMileage(Float i) { this.sMileage = i; }

    public Float getiMileage() {return sMileage; }

    public void setDate(Date date) { this.mDate = date; }

    public Date getDate() { return mDate; }

    public void setbFull(Boolean full) { this.bFull = full; }

    public Boolean getbFull() { return bFull; }

    public void setfAvergeLiters(Float liters) { this.fAvergeLiters = liters; }

    public Float getfAvergeLiters() { return fAvergeLiters; }

    public void setfAvergeCost(Float cost) { this.fAvergeCost = cost; }

    public Float getfAvergeCost() { return fAvergeCost; }
}
