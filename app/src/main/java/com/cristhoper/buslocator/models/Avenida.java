package com.cristhoper.buslocator.models;

/**
 * Created by Cris on 7/12/2017.
 */

public class Avenida {
    private String id_avenida;
    private float latitud;
    private float longitud;

    public String getId_avenida() {
        return id_avenida;
    }

    public void setId_avenida(String id_avenida) {
        this.id_avenida = id_avenida;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "Avenida{" +
                "id_avenida='" + id_avenida + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
