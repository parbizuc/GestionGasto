package com.company.spsolutions.gestiongasto.Login;
/**
 * Created by coralRodriguez on 27/03/19.
 */

public class PresenterLoginImpl{
    /*
    * Esta clase es la implementación del presenter
    * PresenterLoginImpl es el contructor de la clase y recibe la vista (o podría solo el contexto) y un delegado para gestionar esa vista
    * onLogin se encarga de verificar si ya existe un login guardado y omite el login
    * validate es el responsable de comprobar si los datos ingresados tienen el formato adecuado
    * onSuccess es el método que será llamado si el login fue exitoso y enviara a notificar a la vista
    * onError es el método que será llamado si el login tuvo un error y enviara a notificar a la vista
     */

    public PresenterLoginImpl(LoginActivity view, PresenterLogin delegate) {

    }

    public void onLogin (){}

    public boolean validate(){
        return  true;
    }

    public void onSuccess() { }

    public void onError() { }


}

