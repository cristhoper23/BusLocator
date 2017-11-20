package com.cristhoper.buslocator.models;

/**
 * Created by Cris on 2/10/2017.
 */

public class Transport {
    private String empresa;
    private String id_ruta;
    private String desc_ruta;
    private int icon_bus;

    public Transport(){

    }

    public Transport(String empresa, String id_ruta, String desc_ruta, int icon_bus) {
        this.empresa = empresa;
        this.id_ruta = id_ruta;
        this.desc_ruta = desc_ruta;
        this.icon_bus = icon_bus;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(String id_ruta) {
        this.id_ruta = id_ruta;
    }

    public String getDesc_ruta() {
        return desc_ruta;
    }

    public void setDesc_ruta(String desc_ruta) {
        this.desc_ruta = desc_ruta;
    }

    public int getIcon_bus() {
        return icon_bus;
    }

    public void setIcon_bus(int icon_bus) {
        this.icon_bus = icon_bus;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "empresa='" + empresa + '\'' +
                ", id_ruta='" + id_ruta + '\'' +
                ", desc_ruta='" + desc_ruta + '\'' +
                ", icon_bus=" + icon_bus +
                '}';
    }
}
