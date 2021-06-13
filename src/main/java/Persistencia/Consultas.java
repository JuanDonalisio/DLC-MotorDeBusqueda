package Persistencia;

import org.jvnet.fastinfoset.Vocabulary;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

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

    public static long cantidadDeDocumentos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();
        long cantidadDeDocumentos = (long) em2.createQuery("SELECT COUNT(distinct documento) FROM Posteo").getSingleResult();

        em2.close();
        em2.close();
        return cantidadDeDocumentos;
    }
}