package com.cristhoper.buslocator.repositories;

import com.cristhoper.buslocator.R;
import com.cristhoper.buslocator.models.Transport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 2/10/2017.
 */

public class TransportRepository {
    private static List<Transport> transports;

    static {
        transports = new ArrayList<>();
        transports.add(new Transport("Etuchisa", R.drawable.bg_etuchisa));
        transports.add(new Transport("El Rápido", R.drawable.bg_elrapido));
    }

    public static List<Transport> getTransports(){
        return transports;
    }
}
