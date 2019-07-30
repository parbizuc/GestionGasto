package com.company.spsolutions.gestiongasto.Gastos;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.widget.Switch;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by coralRodriguez on 28/03/19.
 */
public class PresenterGastosImpl {
    /* Esta clase es el presentador de las activitys que conforman la gestion de gastos
     * sendGasto se encarga de enviar un nuevo gasto al service
     *
     */ Locale espanol = new Locale("es", "ES");
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
        String date_formated = "";
        String[] text_used = {"total", "importe", "mxn", "m.n"};
        String amount = "";
        //System.out.println(texto);
        for (int i = 0; i < texto.size(); i++) {
            String str = texto.get(i).toLowerCase();
            String pattern_date = "(((0[1-9]|[1-2][0-9]|3[0-1])|((20[0-9]{2})|(19|2[0-9])))(\\/|-|\\s|\\.|)((0[1-9])|(1[0-2])|(ene(?:ro)?|feb(?:rero)?|mar(?:zo)?|abr(?:il)?|may(?:o)?|jun(?:io)?|jul(?:io)?|ago(?:sto)?|sep(?:tiembre)?|oct(?:ubre)?|nov(?:iembre)?|dic(?:iembre)?)|(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?))(\\/|-|\\s|\\.|)(((20[0-9]{2})|(19|2[0-9]))|(0[1-9]|[1-2][0-9]|3[0-1])))";
            Pattern pat_date = Pattern.compile(pattern_date);
            Matcher match_date = pat_date.matcher(str);
            Boolean date_found = match_date.find();
            if (date_found.equals(true)) {
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
            System.out.println(texto);
            System.out.println("Array Date: " + array_date);
            String dateWoutFormat = array_date.get(0);
            System.out.println("fecha a formatear " + dateWoutFormat);
            String pattern_dmy = "((0[1-9]|[1-2][0-9]|3[0-1])(\\/|-|\\s|\\.|)((0[1-9])|(1[0-2])|(ene(?:ro)?|feb(?:rero)?|mar(?:zo)?|abr(?:il)?|may(?:o)?|jun(?:io)?|jul(?:io)?|ago(?:sto)?|sep(?:tiembre)?|oct(?:ubre)?|nov(?:iembre)?|dic(?:iembre)?)|(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?))(\\/|-|\\s|\\.|)((20[0-9]{2})|(19|2[0-9])))";
            String pattern_ymd = "(((20[0-9]{2})|(19|2[0-9]))(\\/|-|\\s|\\.|)((0[1-9])|(1[0-2])|(ene(?:ro)?|feb(?:rero)?|mar(?:zo)?|abr(?:il)?|may(?:o)?|jun(?:io)?|jul(?:io)?|ago(?:sto)?|sep(?:tiembre)?|oct(?:ubre)?|nov(?:iembre)?|dic(?:iembre)?)|(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?))(\\/|-|\\s|\\.|)(0[1-9]|[1-2][0-9]|3[0-1]))";
            Pattern format_dmy = Pattern.compile(pattern_dmy);
            Pattern format_ymd = Pattern.compile(pattern_ymd);
            if (format_dmy.matcher(dateWoutFormat).find()) {
                System.out.println("formato de fecha dia mes año");
                if (dateWoutFormat.contains("/")) {
                    System.out.println("separador /");
                    date_formated = searchDateFormat_dma(dateWoutFormat, "/");
                } else if (dateWoutFormat.contains("-")) {
                    System.out.println("separador -");
                    date_formated = searchDateFormat_dma(dateWoutFormat, "/");
                } else if (dateWoutFormat.contains(" ")) {
                    System.out.println("separador espacio");
                    date_formated = searchDateFormat_dma(dateWoutFormat, " ");
                } else if (dateWoutFormat.contains(".")) {
                    System.out.println("separador .");
                    date_formated = searchDateFormat_dma(dateWoutFormat, ".");
                } else if (dateWoutFormat.length() > 6) {
                    System.out.println("sin separador");
                    date_formated = searchDateFormat_dma(dateWoutFormat, "");
                }
            } else if (format_ymd.matcher(dateWoutFormat).find() && !format_dmy.matcher(dateWoutFormat).find()) {
                System.out.println("formato de fecha año mes dia");
                if (dateWoutFormat.contains("/")) {
                    System.out.println("separador /");
                    date_formated = searchDateFormat_amd(dateWoutFormat, "/");
                } else if (dateWoutFormat.contains("-")) {
                    System.out.println("separador -");
                    date_formated = searchDateFormat_amd(dateWoutFormat, "/");
                } else if (dateWoutFormat.contains(" ")) {
                    System.out.println("separador espacio");
                    date_formated = searchDateFormat_amd(dateWoutFormat, " ");
                } else if (dateWoutFormat.contains(".")) {
                    System.out.println("separador .");
                    date_formated = searchDateFormat_amd(dateWoutFormat, ".");
                } else if (dateWoutFormat.length() > 6) {
                    System.out.println("sin separador");
                    date_formated = searchDateFormat_amd(dateWoutFormat, "");
                }
            }
            if (array_amount.size() > 0) {
                Double iMayor = 0.0;
                for (int h = 0; h < array_amount.size(); h++) {
                    if (Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", "")) > iMayor) {
                        iMayor = Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", ""));
                    }
                }
                System.out.println("mando a monto: " + iMayor + " fecha: " + date_formated);
                delegate.displayTicketResults(Double.toString(iMayor), date_formated);
            } else {
                System.out.println("no encontro monto pero si fecha: " + date_formated);
                delegate.displayTicketResults("", date_formated);
            }
        } else {
            if (array_amount.size() > 0) {
                Double iMayor = 0.0;
                for (int h = 0; h < array_amount.size(); h++) {
                    if (Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", "")) > iMayor) {
                        iMayor = Double.parseDouble(array_amount.get(h).trim().replaceAll("[$:]", ""));
                    }
                }
                //System.out.println("no encontro fecha solo monto: "+iMayor);
                delegate.displayTicketResults(Double.toString(iMayor), "");
            } else {
                //System.out.println("no encontro nada");
                delegate.displayTicketResults("", "");
            }
        }
    }

    private String searchDateFormat_dma(String dateWoutFormat, String separatorChar) {
        String date_formated = "";
        String dd_mm_aaaa = "((0[1-9]|[1-2][0-9]|3[0-1])" + separatorChar + "((0[1-9])|(1[0-2]))" + separatorChar + "(20[0-9]{2}))";
        System.out.println("dd_mm_aaaa->" + dd_mm_aaaa);
        String dd_mmm_aaaa = "((0[1-9]|[1-2][0-9]|3[0-1])" + separatorChar + "(ene|feb|mar|abr|may|jun|jul|ago|sep|oct|nov|dic)" + separatorChar + "(20[0-9]{2}))";
        System.out.println("dd_mmm_aaaa->" + dd_mmm_aaaa);
        String dd_mmmm_aaaa = "((0[1-9]|[1-2][0-9]|3[0-1])" + separatorChar + "(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)" + separatorChar + "(20[0-9]{2}))";
        System.out.println("dd_mmmm_aaaa->" + dd_mmmm_aaaa);
        String dd_mm_aa = "((0[1-9]|[1-2][0-9]|3[0-1])" + separatorChar + "((0[1-9])|(1[0-2]))" + separatorChar + "(19|2[0-9]))";
        System.out.println("dd_mm_aa->" + dd_mm_aa);
        String dd_mmm_aa = "((0[1-9]|[1-2][0-9]|3[0-1])" + separatorChar + "(ene|feb|mar|abr|may|jun|jul|ago|sep|oct|nov|dic)" + separatorChar + "(19|2[0-9]))";
        System.out.println("dd_mmm_aa->" + dd_mmm_aa);
        String dd_mmmm_aa = "((0[1-9]|[1-2][0-9]|3[0-1])" + separatorChar + "(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)" + separatorChar + "(19|2[0-9]))";
        System.out.println("dd_mmmm_aa->" + dd_mmmm_aa);
        Pattern fdd_mm_aaaa = Pattern.compile(dd_mm_aaaa);
        Pattern fdd_mmm_aaaa = Pattern.compile(dd_mmm_aaaa);
        Pattern fdd_mmmm_aaaa = Pattern.compile(dd_mmmm_aaaa);
        Pattern fdd_mm_aa = Pattern.compile(dd_mm_aa);
        Pattern fdd_mmm_aa = Pattern.compile(dd_mmm_aa);
        Pattern fdd_mmmm_aa = Pattern.compile(dd_mmmm_aa);


        if (fdd_mm_aaaa.matcher(dateWoutFormat).find()) {
            System.out.println("dd" + separatorChar + "MM" + separatorChar + "yyyy");
            date_formated = dateFormatter(dateWoutFormat, "dd" + separatorChar + "MM" + separatorChar + "yyyy");
            System.out.println("Fecha dd_mm_aaaa ya formateada: " + date_formated);
        } else if (fdd_mmm_aaaa.matcher(dateWoutFormat).find()) {
            System.out.println("dd" + separatorChar + "MMM" + separatorChar + "yyyy");
            date_formated = dateFormatter(dateWoutFormat, "dd" + separatorChar + "MMM" + separatorChar + "yyyy");
            System.out.println("Fecha dd_mmm_aaaa ya formateada: " + date_formated);
        } else if (fdd_mmmm_aaaa.matcher(dateWoutFormat).find()) {
            System.out.println("dd" + separatorChar + "MMMM" + separatorChar + "yyyy");
            date_formated = dateFormatter(dateWoutFormat, "dd" + separatorChar + "MMMM" + separatorChar + "yyyy");
            System.out.println("Fecha dd_mmmm_aaaa ya formateada: " + date_formated);
        } else if (fdd_mm_aa.matcher(dateWoutFormat).find()) {
            System.out.println("dd" + separatorChar + "MM" + separatorChar + "yy");
            date_formated = dateFormatter(dateWoutFormat, "dd" + separatorChar + "MM" + separatorChar + "yy");
            System.out.println("Fecha ya formateada: " + date_formated);
        } else if (fdd_mmm_aa.matcher(dateWoutFormat).find()) {
            System.out.println("dd" + separatorChar + "MMM" + separatorChar + "yy");
            date_formated = dateFormatter(dateWoutFormat, "dd" + separatorChar + "MMM" + separatorChar + "yy");
            System.out.println("Fecha ya formateada: " + date_formated);
        } else if (fdd_mmmm_aa.matcher(dateWoutFormat).find()) {
            System.out.println("dd" + separatorChar + "MMMM" + separatorChar + "yy");
            date_formated = dateFormatter(dateWoutFormat, "dd" + separatorChar + "MMMM" + separatorChar + "yy");
            System.out.println("Fecha ya formateada: " + date_formated);
        }
        return date_formated;
    }

    private String searchDateFormat_amd(String dateWoutFormat, String separatorChar) {
        String date_formated = "";
        String aaaa_mm_dd = "((20[0-9]{2})" + separatorChar + "((0[1-9])|(1[0-2]))" + separatorChar + "(0[1-9]|[1-2][0-9]|3[0-1]))";
        String aaaa_mmm_dd = "((20[0-9]{2})" + separatorChar + "(ene|feb|mar|abr|may|jun|jul|ago|sep|oct|nov|dic)" + separatorChar + "(0[1-9]|[1-2][0-9]|3[0-1]))";
        String aaaa_mmmm_dd = "((20[0-9]{2})" + separatorChar + "(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)" + separatorChar + "(0[1-9]|[1-2][0-9]|3[0-1]))";
        String aa_mm_dd = "((19|2[0-9])" + separatorChar + "((0[1-9])|(1[0-2]))" + separatorChar + "(0[1-9]|[1-2][0-9]|3[0-1]))";
        String aa_mmm_dd = "((19|2[0-9])" + separatorChar + "(ene|feb|mar|abr|may|jun|jul|ago|sep|oct|nov|dic)" + separatorChar + "(0[1-9]|[1-2][0-9]|3[0-1]))";
        String aa_mmmm_dd = "((19|2[0-9])" + separatorChar + "(enero|febrero|marzo|abril|mayo|junio|julio|agosto|septiembre|octubre|noviembre|diciembre)" + separatorChar + "(0[1-9]|[1-2][0-9]|3[0-1]))";
        Pattern faaaa_mm_dd = Pattern.compile(aaaa_mm_dd);
        Pattern faaaa_mmm_dd = Pattern.compile(aaaa_mmm_dd);
        Pattern faaaa_mmmm_dd = Pattern.compile(aaaa_mmmm_dd);
        Pattern faa_mm_dd = Pattern.compile(aa_mm_dd);
        Pattern faa_mmm_dd = Pattern.compile(aa_mmm_dd);
        Pattern faa_mmmm_dd = Pattern.compile(aa_mmmm_dd);

        if (faaaa_mm_dd.matcher(dateWoutFormat).find()) {
            System.out.println("yyyy" + separatorChar + "MM" + separatorChar + "dd");
            date_formated = dateFormatter(dateWoutFormat, "yyyy" + separatorChar + "MM" + separatorChar + "dd");
            System.out.println("Fecha aaaa_mm_dd ya formateada: " + date_formated);
        } else if (faaaa_mmm_dd.matcher(dateWoutFormat).find()) {
            System.out.println("yyyy" + separatorChar + "MMM" + separatorChar + "dd");
            date_formated = dateFormatter(dateWoutFormat, "yyyy" + separatorChar + "MMM" + separatorChar + "dd");
            System.out.println("Fecha aaaa_mmm_dd ya formateada: " + date_formated);
        } else if (faaaa_mmmm_dd.matcher(dateWoutFormat).find()) {
            System.out.println("yyyy" + separatorChar + "MMMM" + separatorChar + "dd");
            date_formated = dateFormatter(dateWoutFormat, "yyyy" + separatorChar + "MMMM" + separatorChar + "dd");
            System.out.println("Fecha aaaa_mmmm_dd ya formateada: " + date_formated);
        } else if (faa_mm_dd.matcher(dateWoutFormat).find()) {
            System.out.println("yy" + separatorChar + "MM" + separatorChar + "dd");
            date_formated = dateFormatter(dateWoutFormat, "yy" + separatorChar + "MM" + separatorChar + "dd");
            System.out.println("Fecha aa_mm_dd ya formateada: " + date_formated);
        } else if (faa_mmm_dd.matcher(dateWoutFormat).find()) {
            System.out.println("yy" + separatorChar + "MMM" + separatorChar + "dd");
            date_formated = dateFormatter(dateWoutFormat, "yy" + separatorChar + "MMM" + separatorChar + "dd");
            System.out.println("Fecha aa_mmm_dd ya formateada: " + date_formated);
        } else if (faa_mmmm_dd.matcher(dateWoutFormat).find()) {
            System.out.println("yy" + separatorChar + "MMMM" + separatorChar + "dd");
            date_formated = dateFormatter(dateWoutFormat, "yy" + separatorChar + "MMMM" + separatorChar + "dd");
            System.out.println("Fecha aa_mmmm_dd ya formateada: " + date_formated);
        }
        return date_formated;
    }

    public String dateFormatter(String date, String date_format) {
        try {
            System.out.println("fecha a formatear: " + date);
            System.out.println("formato de fecha: " + date_format);
            Date date1 = new SimpleDateFormat(date_format).parse(date);
            System.out.println(date1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", new Locale("es_ES"));
            return df.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
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
                            gasto = new Gasto(estado, fechaG, idDoc, empresa.getId(), moneda, montoG, empresa.getNombre(), nombreP, downloadLink.toString(), anticipo.getId(), anticipo.getImporte(), anticipo.getMoneda(), categoria, comentario, usuario.getId(), usuario.getNombre(), esRecurrente, periodo,"");
                        } else {
                            gasto = new Gasto(estado, fechaG, idDoc, empresa.getId(), moneda, montoG, empresa.getNombre(), nombreP, downloadLink.toString(), "", "", "", categoria, comentario, usuario.getId(), usuario.getNombre(), esRecurrente, periodo,"");
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
