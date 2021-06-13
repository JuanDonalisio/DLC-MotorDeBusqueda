package Api;

import Persistencia.Gestor;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ApiBuscador {

    @Inject Gestor gestor;

    //@GET
    //@Path("/buscar")
    //public Response obtenerTodos(@QueryParam("busqueda") String busqueda) {
    //Response.ok(gestor.obtenerTodos()).build();

    //}

    //@POST
    //@Path("/buscar/{nombreSeleccionado}")
    //public Response buscarDocumento(@QueryParam("seleccionado") String seleccionado) {

    //}

}
