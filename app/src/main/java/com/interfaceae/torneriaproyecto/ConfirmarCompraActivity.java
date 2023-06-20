package com.interfaceae.torneriaproyecto;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class ConfirmarCompraActivity extends AppCompatActivity {
    private Carrito carrito;
    private ServicioAdapter servicioAdapter;
    private TextView textTotal;
    private ImageButton Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacionsolicitud);

        carrito = Carrito.getInstance();

        ListView listView = findViewById(R.id.list_servicios);
        textTotal = findViewById(R.id.text_total);

        servicioAdapter = new ServicioAdapter(this, carrito.getServicios());
        listView.setAdapter(servicioAdapter);

        actualizarTotal();

        Button buttonFinalizarCompra = findViewById(R.id.button_finalizar_compra);
        buttonFinalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfirmarCompraActivity.this, "Compra exitosa", Toast.LENGTH_SHORT).show();

                // Obtener servicios seleccionados
                List<Servicio> serviciosSeleccionados = carrito.getServicios();

                // Obtener costo total
                double costoTotal = carrito.obtenerCostoTotal();

                // Obtener mensaje extra
                EditText editMensaje = findViewById(R.id.edit_mensaje);
                String mensajeExtra = editMensaje.getText().toString();

                // Crear mensaje con los detalles de la compra
                StringBuilder mensajeBuilder = new StringBuilder();
                mensajeBuilder.append("Servicios solicitados:\n");
                for (Servicio servicio : serviciosSeleccionados) {
                    mensajeBuilder.append(servicio.getNombre());
                    mensajeBuilder.append(" - ");
                    mensajeBuilder.append(servicio.getCosto());
                    mensajeBuilder.append("\n");
                }
                mensajeBuilder.append("Costo total: $").append(costoTotal).append("\n");
                mensajeBuilder.append("Mensaje extra: ").append(mensajeExtra);

                // Enviar correo con los detalles de la compra
                enviarCorreo(mensajeBuilder.toString());

                // Redirigir a la pantalla ServicioActivity
                Intent intent = new Intent(ConfirmarCompraActivity.this, principalmain.class);
                startActivity(intent);
            }
        });
        // Set up the back button
        Volver = findViewById(R.id.back_button);
        Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void actualizarTotal() {
        double costoTotal = carrito.obtenerCostoTotal();
        textTotal.setText("Total: $" + costoTotal);
    }

    private void enviarCorreo(String mensaje) {
        String destinatario = "torneriamontero21@gmail.com";
        String asunto = "Detalles de la compra";

        EmailSender.enviarCorreo(this, destinatario, asunto, mensaje);
    }
}
