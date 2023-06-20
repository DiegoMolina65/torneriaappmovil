package com.interfaceae.torneriaproyecto;

import android.app.Application;

public class MyApplication extends Application {
    private Carrito carrito;

    @Override
    public void onCreate() {
        super.onCreate();
        carrito = Carrito.getInstance();
    }

    public Carrito getCarrito() {
        return carrito;
    }
}
