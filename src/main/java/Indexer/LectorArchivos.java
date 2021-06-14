package Indexer;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class LectorArchivos {

    public static File[] obtenerArchivos() throws FileNotFoundException {
        File directory = new File("DocumentosTP1");
        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });
        return files;
    }

    public static String LeerArchivo(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(path)));
        StringBuilder lectura = new StringBuilder();
        while(sc.hasNext())
        {
            lectura.append(sc.nextLine());
            lectura.append("\n");
        }
        return lectura.toString();
    }

    public static void cargarArchivo(File file, HashMap vocabulario) throws FileNotFoundException {
        Indice ind = new Indice(vocabulario);
        ind.obtenerVocabularioYPosteo(file);
    }
}
