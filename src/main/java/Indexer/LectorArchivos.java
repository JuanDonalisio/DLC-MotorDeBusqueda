package Indexer;

import java.io.*;

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

}
