package com.olafwozniak.fuelup;

public class Fuels {
    private int iId;
    private String sTitle;
    private String sLiters;
    private String sType;
    private String sPriceFuel;
    private String sTotalPrice;
    private String sMileage;
    private String carID;

    public int getIdFuel() {
        return iId;
    }

    public void setIdFuel(int mId) {
        this.iId = mId;
    }

    public void setTitleFuel(String s) {
        this.sTitle = s;
    }

    public String getTitleFuel() {
        return sTitle;
    }

    public void setLiters(String s) {
        this.sLiters = s;
    }

    public String getLiters() {
        return sLiters;
    }

    public void setTypeFuel(String s) { this.sType = s; }

    public String getTypeFuel() {
        return sType;
    }

    public void setsPriceFuel(String s) {
        this.sPriceFuel = s;
    }

    public String getsPriceFuel() {
        return sPriceFuel;
    }

    public void setsTotalPrice(String s) { this.sTotalPrice = s; }

    public String getsTotalPrice() { return sTotalPrice; }

    public void setCarID(String i) { this.carID = i; }

    public String  getCarID() {return carID; }

    public void setsMileage(String i) { this.sMileage = i; }

    public String getiMileage() {return sMileage; }
}
