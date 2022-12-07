package com.olafwozniak.fuelup;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class FuelsLab {
    private static FuelsLab sFuelsLab;

    public static List<Fuels> lFuels;
    private DBHandler mDbHandler;

    public int getSize() {
        return lFuels.size();
    }

    public static FuelsLab get(Context context) {
        if (sFuelsLab == null) {
            sFuelsLab = new FuelsLab(context);
        }
        return sFuelsLab;
    }

    private FuelsLab(Context context) {
        lFuels = new ArrayList<>();
    }

    public Fuels getFuels(int id) {
        for (Fuels fuels : lFuels) {
            if (fuels.getIdFuel() == id) {
                return fuels;
            }
        }
        return null;
    }

    public List<Fuels> getFuels() {
        return lFuels;
    }

    public void deleteFuel(int id) {
        for (Fuels fuel : lFuels) {
            if (fuel.getIdFuel() == id) {
                lFuels.remove(fuel);
                break;
            }
        }
    }

    public void newFuel(Fuels fuel) {
        fuel.setIdFuel(lFuels.size());
        fuel.setTitleFuel("New Fuel #" + (int) (fuel.getIdFuel() + 1));
        fuel.setsPriceFuel("");
        fuel.setLiters("");
        fuel.setsTotalPrice("1");
        fuel.setTypeFuel("");
        lFuels.add(fuel);
    }
}
