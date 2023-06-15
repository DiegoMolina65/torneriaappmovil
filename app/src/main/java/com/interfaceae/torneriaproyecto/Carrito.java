package com.interfaceae.torneriaproyecto;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Servicio> servicios;

    public Carrito() {
        this.servicios = new ArrayList<>();
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