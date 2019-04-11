package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by coralRodriguez on 28/03/19.
 */

public class PresenterSolicitudImpl {
    /*
     *PresenterSolicitudes manejara la vista y los cambios que debe sufrir
     * search es el método que busca en el modelo los registros de las solicitudes
     */
    private SolicitudService service;
    private Context context;
    private PresenterSolicitud delegate;


    public PresenterSolicitudImpl(Context context, PresenterSolicitud delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    /*
     * Conectar a la base de datos para obtener los registros
     */
    public CollectionReference connect() {
        service = new SolicitudService();
        return service.connect();
    }

    public void sendData(String fechaInicio, String fechaFin, String descripcion, String centro, String moneda, String importe, String fechaRegistro, String fechaEnviado, String estado, String motivoRechazo, String nombreUsuario, String idUsuario, String idEmpresa, String pais) {
        if (validarCampos(fechaInicio, fechaFin, descripcion, centro, importe)) {
            DocumentReference ref = connect().document();
            String idDoc = ref.getId();
            Solicitud solicitud = new Solicitud(fechaInicio, fechaFin, descripcion, centro, moneda, importe, fechaRegistro, fechaEnviado, estado, null, nombreUsuario, idUsuario, idDoc, idEmpresa, pais);
            ref.set(solicitud).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    delegate.changeActivity();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    delegate.displayLabel("Ocurrio un error");
                }
            });
        } else {
            delegate.displayLabel("Ocurrio un error, verifica los datos");
        }
    }

    private Boolean validarCampos(String fechaInicio, String fechaFin, String descripcion, String centro, String importe) {
        if (fechaInicio.equals("")  || fechaFin.equals("") || descripcion.equals("") || centro.equals("") || importe.equals("")) {
            //mensaje informando que falta datos
            return false;
        }
        return true;
    }

    /*
     *Implementar la logica para buscar un registro
     */

    /*
     * Logica para guardar las solicitudes en firebase si el usuario no quiere procesarlas;
     */
    public void guardar() {
    }

    /*
     * Logica para mandar a procesar las solicitudes y notificar al aprobador
     */
    public void procesar() {
    }

}
