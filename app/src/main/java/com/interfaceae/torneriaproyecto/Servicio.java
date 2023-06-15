package com.interfaceae.torneriaproyecto;

public class Servicio {
    private String nombre;
    private String descripcion;
    private double costo;

    public Servicio(String nombre, String descripcion, double costo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCosto() {
        return costo;
    }
}