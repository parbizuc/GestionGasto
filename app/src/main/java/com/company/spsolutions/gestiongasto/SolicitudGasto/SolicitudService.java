package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.util.Log;

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class SolicitudService {
    /* Esta clase es la que se encargara de hacer consultas a firebase para las solicitudes
     */
    FirebaseDatabase database;
    DatabaseReference dbSolicitud;

    public DatabaseReference connect () {
        dbSolicitud = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        dbSolicitud = database.getReference().child("solicitudes");
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
