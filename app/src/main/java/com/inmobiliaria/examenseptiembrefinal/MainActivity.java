package com.inmobiliaria.examenseptiembrefinal;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.navigation.NavigationView;
import com.inmobiliaria.examenseptiembrefinal.databinding.ActivityMainBinding;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Producto> listaProductos = new ArrayList<>();

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cargar, R.id.nav_listar)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        setupNavigation(navigationView, drawer);
    }

    private void setupNavigation(NavigationView navigationView, DrawerLayout drawer) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_cargar || id == R.id.nav_listar) {
                    NavigationUI.onNavDestinationSelected(item, navController);
                    drawer.closeDrawers();
                    return true;
                } else if (id == R.id.nav_salir) {
                    drawer.closeDrawers();
                    mostrarDialogoSalida();
                    return true;
                }
                return false;
            }
        });
    }

    private void mostrarDialogoSalida() {
        new AlertDialog.Builder(this)
                .setTitle("Salir de la Aplicacion")
                .setMessage("¿Estas seguro de que quieres cerrar la aplicacion?")
                .setPositiveButton("Si", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_menu_exit)
                .show();
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}