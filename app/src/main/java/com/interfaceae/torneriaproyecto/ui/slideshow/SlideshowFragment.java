package com.interfaceae.torneriaproyecto.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.interfaceae.torneriaproyecto.R;
import com.interfaceae.torneriaproyecto.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap mMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contacto, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
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
    }
}
