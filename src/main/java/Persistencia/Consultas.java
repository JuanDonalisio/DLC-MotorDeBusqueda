package Persistencia;

import javax.persistence.*;
import java.io.File;
import java.util.*;

public class Consultas {

    /** Clase que permite realizar las consultas a la base de datos.
     * Se consulta la base de datos para traer el vocabulario a memoria y
     * se trae también el posteo*/


    @PersistenceContext(unitName = "DLCTP")

    public static HashMap<String, Vocabulario> obtenerTodos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();
        List<Vocabulario> listaDerogatory = em2.createQuery("select v from Vocabulario v ", Vocabulario.class).getResultList();
        HashMap vocFinal = new HashMap();

        for (Vocabulario i : listaDerogatory) vocFinal.put(i.getPalabra(),i);
        em2.close();
        em2.close();
        return vocFinal;
    }
    public static HashMap<String, Vocabulario> obtenerTodosPosteos() {
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

    /**Una vez procesado el vocabulario y el posteo debemos pasarlo
     *a la base de datos. Para ello usamos transacciones.
     */

    public void cargarVocabulario(HashMap vocabularioNuevo, HashMap vocabularioViejo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();

        Iterator it = vocabularioNuevo.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            Persistencia.Vocabulario unVocabulario = (Persistencia.Vocabulario) pair.getValue();
            Persistencia.Vocabulario otroVocabulario = new Persistencia.Vocabulario();

            //Cargar vocabulario
            if(vocabularioViejo.containsKey(pair.getKey())) {
                //Update para cuando se encuentra el elemento en el vocabulario

                otroVocabulario.setNr(unVocabulario.getNr());
                otroVocabulario.setTf(unVocabulario.getTf());

                String palabra = (String) pair.getKey();
                int nuevoNr = unVocabulario.getNr();
                int nuevoTf = unVocabulario.getTf();

                Query q = em.createQuery("SELECT v FROM Vocabulario v WHERE v.palabra = :palabraParametro", Vocabulario.class);
                q.setParameter("palabraParametro", palabra);
                otroVocabulario = (Vocabulario) q.getSingleResult();
                em.remove(otroVocabulario);
                otroVocabulario.setNr(nuevoNr);
                otroVocabulario.setTf(nuevoTf);
                em.persist(otroVocabulario);


            }else{
                //insert para cuando NO se encuentra el elemento en el vocabulario
                otroVocabulario.setPalabra(unVocabulario.getPalabra());
                otroVocabulario.setNr(unVocabulario.getNr());
                otroVocabulario.setTf(unVocabulario.getTf());
                em.persist(otroVocabulario);
            }
        }
        t.commit();
        em.close();
        emf.close();
    }
    public void cargarPosteo(File nameDoc, HashMap posteoNuevo) {
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf2.createEntityManager();
        EntityTransaction t2 = em2.getTransaction();
        t2.begin();

        String nombreDoc = nameDoc.getPath();

        HashMap posteoViejo = obtenerTodosPosteos();

        //Cargar Posteo
        Iterator it2 = posteoNuevo.entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry)it2.next();

            String aux = (String) pair.getKey();
            LinkedHashMap unPosteoQuestionMark = (LinkedHashMap) pair.getValue();
            int tfNuevo = (int) unPosteoQuestionMark.get(nombreDoc);

            if (posteoViejo.containsKey(aux)){
                LinkedHashMap posteoV = (LinkedHashMap) posteoViejo.get(aux);

                if (!(posteoV.containsKey(nombreDoc))){
                    Persistencia.Posteo otroPosteo = new Persistencia.Posteo();
                    otroPosteo.setPalabra(aux);
                    otroPosteo.setDocumento(nombreDoc);
                    otroPosteo.setTf(tfNuevo);
                    em2.persist(otroPosteo);
            }
            }else{
                Persistencia.Posteo otroPosteo = new Persistencia.Posteo();
                otroPosteo.setPalabra(aux);
                otroPosteo.setDocumento(nombreDoc);
                otroPosteo.setTf(tfNuevo);
                em2.persist(otroPosteo);
            }
        }
        t2.commit();
        em2.close();
        emf2.close();
    }
}