package com.company.spsolutions.gestiongasto.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

/**
 * Created by coralRodriguez on 27/03/19.
 */

public class PresenterLoginImpl {
    /*
     * Esta clase es la implementación del presenter
     * PresenterLoginImpl es el contructor de la clase y recibe la vista (o podría solo el contexto) y un delegado para gestionar esa vista
     * onLogin se encarga de verificar si ya existe un login guardado y omite el login
     * validate es el responsable de comprobar si los datos ingresados tienen el formato adecuado
     * onSuccess es el método que será llamado si el login fue exitoso y enviara a notificar a la vista
     * onError es el método que será llamado si el login tuvo un error y enviara a notificar a la vista
     */
    Context context;
    PresenterLogin delegate;
    LoginFirebase service;
    FirebaseAuth auth;

    public PresenterLoginImpl(Context context, PresenterLogin delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    /*
     * Verifica si existe una sesión activa
     * 1. Manda a la clase LoginFirebase las contraseñas necesarias (si son validas) para inicias sesión
     * 2. Manda a la clase LoginFirebase para manejar el signout
     */
    public void onLogin(String user, String password) {
        service = new LoginFirebase();
        auth = service.getAuth();
        if (validate(user, password)) {
            authentication(user, password);
            delegate.signIn();
        } else {
            completeAuth(null, null);
            return;
        }
    }

    public void completeAuth(FirebaseUser user, String id) {
        if (user != null) {
            getUser(id);
        }
    }

    private void getUser(String id) {
        service.getDBUsers().whereEqualTo("id", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map datos = document.getData();
                        Usuario.init(datos.get("rol").toString(), datos.get("id").toString(), datos.get("nombre").toString(), datos.get("idEmpresa").toString(), datos.get("nombreEmpresa").toString(), datos.get("pais").toString());
                        getEmpresa(datos.get("idEmpresa").toString());
                        delegate.successLogin();
                    }
                } else {
                    Log.d("ERROR", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void getEmpresa(String id) {
        service.getDbEmpresa().whereEqualTo("id", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map datos = document.getData();
                        Empresa.init(datos.get("id").toString(), datos.get("nombre").toString(), datos.get("moneda").toString(), datos.get("prefijoPais").toString());
                        savePreferences();
                    }
                } else {
                    Log.d("ERROR", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void savePreferences() {
        SharedPreferences userSP = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        SharedPreferences companySP = context.getSharedPreferences("companydetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorU = userSP.edit();
        SharedPreferences.Editor editorE = companySP.edit();
        editorU.putString("idU", Usuario.getInstance().getId());
        editorU.putString("nombreU", Usuario.getInstance().getNombre());
        editorU.putString("idE", Usuario.getInstance().getIdEmpresa());
        editorU.putString("nombreE", Usuario.getInstance().getNombreEmpresa());
        editorU.putString("rol", Usuario.getInstance().getRol());
        editorU.putString("pais", Usuario.getInstance().getPais());
        editorU.commit();
        editorE.putString("id", Empresa.getInstance().getId());
        editorE.putString("nombre", Empresa.getInstance().getNombre());
        editorE.putString("moneda", Empresa.getInstance().getMoneda());
        editorE.putString("pais", Empresa.getInstance().getPrefijoPais());
        editorE.commit();
    }

    private void authentication(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onSuccess(task.getResult().getUser().getUid());
                        } else {
                            //Log.e("Error", "signInWithEmail:failure", task.getException());
                            onError();
                        }
                    }
                });
    }

    /* Método validate
     * 1. Verificar la estructura del usuario y checar que es valido (email correcto)
     * 2. Comprobar que la contraseña tenga la estructura necesaria
     */
    public boolean validate(String user, String password) {
        if (user.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            delegate.displayEmailError("Error en el correo");
            return false;
        }
        if (password.isEmpty()) {
            delegate.displayPasswordError("Error en la contraseña");
            return false;
        }
        return true;
    }

    /* Método onSuccess
     * 1. Si el login fue exitoso se le notifica a la vista que mande a cargar y a la siguiente activity
     */
    public void onSuccess(String id) {
        FirebaseUser user = auth.getCurrentUser();
        completeAuth(user, id);
    }

    /* Método onError
     * 1. Si el login fallo se le notifica a la vista que mande el error correspondiente
     */
    public void onError() {
        delegate.displaySigninError("Ocurrio un error");
        delegate.failSignIn();
        completeAuth(null, null);
    }


}

