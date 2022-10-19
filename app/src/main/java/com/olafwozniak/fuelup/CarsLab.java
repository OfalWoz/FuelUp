package com.olafwozniak.fuelup;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class CarsLab
{

    private static CarsLab sCarsLab;

    public static List<Cars> lCars;
    private DBHandler mDbHandler;

    public int getSize()
    {
        return lCars.size();
    }

    public static CarsLab get(Context context)
    {
        if(sCarsLab == null)
        {
            sCarsLab = new CarsLab(context);
        }
        return sCarsLab;
    }

    private CarsLab(Context context)
    {
        lCars = new ArrayList<>();
    }

    public Cars getCars(int id) {
        for (Cars car : lCars) {
            if (car.getId() == id) {
                return car;
            }
        }
        return null;
    }

    public List<Cars> getCars()
    {
        return lCars;
    }

    public void deleteCar(int id)
    {
        int i = 0;
        for(Cars car: lCars)
        {
            if(car.getId() == id)
            {
                lCars.remove(car);
                break;
            }
        }
    }

    public void newCar(Cars car)
    {
        car.setId(lCars.size());
        car.setTitle("Click to set");
        car.setLicentNumber("Licent Number");
        car.setType("Gas");
        lCars.add(car);
    }
}
