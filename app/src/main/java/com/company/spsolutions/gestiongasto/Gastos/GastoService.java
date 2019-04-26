package com.company.spsolutions.gestiongasto.Gastos;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by coralRodriguez on 28/03/19.
 */
public class GastoService {
    /* Esta clase se encarga de conectarse a firebase para acceder a los datos de los gastos
     *
     */
    FirebaseFirestore database;
    CollectionReference dbSolicitud;

    /*
     * 1. Realizar la conexcion a firebase y a la base de datos necesaria
     */
    public CollectionReference connectFirebase() {
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("gastos");
        return dbSolicitud;
    }

    /*
     * Se realiza la lógica  añadir un nuevo gasto
     */
    public void guardarGasto() {
    }

    /*
     * Este método servira para traer los datos de los gastos
     */
    public void obtenerGasto() {
    }

}
