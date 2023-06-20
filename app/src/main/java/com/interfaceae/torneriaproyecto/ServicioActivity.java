package com.interfaceae.torneriaproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

// Clase ServicioActivity
public class ServicioActivity extends AppCompatActivity {
    private Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);

        carrito = Carrito.getInstance();

        // Definir los servicios
        final Servicio servicio1 = new Servicio("Construcción, preparación de máquinas y equipos de procesos industriales", "...", 100);
        final Servicio servicio2 = new Servicio("Trabajos de mantenimiento y preparaciones en fábrica", "...", 150);
        final Servicio servicio3 = new Servicio("Mantenimiento eléctrico", "...", 200);

        // Encontrar los botones por ID y configurar los listeners
        Button buttonSolicitar1 = findViewById(R.id.buttonSoliciar1);
        buttonSolicitar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito.agregarServicio(servicio1);
            }
        });

        Button buttonSolicitar2 = findViewById(R.id.buttonSoliciar2);
        buttonSolicitar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito.agregarServicio(servicio2);
            }
        });

        Button buttonSolicitar3 = findViewById(R.id.buttonSoliciar3);
        buttonSolicitar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito.agregarServicio(servicio3);
            }
        });

        // No hay más código para el cart_button aquí
     /*   ImageButton cartButton = findViewById(R.id.cart_button);
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarritoDialogFragment carritoDialog = new CarritoDialogFragment(carrito);
                carritoDialog.show(getSupportFragmentManager(), "CarritoDialog");
            }
        });*/
    }
}
