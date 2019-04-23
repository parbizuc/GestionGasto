package com.company.spsolutions.gestiongasto.Informes;

import android.content.Context;

/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class PresenterInformeImpl {
    Context context;
    PresenterInforme presenterInforme;

    public PresenterInformeImpl (Context context, PresenterInforme presenterInforme){
        this.context = context;
        this.presenterInforme = presenterInforme;
    }

    /*
    * Lógica para agregar un informe y mandar a guardarlo en firebase utulizando informeService
     */
public void addInforme(){}

/*
* Obtener los informes de firebase
 */
public void getInformes(){}
}
