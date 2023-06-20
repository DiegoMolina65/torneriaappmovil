package com.interfaceae.torneriaproyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        if (sharedPreferences.contains("email")) {
            Intent intent = new Intent(MainActivity.this, principalmain.class);
            startActivity(intent);
            finish();
        }

        dbHelper = new DataBaseHelper(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonPregistro = findViewById(R.id.buttonPregistro);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    new CheckUserCredentialsTask().execute(email, password);
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
    }

    private class CheckUserCredentialsTask extends AsyncTask<String, Void, Boolean> {
        String email;

        @Override
        protected Boolean doInBackground(String... details) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            email = details[0];

            Cursor cursor = db.query(
                    DataBaseHelper.TABLE_NAME,
                    null,
                    DataBaseHelper.COLUMN_EMAIL + "=? AND " + DataBaseHelper.COLUMN_PASSWORD + "=?",
                    details,
                    null,
                    null,
                    null
            );
            int count = cursor.getCount();
            cursor.close();

            return count > 0;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                String username = dbHelper.getUserName(email);
                editor.putString("username", username);
                editor.apply();

                Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, principalmain.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(MainActivity.this, "Correo electrónico o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
