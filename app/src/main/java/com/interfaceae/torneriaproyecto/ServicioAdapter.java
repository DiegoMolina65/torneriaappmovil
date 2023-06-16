package com.interfaceae.torneriaproyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ServicioAdapter extends ArrayAdapter<Servicio> {
    private final Context context;
    private final List<Servicio> servicios;
    public ServicioAdapter(Context context, List<Servicio> servicios) {
        super(context, 0, servicios);
        this.context = context;
        this.servicios = servicios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.servicio_list_item, parent, false);
        }

        Servicio servicio = servicios.get(position);

        TextView nombreServicioTextView = view.findViewById(R.id.nombre_servicio);
        TextView costoServicioTextView = view.findViewById(R.id.costo_servicio);

        nombreServicioTextView.setText(servicio.getNombre());
        costoServicioTextView.setText("$" + servicio.getCosto());

        return view;
    }
}