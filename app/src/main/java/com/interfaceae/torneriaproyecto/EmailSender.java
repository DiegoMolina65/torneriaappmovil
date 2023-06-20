package com.interfaceae.torneriaproyecto;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    public static void enviarCorreo(final Activity activity, final String destinatario, final String asunto, final String mensaje, final String numeroTelefono) {

        final String correo = "diegobrian042@gmail.com"; // tu correo
        final String contrasena = "nnlbtchpzfjluhsm"; // tu contraseña

        Thread enviar = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");

                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(correo, contrasena);
                        }
                    });

                    MimeMessage message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(correo));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                    message.setSubject(asunto);

                    // Crear contenido HTML del mensaje
                    String htmlContent = "<html><body style='background-color:#F4F4F4;'>";
                    htmlContent += "<div style='text-align:right;padding:20px;'>";
                    htmlContent += "<img src='@drawable/logotorneria' alt='Logo' style='height:80px;'>";
                    htmlContent += "</div>";
                    htmlContent += "<div style='padding:20px;'>";
                    htmlContent += "<h3>Detalles de la compra</h3>";
                    htmlContent += "<p>Servicios solicitados:</p>";
                    htmlContent += "<ul>";
                    // Agregar los detalles de los servicios seleccionados
                    Carrito carrito = Carrito.getInstance(); // Obtener instancia de Carrito
                    List<Servicio> serviciosSeleccionados = carrito.getServicios();
                    for (Servicio servicio : serviciosSeleccionados) {
                        htmlContent += "<li>" + servicio.getNombre() + " - $" + servicio.getCosto() + "</li>";
                    }
                    htmlContent += "</ul>";
                    htmlContent += "<p>Costo total: $" + carrito.obtenerCostoTotal() + "</p>";
                    htmlContent += "<p>Mensaje extra: " + mensaje + "</p>";
                    htmlContent += "<p>Número de teléfono: " + numeroTelefono + "</p>";
                    htmlContent += "</div>";
                    htmlContent += "</body></html>";

                    message.setContent(htmlContent, "text/html; charset=utf-8");

                    Transport.send(message);

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (final MessagingException e) {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "Error al enviar correo: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        enviar.start();
    }
}