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
        transports.add(new Transport("Etuchisa", "1802", "Pte. Piedra - Villa El Salvador", R.mipmap.ic_launcher));
        transports.add(new Transport("El Rápido", "2411", "San Martín de Porres - Lurigancho", R.mipmap.ic_launcher));
    }

    public static List<Transport> getTransports(){
        return transports;
    }
}
