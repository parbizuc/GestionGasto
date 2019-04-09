package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.R;

/**
 * Created by coralRodriguez on 28/03/19.
 */
public class SolicitudActivity extends AppCompatActivity implements PresenterSolicitud {
    /*
     * Esta clase es la vista principal de las solicitudes controla la vista de las solicitudes actuales y pasadas
     * displayError muestra un error en caso de ser necesario
     * displayloader muestra un loader para actualizar los datos
     * displaylabel hace cambios en el label de las solicitudes
     */
    private FragmentSolicitudes.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton addsolicitud_fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);
        addsolicitud_fab = findViewById(R.id.addSolicitud_fab);
        Toolbar toolbar = findViewById(R.id.toolbarSolicitud);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new FragmentSolicitudes.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        setListeners();
    }

    /*
     *Hacer la logica para ocultar el boton flotante según los perfiles
     */
    public void setListeners() {
        addsolicitud_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarSolicitud();
            }
        });
    }

    @Override
    public void displayError(String error) {

    }

    @Override
    public void displayLoader(boolean loader) {

    }

    @Override
    public void displayLabel() {

    }

    /*
     * Método usado para agregar solicitudes al presionar el boton flotante
     * 1. Agregar intent
     */
    public void agregarSolicitud() {
        Intent nsolicitud = new Intent(this, AddSolicitudActivity.class);
        startActivity(nsolicitud);

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

}
