package com.company.spsolutions.gestiongasto.Gastos;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by coralRodriguez on 28/03/19.
 */
public class GastoService {
    /* Esta clase se encarga de conectarse a firebase para acceder a los datos de los gastos
     *
     */
    FirebaseFirestore database;
    CollectionReference dbSolicitud;
    FirebaseStorage storage;
    StorageReference storageReference;

    /*
     * 1. Realizar la conexcion a firebase y a la base de datos necesaria
     */
    public CollectionReference connectFirebase() {
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("gastos");
        return dbSolicitud;
    }
    public CollectionReference connectCategorias() {
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("categorias");
        return dbSolicitud;
    }

    public CollectionReference connectGastosDB(){
        database = FirebaseFirestore.getInstance();
        dbSolicitud = database.collection("gastos");
        return dbSolicitud;
    }

    public StorageReference connectStorage(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        return  storageReference;
    }
    /*
     * Se realiza la lógica  añadir un nuevo gasto
     */
    public void guardarGasto() {
    }

    /*
     * Este método servira para traer los datos de los gastos
     */
    public void obtenerGasto() {
    }

}
