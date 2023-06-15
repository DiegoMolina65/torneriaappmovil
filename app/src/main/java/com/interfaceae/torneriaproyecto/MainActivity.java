package com.interfaceae.torneriaproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonPregistro;
    DataBaseHelper dbHelper;
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    Button buttonInvitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DataBaseHelper(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonPregistro = findViewById(R.id.buttonPregistro);
        buttonInvitado = findViewById(R.id.buttonInvitado);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if(checkUserCredentials(email, password)){
                        Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                        // Iniciar nueva actividad
                        Intent intent = new Intent(MainActivity.this, Informacion.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonPregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });

        buttonInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Informacion.class);
                startActivity(intent);
            }
        });

    }

    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                DataBaseHelper.TABLE_NAME,
                null,
                DataBaseHelper.COLUMN_EMAIL + "=? AND " + DataBaseHelper.COLUMN_PASSWORD + "=?",
                new String[]{email, password},
                null,
                null,
                null
        );
        if(cursor.getCount() > 0) {
            cursor.close();
            return true;    // Las credenciales del usuario son correctas
        } else {
            cursor.close();
            return false;   // Las credenciales del usuario son incorrectas
        }
    }

}



