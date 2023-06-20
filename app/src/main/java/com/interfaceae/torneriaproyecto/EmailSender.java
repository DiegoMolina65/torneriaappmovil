package com.interfaceae.torneriaproyecto;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;
import java.util.Properties;

import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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
                    StringBuilder mensajeBuilder = new StringBuilder();
                    mensajeBuilder.append("<html><body style='background-color:#8E0B0B;color:#FFFFFF;font-family:Arial, sans-serif;'>");
                    mensajeBuilder.append("<div style='text-align:right;padding:20px;'>");
                    mensajeBuilder.append("<img src=\"https://cdn-icons-png.flaticon.com/512/6344/6344693.png\" alt=\"Logo\" height=\"40px\">");
                    mensajeBuilder.append("</div>");
                    mensajeBuilder.append("<div style='padding:20px;'>");
                    mensajeBuilder.append("<h3 style='color:#FFFFFF;font-weight:bold;font-size:24px;'>Detalles de la compra</h3>");
                    mensajeBuilder.append("<p style='color:#FFFFFF;font-weight:bold;font-size:18px;'>Servicios solicitados:</p>");
                    mensajeBuilder.append("<ul style='color:#FFFFFF;font-size:16px;'>");
                    // Agregar los detalles de los servicios seleccionados
                    Carrito carrito = Carrito.getInstance(); // Obtener instancia de Carrito
                    List<Servicio> serviciosSeleccionados = carrito.getServicios();
                    for (Servicio servicio : serviciosSeleccionados) {
                        mensajeBuilder.append("<li>").append(servicio.getNombre()).append(" - $").append(servicio.getCosto()).append("</li>");
                    }
                    mensajeBuilder.append("</ul>");
                    mensajeBuilder.append("<p style='color:#FFFFFF;font-weight:bold;font-size:18px;'>Costo total: $").append(carrito.obtenerCostoTotal()).append("</p>");
                    mensajeBuilder.append("<p style='color:#FFFFFF;font-weight:bold;font-size:18px;'>Mensaje extra: ").append(mensaje).append("</p>");
                    mensajeBuilder.append("<p style='color:#FFFFFF;font-weight:bold;font-size:18px;'>Teléfono: ").append(numeroTelefono).append("</p>");
                    mensajeBuilder.append("</div>");
                    mensajeBuilder.append("</body></html>");

                    message.setContent(mensajeBuilder.toString(), "text/html; charset=utf-8");

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