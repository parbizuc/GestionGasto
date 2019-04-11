package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.util.Log;

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class SolicitudService {
    /* Esta clase es la que se encargara de hacer consultas a firebase para las solicitudes
     */
    FirebaseFirestore database;
    CollectionReference dbSolicitud;

    public CollectionReference connect () {
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("solicitudes");
        return dbSolicitud;
    }

    /*
     * Conectar a firebase para guardar la solicitud del usuario
     */
    public void guardarSolicitud() {
    }

    /*
     * Conectar a firebase para mandar a procesar la solicitud del usuario
     */

    public void procesarSolicitud() {
    }
}
