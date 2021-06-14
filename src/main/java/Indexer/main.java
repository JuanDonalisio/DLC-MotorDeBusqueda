package Indexer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class main {

    public static void main(String[] args) throws IOException {

        //File[] files = LectorArchivos.obtenerArchivos();
        /*for (int i = 0; i < files.length; i++){
            Indice.obtenerVocabularioYPosteo(files[i]);
        }*/
        LectorArchivos.cargarArchivo("DocumentosTP1/doctest.txt");

        //em.createNativeQuery("truncate table Vocabulario").executeUpdate();     -->Para borrar lo que hay en la BD tabla posteo
       //em.createNativeQuery("truncate table Posteo").executeUpdate();      -->Para borrar lo que hay en la BD tabla posteo




    }
}
