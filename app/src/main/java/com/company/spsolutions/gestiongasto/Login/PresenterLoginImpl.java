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

    /*
    * Verifica si existe una sesión activa
     * 1. Manda a la clase LoginFirebase las contraseñas necesarias (si son validas) para inicias sesión
     * 2. Manda a la clase LoginFirebase para manejar el signout
     */
    public void onLogin (){}

    /* Método validate
    * 1. Verificar la estructura del usuario y checar que es valido (email correcto)
    * 2. Comprobar que la contraseña tenga la estructura necesaria
     */
    public boolean validate(String user, String password){
        return  true;
    }

    /* Método onSuccess
     * 1. Si el login fue exitoso se le notifica a la vista que mande a cargar y a la siguiente activity
     */
    public void onSuccess() { }

    /* Método onError
     * 1. Si el login fallo se le notifica a la vista que mande el error correspondiente
     */
    public void onError() { }


}

