package Persistencia;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
public class VocabularioEnMemoria {
    HashMap vocFinal;

    public VocabularioEnMemoria() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();
        List<Vocabulario> listaDerogatory = em2.createQuery("select v from Vocabulario v ", Vocabulario.class).getResultList();
        vocFinal = new HashMap();

        for (Vocabulario i : listaDerogatory) vocFinal.put(i.getPalabra(),i);
        em2.close();
        em2.close();
    }
    //Gracias
    public HashMap getVocFinal() {
        return vocFinal;
    }

}
