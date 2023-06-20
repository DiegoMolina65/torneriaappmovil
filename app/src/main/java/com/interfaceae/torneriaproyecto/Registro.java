package com.interfaceae.torneriaproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Bundle;

public class Registro extends AppCompatActivity {
    DataBaseHelper dbHelper;
    EditText editTextNombre, editTextApellido, editTextEmail, editTextPassword;
    Button buttonRegistrar;
    Button buttonVolveratras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        dbHelper = new DataBaseHelper(this);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);
        buttonVolveratras = findViewById(R.id.buttonVolveratras);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Registro.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(nombre, apellido, email, password);
                    Toast.makeText(Registro.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Registro.this, principalmain.class);
                    startActivity(intent);
                }
            }
        });

        buttonVolveratras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void registerUser(String name, String surname, String email, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.COLUMN_NAME, name);
        values.put(DataBaseHelper.COLUMN_SURNAME, surname);
        values.put(DataBaseHelper.COLUMN_EMAIL, email);
        values.put(DataBaseHelper.COLUMN_PASSWORD, password);

        long newRowId = db.insert(DataBaseHelper.TABLE_NAME, null, values);
    }
}