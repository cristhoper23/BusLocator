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
        busStopList.add(new BusStop(-12.2396649, -76.928194, "Av. Forestal"));
        busStopList.add(new BusStop(-12.2391815, -76.9287076, "Oasis 1"));
        busStopList.add(new BusStop(-12.2353719, -76.9350211, "Oasis 2"));
        busStopList.add(new BusStop(-12.2339414, -76.9348312, "Av. Pastor Sevilla"));
        busStopList.add(new BusStop(-12.2311157, -76.9376556, "Av. Talara"));
        busStopList.add(new BusStop(-12.2287509, -76.9390645, "Av. Olaya"));
        busStopList.add(new BusStop(-12.2250500, -76.9405168, "Av. Mariátgui"));
        busStopList.add(new BusStop(-12.2234635, -76.9422505, "Av. 3 de Octubre"));
        busStopList.add(new BusStop(-12.2186482, -76.9451071, "Av. Vallejo"));
        busStopList.add(new BusStop(-12.2158451, -76.9467828, "Av. Jorge Chavez"));
    }

    public static List<BusStop> getBusStopList() {
        return busStopList;
    }
}
