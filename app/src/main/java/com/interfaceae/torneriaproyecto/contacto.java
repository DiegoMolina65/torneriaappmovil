package com.interfaceae.torneriaproyecto;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class contacto extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Crea un objeto LatLng con las coordenadas de Tornería Montero.
        LatLng TM = new LatLng(-17.349417393693535, -63.253402445030375);

        // Añade un marcador en esa posición.
        mMap.addMarker(new MarkerOptions().position(TM).title("Torneria Montero"));

        // Mueve la cámara a la posición del marcador.
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TM));

        // Mueve la cámara a la posición del marcador y ajusta el nivel de zoom.
        float zoomLevel = 15.0f; // Este es tu nivel de zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TM, zoomLevel));

        Log.d("Map", "Latitud: " + TM.latitude + ", Longitud: " + TM.longitude);
    }

}
