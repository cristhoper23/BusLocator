package com.cristhoper.buslocator.models;

/**
 * Created by Cris on 15/10/2017.
 */

public class BusStop {
    private String id_paradero;
    private String nombre;
    private double latitud;
    private double longitud;

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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
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
