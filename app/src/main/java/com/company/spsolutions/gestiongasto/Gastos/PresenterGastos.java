package com.company.spsolutions.gestiongasto.Gastos;

import android.net.Uri;

/**
 * Created by coralRodriguez on 28/03/19.
 */
public interface PresenterGastos {
    /*
     * metodos necesarios para comunicarse con la vista
     */

    void displayTicketResults(String amount, String date);

    void changeActivity();

    void setError(Integer type, String text);

    void displayProgress(Boolean isDisplayed, String texto);
}
