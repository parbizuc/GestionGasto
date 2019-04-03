package com.company.spsolutions.gestiongasto.Login;
/**
 * Created by coralRodriguez on 27/03/19.
 */

public class LoginFirebase {
/*
* Esta clase es la que gestiona el acceso a firebase
* LoginFirebase es el constructor de la clase y necesita el presentador para gestionar la vista y obtener los datos
* signin es el método que se conecta a firebase y manda a actualizar la vista con el presenter
 */

    public LoginFirebase (PresenterLoginImpl presenter){}

    /*
    * El siguiente método se encargara de hacer toda la lógica
    * para acceder a firebase y comprobar que el inicio de sesión con las credenciales enviadas son válidas
     */
    public void signin(String user, String password){}

    /*
     * El siguiente método se encargara de hacer toda la lógica
     * para acceder a firebase y terminar con la sesión
     */
    public void signout(){}

}
