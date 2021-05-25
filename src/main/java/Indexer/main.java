package Indexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class main {

    public static void main(String[] args) throws FileNotFoundException {

        File[] files = LectorArchivos.obtenerArchivos();
        HashMap<String, LinkedHashMap<String, Integer>> posteo = new HashMap<>();
        HashMap<String, Vocabulario > vocabulario = new HashMap<>();

        for (int i = 0; i < 2; i++){
            Indice.obtenerVocabularioYPosteo(files[i],(HashMap) vocabulario, posteo);
        }
        //ACA se pone el coso que pasa a la bd



        Iterator it = posteo.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
        }
    }
}
