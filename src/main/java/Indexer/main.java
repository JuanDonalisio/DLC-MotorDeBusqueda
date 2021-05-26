package Indexer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class main {

    public static void main(String[] args) throws IOException {

        File[] files = LectorArchivos.obtenerArchivos();
        HashMap<String, LinkedHashMap<String, Integer>> posteo = new HashMap<>();
        HashMap<String, Vocabulario > vocabulario = new HashMap<>();

        for (int i = 0; i < files.length; i++){
            Indice.obtenerVocabularioYPosteo(files[i], vocabulario, posteo);
        }

        /**Una vez procesado el vocabulario y el posteo debemos pasarlo
         *a la base de datos. Para ello usamos transacciones.
         */

        //Escritura del vocabulario
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();

        em.createNativeQuery("truncate table Vocabulario").executeUpdate();
        Iterator it = vocabulario.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Vocabulario unVocabulario = (Vocabulario) pair.getValue();
            Persistencia.Vocabulario otroVocabulario = new Persistencia.Vocabulario();
            otroVocabulario.setPalabra(unVocabulario.getPalabra());
            otroVocabulario.setNr(unVocabulario.getNr());
            otroVocabulario.setTf(unVocabulario.getMaxTf());
            em.persist(otroVocabulario);
            int i=0;
            if (i % 50 == 0) {
                em.flush();
                em.clear();
                i++;
            }
        }
        em.createNativeQuery("truncate table Posteo").executeUpdate();

        Iterator it2 = posteo.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry)it2.next();
            LinkedHashMap unPosteoQuestionMark = (LinkedHashMap) pair.getValue();
            Persistencia.Posteo otroPosteo = new Persistencia.Posteo();
            String aux = (String) pair.getKey();

            Iterator it3 = unPosteoQuestionMark.entrySet().iterator();
                while (it3.hasNext()) {
                    Map.Entry pair2 = (Map.Entry)it3.next();
                    //File aux = (File) pair2.getKey();
                    //String content = Files.readString(Paths.get(aux).-);
                    otroPosteo.setPalabra(aux);
                    otroPosteo.setDocumento((String) pair2.getKey());
                    otroPosteo.setTf((Integer) pair2.getValue());
                    em.persist(otroPosteo);
                }
            int i=0;
            if (i % 50 == 0) {
                em.flush();
                em.clear();
                i++;
            }
        }
        t.commit();
        em.close();
        emf.close();
    }
}
