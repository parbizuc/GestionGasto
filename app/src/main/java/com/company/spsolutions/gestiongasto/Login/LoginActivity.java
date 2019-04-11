package com.company.spsolutions.gestiongasto.Login;
/**
 * Created by coralRodriguez on 27/03/19.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.company.spsolutions.gestiongasto.MenuPrincipal.MainActivity;
import com.company.spsolutions.gestiongasto.R;

public class LoginActivity extends AppCompatActivity implements PresenterLogin {
    /*
     * Esta clase se encarga de gestionar la vista del login
     * initCompinents debe inicializar los componentes con sus respectivos listeners, intents,
     * displayEmailError implementa el método de la interfaz para mostrar un error si el email es invalido
     * displayPasswordError implementa el método de la interfaz para mostrar un error si el password es invalido
     * displaySigninError implementa el método de la interfaz para mostrar un error si ocurrio un error al iniciar sesion (contraseña erronea, sin conexión,etc)
     * displayLoader implementa el método de la interfaz para mostrar un  loader mientras se espera la respuesta de firebase
     * successLogin implementa el método de la interfaz para mostrar la siguiente interacción si el login es exitoso
     */

    /* En el método onCreate
     *1. Crear una variable del contexto y de la interfaz PresenterLogin (delegado)
     *2. Crear una instancia del presentador (clase PresenterLoginImpl)
     */
    EditText userET;
    EditText passwordET;
    Button loginBNT;
    PresenterLoginImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userET = findViewById(R.id.usuario_et);
        passwordET = findViewById(R.id.contraseña_et);
        loginBNT = findViewById(R.id.ingresar_btn);
        initComponents();
    }

    /*
    Acciones del boton :
    1. inicializar componentes de cajas de texto usuario, contraseña y boton de ingresar
    2. Implementar logica del metodo setlistener del boton ingresar
    */
    public void initComponents() {
        presenter = new PresenterLoginImpl(this, this);
        loginBNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                presenter.onLogin(email, password);
            }
        });

    }


    @Override
    public void displayEmailError(String error) {

    }

    @Override
    public void displayPasswordError(String error) {

    }

    @Override
    public void displaySigninError(String error) {

    }

    @Override
    public void displayLoader(boolean loader) {

    }


    @Override
    public void successLogin() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
