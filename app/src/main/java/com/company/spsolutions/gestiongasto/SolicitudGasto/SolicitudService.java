package com.company.spsolutions.gestiongasto.SolicitudGasto;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * Created by coralRodriguez on 28/03/19.
 */

public class SolicitudService {
    /* Esta clase es la que se encargara de hacer consultas a firebase para las solicitudes
     */
    FirebaseFirestore database;
    CollectionReference dbSolicitud;

    public CollectionReference connect() {
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("solicitudes");
        return dbSolicitud;
    }

}
