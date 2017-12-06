package com.cristhoper.buslocator.models;

/**
 * Created by Cris on 2/10/2017.
 */

public class Transport {
    private int id_empresa;
    private String nombre;
    private String id_ruta;
    private String descripcion;
    private String imagen;

    public int getId_empresa() {
        return id_empresa;
    }

    public void setId_empresa(int id_empresa) {
        this.id_empresa = id_empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(String id_ruta) {
        this.id_ruta = id_ruta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "nombre='" + nombre + '\'' +
                ", id_ruta='" + id_ruta + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}
