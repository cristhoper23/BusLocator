package com.cristhoper.buslocator.models;

/**
 * Created by Cris on 15/10/2017.
 */

public class BusStop {
    private String id_paradero;
    private String nombre;
    private float latitud;
    private float longitud;

    public String getId_paradero() {
        return id_paradero;
    }

    public void setId_paradero(String id_paradero) {
        this.id_paradero = id_paradero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return "BusStop{" +
                "id_paradero='" + id_paradero + '\'' +
                ", nombre='" + nombre + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
