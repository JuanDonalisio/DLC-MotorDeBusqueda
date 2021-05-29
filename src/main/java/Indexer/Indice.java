package Indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;

public class Indice {

    public static void obtenerVocabularioYPosteo(File name, HashMap map, HashMap posteo) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader((name))));
        int aux;
        String nombreDoc = name.getPath();

        while (scanner.hasNext()) {
            String palabra = scanner.next().toLowerCase(Locale.ROOT).replaceAll("[^a-z]","");
            palabra = palabra.replaceAll("�", "");
            if (!palabra.isBlank()) {
                LinkedHashMap p1 = new LinkedHashMap();
                Vocabulario v1 = new Vocabulario(palabra.toLowerCase());

                //Obtiene el vocabulario
                if (map.containsKey(palabra.toLowerCase())) {
                    v1 = (Vocabulario) map.get(palabra.toLowerCase());
                    map.replace(palabra, v1);
                } else {
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
    }

    public static void obtenerNr(HashMap map, HashMap mapVoc){
        LinkedHashMap aux;

        Iterator it = mapVoc.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Vocabulario palabra = (Vocabulario) pair.getValue();
            String palabraString = palabra.getPalabra();
            aux = (LinkedHashMap) map.get(palabraString);
            palabra.setNr(aux.size());
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
        LinkedHashMap hashDesordenada = new LinkedHashMap();
        LinkedHashMap hashOrdenada = new LinkedHashMap();

         Iterator it = mapVoc.entrySet().iterator();
         while (it.hasNext()) {
             Map.Entry pair = (Map.Entry)it.next();
             Vocabulario palabra = (Vocabulario) pair.getValue();
             String palabraString = palabra.getPalabra();
             hashDesordenada = (LinkedHashMap) map.get(palabraString);
             hashOrdenada = sortByValue(hashDesordenada);
             map.replace(palabraString, hashOrdenada);

             Map.Entry par = (Map.Entry) hashOrdenada.entrySet().iterator().next();
             int frecuencia = (int) par.getValue();
             palabra.setMaxTf(frecuencia);
         }
    }
}