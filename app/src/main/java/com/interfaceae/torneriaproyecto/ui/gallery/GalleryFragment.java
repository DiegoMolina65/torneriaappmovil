package com.interfaceae.torneriaproyecto.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.interfaceae.torneriaproyecto.Carrito;
import com.interfaceae.torneriaproyecto.R;
import com.interfaceae.torneriaproyecto.Servicio;

public class GalleryFragment extends Fragment {

    private View view;
    private Carrito carrito;

    private Button buttonSolicitar1;
    private Button buttonSolicitar2;
    private Button buttonSolicitar3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_servicio, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        carrito = Carrito.getInstance();

        // Definir los servicios
        final Servicio servicio1 = new Servicio("Construcción, preparación de máquinas y equipos de procesos industriales", "...", 100);
        final Servicio servicio2 = new Servicio("Trabajos de mantenimiento y preparaciones en fábrica", "...", 150);
        final Servicio servicio3 = new Servicio("Mantenimiento eléctrico", "...", 200);

        // Encontrar los botones por ID y configurar los listeners
        buttonSolicitar1 = view.findViewById(R.id.buttonSoliciar1);
        buttonSolicitar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito.agregarServicio(servicio1);
                showToastMessage("Servicio 1 agregado a su carrito.");
                buttonSolicitar1.setEnabled(false);
                buttonSolicitar2.setEnabled(true);
                buttonSolicitar3.setEnabled(true);
            }
        });

        buttonSolicitar2 = view.findViewById(R.id.buttonSoliciar2);
        buttonSolicitar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito.agregarServicio(servicio2);
                showToastMessage("Servicio 2 agregado a su carrito.");
                buttonSolicitar1.setEnabled(true);
                buttonSolicitar2.setEnabled(false);
                buttonSolicitar3.setEnabled(true);
            }
        });

        buttonSolicitar3 = view.findViewById(R.id.buttonSoliciar3);
        buttonSolicitar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito.agregarServicio(servicio3);
                showToastMessage("Servicio 3 agregado a su carrito.");
                buttonSolicitar1.setEnabled(true);
                buttonSolicitar2.setEnabled(true);
                buttonSolicitar3.setEnabled(false);
            }
        });
    }

    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
