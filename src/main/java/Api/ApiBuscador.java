package Api;

import Indexer.Indice;
import Indexer.Vocabulario;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class ApiBuscador {


    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("DLCTP");

    public void calificacionPara(HashMap vocabulario, HashMap posteo, Integer numeroDocumentos) {
        String ingresar = new String(String.valueOf(System.in));

        String[] palabras = ingresar.split(" ");
        LinkedHashMap documentosRelevantes = new LinkedHashMap();

        for (int i = 0; i < palabras.length; i++) {

            Indexer.Vocabulario palabra = (Vocabulario) vocabulario.get(palabras[i]);
            LinkedHashMap documentosPalabra = (LinkedHashMap) posteo.get(palabra);


            int j = 0;
            Iterator it = documentosPalabra.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                int tf = (int) pair.getValue();


                //Idf es logaritmo de N/nR
                int nr = documentosPalabra.size();
                double idf = Math.log(numeroDocumentos / nr);

                double peso = (tf * idf);
                String nombreDoc = (String) pair.getKey();

                if (documentosRelevantes.containsKey(nombreDoc)) {
                    Integer pesoViejo = (Integer) documentosRelevantes.get(nombreDoc);
                    documentosRelevantes.replace(nombreDoc, pesoViejo, pesoViejo + peso);
                } else {
                    documentosRelevantes.put(nombreDoc, peso);
                }

                j++;
                if (j == 10) {
                    break;
                }
                //veamos estlo con jojs limpitos

            }
        }
        documentosRelevantes = Indice.sortByValue(documentosRelevantes);


    }


















}
