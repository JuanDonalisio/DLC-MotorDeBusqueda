package Menu;

import Indexer.Indice;
import Persistencia.Consultas;
import java.util.*;

public class Buscador {

    private static HashMap vocabulario;

    public Buscador(HashMap vocabulario) {
        this.vocabulario = vocabulario;
    }

    public static HashMap<String, Integer> calificacionPara(String ingresar) {

        Consultas cons = new Consultas();

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
                j++;
            }
        }
        documentosRelevantes = Indice.sortByValue(documentosRelevantes);
        return documentosRelevantes;
    }

    public HashMap<String, Double> buscador(String busqueda, int R) {
        HashMap docRelevantes = calificacionPara(busqueda);
        LinkedHashMap<String, Double> subRelist = new LinkedHashMap<>();
        //List<String> listRel = new ArrayList<String>(docRelevantes.keySet());
        //List<String> subRelist = new ArrayList<String>(listRel.subList(0, R));

        Iterator it = docRelevantes.entrySet().iterator();
        int j=0;
        while (it.hasNext() && j < R) {
            Map.Entry pair = (Map.Entry) it.next();
            String doc = (String) pair.getKey();
            double peso = (double) pair.getValue();
            subRelist.put(doc, peso);
        }
        subRelist = Indice.sortByValue(subRelist);

        return subRelist;
    }

}
