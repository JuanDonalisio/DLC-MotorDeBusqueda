package Persistencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class Consultas {

    /** Clase que permite realizar las consultas a la base de datos.
     * Se consulta la base de datos para traer el vocabulario a memoria y
     * se trae también el posteo*/


    @PersistenceContext(unitName = "DLCTP")

    public List<Vocabulario> obtenerTodos() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");
        EntityManager em2 = emf.createEntityManager();
        List<Vocabulario> vocFinal = em2.createQuery("select v from Vocabulario v ", Vocabulario.class).getResultList();
        em2.close();
        em2.close();
        return vocFinal;
    }
}