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

        File[] files = LectorArchivos.obtenerArchivos();
        HashMap<String, LinkedHashMap<String, Integer>> posteo = new HashMap<>();
        HashMap<String, Vocabulario > vocabulario = new HashMap<>();

        for (int i = 0; i < files.length; i++){
            Indice.obtenerVocabularioYPosteo(files[i]);
        }

        /**Una vez procesado el vocabulario y el posteo debemos pasarlo
         *a la base de datos. Para ello usamos transacciones.
         */


        //Escritura del vocabulario
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();

        //em.createNativeQuery("truncate table Vocabulario").executeUpdate();     -->Para borrar lo que hay en la BD tabla posteo
       //em.createNativeQuery("truncate table Posteo").executeUpdate();      -->Para borrar lo que hay en la BD tabla posteo




    }
}
