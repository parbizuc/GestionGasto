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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.OnProgressListener;
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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

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
    String reference_path;

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
        for (int i = 0; i < texto.size(); i++) {
            String str = texto.get(i).toLowerCase();
            // Busca Fecha en ticket
            String pattern_date = "((([0-9]{2}|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec|ene|feb|abr|ago|dic|enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|diciembre))(\\/|-|\\.)(([0-9]{2}|(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec|ene|feb|abr|ago|dic|enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|diciembre))|)(\\/|-|\\.)[0-9]{4})|([0-9]{4}(\\/|-|\\.)[0-9]{2}(\\/|-|\\.)[0-9]{2}))";
            Pattern pat_date = Pattern.compile(pattern_date);
            Matcher match_date = pat_date.matcher(str);
            Boolean date_found = match_date.find();
            if (date_found.equals(true)) {
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
                if (amount != "") {
                    delegate.displayTicketResults(amount, array_date.get(0));
                } else {
                    Double iMayor = 0.0;
                    for (int h = 0; h < array_amount.size(); h++) {
                        if (Double.parseDouble(array_amount.get(h).trim().replaceAll("[$-+.^:,]", "")) > iMayor) {
                            iMayor = Double.parseDouble(array_amount.get(h).trim().replaceAll("[$-+.^:,]", ""));
                        }
                    }
                    delegate.displayTicketResults(Double.toString(iMayor), array_date.get(0));
                }
            } else {
                delegate.displayTicketResults("", array_date.get(0));
            }
        } else {
            if (array_amount.size() > 0) {
                if (amount != "") {
                    delegate.displayTicketResults(amount, "");
                } else {
                    Double iMayor = 0.0;
                    for (int h = 0; h < array_amount.size(); h++) {
                        if (Double.parseDouble(array_amount.get(h).trim().replaceAll("[$-+.^:,]", "")) > iMayor) {
                            iMayor = Double.parseDouble(array_amount.get(h).trim().replaceAll("[$-+^:]", ""));
                        }
                    }
                    delegate.displayTicketResults(Double.toString(iMayor), "");
                }
            } else {
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
            DocumentReference ref = connectGastosDB().document();
            String idDoc = ref.getId();
            saveFirebase(filePath, idDoc);
            Gasto gasto;
            if (anticipo != null) {
                gasto = new Gasto(estado, fechaG, idDoc, empresa.getId(), moneda, montoG, empresa.getNombre(), nombreP, reference_path, anticipo.getId(), anticipo.getImporte(), anticipo.getMoneda(), categoria, comentario, usuario.getId(), usuario.getNombre(), esRecurrente, periodo);
            } else {
                gasto = new Gasto(estado, fechaG, idDoc, empresa.getId(), moneda, montoG, empresa.getNombre(), nombreP, reference_path, "", "", "", categoria, comentario, usuario.getId(), usuario.getNombre(), esRecurrente, periodo);
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

    private void saveFirebase(Uri file, String idDoc) {
        if (file != null) {
            reference_path = "ticket/" + idDoc;
            StorageReference ref = connectStorage().child(reference_path);
            ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Imagen en Firebase");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("Imagen fallo en carga a Firebase");
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    System.out.println("Uploaded " + (int) progress + "%");
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
