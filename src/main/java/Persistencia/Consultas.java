package Persistencia;

import Indexer.Indice;
import org.jvnet.fastinfoset.Vocabulary;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Consultas {

    /** Clase que permite realizar las consultas a la base de datos.
     * Se consulta la base de datos para traer el vocabulario a memoria y
     * se trae también el posteo*/


    @PersistenceContext(unitName = "DLCTP")

    public HashMap<String, Vocabulario> obtenerTodos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();
        List<Vocabulario> listaDerogatory = em2.createQuery("select v from Vocabulario v ", Vocabulario.class).getResultList();
        HashMap vocFinal = new HashMap();

        for (Vocabulario i : listaDerogatory) vocFinal.put(i.getPalabra(),i);
        em2.close();
        em2.close();
        return vocFinal;
    }
    public HashMap<String, Vocabulario> obtenerTodosPosteos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();
        List<Posteo> listaDerogatory = em2.createQuery("select p from Posteo p ORDER BY p.tf DESC", Posteo.class).getResultList();
        HashMap posteoFinal = new HashMap();

        for (Posteo i : listaDerogatory) {
            LinkedHashMap documentos = new LinkedHashMap();
            if (posteoFinal.containsKey(i.getPalabra())){
                documentos = (LinkedHashMap) posteoFinal.get(i.getPalabra());
                documentos.put(i.getDocumento(), i.getTf());
            }else{
                documentos.put(i.getDocumento(), i.getTf());
                posteoFinal.put(i.getPalabra(), documentos);
            }
        }

        em2.close();
        em2.close();
        return posteoFinal;
    }

    public long cantidadDeDocumentos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();

        long cantidadDeDocumentos = (long) em2.createQuery("SELECT COUNT(distinct documento) FROM Posteo").getSingleResult();

        em2.close();
        em2.close();
        return cantidadDeDocumentos;
    }

    public void cargarDocumento(File file, HashMap vocabularioNuevo, HashMap posteoNuevo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();

        HashMap vocabularioViejo = obtenerTodos();
        HashMap posteoViejo = obtenerTodosPosteos();

        Iterator it = vocabularioViejo.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            Indexer.Vocabulario unVocabulario = (Indexer.Vocabulario) pair.getValue();
            Persistencia.Vocabulario otroVocabulario = new Persistencia.Vocabulario();

            //Cargar vocabulario
            if(vocabularioViejo.containsKey(pair.getKey())) {
                //Update para cuando se encuentra el elemento en el vocabulario
                em.find(Vocabulario.class, otroVocabulario.getPalabra());
                otroVocabulario.setNr(unVocabulario.getNr());
                otroVocabulario.setTf(unVocabulario.getMaxTf());

                //Query q = em.createQuery



            }else{
                //insert para cuando NO se encuentra el elemento en el vocabulario
                otroVocabulario.setPalabra(unVocabulario.getPalabra());
                otroVocabulario.setNr(unVocabulario.getNr());
                otroVocabulario.setTf(unVocabulario.getMaxTf());
                em.persist(otroVocabulario);
            }
        }
        t.commit();
        em.close();
        emf.close();
    }

    public void modificarVocabularioYPosteo(HashMap vocabulario, HashMap posteo){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();


        em2.createQuery("");

        em2.close();
        em2.close();
    }

    public void cargarVocabularioYPosteo(HashMap vocabulario, HashMap posteo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        int i = 0;

        //Cargar Vocabulario
        Iterator it = vocabulario.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Indexer.Vocabulario unVocabulario = (Indexer.Vocabulario) pair.getValue();
            Persistencia.Vocabulario otroVocabulario = new Persistencia.Vocabulario();
            otroVocabulario.setPalabra(unVocabulario.getPalabra());
            otroVocabulario.setNr(unVocabulario.getNr());
            otroVocabulario.setTf(unVocabulario.getMaxTf());
            em.persist(otroVocabulario);
            /*if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
            i++;*/
        }

        //Cargar Posteo
        Iterator it2 = posteo.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry)it2.next();
            LinkedHashMap unPosteoQuestionMark = (LinkedHashMap) pair.getValue();
            String aux = (String) pair.getKey();
            Iterator it3 = unPosteoQuestionMark.entrySet().iterator();
            while (it3.hasNext()) {
                Map.Entry pair2 = (Map.Entry)it3.next();
                Persistencia.Posteo otroPosteo = new Persistencia.Posteo();
                otroPosteo.setPalabra(aux);
                otroPosteo.setDocumento((String) pair2.getKey());
                otroPosteo.setTf((Integer) pair2.getValue());
                em.persist(otroPosteo);
            }
            /*if (i % 50 == 0) {
                em.flush();
                em.clear();
            }
            i++;*/
        }
        t.commit();
        em.close();
        emf.close();
    }
}