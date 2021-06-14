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

    public static String LeerArchivo(String path) {
        StringBuilder data = null;
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
                //System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo.");
            e.printStackTrace();
        }
        return data.toString();
    }

    public static void cargarArchivo(File file, HashMap vocabulario) throws FileNotFoundException {
        Indice ind = new Indice(vocabulario);
        ind.obtenerVocabularioYPosteo(file);
    }

}
