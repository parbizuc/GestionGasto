package com.company.spsolutions.gestiongasto.Informes;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class InformeService {
    /* Esta clase se conecta a firebase para extraer los datos del informe de gastos
     */
    FirebaseFirestore database;
    CollectionReference dbSolicitud;

    /*
     * 1. Realizar la conexion a firebase y a la base de datos necesaria
     */
    public CollectionReference connectFirebase() {
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("informes");
        return dbSolicitud;
    }

    public CollectionReference connectGastosI() {
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("gastosInforme");
        return dbSolicitud;
    }

    /*
     * Se realiza la lógica  añadir un nuevo informe o editar alguno
     */
    public void guardarInforme() {
    }

    /*
     * Este método servira para traer los datos de los informes
     */
    public void obtenerInformes() {
    }
}

