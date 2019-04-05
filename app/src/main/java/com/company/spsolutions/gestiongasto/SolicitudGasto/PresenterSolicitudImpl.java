package com.company.spsolutions.gestiongasto.SolicitudGasto;

import android.content.Context;
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
    public DatabaseReference connect() {
        service = new SolicitudService();
        return service.connect();
    }
    /*
    * Separa la lógica de la vista para obtener los registros de solicitudes registradas
     */
    public List<Solicitud> getDataRegistradas(List<Solicitud> solicitudes) {
        List<Solicitud> registradas = new ArrayList<>();
        for (Solicitud solicitud : solicitudes) {
            if (solicitud.getEstatus() == null) {
                registradas.add(solicitud);
            }
        }
        return registradas;
    }

    /*
     * Separa la lógica de la vista para obtener los registros de solicitudes ya procesadas
     */
    public List<Solicitud> getDataProcesadas(List<Solicitud> solicitudes) {
        List<Solicitud> procesadas = new ArrayList<>();
        for (Solicitud solicitud : solicitudes) {
            if (solicitud.getEstatus() != null) {
                procesadas.add(solicitud);
            }
        }
        return procesadas;
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
