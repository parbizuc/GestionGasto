package com.company.spsolutions.gestiongasto.Gastos;
/**
 * Created by coralRodriguez on 28/03/19.
 */

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.company.spsolutions.gestiongasto.Modelos.Empresa;
import com.company.spsolutions.gestiongasto.Modelos.Gasto;
import com.company.spsolutions.gestiongasto.Modelos.Solicitud;
import com.company.spsolutions.gestiongasto.Modelos.Usuario;
import com.company.spsolutions.gestiongasto.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class AddGastoActivity extends AppCompatActivity implements PresenterGastos {
    /* AddGastosActivity es la activity mostrada al querer añadir o editar un gasto
     * initComponents se encarga de inicializar los views
     * setlistener controla los listeners a cada view que lo necesite
     * loadImage se encarga de manejar la carga de la imagen asi como el zoom
     * sendData envia los datos al presentador
     * comboRecurrente maneja toda la lógica en caso de que exista cargos recurrentes
     */
    Gasto gasto;
    Boolean isEditar;
    List<Solicitud> solicitudes = new ArrayList<>();
    List<String> categorias = new ArrayList<>();
    LinearLayout frecuenciaLL;
    CheckBox recurrenteCB;
    Spinner monedaSP, frecuenciaSP, categoriaSP, adelantoSP;
    ArrayAdapter<Solicitud> adapterASP;
    ArrayAdapter<String> adapterCSP;
    Button pictureBTN, saveBTN;
    ImageButton fechaIB;
    ImageView pictureIV;
    Uri file;
    String fechaG = "";
    Boolean esRecurrente = false;
    ProgressDialog wProgress;
    PresenterGastosImpl presenter;
    TextView monedaTV, categoriaTV, frecuenciaTV, contadorTV, rolTV, empresaTV, usuarioTV, tituloTV, verdocTV;
    EditText importeET, fechaReciboET, proveedorET, comentarioET;
    final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgasto);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        initComponents();
        setListeners();
    }

    /*
     * Crear instancia  de los checkbox,edittext,textview,etc
     */
    public void initComponents() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportActionBar().hide();
        isEditar = getIntent().hasExtra("gasto");

        presenter = new PresenterGastosImpl(this, this);
        getDatos();
        //Spinner MONEDAS
        monedaSP = findViewById(R.id.moneda_sp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.monedas_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monedaSP.setAdapter(adapter);
        //Spinner FRECUENCIA
        frecuenciaSP = findViewById(R.id.frecuencia_sp);
        ArrayAdapter<CharSequence> adapter_frec = ArrayAdapter.createFromResource(this, R.array.frecuencia_array, android.R.layout.simple_spinner_item);
        adapter_frec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        frecuenciaSP.setAdapter(adapter_frec);
        categoriaSP = findViewById(R.id.categoria_sp);
        adelantoSP = findViewById(R.id.adelanto_et);
        proveedorET = findViewById(R.id.proveedor_et);
        pictureBTN = findViewById(R.id.ticketImg_btn);
        fechaIB = findViewById(R.id.fechaag_ib);
        saveBTN = findViewById(R.id.guardarng_btn);
        comentarioET = findViewById(R.id.coment_et);
        recurrenteCB = findViewById(R.id.isrecurrente_cb);
        frecuenciaLL = findViewById(R.id.frecuenciaLayout_ll);
        pictureIV = findViewById(R.id.imgMostrar);
        importeET = findViewById(R.id.montog_et);
        fechaReciboET = findViewById(R.id.fechang_et);
        fechaReciboET.setFocusable(false);
        monedaTV = findViewById(R.id.monedaag_tv);
        categoriaTV = findViewById(R.id.categoriaag_tv);
        frecuenciaTV = findViewById(R.id.frecuenciaag_tv);
        contadorTV = findViewById(R.id.contador_tv);
        rolTV = findViewById(R.id.rolag_tv);
        usuarioTV = findViewById(R.id.usuarioag_tv);
        empresaTV = findViewById(R.id.empresaag_tv);
        verdocTV = findViewById(R.id.verDoc_tv);
        wProgress = new ProgressDialog(this);
        setToday();
        empresaTV.setText(Empresa.getInstance().getNombre());
        usuarioTV.setText(Usuario.getInstance().getNombre());
        rolTV.setText(Usuario.getInstance().getRol());
        if (isEditar) {
            gasto = (Gasto) getIntent().getSerializableExtra("gasto");
            //se agregan los gatos para mostrarlos
            proveedorET.setText(gasto.getNombreProveedor());
            importeET.setText(gasto.getMontoGasto());
            //categoriaSP.
            int spinnerPosition = adapter.getPosition(gasto.getMonedaGasto());
            monedaSP.setSelection(spinnerPosition);
            comentarioET.setText(gasto.getComentario());
            //adelantoSP
            //frecuenciaSP
            fechaReciboET.setText(gasto.getFechaGasto());
            if(gasto.getImagen()!=null){
                new DownLoadImageTask(pictureIV).execute(gasto.getImagen());
            }else{
                pictureIV.setVisibility(View.GONE);
                pictureBTN.setVisibility(View.GONE);
                verdocTV.setVisibility(View.VISIBLE);
                verdocTV.setText(Html.fromHtml("<a href=\""+gasto.getUrldocumento()+"\"> ver Docuemento :P</a>"));
                verdocTV.setClickable(true);
                verdocTV.setMovementMethod (LinkMovementMethod.getInstance());
            }
        }
    }

    private void setToday() {
        int mesA = mes + 1;
        String diaFormateado = (dia < 10) ? "0" + String.valueOf(dia) : String.valueOf(dia);
        String mesFormateado = (mesA < 10) ? "0" + String.valueOf(mesA) : String.valueOf(mesA);
        fechaReciboET.setText(diaFormateado + "/" + mesFormateado + "/" + anio);
        fechaG = anio + "-" + mesFormateado + "-" + diaFormateado;
    }

    private void getDatos() {
        presenter.searchAdelantos().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                solicitudes.clear();
                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    Solicitud motivo = document.toObject(Solicitud.class);
                    solicitudes.add(motivo);
                }
                solicitudes.add(0, new Solicitud());
                solicitudes.get(0).setMotivo("Sin Anticipo");
                adapterASP = new ArrayAdapter<Solicitud>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, solicitudes);
                adapterASP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adelantoSP.setGravity(View.TEXT_ALIGNMENT_CENTER);
                adelantoSP.setAdapter(adapterASP);
                if (isEditar) {
                    //si el gasto no tiene un id atisipo se pondra sin antisipo de lo contrario se busccara el atnticipo
                    int spinnerPositionA = adapterASP.getPosition(solicitudes.get(0));
                    adelantoSP.setSelection(spinnerPositionA);
                }

            }
        });

        presenter.connectCategorias().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                categorias.clear();
                for (DocumentSnapshot document : snapshots.getDocuments()) {
                    String categoria = (String) document.get("descripcion");
                    categorias.add(categoria);
                }
                Collections.sort(categorias);
                categorias.add(0, "Selecciona una categoria");
                adapterCSP = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categorias);
                adapterCSP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categoriaSP.setGravity(View.TEXT_ALIGNMENT_CENTER);
                categoriaSP.setAdapter(adapterCSP);
                if (isEditar) {
                    int spinnerPosition = adapterCSP.getPosition(gasto.getCategoria());
                    categoriaSP.setSelection(spinnerPosition);
                }
            }
        });

    }

    /*
     * Agregar los listeners a los componentes
     * Se recomienda hacer un metodo por cada lógica de un listener
     */

    public void setListeners() {
        pictureBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        recurrenteCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recurrenteCB.isChecked()) {
                    esRecurrente = true;
                    frecuenciaLL.setVisibility(View.VISIBLE);
                } else {
                    esRecurrente = false;
                    frecuenciaLL.setVisibility(View.GONE);
                }
            }
        });

        fechaReciboET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });
        fechaIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPicker();
            }
        });
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        comentarioET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() < 120) {
                    contadorTV.setText(String.valueOf(s.length() + 1) + "/120");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /*
     * Agregar la logica para cargar la imagen e interactuar con ella (zoom)
     */
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            pictureBTN.setEnabled(false);
            Toast.makeText(this, "Por favor activa los permisos", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            file = Uri.fromFile(presenter.getOutputMediaFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, file);
            startActivityForResult(intent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Glide.with(this).load(file).into(pictureIV);
                showProgress("Analizando Imagen");
                processImagen();
            }
        }
    }

    private void processImagen() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (Looper.myLooper()==null)
                    Looper.prepare();
                try {
                    return presenter.processImagen(file);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                Looper.loop();
                return null;
            }
        }.execute();
    }

    /*
     * Implementar la logica para el boton de guardar
     * Este boton debe mandar al presentador el nuevo gasto y notificar a la vista
     */
    public void sendData() {
        String nombreP = proveedorET.getText().toString();
        String montoG = importeET.getText().toString();
        String categoria = (String) categoriaSP.getSelectedItem();
        String moneda = (String) monedaSP.getSelectedItem();
        String comentario = comentarioET.getText().toString();
        Solicitud anticipo = null;
        String periodo = "";
        if (adelantoSP.getSelectedItemPosition() != 0) {
            anticipo = (Solicitud) adelantoSP.getSelectedItem();
        }
        if (esRecurrente) {
            if (frecuenciaSP.getSelectedItemPosition() != 0) {
                periodo = (String) frecuenciaSP.getSelectedItem();
            }
        }
        presenter.sendGasto(nombreP, fechaG, montoG, file, anticipo, categoria, comentario, esRecurrente, periodo, moneda, "REGISTRADO");
    }

    private void showProgress(String texto) {
        wProgress.setTitle(texto);
        wProgress.setMessage("Por favor espere");
        wProgress.setCancelable(false);
        wProgress.setIndeterminate(true);
        wProgress.show();
    }

    /*
     *Realizar la lógica que requiera el checkbox para gastos recurrentes
     */
    public void comboRecurrente() {
    }

    private void showDataPicker() {
        DatePickerDialog recogerFecha = new DatePickerDialog(AddGastoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                //Antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? "0" + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10) ? "0" + String.valueOf(mesActual) : String.valueOf(mesActual);
                fechaG = anio + "-" + mesFormateado + "-" + diaFormateado;
                fechaReciboET.setText(diaFormateado + "/" + mesFormateado + "/" + anio);
            }
        }, anio, mes, dia);
        recogerFecha.show();
    }


    @Override
    public void displayTicketResults(String amount, String date) {
        if (!amount.equals("")) {
            importeET.setText(amount);
            wProgress.dismiss();
        }
        if (!date.equals("")) {
            fechaReciboET.setText(date);
            wProgress.dismiss();
        }
        wProgress.dismiss();
    }

    @Override
    public void changeActivity() {
        wProgress.dismiss();
        finish();
    }

    @Override
    public void setError(Integer type, String text) {
        switch (type) {
            case 0:
                fechaReciboET.setError(text);
                break;
            case 1:
                proveedorET.setError(text);
                break;
            case 2:
                importeET.setError(text);
                break;
            case 3:
                categoriaTV.setError(text);
                break;
            case 4:
                pictureBTN.setError(text);
                break;
            case 5:
                frecuenciaTV.setError(text);
                break;
            case 6:
                AlertDialog.Builder builder = new AlertDialog.Builder(AddGastoActivity.this);
                builder.setMessage(text).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //algo
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void displayProgress(Boolean isDisplayed, String texto) {
        if (isDisplayed) {
            showProgress(texto);
        } else {
            wProgress.dismiss();
        }
    }
    private class DownLoadImageTask extends AsyncTask<String,Void, Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}

