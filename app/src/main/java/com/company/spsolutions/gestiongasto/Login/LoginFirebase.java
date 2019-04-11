package com.company.spsolutions.gestiongasto.Login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by coralRodriguez on 27/03/19.
 */

public class LoginFirebase {
    /*
     * Esta clase es la que gestiona el acceso a firebase
     * LoginFirebase es el constructor de la clase y necesita el presentador para gestionar la vista y obtener los datos
     * signin es el método que se conecta a firebase y manda a actualizar la vista con el presenter
     */
    FirebaseAuth auth;
    FirebaseFirestore database;
    CollectionReference dbUsuario;
    CollectionReference dbEmpresa;

    public void connect() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    public FirebaseAuth getAuth() {
        connect();
        return auth;
    }

    public CollectionReference getDBUsers() {
        dbUsuario = database.collection("usuarios");
        return dbUsuario;
    }

    public CollectionReference getDbEmpresa() {
        dbEmpresa = database.collection("empresas");
        return dbEmpresa;
    }

    /*
     * El siguiente método se encargara de hacer toda la lógica
     * para acceder a firebase y terminar con la sesión
     */
    public void signout() {
        FirebaseAuth.getInstance().signOut();
    }

}
