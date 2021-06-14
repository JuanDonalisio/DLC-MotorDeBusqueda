package Indexer;

import java.io.*;
import java.util.HashMap;
import java.util.List;
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

    public static void LeerArchivo(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                System.out.println(data);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo.");
            e.printStackTrace();
        }
    }

    public static void cargarArchivo(String path) throws FileNotFoundException {
        File file = new File(path);
        Indice.obtenerVocabularioYPosteo(file);

    }

}
