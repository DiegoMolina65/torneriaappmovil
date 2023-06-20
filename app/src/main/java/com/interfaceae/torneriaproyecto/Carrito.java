package com.interfaceae.torneriaproyecto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Carrito implements Serializable {
    private static Carrito instance;
    private List<Servicio> servicios;

    private Carrito() {
        this.servicios = new ArrayList<>();
    }

    public static Carrito getInstance() {
        if (instance == null) {
            instance = new Carrito();
        }
        return instance;
    }

    public void agregarServicio(Servicio servicio) {
        this.servicios.add(servicio);
    }


    public double obtenerCostoTotal() {
        double total = 0.0;
        for (Servicio servicio : this.servicios) {
            total += servicio.getCosto();
        }
        return total;
    }

    public List<Servicio> getServicios() {
        return this.servicios;
    }

    public void eliminarServicio(Servicio servicio) {
        this.servicios.remove(servicio);
    }
}