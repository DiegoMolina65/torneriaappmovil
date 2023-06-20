package com.interfaceae.torneriaproyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.interfaceae.torneriaproyecto.databinding.ActivityPrincipalmainBinding;

public class principalmain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityPrincipalmainBinding binding;
    private Carrito carrito;
    private DataBaseHelper dbHelper;
    private TextView textViewUsername;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalmainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarPrincipalmain.toolbar);

        // Obtenemos la instancia de Carrito
        MyApplication app = (MyApplication) getApplication();
        carrito = app.getCarrito();

        // Inicializar DataBaseHelper
        dbHelper = new DataBaseHelper(this);

        // Obtener los TextView
        View headerView = binding.navView.getHeaderView(0);
        textViewUsername = headerView.findViewById(R.id.textViewUsername);
        textViewEmail = headerView.findViewById(R.id.textViewEmail);

        // Actualizar los datos del usuario
        actualizarDatosUsuario();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarritoDialogFragment dialogFragment = CarritoDialogFragment.newInstance(carrito);
                dialogFragment.show(getSupportFragmentManager(), "CarritoDialogFragment");
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principalmain);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principalmain, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principalmain);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            cerrarSesion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();

        Intent loginIntent = new Intent(principalmain.this, MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void actualizarDatosUsuario() {
        String username = dbHelper.obtenerUsername(this);
        String email = dbHelper.obtenerEmail(this);

        Log.d("Datos Usuario", "Username: " + username);
        Log.d("Datos Usuario", "Email: " + email);

        if (textViewUsername != null) {
            textViewUsername.setText(username);
        }

        if (textViewEmail != null) {
            textViewEmail.setText(email);
        }
    }
}
