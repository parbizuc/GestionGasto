package com.company.spsolutions.gestiongasto.Informes;

/**
 * Created by coralRodriguez on 29/03/2019.
 */
public interface PresenterInforme {
    /*
     * Delegado entre el presenter y la vista
     */
    String getTotal();
    void changeTotal(String total);
    void setError(Integer type,String message);
    void changeActivity();

    interface ResumenGastos {
        void showPopUp();
    }
}
