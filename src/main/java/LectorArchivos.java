import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class LectorArchivos {

    public static File[] obtenerArchivos() throws FileNotFoundException {
        File directory = new File("src/ArchivosTest");
        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });
        return files;
    }

}
