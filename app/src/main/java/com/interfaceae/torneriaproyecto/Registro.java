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
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class Registro extends AppCompatActivity implements View.OnClickListener {
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

        buttonRegistrar.setOnClickListener(this);
        buttonVolveratras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonRegistrar:
                String nombre = editTextNombre.getText().toString();
                String apellido = editTextApellido.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(Registro.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    new RegisterUserTask().execute(nombre, apellido, email, password);
                }
                break;

            case R.id.buttonVolveratras:
                Intent intent = new Intent(Registro.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class RegisterUserTask extends AsyncTask<String, Void, Long> {
        String nombre, email;

        @Override
        protected Long doInBackground(String... details) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            nombre = details[0];
            email = details[2];

            ContentValues values = new ContentValues();
            values.put(DataBaseHelper.COLUMN_NAME, details[0]);
            values.put(DataBaseHelper.COLUMN_SURNAME, details[1]);
            values.put(DataBaseHelper.COLUMN_EMAIL, details[2]);
            values.put(DataBaseHelper.COLUMN_PASSWORD, details[3]);

            return db.insert(DataBaseHelper.TABLE_NAME, null, values);
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result != -1) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", nombre);
                editor.putString("email", email);
                editor.apply();

                Toast.makeText(Registro.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Registro.this, principalmain.class);
                startActivity(intent);
            } else {
                Toast.makeText(Registro.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
