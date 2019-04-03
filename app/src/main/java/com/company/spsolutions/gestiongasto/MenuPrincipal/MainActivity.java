package com.company.spsolutions.gestiongasto.MenuPrincipal;
/**
 * Created by coralRodriguez on 27/03/19.
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.company.spsolutions.gestiongasto.R;

import java.util.ArrayList;
import java.util.List;

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

    /*
    * 1. Crear el menú y el buscador
    * 2. Cambiar los datos de la app bar (toolbar)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();

    }

    public void initComponents(){
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
    public void search(){}

    public List<ItemMenu> getData(){
        List<ItemMenu> items= new ArrayList <ItemMenu>();
        items.add(new ItemMenu("Gestión de solicitudes","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlv7N1qgf_Al1YinpbuSLGwciK_e2r3EK3Ewjx78s8zBl88YPl"));
        items.add(new ItemMenu("Gestión de gastos","https://cooperandoando.com/media/2017/12/19-Gastos.png"));
        items.add(new ItemMenu("Informe de gastos","https://cooperandoando.com/media/2017/12/19-Gastos.png"));
        return items;
    }

}
