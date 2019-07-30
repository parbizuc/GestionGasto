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
import java.util.Date;
import java.text.SimpleDateFormat;
//--
import com.google.android.gms.tasks.OnCompleteListener;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;


/**
 * Created by coralRodriguez on 29/03/2019.
 */
public class PresenterInformeImpl {
    Context context;
    GastoInforme gastoInforme;
    PresenterInforme delegate;
    GastoService serviceGastos;
    SolicitudService serviceSolicitud;
    InformeService serviceInforme;
    Double currentTotal = 0.00;
    private InformeService service;

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



    public Query searchGastos(String estado) {
        Query ref;
        System.out.println("Estado informe: "+estado);

            //editar guardados
            if(estado.equals("INFORMADO")) {
                System.out.println("traer estados INFORMADO s");
                System.out.println("Usuario.getInstance().getRol(): "+Usuario.getInstance().getRol());
                if (Usuario.getInstance().getRol().equals("usuario")) {
                    System.out.println("Ejecuar query 1");
                    ref=connectGastos().whereEqualTo("idUsuario", Usuario.getInstance().getId()).whereEqualTo("estado", "INFORMADO");
                    return ref;

                } else {
                    System.out.println("Ejecuar query 2");
                    ref = connectGastos().orderBy("idUsuario");
                }
                return ref;
            }
            if(estado.equals("ENVIADO")){
                //traer enviados
                System.out.println("traer estados ENVIADO s");
                if (Usuario.getInstance().getRol().equals("usuario")) {
                    ref = connectGastos().whereEqualTo("idUsuario", Usuario.getInstance().getId()).whereEqualTo("estado", "ENVIADO");
                } else {
                    ref = connectGastos().orderBy("idUsuario");
                }
                return ref;
            }
        if(estado.equals("NUEVO")){
            //traer enviados
            System.out.println("traer estados REGISTRADO s");
            if (Usuario.getInstance().getRol().equals("usuario")) {
                ref = connectGastos().whereEqualTo("idUsuario", Usuario.getInstance().getId()).whereEqualTo("estado", "REGISTRADO");
            } else {
                ref = connectGastos().orderBy("idUsuario");
            }
            return ref;
        }

        return null;
    }
//-----------------------------
    public Query searchGastoByID(String id) {
    Query ref;
    System.out.println("ID gasto: "+id);
        //Buscar Gasto por id de gasto
        System.out.println("traer gastos por ID");
        return connectGastos().whereEqualTo("estado", "ENVIADO");
    }
//-----------------------------
    public Query searchInfoGastos(String idInforme, String estado) {
        Query ref;
        System.out.println("ID informe: "+idInforme);
        System.out.print("estado: "+estado);
        //editar guardados
            System.out.println("searchInfoGastos ");
            System.out.println("Usuario.getInstance().getRol(): "+Usuario.getInstance().getRol());
            if (Usuario.getInstance().getRol().equals("usuario")) {
                System.out.println("Ejecuar query 1 searchInfoGastos");
                ref=connectGastosInf().whereEqualTo("idInforme",idInforme).whereEqualTo("estado",estado);
                return ref;

            } else {
                System.out.println("Ejecuar query 2 searchInfoGastos");
                ref = connectGastos().orderBy("idUsuario");
            }
            return ref;

    }
    //------------------------------------------------

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
        System.out.println("--addInforme--");
        System.out.println("gastos: "+gastos.toArray().toString());
        System.out.println("estado: "+estado);
        if (validarCampos(fechaInicio, fechaFin, gastos, titulo)) {
            Empresa empresa = Empresa.getInstance();
            Usuario usuario = Usuario.getInstance();
            DocumentReference refI = connectInforme().document();
            String idInfo = refI.getId();
            Informe informe;
            if (solicitud == null) {
                informe = new Informe(idInfo, empresa.getId(), empresa.getNombre(), titulo, null, fechaInicio, fechaFin, comentario, monto, empresa.getMoneda(), usuario.getId(), usuario.getNombre(), fechaRegistro, fechaEnviado, estado);
            } else {
                informe = new Informe(idInfo, empresa.getId(), empresa.getNombre(), titulo, solicitud.getId(), fechaInicio, fechaFin, comentario, monto, empresa.getMoneda(), usuario.getId(), usuario.getNombre(), fechaRegistro, fechaEnviado, estado);
            }
            for (Gasto gasto : gastos) {
                DocumentReference refGI = connectGastosInf().document();
                String idGI = refGI.getId();
                GastoInforme gastoInforme = new GastoInforme(idGI, idInfo, gasto.getId(), "INFORMADO");
                refGI.set(gastoInforme);
                Map<String, Object> data = new HashMap<>();
                data.put("idInforme",idInfo);
                if(estado.equalsIgnoreCase("ENVIADO")){
                    data.put("estado", "ENVIADO");
                }else {
                    data.put("estado", gasto.getEstado());
                }
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
                    delegate.setError(6, "Ocurrio un error");
                }
            });
        }
    }
    public String formatoFechaDB (String fecha){
        Date d = new Date(fecha);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        fecha = simpleDate.format(d);
        return fecha;
    }
    public void editData(String titulo, String fechaInicio, String fechaFin, String comentario, String monto, String fechaRegistro, String fechaEnviado, String estado,List<Gasto> gastos, String idinforme){
        System.out.println("--editData--");
        System.out.println("titulo ->"+titulo+"");
        String fechaInicioFormato=formatoFechaDB(fechaInicio);
        System.out.println("fechaInicio ->"+fechaInicioFormato+"");
        String fechaFinFormato=formatoFechaDB(fechaFin);
        System.out.println("fechaFin: ->"+fechaFinFormato+"");
        System.out.println("comentario: ->"+comentario+"");
        System.out.println("monto ->"+monto+"");
        System.out.println("fechaRegistro ->"+fechaRegistro+"");
        System.out.println("fechaEnviado -> "+fechaEnviado+"");
        System.out.println("estado ->"+estado);
        System.out.println("gastos ->"+gastos+"");
        System.out.println("idinforme ->"+idinforme+"");
        System.out.println("gastos.size ->"+ gastos.size());

        for (Gasto gasto : gastos) {
            DocumentReference refGI = connectGastosInf().document();
            GastoInforme gastoInforme = new GastoInforme(refGI.getId(), idinforme, gasto.getId(), gasto.getEstado());
            refGI.set(gastoInforme);
            Map<String, Object> data = new HashMap<>();
            if(estado.equals("ENVIADO")){
                System.out.println("gasto como ENVIADO");
                data.put("estado", "ENVIADO");
            }else {
                data.put("estado", gasto.getEstado());
            }
            connectGastos().document(gasto.getId()).set(data, SetOptions.merge());
        }
            //quitar los registros que se cabiaron a registrado
            Query query= connectGastosInf()
                    .whereEqualTo("idInforme",idinforme).whereEqualTo("estado","REGISTRADO");
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        for(int i=0; i<queryDocumentSnapshots.size(); i++){
                            System.out.println("queryDocumentSnapshots.ID"+queryDocumentSnapshots.getDocuments().get(i).getId());
                            queryDocumentSnapshots.getDocuments().get(i).getReference().delete();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        //-------------------------------------------------------------
        System.out.println("se llama validar campos");
        if (validarCampos(fechaInicio, fechaFin, gastos, titulo)) {
            System.out.println("Se llamo validar campos ");
            Map<String, Object> updates = new HashMap<>();
            updates.put("fechaInicio", fechaInicioFormato);
            updates.put("fechaFin", fechaFinFormato);
            updates.put("titulo", titulo);
            updates.put("comentario", comentario);
            updates.put("montoInforme", monto);
            updates.put("fechaRegistro", fechaRegistro);
            updates.put("fechaEnviado", fechaEnviado);
            updates.put("estado", estado);
           // updates.put("gastoInforemid", gastoInforemid);
          //  updates.put("id", idinforme);
          //  updates.put("solicitud", solicitud.getId());
            connect().document(idinforme).update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private Boolean validarCampos(String fechaInicio, String fechaFin, List<Gasto> gastos, String titulo) {
        System.out.println("validar campos");
        if (fechaInicio.equals("")) {
            delegate.setError(0, "Este campo es obligatorio");
            return false;
        }
        if (fechaFin.equals("")) {
            delegate.setError(1, "Este campo es obligatorio");
            return false;
        }
        if (titulo.equals("")) {
            delegate.setError(2, "Este campo es obligatorio");
            return false;
        }
        /*if (gastos.isEmpty()) {
            delegate.setError(3, "Seleccione al menos un gasto");
            return false;
        }*/
        return true;
    }


    /*
     * Obtener los informes de firebase
     */
    public void getInformes() {
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

    public CollectionReference connect() {
        service = new InformeService();
        return service.connectFirebase();
    }
}
