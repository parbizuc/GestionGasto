package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.content.Context;


import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


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

    public void sendData(String fechaInicio, String fechaFin, String descripcion, String centro, String motivo, String moneda, String importe, String fechaRegistro, String fechaEnviado, String estado, String nombreUsuario, String idUsuario, String idEmpresa, String pais) {
        if (validarCampos(fechaInicio, fechaFin, importe, motivo)) {
            DocumentReference ref = connect().document();
            String idDoc = ref.getId();
            Solicitud solicitud = new Solicitud(fechaInicio, fechaFin, descripcion, centro, motivo, moneda, importe, fechaRegistro, fechaEnviado, estado, null, nombreUsuario, idUsuario, idDoc, idEmpresa, pais);
            ref.set(solicitud).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    delegate.changeActivity();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    delegate.setError(4, "Ocurrio un error al querer guardar los datos. Intente más tarde");
                }
            });
        }
    }

    public void editData(String fechaInicio, String fechaFin, String descripcion, String centro, String motivo, String importe, String fechaRegistro, String fechaEnviado, String estado, String idSolicitud) {
        if (validarCampos(fechaInicio, fechaFin, importe, motivo)) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("fechaInicio", fechaInicio);
            updates.put("fechaFin", fechaFin);
            updates.put("descripcion", descripcion);
            updates.put("centro", centro);
            updates.put("motivo", motivo);
            updates.put("importe", importe);
            updates.put("fechaRegistro", fechaRegistro);
            updates.put("fechaEnviado", fechaEnviado);
            updates.put("estado", estado);
            connect().document(idSolicitud).update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    delegate.changeActivity();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    delegate.setError(4, "Ocurrio un error al querer guardar los datos. Intente más tarde");
                }
            });
        }
    }

    public Query getQuery() {
        Query ref;
        if (Usuario.getInstance().getRol().equals("usuario")) {
            ref = connect().whereEqualTo("idUsuario", Usuario.getInstance().getId());
        } else {
            ref = connect().orderBy("idUsuario");
        }
        return ref;
    }

    public boolean getValorEstado() {
        boolean ref;
        if (Usuario.getInstance().getRol().equals("usuario")) {
            ref = true;
        } else if (Usuario.getInstance().getRol().equals("aprobador")) {
            ref = false;
        } else {
            ref = true;
        }
        return ref;
    }

    private Boolean validarCampos(String fechaInicio, String fechaFin, String importe, String motivo) {
        if (fechaInicio.equals("")) {
            delegate.setError(0, "Este campo es obligatorio");
            return false;
        }
        if (fechaFin.equals("")) {
            delegate.setError(1, "Este campo es obligatorio");
            return false;
        }
        if (motivo.equals("")) {
            delegate.setError(2, "Este campo es obligatorio");
            return false;
        }
        if (importe.equals("")) {
            delegate.setError(3, "Este campo es obligatorio");
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
