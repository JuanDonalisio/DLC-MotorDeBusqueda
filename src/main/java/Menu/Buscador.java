package Menu;

import Api.ApiBuscador;
import Indexer.Indice;
import Indexer.LectorArchivos;
import Indexer.Vocabulario;
import Persistencia.Consultas;

import java.util.*;

public class Buscador {

    public static HashMap<String, Integer> calificacionPara(String ingresar) {

        Consultas cons = new Consultas();

        HashMap vocabulario = cons.obtenerTodos();
        HashMap posteo = cons.obtenerTodosPosteos();
        long numeroDocumentos = cons.cantidadDeDocumentos();

        String[] palabras = ingresar.split(" ");
        LinkedHashMap documentosRelevantes = new LinkedHashMap();

        for (String i: palabras){

            double idf = 0;

            if (vocabulario.containsKey(i)) {
                Persistencia.Vocabulario palabra = (Persistencia.Vocabulario) vocabulario.get(i);
                int nr = palabra.getNr();
                idf = Math.log(numeroDocumentos / nr);
            } else {
                continue;
            }

            LinkedHashMap documentosPalabra = (LinkedHashMap) posteo.get(i);

            int j = 0;
            Iterator it = documentosPalabra.entrySet().iterator();
            while (it.hasNext() && j < 10) {
                Map.Entry pair = (Map.Entry) it.next();
                int tf = (int) pair.getValue();

                //Idf es logaritmo de N/nR
                //int nr = documentosPalabra.size();

                double peso = (tf * idf);
                String nombreDoc = (String) pair.getKey();

                if (documentosRelevantes.containsKey(nombreDoc)) {
                    Integer pesoViejo = (Integer) documentosRelevantes.get(nombreDoc);
                    documentosRelevantes.replace(nombreDoc, pesoViejo, pesoViejo + peso);
                } else {
                    documentosRelevantes.put(nombreDoc, peso);
                }
                //veamos estlo con jojs limpitos
                j++;
            }
        }
        documentosRelevantes = Indice.sortByValue(documentosRelevantes);
        return documentosRelevantes;
    }

    public static void buscador(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("¿Que desea buscar?\n");
        String busqueda = scanner.nextLine();
        System.out.println(busqueda);

        LectorArchivos lector = new LectorArchivos();

        int R = 5;
        int j = 1;

        HashMap docRelevantes = calificacionPara(busqueda);

        if (docRelevantes.size() !=0) {
            System.out.println("Estos son los diez documentos más relevantes:\n");

            List<String> listRel = new ArrayList<String>(docRelevantes.keySet());
            //List<Double> listRel2 = new ArrayList<Double>(docRelevantes.values());
            List<String> subRelist = new ArrayList<String>(listRel.subList(0, R));
            //List<Double> subRelist2 = new ArrayList<Double>(listRel2.subList(0, R));

            for (String i : subRelist) {
                System.out.println("Numero: " + j + " | " + i + "\n");
                j++;
            }

            // esto es para mostrar los pesos
            /*
            for ( double i : subRelist2) {
                String s = String.valueOf(i);
                System.out.println("Numero: " + j + " | " + s + "\n");
                j++;
            }*/

            System.out.println("Ingrese el numero del documento a mostrar: ");
            int aux = scanner.nextInt();
            if (R < aux) {
                System.out.println("El numero ingresado no es válido. Por favor, ingrese un número válido.");
                aux = scanner.nextInt();
            }

            lector.ReadFile(subRelist.get(aux-1));

        }else{
            System.out.println("No se encontraron las palabras en ningun documento\n" );
        }

}

    public static void main(String[] args) {
        buscador();
    }

}
