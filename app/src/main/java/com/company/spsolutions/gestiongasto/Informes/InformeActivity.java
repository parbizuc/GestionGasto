package com.company.spsolutions.gestiongasto.Informes;
/**
 * Created by coralRodriguez on 27/03/19.
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.company.spsolutions.gestiongasto.R;
import com.company.spsolutions.gestiongasto.SolicitudGasto.FragmentSolicitudes;

public class InformeActivity extends AppCompatActivity implements PresenterInforme {
    /* Esta clase controla la vista principal del informe de gastos
     * initComponents() se encarga de crear las instancias de los view y sus listeners
     * onClick es el metodo que se encarga de gestionar las acciones al dar click en los view
     *
     */
    private FragmentInformes.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addInforme_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addi = new Intent(InformeActivity.this, AddInformeActivity.class);
                startActivity(addi);

            }
        });
        Toolbar toolbar = findViewById(R.id.toolbarInforme);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new FragmentInformes.SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#0070c0"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#c5d1e1"), Color.parseColor("#ffffff"));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
    /*
     * Mandar al activity para agregar un informe
     */
    public void addInforme() {
    }

    @Override
    public String getTotal() {
        return null;
    }

    @Override
    public void changeTotal(String total) {

    }

    @Override
    public void setError(Integer type, String texto) {

    }

    @Override
    public void changeActivity() {

    }

}
