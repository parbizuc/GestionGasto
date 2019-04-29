package com.company.spsolutions.gestiongasto.MenuPrincipal;
/**
 * Created by coralRodriguez on 27/03/19.
 */

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.company.spsolutions.gestiongasto.Login.LoginActivity;
import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity {
    /*
     *Esta clase gestiona la vista principal del menu con las opciones
     * el método initComponents inicializa los componentes
     * el método setListeners coloca los listeners a cada opción de menú con su correspondiente intent y logica para mandar a la siguiente actividad
     *
     */
    private RecyclerView recyclerMenu;
    private MainAdapter mAdapter;
    private LinearLayoutManager layoutManager;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences user, company;

    /*
     * 1. Crear el menú y el buscador
     * 2. Cambiar los datos de la app bar (toolbar)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentSession();
        initComponents();

    }

    public void currentSession() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        company = getSharedPreferences("companydetails", MODE_PRIVATE);
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            if (Usuario.getInstance() == null) {
                Usuario.init(user.getString("rol", ""), user.getString("idU", ""), user.getString("nombreU", ""), user.getString("idE", ""), user.getString("nombreE", ""), user.getString("pais", ""));
                Empresa.init(company.getString("id", ""), company.getString("nombre", ""), company.getString("moneda", ""), company.getString("pais", ""));
                //logout();
            }
        }
    }

    public void initComponents() {
        recyclerMenu = findViewById(R.id.recycler_menu);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerMenu.setLayoutManager(layoutManager);
        mAdapter = new MainAdapter(getData(), getApplicationContext());
        recyclerMenu.setAdapter(mAdapter);
    }

    /*
     * Implementar la logica para realizar la busqueda
     */
    public void search() {
    }

    /*
     *  Modificar segun el perfil del usuario
     */
    public List<ItemMenu> getData() {
        List<ItemMenu> items = new ArrayList<ItemMenu>();
        items.add(new ItemMenu("Gestión de solicitudes", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlv7N1qgf_Al1YinpbuSLGwciK_e2r3EK3Ewjx78s8zBl88YPl"));
        items.add(new ItemMenu("Gestión de gastos", "https://cooperandoando.com/media/2017/12/19-Gastos.png"));
        items.add(new ItemMenu("Informe de gastos", "https://cooperandoando.com/media/2017/12/19-Gastos.png"));
        return items;
    }
    private void logout() {
        company.edit().clear().commit();
        user.edit().clear().commit();
        firebaseAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                // logout and return to login activity
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
