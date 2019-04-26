package com.company.spsolutions.gestiongasto.Informes;

import android.content.Context;

import com.company.spsolutions.gestiongasto.Gastos.GastoService;
import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.Modelos.GastoInforme;
import com.company.spsolutions.gestiongasto.Modelos.Informe;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.SolicitudGasto.SolicitudService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class PresenterInformeImpl {
    Context context;
    PresenterInforme delegate;
    GastoService serviceGastos;
    SolicitudService serviceSolicitud;
    InformeService serviceInforme;
    Double currentTotal = 0.00;

    public PresenterInformeImpl(Context context, PresenterInforme delegate) {
        this.context = context;
        this.delegate = delegate;
    }

    /*
     * Conectar a la base de datos para obtener los registros
     */
    public CollectionReference connectInforme() {
        serviceInforme = new InformeService();
        return serviceInforme.connectFirebase();
    }

    public CollectionReference connectGastosInf() {
        serviceInforme = new InformeService();
        return serviceInforme.connectGastosI();
    }

    public CollectionReference connectGastos() {
        serviceGastos = new GastoService();
        return serviceGastos.connectFirebase();
    }

    public CollectionReference connectAdelanto() {
        serviceSolicitud = new SolicitudService();
        return serviceSolicitud.connect();
    }

    public Query searchGastos() {
        Query ref;
        if (Usuario.getInstance().getRol().equals("usuario")) {
            ref = connectGastos().whereEqualTo("idUsuario", Usuario.getInstance().getId()).whereEqualTo("estado", "REGISTRADO");
        } else {
            ref = connectGastos().orderBy("idUsuario");
        }
        return ref;

    }

    public Query searchAdelantos() {
        Query ref;
        if (Usuario.getInstance().getRol().equals("usuario")) {
            ref = connectAdelanto().whereEqualTo("idUsuario", Usuario.getInstance().getId()).whereEqualTo("estado", "APROBADO");
        } else {
            ref = connectAdelanto().orderBy("idUsuario");
        }
        return ref;

    }

    public void total(String monto, Boolean isAdd) {
        Double montoNuevo = Double.parseDouble(monto);
        Double total = Double.parseDouble(delegate.getTotal());
        if (isAdd)
            currentTotal = montoNuevo + total;
        else
            currentTotal = total - montoNuevo;
        currentTotal = Double.valueOf(Math.floor(currentTotal * 100) / 100);
        delegate.changeTotal(currentTotal.toString());
    }

    /*
     * Lógica para agregar un informe y mandar a guardarlo en firebase utulizando informeService
     */
    public void addInforme(String titulo, String fechaInicio, String fechaFin, String comentario, String monto, String fechaRegistro, String fechaEnviado, String estado, Solicitud solicitud, List<Gasto> gastos) {
        if (validarCampos(fechaInicio, fechaFin, comentario, monto)) {
            Empresa empresa = Empresa.getInstance();
            Usuario usuario = Usuario.getInstance();
            DocumentReference refI = connectInforme().document();
            String idInfo = refI.getId();
            Informe informe = new Informe(idInfo, empresa.getId(), empresa.getNombre(), titulo, solicitud.getId(), fechaInicio, fechaFin, comentario, monto, empresa.getMoneda(), usuario.getId(), usuario.getNombre(), fechaRegistro, fechaEnviado, estado);
            for (Gasto gasto : gastos) {
                DocumentReference refGI = connectGastosInf().document();
                String idGI = refGI.getId();
                GastoInforme gastoInforme = new GastoInforme(idGI, idInfo, gasto.getId(), "REGISTRADO");
                refGI.set(gastoInforme);
                Map<String, Object> data = new HashMap<>();
                data.put("estado", "INFORMADO");
                connectGastos().document(gasto.getId()).set(data, SetOptions.merge());
            }
            refI.set(informe).addOnSuccessListener(new OnSuccessListener<Void>() {
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


    private Boolean validarCampos(String fechaInicio, String fechaFin, String comentario, String monto) {
        if (fechaInicio.equals("") || fechaFin.equals("") || comentario.equals("") || monto.equals("")) {
            //mensaje informando que falta datos
            return false;
        }
        return true;
    }


    /*
     * Obtener los informes de firebase
     */
    public void getInformes() {
    }
}
