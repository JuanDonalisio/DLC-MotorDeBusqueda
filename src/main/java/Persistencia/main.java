package Persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class main {
    public static void main(String[] args) {
        // Escritura
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.createNativeQuery("truncate table Vocabulario").executeUpdate();
        Vocabulario testVocabulario = new Vocabulario();
        testVocabulario.setPalabra("Patas");
        testVocabulario.setNr(5);
        testVocabulario.setTf(7);
        em.persist(testVocabulario);
        t.commit();
        em.close();
        emf.close();


    }
}
