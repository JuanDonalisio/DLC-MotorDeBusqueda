package Indexer;

import Persistencia.Consultas;
import Persistencia.Vocabulario;

import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;

public class Indice {

    private static HashMap vocabulario;

    public Indice(HashMap vocabulario) {
        this.vocabulario = vocabulario;
    }

    public void obtenerVocabularioYPosteo(File name) throws FileNotFoundException {
        Consultas consulta = new Consultas();

        Scanner scanner = new Scanner(new BufferedReader(new FileReader((name))));
        int aux;
        String nombreDoc = name.getPath();

        HashMap map = new HashMap();
        HashMap posteo = new HashMap();

        while (scanner.hasNext()) {
            String palabra = scanner.next().toLowerCase(Locale.ROOT).replaceAll("[^a-z]","");
            palabra = palabra.replaceAll("�", "");
            if (!palabra.isBlank()) {
                LinkedHashMap p1 = new LinkedHashMap();
                //Persistencia.Vocabulario v1 = new Persistencia.Vocabulario

                if (!(map.containsKey(palabra.toLowerCase()))) {
                    Persistencia.Vocabulario v1 = new Persistencia.Vocabulario();
                    v1.setPalabra(palabra);
                    map.put(palabra, v1);
                }

                //Obtiene el posteo
                if (posteo.containsKey(palabra.toLowerCase())) {
                    p1 = (LinkedHashMap) posteo.get(palabra.toLowerCase());
                    if (p1.containsKey(nombreDoc)) {
                        aux = (Integer) p1.get(nombreDoc);
                        p1.replace(nombreDoc, aux, aux + 1);
                    } else {
                        p1.put(nombreDoc, 1);
                    }
                } else {
                    p1.put(nombreDoc, 1);
                    posteo.put(palabra.toLowerCase(), p1);
                }
            }
        }

        obtenerNr(posteo, map);
        obtenerMaxTF(posteo, map);

        consulta.cargarVocabulario(map, vocabulario);
        consulta.cargarPosteo(name, posteo);
    }

    public static void obtenerNr(HashMap map, HashMap mapVoc){
        LinkedHashMap documentos;

        Consultas consulta = new Consultas();

        Iterator it = mapVoc.entrySet().iterator();
        while (it.hasNext()) {
            int aux = 0;
            Map.Entry pair = (Map.Entry)it.next();
            Persistencia.Vocabulario palabra = (Persistencia.Vocabulario) pair.getValue();
            String palabraString = palabra.getPalabra();

            HashMap posteosViejo = consulta.obtenerTodosPosteos();

            if(posteosViejo.containsKey(palabraString)){
                documentos = (LinkedHashMap) posteosViejo.get(palabraString);
                aux = documentos.size();
            }
            aux +=1;
            palabra.setNr(aux);
        }
    }

    public static <K, V extends Comparable<V>> LinkedHashMap<K, V> sortByValue(HashMap<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());
        Collections.reverse(list);

        LinkedHashMap<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void obtenerMaxTF(HashMap map, HashMap mapVoc){
        LinkedHashMap hashDesordenada;
        LinkedHashMap hashOrdenada;

        Consultas consulta = new Consultas();
        Iterator it = mapVoc.entrySet().iterator();
        while (it.hasNext()) {


            Map.Entry pair = (Map.Entry)it.next();
            Persistencia.Vocabulario palabra = (Persistencia.Vocabulario) pair.getValue();
            String palabraString = palabra.getPalabra();

            hashDesordenada = (LinkedHashMap) map.get(palabraString);
            hashOrdenada = sortByValue(hashDesordenada);
            map.replace(palabraString, hashOrdenada);

            Map.Entry par = (Map.Entry) hashOrdenada.entrySet().iterator().next();
            int frecuencia = (int) par.getValue();

            if(vocabulario.containsKey(palabraString)){
                Persistencia.Vocabulario palabra2 = (Persistencia.Vocabulario) vocabulario.get(palabraString);
                if(frecuencia < palabra2.getTf()){
                    frecuencia = palabra2.getTf();
                }
            }
            palabra.setTf(frecuencia);
         }
    }
}