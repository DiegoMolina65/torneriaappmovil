package com.interfaceae.torneriaproyecto;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import java.util.ArrayList;
import java.util.List;

public class CarritoDialogFragment extends DialogFragment {
    private Carrito carrito;
    private List<Servicio> servicios;

    public CarritoDialogFragment(Carrito carrito) {
        this.carrito = carrito;
        // Hacer una copia de los servicios para evitar modificar la lista original
        this.servicios = new ArrayList<>(carrito.getServicios());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        StringBuilder message = new StringBuilder();

        for (Servicio servicio : servicios) {
            message.append(servicio.getNombre()).append(": $").append(servicio.getCosto()).append("\n");
        }

        message.append("\nTotal: $").append(carrito.obtenerCostoTotal());

        builder.setMessage(message.toString())
                .setTitle("Carrito")
                .setPositiveButton("Eliminar servicio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mostrarDialogoEliminar();
                    }
                })
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cierra el diálogo
                    }
                })
                .setNeutralButton("Confirmar compra", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmarCompra();
                    }
                });

        // Establecer el estilo del diálogo
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(createDialogBackground());
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                setDialogStyle((AlertDialog) dialog);
            }
        });

        return dialog;
    }

    private void mostrarDialogoEliminar() {
        final List<Servicio> servicios = carrito.getServicios();

        // Crear un arreglo de nombres de servicios para mostrar en el diálogo
        String[] nombresServicios = new String[servicios.size()];
        for (int i = 0; i < servicios.size(); i++) {
            nombresServicios[i] = servicios.get(i).getNombre();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Eliminar servicio")
                .setItems(nombresServicios, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarServicio(which);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarServicio(int index) {
        if (index >= 0 && index < servicios.size()) {
            Servicio servicioEliminado = servicios.remove(index);
            carrito.eliminarServicio(servicioEliminado);
            // Actualizar la vista del diálogo con la lista actualizada de servicios
            actualizarVistaDialogo();
        }
    }

    private void actualizarVistaDialogo() {
        StringBuilder message = new StringBuilder();

        for (Servicio servicio : servicios) {
            message.append(servicio.getNombre()).append(": $").append(servicio.getCosto()).append("\n");
        }

        message.append("\nTotal: $").append(carrito.obtenerCostoTotal());

        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            dialog.setMessage(message.toString());
        }
    }


    private void confirmarCompra() {
        Intent intent = new Intent(getActivity(), ConfirmarCompraActivity.class);
        // Pasa cualquier información necesaria a la actividad de confirmación de compra
        intent.putExtra("costo_total", carrito.obtenerCostoTotal());
        startActivity(intent);
    }

    private Drawable createDialogBackground() {
        float[] outerRadii = new float[]{16, 16, 16, 16, 16, 16, 16, 16};
        RoundRectShape roundRectShape = new RoundRectShape(outerRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
        shapeDrawable.getPaint().setColor(Color.parseColor("#9BABB8"));
        return shapeDrawable;
    }

    private void setDialogStyle(AlertDialog dialog) {
        Drawable overlay = new ColorDrawable(Color.parseColor("#4F709C"));
        Drawable background = dialog.getWindow().getDecorView().getBackground();
        Drawable[] layers = {background, overlay};
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        dialog.getWindow().setBackgroundDrawable(layerDrawable);
    }
}

