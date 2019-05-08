package com.company.spsolutions.gestiongasto.Gastos;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClient;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.SolicitudGasto.SolicitudService;
import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by coralRodriguez on 28/03/19.
 */
public class PresenterGastosImpl {
    /* Esta clase es el presentador de las activitys que conforman la gestion de gastos
     * sendGasto se encarga de enviar un nuevo gasto al service
     *
     */
    Context context;
    PresenterGastos delegate;
    SolicitudService serviceSolicitud;
    GastoService gastoService;
    File file;
    ArrayList<String> texto = new ArrayList<String>();

    public PresenterGastosImpl(Context context, PresenterGastos delegate) {
        this.context = context;
        this.delegate = delegate;

    }

    public StorageReference connectStorage() {
        gastoService = new GastoService();
        return gastoService.connectStorage();
    }

    private CollectionReference connectAdelanto() {
        serviceSolicitud = new SolicitudService();
        return serviceSolicitud.connect();
    }

    public CollectionReference connectCategorias() {
        gastoService = new GastoService();
        return gastoService.connectCategorias();
    }

    public CollectionReference connectGastosDB() {
        gastoService = new GastoService();
        return gastoService.connectGastosDB();
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

    public static File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "XRendir");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "ticket_" + timeStamp + ".jpg");
    }

    public void processImagen(Uri file) throws IOException {
        //delegate.displayProgress(true, "Analizando Imagen");
        InputStream imageInputStream = context.getContentResolver().openInputStream(file);
        final byte[] imageArray = IOUtils.toByteArray(imageInputStream);
        AmazonRekognition rekognitionClient = new AmazonRekognitionClient(new BasicAWSCredentials("AKIAR4ZFJVKMXECCQELG", "gDoPReVKxpaWLz5/Pw/sdFuFrl1tdAm96tMEztcy"));
        DetectTextRequest request = new DetectTextRequest().withImage(new Image().withBytes(ByteBuffer.wrap(imageArray)));
        try {
            DetectTextResult result = rekognitionClient.detectText(request);
            List<TextDetection> labels = result.getTextDetections();
            for (TextDetection label : labels) {
                texto.add(label.getDetectedText());
            }
            ticketAnalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ticketAnalize() {
        ArrayList<String> array_date = new ArrayList<String>();
        ArrayList<String> array_amount = new ArrayList<String>();
        String[] text_used = {"total", "importe", "mxn", "m.n"};
        String amount = "";
        //System.out.println(texto);
        for (int i = 0; i < texto.size(); i++) {
            String str = texto.get(i).toLowerCase();
            // Busca Fecha en ticket
            String pattern_date = "(([0-9]{2}|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec|ene|feb|abr|ago|dic|enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|diciembre)|[0-9]{4})(\\/|-|\\s|\\.)([0-9]{2}|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec|ene|feb|abr|ago|dic|enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|diciembre))(\\/|-|\\s|\\.)([0-9]{4}|[0-9]{2}))";
            Pattern pat_date = Pattern.compile(pattern_date);
            Matcher match_date = pat_date.matcher(str);
            Boolean date_found = match_date.find();
            if (date_found.equals(true)) {
                //System.out.println("fecha encontrada: "+texto.get(i).toLowerCase().substring
                // (match_date.start(),match_date.end()));
                array_date.add(texto.get(i).toLowerCase().substring(match_date.start(), match_date.end()));
            }
            // Busca monto en ticket
            String pattern_amount = "([\\$|\\ |\\:|]*[0-9]*[\\.,\\,][0-9]{2})";
            Pattern pat_amount = Pattern.compile(pattern_amount);
            Matcher match_amount = pat_amount.matcher(str);
            Boolean amount_found = match_amount.find();
            if (amount_found.equals(true)) {
                for (String j : text_used) {
                    Boolean text_amount = texto.get(i).toLowerCase().contains(j);
                    if (text_amount.equals(true)) {
                        amount = texto.get(i).substring(match_amount.start(), match_amount.end());
                    }
                }
                array_amount.add(texto.get(i).substring(match_amount.start(), match_amount.end()));
            }
        }
        if (array_date.size() > 0) {
            if (array_amount.size() > 0) {
                /*if (amount != "") {
                    //System.out.println("mando a monto vacio: "+ amount +" fecha: "+array_date
                    // .get(0));
                    delegate.displayTicketResults(amount, array_date.get(0));
                } else {*/
                Double iMayor = 0.0;
                for (int h = 0; h < array_amount.size(); h++) {
                    if (Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", "")) > iMayor) {
                        iMayor = Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", ""));
                    }
                }
                //System.out.println("mando a monto: "+ iMayor +" fecha: "+array_date.get(0));
                delegate.displayTicketResults(Double.toString(iMayor), array_date.get(0));
                //}
            } else {
                //System.out.println("no encontro monto pero si fecha: "+array_date.get(0));
                delegate.displayTicketResults("", array_date.get(0));
            }
        } else {
            if (array_amount.size() > 0) {/*
                if (amount != "") {
                    //System.out.println("no encontro fecha solo monto: "+amount);
                    delegate.displayTicketResults(amount, "");
                } else {*/
                Double iMayor = 0.0;
                for (int h = 0; h < array_amount.size(); h++) {
                    if (Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", "")) > iMayor) {
                        iMayor = Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", ""));
                    }
                }
                //System.out.println("no encontro fecha solo monto: "+iMayor);
                delegate.displayTicketResults(Double.toString(iMayor), "");
                //}
            } else {
                //System.out.println("no encontro nada");
                delegate.displayTicketResults("", "");
            }
        }
    }

    /*
     * Mandara a llamar a gastoservice para guardar el nuevo gasto
     * Procesara los datos y/o limpiara
     * Guarda imagen y gasto
     */
    public void sendGasto(final String nombreP, final String fechaG, final String montoG, Uri filePath, final Solicitud anticipo, final String categoria, final String comentario, final Boolean esRecurrente, final String periodo, final String moneda, final String estado) {
        final Empresa empresa = Empresa.getInstance();
        final Usuario usuario = Usuario.getInstance();
        if (validate(fechaG, nombreP, montoG, categoria, filePath, esRecurrente, periodo)) {
            delegate.displayProgress(true, "Guardando");
            final StorageReference fotoRef = connectStorage().child("Fotos").child(Usuario.getInstance().getId()).child(filePath.getLastPathSegment());
            fotoRef.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw new Exception();
                    }

                    return fotoRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadLink = task.getResult();
                        DocumentReference ref = connectGastosDB().document();
                        String idDoc = ref.getId();
                        Gasto gasto;
                        if (anticipo != null) {
                            gasto = new Gasto(estado, fechaG, idDoc, empresa.getId(), moneda, montoG, empresa.getNombre(), nombreP, downloadLink.toString(), anticipo.getId(), anticipo.getImporte(), anticipo.getMoneda(), categoria, comentario, usuario.getId(), usuario.getNombre(), esRecurrente, periodo);
                        } else {
                            gasto = new Gasto(estado, fechaG, idDoc, empresa.getId(), moneda, montoG, empresa.getNombre(), nombreP, downloadLink.toString(), "", "", "", categoria, comentario, usuario.getId(), usuario.getNombre(), esRecurrente, periodo);
                        }
                        ref.set(gasto).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                delegate.changeActivity();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                delegate.displayProgress(false, "");
                                delegate.setError(6, "Ocurrio un error al querer guardar los datos. Intente más tarde");
                            }
                        });
                    }
                }


            });
        }

    }

    private Boolean validate(String fecha, String proveedor, String monto, String categoria, Uri photo, Boolean esRecurrente, String frecuencia) {
        if (photo == null) {
            delegate.setError(4, "La imagen es obligatoria");
            return false;
        } else if (proveedor.equals("")) {
            delegate.setError(1, "Este campo es obligatorio");
            return false;
        } else if (fecha.equals("")) {
            delegate.setError(0, "Este campo es obligatorio");
            return false;
        } else if (categoria.equals("")) {
            delegate.setError(3, "Este campo es obligatorio");
            return false;
        } else if (monto.equals("")) {
            delegate.setError(2, "Este campo es obligatorio");
            return false;
        } else if (esRecurrente) {
            if (frecuencia.equals("")) {
                delegate.setError(5, "Este campo es obligatorio");
                return false;
            }
        }
        return true;
    }

    /*
     * este método debe obtener los gastos de firebase y determinar si son registrados o informados
     */
    public void getGastos() {

    }
}
