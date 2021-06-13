package Persistencia;

import Menu.Buscador;

import java.io.File;
import java.util.HashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ApplicationScoped
public class Gestor {

    @PersistenceContext(unitName = "DLCTP")
    private EntityManager em2;

    public HashMap<String, Integer> obtenerDocumentos(String busqueda) {
        Buscador busq = new Buscador();
        return busq.calificacionPara(busqueda);
    }

    //public File buscarDocumento(String nombre) {
    //    File file = new File(nombre);
    //}
}
