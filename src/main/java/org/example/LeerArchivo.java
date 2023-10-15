package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Lee un archivo a una lista de String
 */
public class LeerArchivo {
    String nombreArchivo;

    public LeerArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * Leer un archivo linea a linea
     * @return Lista de String por cada linea del archivo
     */
    public List<String> leer() {
        List<String> texto = null;

        try (var br = new BufferedReader(new FileReader(nombreArchivo))) {
            texto = br.lines().toList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error al leer el fichero \"" + nombreArchivo + "\"");
        } catch (IOException e) {
            System.err.println("Error de entrada salida");
        }
        return texto;
    }
}
