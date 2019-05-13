package com.company.spsolutions.gestiongasto.Gastos;
/**
 * Created by coralRodriguez on 28/03/19.
 */

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;
import com.company.spsolutions.gestiongasto.SolicitudGasto.FragmentSolicitudes;

public class GastosActivity extends AppCompatActivity implements PresenterGastos {
    /*
     * Esta clase es la vista principal de los gastos controla la vista de las gastos actuales y pasados
     * displayError se encarga de colocar error en caso que exista
     */

    private FragmentGastos.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    //declaracion de variables de la barra agregada
    Usuario usuario;
    Empresa empresa;
    TextView usuarioTV, rolTV, empresaTV,tituloTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gastos);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addGastos_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarGasto();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbarGastos);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new FragmentGastos.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        initComponents();

    }

    private void initComponents() {
        usuarioTV = findViewById(R.id.usuarioas_tv);
        tituloTV = findViewById(R.id.titulo);
        rolTV = findViewById(R.id.rol_tv);
        empresaTV = findViewById(R.id.empresa_tv);


        usuario = Usuario.getInstance();
        empresa = Empresa.getInstance();

        tituloTV.setText("Gastos");
        usuarioTV.setText(usuario.getNombre());

        rolTV.setText(usuario.getRol());
        empresaTV.setText(empresa.getNombre());

    }
    /*
     * Método usado para agregar gasto al presionar el boton flotante
     * 1. Agregar intent
     */
    public void agregarGasto() {
        Intent addgasto = new Intent(GastosActivity.this, AddGastoActivity.class);
        startActivity(addgasto);
    }

    /*
     * Crear las opciones del menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /*
     * Crear las acciones de las opciones del menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void displayTicketResults(String amount, String date) {

    }

    @Override
    public void changeActivity() {

    }

    @Override
    public void setError(Integer type, String text) {

    }

    @Override
    public void displayProgress(Boolean isDisplayed, String texto) {

    }

}
