package com.cristhoper.buslocator.repositories;

import com.cristhoper.buslocator.models.BusStop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 15/10/2017.
 */

public class BusStopRepository {

    private static List<BusStop> busStopList;

    static {
        busStopList = new ArrayList<>();
        busStopList.add(new BusStop(-12.2407346, -76.9229523, "My home"));
    }

    public static List<BusStop> getBusStopList() {
        return busStopList;
    }
}
